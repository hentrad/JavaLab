package com.hensin.lab1;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.hensin.LabException;
import com.hensin.LabException.Code;

public class Box {

    int ball;
    boolean isAlive;

    public Box(int ball) {
        this.ball = ball;
        this.isAlive = false;
    }

public void inputBox(Scanner scanner) {
    boolean valid;
    do {
        try {
            System.out.print("ball целое положительное: ");
            ball = scanner.nextInt();
            valid = ball >= 0;
        } catch (InputMismatchException e) {
            scanner.next();
            valid = false;
        }
    } while (!valid);

    do {
        try {
            System.out.print("isAlive (true/false): ");
            isAlive = scanner.nextBoolean();
            valid = true;
        } catch (InputMismatchException e) {
            scanner.next();
            valid = false;
        }
    } while (!valid);
}

    public int getBall() { return ball; }
    public boolean isAlive() { return isAlive; }

    public int add(int a) {
        return ball + a;
    }

    public int add1(int a) throws LabException {
        if (a == 0) throw new LabException(Code.DIVIDE_BY_ZERO, "a");
        return ball / a;
    }

    public int add2(int a) {
        return ball *= a;
    }

    public int add3(int a) throws LabException {
        if (a < 0) throw new LabException(Code.NEGATIVE_SHIFT, a);
        return ball >> a;
    }

    public int add4(int a) {
        return a >>>= ball;
    }

    public boolean add5(int a) {
        return ball >= a;
    }

    public boolean add6(boolean aa) {
        return isAlive | aa;
    }

    public boolean add7(boolean aa) {
        return isAlive && aa;
    }

    public boolean add8(boolean aa) {
        return isAlive != aa;
    }

    //приоритеры
    
    int pr1(int x, int b) {
        return ball + x * b;
    }

    int pr2(int x, int b) throws LabException {
        if (b == 0) throw new LabException(Code.DIVIDE_BY_ZERO, b);
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

    int as2(int c, int d) throws LabException {
        if (ball == 0 || d == 0) throw new LabException(Code.DIVIDE_BY_ZERO, d);
        return c / ball / d;
    }

    int as3(int c, int d) throws LabException {
        if (ball < 0 || d < 0) throw new LabException(Code.NEGATIVE_SHIFT, d);
        return c >> ball >> d;
    }

    int as4(int c, int d) {
        return c | ball | d;
    }

    boolean as5(boolean c, boolean d, boolean e) {
        return c && e && d;
    }
}