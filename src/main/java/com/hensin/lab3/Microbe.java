package com.hensin.lab3;

import com.hensin.LabException;
import com.hensin.LabException.Code;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Microbe {
    private int time;

    public Microbe() {}

    public Microbe(int time) throws LabException {
        if (time < 0 || time > 30) {
            throw new LabException(Code.TIME_OUT_OF_RANGE, time);
        }
        this.time = time;
    }

    public void inputTime(Scanner scanner) {
        boolean valid;
        do {
            try {
                System.out.print("Момент времени (0..30): ");
                time = scanner.nextInt();
                valid = time >= 0 && time <= 30;
                
            } catch (InputMismatchException e) {
                scanner.next();
                valid = false;
            }
        } while (!valid);
    }

    public long countBacteria() throws LabException {
        if (time < 0 || time > 30) {
            throw new LabException(Code.TIME_OUT_OF_RANGE, time);
        }
        return 1L << time;
    }

    public int getTime() { return time; }
}