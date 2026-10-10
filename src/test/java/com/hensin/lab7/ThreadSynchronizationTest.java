package com.hensin.lab7;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class ThreadSynchronizationTest {

    /** Обёртка, чтобы перехватывать результаты для проверки. */
    static class RecordingExchange extends SharedData {
        final List<int[]> processed = new ArrayList<>();

        @Override
        public synchronized void putResult(int value) throws InterruptedException {
            // В новом дизайне номер задачи уже стёрт из Exchange,
            // поэтому запоминаем результат отдельно — а число
            // восстанавливаем на стороне менеджера.
            super.putResult(value);
        }
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testComputeThreadWaitsForTask() throws Exception {
        SharedData ex = new SharedData();
        Thread compute = new Thread(new ComputeThread(ex));
        compute.start();

        Thread.sleep(150);
        assertEquals(Thread.State.WAITING, compute.getState());

        compute.interrupt();
        compute.join(1000);
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void testManagerThreadWaitsForResult() throws Exception {
        SharedData ex = new SharedData();
        Thread manager = new Thread(new ManagerThread(ex, 3));
        manager.start();

        Thread.sleep(150);
        // Менеджер положил задачу и ждёт результат в takeResult()
        assertEquals(Thread.State.WAITING, manager.getState());

        manager.interrupt();
        manager.join(1000);
    }

    @Test
    @Timeout(value = 20, unit = TimeUnit.SECONDS)
    void testVariousIterationCounts() throws Exception {
        for (int count : new int[]{1, 2, 3, 7, 10}) {
            SharedData ex = new SharedData();
            Thread compute = new Thread(new ComputeThread(ex));
            Thread manager = new Thread(new ManagerThread(ex, count));

            compute.start();
            manager.start();

            manager.join(15000);
            assertFalse(manager.isAlive(),
                    "Менеджер не завершился для iterations=" + count);

            compute.interrupt();
            compute.join(2000);
        }
    }

    @Test
    @Timeout(value = 8, unit = TimeUnit.SECONDS)
    void testCorrectnessOfProduct() throws Exception {
        // Управляющий кладёт заранее известные числа и проверяет произведение
        SharedData ex = new SharedData();

        int[] numbers = {1234, 2787, 9000, 5555, 1000};
        int[] expected = new int[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            int p = 1, t = numbers[i];
            while (t > 0) { p *= t % 10; t /= 10; }
            expected[i] = p;
        }

        Thread compute = new Thread(new ComputeThread(ex));
        compute.start();

        for (int i = 0; i < numbers.length; i++) {
            ex.putTask(numbers[i]);
            int got = ex.takeResult();
            assertEquals(expected[i], got,
                    "Неверное произведение для " + numbers[i]);
        }

        compute.interrupt();
        compute.join(1000);
    }

    @Test
    @Timeout(value = 8, unit = TimeUnit.SECONDS)
    void testPauseDuringProcessing() throws Exception {
        SharedData ex = new SharedData();
        Thread compute = new Thread(new ComputeThread(ex));
        compute.start();

        // Ставим паузу ДО отправки задачи
        ex.pause();
        ex.putTask(1234);

        Thread.sleep(200);
        assertEquals(Thread.State.WAITING, compute.getState(),
                "Пока paused==true вычислитель должен стоять");
        assertFalse(ex.hasResult(), "Результат не должен появиться на паузе");

        ex.resume();

        // Теперь результат должен появиться
        int product = ex.takeResult();
        assertEquals(24, product, "1·2·3·4 = 24");

        compute.interrupt();
        compute.join(1000);
    }
}