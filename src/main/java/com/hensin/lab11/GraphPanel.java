package com.hensin.lab11;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class GraphPanel extends JPanel {

    private double a = 1.0;
    private double l = 10.0;
    private double maxPhi = 4 * Math.PI;

    public GraphPanel() {
        setBackground(Color.WHITE);
    }

    public void setParameters(double a, double l, double maxPhi) {
        this.a = a;
        this.l = l;
        this.maxPhi = maxPhi;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;

        List<Point2D.Double> points = GraphMath.generatePoints(a, l, maxPhi, 1000);
        if (points.isEmpty()) return;

        double minX = Double.MAX_VALUE, maxX = -Double.MAX_VALUE;
        double minY = Double.MAX_VALUE, maxY = -Double.MAX_VALUE;
        
        for (Point2D.Double p : points) {
            if (p.x < minX) minX = p.x;
            if (p.x > maxX) maxX = p.x;
            if (p.y < minY) minY = p.y;
            if (p.y > maxY) maxY = p.y;
        }

        double margin = 0.9;
        double rangeX = maxX - minX;
        double rangeY = maxY - minY;
        
        if (rangeX == 0) rangeX = 1; 
        if (rangeY == 0) rangeY = 1;

        double scaleX = (width * margin) / rangeX;
        double scaleY = (height * margin) / rangeY;
        double scale = Math.min(scaleX, scaleY);

        double midX = (minX + maxX) / 2;
        double midY = (minY + maxY) / 2;

        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
        

        int axisY = (int) (centerY + midY * scale); 
        g2.drawLine(0, axisY, width, axisY);

        int axisX = (int) (centerX - midX * scale);
        g2.drawLine(axisX, 0, axisX, height);

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        g2.drawString("X", width - 20, axisY - 10);
        g2.drawString("Y", axisX + 10, 20);
        g2.drawString("0", axisX + 5, axisY + 15);

        g2.setColor(Color.BLUE);
        g2.setStroke(new BasicStroke(2f));
        
        for (int i = 0; i < points.size() - 1; i++) {
            Point2D.Double p1 = points.get(i);
            Point2D.Double p2 = points.get(i + 1);

            int x1 = (int) (centerX + (p1.x - midX) * scale);
            int y1 = (int) (centerY - (p1.y - midY) * scale);
            int x2 = (int) (centerX + (p2.x - midX) * scale);
            int y2 = (int) (centerY - (p2.y - midY) * scale);

            g2.drawLine(x1, y1, x2, y2);
        }
    }
}
