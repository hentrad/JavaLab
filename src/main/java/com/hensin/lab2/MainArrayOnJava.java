package com.hensin.lab2;

public class MainArrayOnJava {
        public static void main(String[] args) {
        ArrayOnJava ops = new ArrayOnJava();
        ops.inputArray();
        
        System.out.println("Исходный массив:");
        ops.printArray();
        
        int count = ops.countNum();
        System.out.println("числа % 3 == 0: " + count);
        
        ops.sortArray();
        System.out.println("Отсортированный массив:");
        ops.printArray();
    }
}
