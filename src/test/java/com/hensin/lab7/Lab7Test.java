package com.hensin.lab7;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class Lab7Test {

    @Test
    public void testProductOfDigitsNormal() {
        assertEquals(24, ComputeThread.productOfDigits(1234));
        assertEquals(120, ComputeThread.productOfDigits(5432));
        assertEquals(36, ComputeThread.productOfDigits(4321));
    }

    @Test
    public void testSharedDataMethodsSynchronized() {
        Method[] methods = SharedData.class.getDeclaredMethods();
        for (Method m : methods) {
            if (Modifier.isPublic(m.getModifiers())
                    && !Modifier.isStatic(m.getModifiers())) {
                assertTrue(Modifier.isSynchronized(m.getModifiers()),
                        "Метод " + m.getName() + "() в SharedData должен быть synchronized");
            }
        }
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testComputeThreadWaitsWhenNoData() throws Exception {
        SharedData shared = new SharedData();
        Thread t = new Thread(new ComputeThread(shared), "ComputeThread");
        t.start();

        Thread.sleep(300);

        assertEquals(Thread.State.WAITING, t.getState(),
                "Вычислительный поток должен быть в WAITING (вызван wait())");

        t.interrupt();
        t.join(1000);
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testManagerThreadWaitsWhenBusy() throws Exception {
        SharedData shared = new SharedData();

        synchronized (shared) {
            shared.setNumber(1234);
        }

        Thread t = new Thread(new ManagerThread(shared, 1), "ManagerThread");
        t.start();
        Thread.sleep(300);

        assertEquals(Thread.State.WAITING, t.getState(),
                "Управляющий поток должен быть в WAITING (вызван wait())");

        t.interrupt();
        t.join(1000);
    }

    @Test
    @Timeout(value = 8, unit = TimeUnit.SECONDS)
    public void testPauseBlocksComputation() throws Exception {
        SharedData shared = new SharedData();
        Thread t = new Thread(new ComputeThread(shared), "ComputeThread");
        t.start();

        shared.pause();

        synchronized (shared) {
            shared.setNumber(1234);
            shared.notify();
        }

        Thread.sleep(400);
        assertFalse(shared.isResultReady(),
                "Пока isPaused=true, вычисление выполняться не должно");

        shared.resume();

        synchronized (shared) {
            long start = System.currentTimeMillis();
            while (!shared.isResultReady()
                    && System.currentTimeMillis() - start < 3000) {
                shared.wait(200);
            }
        }

        assertTrue(shared.isResultReady(),
                "После resume() результат должен появиться");
        assertEquals(24, shared.getProduct());

        t.interrupt();
        t.join(1000);
    }

    @Test
    public void testSharedDataFlags() {
        SharedData shared = new SharedData();

        assertFalse(shared.isDataReady());
        assertFalse(shared.isResultReady());
        assertFalse(shared.isPaused());

        shared.consumeResult();
        assertFalse(shared.isResultReady());

        shared.pause();
        assertTrue(shared.isPaused());
        shared.resume();
        assertFalse(shared.isPaused());
    }

    @Test
    public void testManagerThreadCustomIterations() {
        SharedData shared = new SharedData();
        ManagerThread manager = new ManagerThread(shared, 12);
        assertEquals(12, manager.getIterations());
    }

    @Test
    public void testManagerThreadMinIterations() {
        SharedData shared = new SharedData();
        ManagerThread manager = new ManagerThread(shared, ManagerThread.MIN_ITERATIONS);
        assertEquals(ManagerThread.MIN_ITERATIONS, manager.getIterations());
    }

    @Test
    public void testManagerThreadMaxIterations() {
        SharedData shared = new SharedData();
        ManagerThread manager = new ManagerThread(shared, ManagerThread.MAX_ITERATIONS);
        assertEquals(ManagerThread.MAX_ITERATIONS, manager.getIterations());
    }

    @Test
    public void testManagerThreadRejectsNegative() {
        SharedData shared = new SharedData();
        assertThrows(IllegalArgumentException.class, () -> {
            new ManagerThread(shared, -3);
        });
    }

    @Test
    public void testManagerThreadRejectsTooBig() {
        SharedData shared = new SharedData();
        assertThrows(IllegalArgumentException.class, () -> {
            new ManagerThread(shared, ManagerThread.MAX_ITERATIONS + 1);
        });
    }

    @Test
    @Timeout(value = 15, unit = TimeUnit.SECONDS)
    public void testManagerAndComputeFiveIterations() throws Exception {
        SharedData shared = new SharedData();
        Thread compute = new Thread(new ComputeThread(shared), "ComputeThread");
        Thread manager = new Thread(new ManagerThread(shared, 5), "ManagerThread");

        compute.start();
        manager.start();

        manager.join(12000);
        compute.interrupt();
        compute.join(2000);

        assertFalse(shared.isDataReady(),
                "После завершения флаг isDataReady должен быть сброшен");
        assertFalse(shared.isResultReady(),
                "После завершения флаг isResultReady должен быть сброшен");
        assertFalse(compute.isAlive(),
                "Вычислительный поток должен быть остановлен");
    }

    @Test
    @Timeout(value = 20, unit = TimeUnit.SECONDS)
    public void testManagerAndComputeWithCustomIterations() throws Exception {
        SharedData shared = new SharedData();
        Thread compute = new Thread(new ComputeThread(shared), "ComputeThread");
        Thread manager = new Thread(new ManagerThread(shared, 3), "ManagerThread");

        compute.start();
        manager.start();

        manager.join(15000);
        compute.interrupt();
        compute.join(2000);

        assertFalse(compute.isAlive());
        assertFalse(shared.isDataReady());
        assertFalse(shared.isResultReady());
    }

    @Test
    @Timeout(value = 20, unit = TimeUnit.SECONDS)
    public void testManagerAndComputeSingleIteration() throws Exception {
        SharedData shared = new SharedData();
        Thread compute = new Thread(new ComputeThread(shared), "ComputeThread");
        Thread manager = new Thread(new ManagerThread(shared, 1), "ManagerThread");

        compute.start();
        manager.start();

        manager.join(10000);
        compute.interrupt();
        compute.join(2000);

        assertFalse(compute.isAlive());
        assertFalse(shared.isDataReady());
        assertFalse(shared.isResultReady());
    }
}