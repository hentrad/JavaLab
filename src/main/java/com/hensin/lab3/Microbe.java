package com.hensin.lab3;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Microbe {
    private int time;
    
    public Microbe() {}
    
    public Microbe(int time) {
        this.time = time;
    }
    
    public void inputTime() {
        Scanner scanner = new Scanner(System.in);
        boolean valid = false;

        do {
            try {
                System.out.print("Момент времени (целое число минут t ∈ [0..30]): ");
                time = scanner.nextInt();
                if (time < 0 | time > 30) {
                    continue;
                }
                valid = true;
            } catch (InputMismatchException e) {
                scanner.next();
            }
        } while (!valid);
        scanner.close();
    }
    
    public long countBacteria() {
        return 1L << time;
    }
    
    public int getTime() {
        return time;
    }
}