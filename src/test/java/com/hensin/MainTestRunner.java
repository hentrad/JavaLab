package com.hensin;

import java.lang.reflect.Method;

import com.hensin.lab1.BoxTest;
import com.hensin.lab2.ArrayOnJavaTest;
import com.hensin.lab3.MicrobeTest;

public class MainTestRunner {
    
    public static void main(String[] args) {
        
        runTests("lab1 (Box)", BoxTest.class);
        runTests("lab2 (ArrayOnJava)", ArrayOnJavaTest.class);
        runTests("lab3 (Microbe)", MicrobeTest.class);
    }
    
    private static void runTests(String labName, Class<?> testClass) {
        System.out.println("\n" + "-".repeat(50));
        System.out.println("Тесты для " + labName);
        System.out.println();
        
        try {
            Object testInstance = testClass.getDeclaredConstructor().newInstance();
            Method[] methods = testClass.getDeclaredMethods();
            int passed = 0;
            int failed = 0;
            
            for (Method method : methods) {

                if (method.isAnnotationPresent(org.junit.jupiter.api.Test.class)) {
                    try {
                        method.invoke(testInstance);
                        System.out.println(method.getName() + " - SUC");
                        passed++;
                    } catch (Exception e) {
                        System.out.println(method.getName() + " - FAIL");
                        
                        Throwable cause = e.getCause();
                        if (cause != null) {
                            System.out.println("Ошибка: " + cause.getMessage());
                        } else {
                            System.out.println("Ошибка: " + e.getMessage());
                        }
                        failed++;
                    }
                }
            }

            System.out.println();
            System.out.println("Пройдено " + passed + ", провалено " + failed);
            
        } catch (Exception e) {
            System.out.println("Ошибка при запуске тестов: " + e.getMessage());
            e.printStackTrace();
        }
    }
}