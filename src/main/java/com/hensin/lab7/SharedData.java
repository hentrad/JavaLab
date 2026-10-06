package com.hensin.lab7;

public class SharedData {
    private int number;
    private int product;
    private boolean isDataReady = false;
    private boolean isResultReady = false;
    private boolean isPaused = false;

    public synchronized void setNumber(int number) {
        this.number = number;
        this.isDataReady = true;
        this.isResultReady = false;
    }

    public synchronized int getNumber() {
        return number;
    }

    public synchronized void setProduct(int product) {
        this.product = product;
        this.isDataReady = false;
        this.isResultReady = true;
    }

    public synchronized int getProduct() {
        return product;
    }
    
    public synchronized void consumeResult() {
        this.isResultReady = false;
    }

    public synchronized boolean isDataReady() {
        return isDataReady;
    }

    public synchronized boolean isResultReady() {
        return isResultReady;
    }

    public synchronized void pause() {
        this.isPaused = true;
    }

    public synchronized void resume() {
        this.isPaused = false;
        notify();
    }

    public synchronized boolean isPaused() {
        return isPaused;
    }
}