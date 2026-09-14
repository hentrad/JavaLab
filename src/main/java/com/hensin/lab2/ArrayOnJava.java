package com.hensin.lab2;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ArrayOnJava {

    private int[] array;
    
    public ArrayOnJava() {}
    
    public ArrayOnJava(int[] newArray) {
        this.array = newArray;
    }
    
    public void inputArray() {
        Scanner scanner = new Scanner(System.in);
    
        int size = 0;
        do {
            try {
                System.out.print("Размер массива: ");
                size = scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.next();
            }
        } while (size <= 0);
        
        array = new int[size];
        
        System.out.println("Введите " + array.length + " целых чисел:");
        for (int i = 0; i < array.length; i++) {
            boolean valid = false;
            do {
                try {
                    System.out.print("[" + i + "]: ");
                    array[i] = scanner.nextInt();
                    valid = true;
                } catch (InputMismatchException e) {
                    scanner.next();
                }
            } while (!valid);
        }
        scanner.close();
    }
    
    public void printArray() {
        System.out.println(Arrays.toString(array));
    }
    
    public void sortArray() {
        Arrays.sort(array);
    }
    
    public int countNum() {
        int count = 0;
        for (int num : array) {
            if (num % 3 == 0) {
                count++;
            }
        }
        return count;
    }
    
    public int[] getArray() {
        return array;
    }
    
    public int getLength() {
        return array.length;
    }
}