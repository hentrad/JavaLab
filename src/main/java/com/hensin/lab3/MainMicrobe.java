package com.hensin.lab3;

public class MainMicrobe {
    public static void main(String[] args) {
        Microbe ops = new Microbe();
        ops.inputTime();
        
        long count = ops.countBacteria();
        System.out.println("Количество бактерий: " + count);
    }
}