package com.hensin.lab1;

public class MainBox {
    public static void main(String[] args) {
        Box p = new Box(0);  // Временное значение, потом перезапишется
        
        p.inputBox();
        
        System.out.println("\n[ 9 операторов ]");
        System.out.println(p.ball + " + 5 = " + p.add(5));
        System.out.println(p.ball + " / 3 = " + p.add1(3));
        System.out.println(p.ball + " *= 5 = " + p.add2(5));
        System.out.println(p.ball + " >> 2 = " + p.add3(2));
        System.out.println("a >>>= ball: " + p.add4(3));
        System.out.println(p.ball + " >= 5 = " + p.add5(5));
        System.out.println(p.isAlive + " | true = " + p.add6(true));
        System.out.println(p.isAlive + " && false = " + p.add7(false));
        System.out.println(p.isAlive + " != false = " + p.add8(false));
    }
}