package com.hensin.lab8;

import javax.swing.*;
import java.util.Scanner;

public class MainTrafficLight {

    public static void main(String[] args) {
        run(null);
    }

    public static void run(Scanner scanner) {
    SwingUtilities.invokeLater(() -> {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}
        TrafficLightFrame frame = new TrafficLightFrame();
        frame.setVisible(true);
    });
}
}