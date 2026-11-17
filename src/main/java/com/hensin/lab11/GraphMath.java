package com.hensin.lab11;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class GraphMath {

    public static List<Point2D.Double> generatePoints(double a, double l, double maxPhi, int steps) {
        List<Point2D.Double> points = new ArrayList<>();
        if (steps <= 0 || maxPhi <= 0) {
            return points;
        }

        double totalRange = 2 * maxPhi;
        double stepSize = totalRange / steps;

        for (int i = 0; i <= steps; i++) {
            double phi = -maxPhi + i * stepSize;
            
            double rho = a * phi * phi - l;
            
            double x = rho * Math.cos(phi);
            double y = rho * Math.sin(phi);
            
            points.add(new Point2D.Double(x, y));
        }
        return points;
    }
}