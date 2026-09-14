package com.hensin.lab1;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Box {

    int ball;
    boolean isAlive;

    Box(int ball) {
        this.ball = ball;
        this.isAlive = false;
    }
    
        public void inputBox() {
        Scanner scanner = new Scanner(System.in);
        
        boolean valid = false;
        do {
            try {
                System.out.print("ball (целое число): ");
                ball = scanner.nextInt();
                valid = true;
            } catch (InputMismatchException e) {
                scanner.next();
            }
        } while (!valid);
        
        valid = false;
        do {
            try {
                System.out.print("isAlive (true/false): ");
                isAlive = scanner.nextBoolean();
                valid = true;
            } catch (InputMismatchException e) {
                scanner.next();
            }
        } while (!valid);
        
        scanner.close();
    }

    int add(int a) {
        return ball + a;
    }

    int add1(int a) {
        return ball / a;
    }

    int add2(int a) {
        return ball *= a;
    }

    int add3(int a) {
        return ball >> a;
    }

    int add4(int a) {
        return a >>>= ball;
    }

    boolean add5(int a) {
        return ball >= a;
    }

    boolean add6(boolean aa) {
        return isAlive | aa;
    }

    boolean add7(boolean aa) {
        return isAlive && aa;
    }

    boolean add8(boolean aa) {
        return isAlive != aa;
    }

    //приоритеры
    
    int pr1(int x, int b) {
        return ball + x * b;
    }

    int pr2(int x, int b) {
        return ball + x / b;
    }

    int pr3(int x) {
        return ball + (x >> 2);
    }

    boolean pr4(int x, boolean b) {
        return ball > x | b;
    }

    boolean pr5(int x, boolean b) {
        return (ball > x) && b;
    }

    //ассоциативнсти
    
    int as1(int c, int d) {
        return c + ball + d;
    }

    int as2(int c, int d) {
        return c / ball / d;
    }

    int as3(int c, int d) {
        return c >> ball >> d;
    }

    int as4(int c, int d) {
        return c | ball | d;
    }

    boolean as5(boolean c, boolean d, boolean e) {
        return c && e && d;
    }
}