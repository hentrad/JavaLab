package com.hensin.lab11;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class MainGraph extends JFrame {

    private final GraphPanel graphPanel;
    private final JSpinner spinnerA;
    private final JSpinner spinnerL;
    private final JSpinner spinnerPhi;

    public MainGraph() {
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        graphPanel = new GraphPanel();
        add(graphPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        
        spinnerA = new JSpinner(new SpinnerNumberModel(1.0, -10.0, 10.0, 0.1));
        spinnerL = new JSpinner(new SpinnerNumberModel(10.0, 0.0, 100.0, 1.0)); // l >= 0
        spinnerPhi = new JSpinner(new SpinnerNumberModel(4 * Math.PI, 1.0, 20 * Math.PI, Math.PI));

        JButton drawBtn = new JButton("Построить");

        controlPanel.add(new JLabel("a:"));
        controlPanel.add(spinnerA);
        controlPanel.add(new JLabel("l (>=0):"));
        controlPanel.add(spinnerL);
        controlPanel.add(new JLabel("Max ф (рад):"));
        controlPanel.add(spinnerPhi);
        controlPanel.add(drawBtn);

        add(controlPanel, BorderLayout.NORTH);

        drawBtn.addActionListener(e -> updateGraph());

        updateGraph();
        setLocationRelativeTo(null);
    }

    private void updateGraph() {
        double a = (Double) spinnerA.getValue();
        double l = (Double) spinnerL.getValue();
        double phi = (Double) spinnerPhi.getValue();
        graphPanel.setParameters(a, l, phi);
    }

    public static void run(Scanner scanner) {
        SwingUtilities.invokeLater(() -> {
            MainGraph frame = new MainGraph();
            frame.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGraph().setVisible(true));
    }
}
