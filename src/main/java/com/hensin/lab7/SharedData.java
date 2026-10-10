package com.hensin.lab7;

public class SharedData {

    private Integer task = null;
    private Integer result = null;
    private boolean paused = false;

    public synchronized void putTask(int number) throws InterruptedException {
        while (task != null) {
            wait();
        }
        task = number;
        notifyAll();
    }

    public synchronized int takeTask() throws InterruptedException {
        while (task == null || paused) {
            wait();
        }
        int value = task;
        task = null;
        notifyAll();
        return value;
    }

    public synchronized void putResult(int value) throws InterruptedException {
        while (result != null) {
            wait();
        }
        result = value;
        notifyAll();
    }

    public synchronized int takeResult() throws InterruptedException {
        while (result == null) {
            wait();
        }
        int value = result;
        result = null;
        notifyAll();
        return value;
    }

    public synchronized void pause() {
        paused = true;
    }

    public synchronized void resume() {
        paused = false;
        notifyAll();
    }

    public synchronized boolean hasTask()   { return task != null; }
    public synchronized boolean hasResult() { return result != null; }
    public synchronized boolean isPaused()  { return paused; }
}