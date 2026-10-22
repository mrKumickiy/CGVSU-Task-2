package ru.edu.vsu.cs.cg.kumickiy_k_s.task_two.rasterizations;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import ru.edu.vsu.cs.cg.kumickiy_k_s.task_two.shapes.Vertex;

import java.util.Arrays;

public class TriangleRasterizationV1 implements Rasterization {
    private record BarycentricCoordinates(double alpha, double beta, double gamma) {
    }
    private class PlacedVertices {
        private final Vertex top;
        private final Vertex middle;
        private final Vertex bottom;

        public PlacedVertices(final Vertex[] vertices) {
            Vertex[] sortedVertices = sortPoints(vertices);
            this.top = sortedVertices[0];
            this.middle = sortedVertices[1];
            this.bottom = sortedVertices[2];
        }
    }
    //private Vertex topVertex;
    //private Vertex middleVertex;
    //private Vertex bottomVertex;

    public TriangleRasterizationV1() {
    }


    @Override
    public void draw(GraphicsContext graphicsContext, Vertex[] vertices) {
        if (graphicsContext == null) {
            return;
        }
        PixelWriter pixelWriter = graphicsContext.getPixelWriter();
        PlacedVertices placedVertices = new PlacedVertices(vertices);
        if (placedVertices.middle.getY() == placedVertices.bottom.getY()) {
            drawSplit(pixelWriter, placedVertices.top, placedVertices.middle,
                    placedVertices.bottom, placedVertices);
            return;
        }
        if (placedVertices.top.getY() == placedVertices.middle.getY()) {
            drawSplit(pixelWriter, placedVertices.bottom, placedVertices.middle,
                    placedVertices.top, placedVertices);
            return;
        }
        drawSplit(pixelWriter, placedVertices.top, placedVertices.middle,
                placedVertices.bottom, placedVertices);
        drawSplit(pixelWriter, placedVertices.bottom, placedVertices.middle,
                placedVertices.top, placedVertices);
    }

    private static BarycentricCoordinates getBarycentricCoordinatesForPoint(int x, int y, PlacedVertices placed) {
        double det = (placed.top.getX() - placed.bottom.getX()) * (placed.middle.getY() - placed.bottom.getY()) -
                (placed.middle.getX() - placed.bottom.getX()) * (placed.top.getY() - placed.bottom.getY());
        double det1 = (x - placed.bottom.getX()) * (placed.middle.getY() - placed.bottom.getY()) -
                (placed.middle.getX() - placed.bottom.getX()) * (y - placed.bottom.getY());
        double det2 = (placed.top.getX() - placed.bottom.getX()) * (y - placed.bottom.getY()) -
                (x - placed.bottom.getX()) * (placed.top.getY() - placed.bottom.getY());
        double alpha = Math.clamp(det1 / det, 0, 1);
        double beta = Math.clamp(det2 / det, 0, 1);
        double gamma = Math.clamp(1 - alpha - beta, 0, 1);

        return new BarycentricCoordinates(alpha, beta, gamma);
    }

    private static Color getColorInPoint(int x, int y, PlacedVertices placed) {
        BarycentricCoordinates barycentricCoords = getBarycentricCoordinatesForPoint(x, y, placed);
        double red = barycentricCoords.alpha() * placed.top.getColor().getRed() +
                barycentricCoords.beta() * placed.middle.getColor().getRed() +
                barycentricCoords.gamma() * placed.bottom.getColor().getRed();

        double green = barycentricCoords.alpha() * placed.top.getColor().getGreen() +
                barycentricCoords.beta() * placed.middle.getColor().getGreen() +
                barycentricCoords.gamma() * placed.bottom.getColor().getGreen();

        double blue = barycentricCoords.alpha() * placed.top.getColor().getBlue() +
                barycentricCoords.beta() * placed.middle.getColor().getBlue() +
                barycentricCoords.gamma() * placed.bottom.getColor().getBlue();

        return new Color(red, green, blue, 1);
    }

    private static void drawSplit(PixelWriter pixelWriter, Vertex top, Vertex low, Vertex lerped, PlacedVertices placed) {
        Vertex left;
        Vertex right;
        if (low.getX() < lerped.getX()) {
            left = low;
            right = lerped;
        } else {
            left = lerped;
            right = low;
        }

        if (top.getY() <= low.getY()) {
            for (int y = top.getY(); y <= low.getY(); y++) {
                drawLine(pixelWriter, y, top, left, right, placed);
            }
        } else {
            for (int y = top.getY(); y >= low.getY(); y--) {
                drawLine(pixelWriter, y, top, left, right, placed);
            }
        }
    }

    private static void drawLine(PixelWriter pixelWriter, int y, Vertex top, Vertex left, Vertex right, PlacedVertices placed) {
        int xLeft = getLerpedXByY(y, top, left);
        int xRight = getLerpedXByY(y, top, right);
        for (int x = xLeft; x <= xRight; x++) {
            pixelWriter.setColor(x, y, getColorInPoint(x ,y, placed));
        }
    }

    private static int getLerpedXByY(int y, Vertex startVertex, Vertex endVertex) {
        return (int)(startVertex.getX() + (endVertex.getX() - startVertex.getX()) *
                (double)(y - startVertex.getY()) / (endVertex.getY() - startVertex.getY()));
    }

    private static Vertex[] sortPoints(Vertex[] vertices) {
        if (vertices == null) {
            return new Vertex[0];
        }
        Vertex[] sortedVertices = Arrays.copyOf(vertices, vertices.length);
        for (int i = 0; i < sortedVertices.length; i++) {
            Vertex initialVertex = sortedVertices[i];
            int minPointIndex = i;
            for (int j = i + 1; j < sortedVertices.length; j++) {
                if (sortedVertices[j].getY() < initialVertex.getY()) {
                    minPointIndex = j;
                }
            }
            sortedVertices[i] = sortedVertices[minPointIndex];
            sortedVertices[minPointIndex] = initialVertex;
        }
        return sortedVertices;
    }
}
