package com.hensin.lab2;

import com.hensin.LabException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ArrayOnJava {

    private int[] array;

    public ArrayOnJava() {}

    public ArrayOnJava(int[] newArray) throws LabException {
        if (newArray == null || newArray.length == 0) {
            throw new LabException("Массив пустой или null");
        }
        this.array = newArray;
    }

    public void inputArray(Scanner scanner) throws LabException {
        boolean valid = false;
        int size = 0;
        do {
            try {
                System.out.print("Размер массива: ");
                size = scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.next();
            }
        } while (size <= 0);

        if (size <= 0) {
            throw new LabException("Размер должен быть > 0, получено: " + size);
        }
        if (size > 100_000) {
            throw new LabException("Слишком большой размер: " + size);
        }

        array = new int[size];

        System.out.println("Введите " + array.length + " целых чисел:");
        for (int i = 0; i < array.length; i++) {
            valid = true;
            do {
                try {
                    System.out.print("[" + i + "]: ");
                    array[i] = scanner.nextInt();
                    valid = false;
                } catch (InputMismatchException e) {
                    scanner.next();
                }
            } while (valid);
        }
    }

    public void printArray() throws LabException {
        if (array == null) throw new LabException("Массив не инициализирован");
        System.out.println(Arrays.toString(array));
    }

    public void sortArray() throws LabException {
        if (array == null) throw new LabException("Массив не инициализирован");
        Arrays.sort(array);
    }

    public int countNum() throws LabException {
        if (array == null) throw new LabException("Массив не инициализирован");
        int count = 0;
        for (int num : array) {
            if (num % 3 == 0) count++;
        }
        return count;
    }

    public int[] getArray() { return array; }
    public int getLength() { return array == null ? 0 : array.length; }
}