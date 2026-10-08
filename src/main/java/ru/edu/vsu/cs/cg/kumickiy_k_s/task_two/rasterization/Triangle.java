package ru.edu.vsu.cs.cg.kumickiy_k_s.task_two.rasterization;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class Triangle implements RasterizedShape{
    private static class Point {
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

    private record BarycentricCoordinates(double alpha, double beta, double gamma) {
    }

    private final Point topPoint;
    private final Point middlePoint;
    private final Point bottomPoint;

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

       this.topPoint = sortedPoints[0];
       this.middlePoint = sortedPoints[1];
       this.bottomPoint = sortedPoints[2];
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        if (graphicsContext == null) {
            return;
        }
        PixelWriter pixelWriter = graphicsContext.getPixelWriter();
        drawSplit(pixelWriter, topPoint, middlePoint, bottomPoint);
        drawSplit(pixelWriter, bottomPoint, middlePoint, topPoint);
    }

    private BarycentricCoordinates getBarycentricCoordinatesForPoint(int x, int y) {
        double det = (topPoint.getX() - bottomPoint.getX()) * (middlePoint.getY() - bottomPoint.getY()) -
                (middlePoint.getX() - bottomPoint.getX()) * (topPoint.getY() - bottomPoint.getY());
        double det1 = (x - bottomPoint.getX()) * (middlePoint.getY() - bottomPoint.getY()) -
                (middlePoint.getX() - bottomPoint.getX()) * (y - bottomPoint.getY());
        double det2 = (topPoint.getX() - bottomPoint.getX()) * (y - bottomPoint.getY()) -
                (x - bottomPoint.getX()) * (topPoint.getY() - bottomPoint.getY());
        double alpha = Math.max(det1 / det, 0);
        double beta = Math.max(det2 / det, 0);
        double gamma = Math.max(1 - alpha - beta, 0);

        return new BarycentricCoordinates(alpha, beta, gamma);
    }

    private Color getColorInPoint(int x, int y) {
        BarycentricCoordinates barycentricCoords = getBarycentricCoordinatesForPoint(x, y);
        double red = barycentricCoords.alpha() * topPoint.getColor().getRed() +
                barycentricCoords.beta() * middlePoint.getColor().getRed() +
                barycentricCoords.gamma() * bottomPoint.getColor().getRed();

        double green = barycentricCoords.alpha() * topPoint.getColor().getGreen() +
                barycentricCoords.beta() * middlePoint.getColor().getGreen() +
                barycentricCoords.gamma() * bottomPoint.getColor().getGreen();

        double blue = barycentricCoords.alpha() * topPoint.getColor().getBlue() +
                barycentricCoords.beta() * middlePoint.getColor().getBlue() +
                barycentricCoords.gamma() * bottomPoint.getColor().getBlue();

        return new Color(red, green, blue, 1);
    }

    private void drawSplit(PixelWriter pixelWriter, Point top, Point low, Point lerped) {
        Point left;
        Point right;
        if (low.getX() < lerped.getX()) {
            left = low;
            right = lerped;
        } else {
            left = lerped;
            right = low;
        }

        if (top.getY() <= low.getY()) {
            for (int y = top.getY(); y <= low.getY(); y++) {
                drawLine(pixelWriter, y, top, left, right);
            }
        } else {
            for (int y = top.getY(); y >= low.getY(); y--) {
                drawLine(pixelWriter, y, top, left, right);
            }
        }
    }

    private void drawLine(PixelWriter pixelWriter, int y, Point top, Point left, Point right) {
        int xLeft = getLerpedXByY(y, top, left);
        int xRight = getLerpedXByY(y, top, right);
        for (int x = xLeft; x <= xRight; x++) {
            pixelWriter.setColor(x, y, getColorInPoint(x ,y));
        }
    }

    private static int getLerpedXByY(int y, Point startPoint, Point endPoint) {
        return (int)(startPoint.getX() + (endPoint.getX() - startPoint.getX()) *
                (double)(y - startPoint.getY()) / (endPoint.getY() - startPoint.getY()));
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
