package com.hensin.lab11;

import org.junit.jupiter.api.Test;
import java.awt.geom.Point2D;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Lab11Test {

    @Test
    public void testGeneratePointsCount() {
        // Если шагов 100, должно сгенерироваться 101 точка (включая 0)
        List<Point2D.Double> points = GraphMath.generatePoints(1.0, 0.0, 4 * Math.PI, 100);
        assertEquals(101, points.size());
    }

    @Test
    public void testPointAtZero() {
        // При phi = 0, x = (a*0 - l) * cos(0) = -l, y = 0
        List<Point2D.Double> points = GraphMath.generatePoints(2.0, 5.0, Math.PI, 10);
        Point2D.Double firstPoint = points.get(0);
        
        assertEquals(-5.0, firstPoint.x, 0.001);
        assertEquals(0.0, firstPoint.y, 0.001);
    }

    @Test
    public void testZeroParameters() {
        // Если a=0 и l=0, то rho всегда 0, все точки в начале координат (0,0)
        List<Point2D.Double> points = GraphMath.generatePoints(0.0, 0.0, 10.0, 50);
        for (Point2D.Double p : points) {
            assertEquals(0.0, p.x, 0.001);
            assertEquals(0.0, p.y, 0.001);
        }
    }

    @Test
    public void testInvalidSteps() {
        // Отрицательное количество шагов должно возвращать пустой список
        List<Point2D.Double> points = GraphMath.generatePoints(1.0, 1.0, 10.0, -5);
        assertTrue(points.isEmpty());
    }
}
