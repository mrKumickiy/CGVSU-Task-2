package ru.edu.vsu.cs.cg.kumickiy_k_s.task_two.rasterization;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Triangle implements RasterizedShape{
    public static class Point {
        private int x;
        private int y;
        private Color color;

        public Point(final int x, final int y, final Color color) {
            this.x = x;
            this.y = y;
            this.color = color;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Color getColor() {
            return color;
        }
    }

    private Point upperPoint;
    private Point middlePoint;
    private Point lowerPoint;

    public Triangle(
            int x1, int y1, Color c1,
            int x2, int y2, Color c2,
            int x3, int y3, Color c3)
    {
        Point p1 = new Point(x1, y1, c1);
        Point p2 = new Point(x2, y2, c2);
        Point p3 = new Point(x3, y3, c3);

       Point[] sortedPoints = {p1, p2, p3};
       sortPoints(sortedPoints);

       this.upperPoint = sortedPoints[0];
       this.middlePoint = sortedPoints[1];
       this.lowerPoint = sortedPoints[2];
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {

    }

    public int getUpperY() {
        return upperPoint.getY();
    }

    private static void sortPoints(Point[] points) {
        if (points == null) {
            return;
        }
        for (int i = 0; i < points.length; i++) {
            Point initialPoint = points[i];
            int minPointIndex = i;
            for (int j = i + 1; j < points.length; j++) {
                if (points[j].getY() < initialPoint.getY()) {
                    minPointIndex = j;
                }
            }
            points[i] = points[minPointIndex];
            points[minPointIndex] = initialPoint;
        }
    }
}
