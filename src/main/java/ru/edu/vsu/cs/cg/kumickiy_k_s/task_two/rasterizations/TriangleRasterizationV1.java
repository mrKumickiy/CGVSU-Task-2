package ru.edu.vsu.cs.cg.kumickiy_k_s.task_two.rasterizations;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import ru.edu.vsu.cs.cg.kumickiy_k_s.task_two.shapes.Vertex;
import ru.edu.vsu.cs.cg.kumickiy_k_s.task_two.tools.BarycentricCoordinates;

import java.util.Arrays;

public class TriangleRasterizationV1 implements Rasterization {
    private static class PlacedVertices {
        private final Vertex top;
        private final Vertex middle;
        private final Vertex bottom;

        public PlacedVertices(final Vertex[] vertices) {
            Vertex[] sortedVertices = sortPoints(vertices);
            this.top = sortedVertices[0];
            this.middle = sortedVertices[1];
            this.bottom = sortedVertices[2];
        }

        public Vertex[] toArray() {
            Vertex[] arr = new Vertex[3];
            arr[0] = top;
            arr[1] = middle;
            arr[2] = bottom;
            return arr;
        }
    }

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

    private static Color getColorInPoint(int x, int y, PlacedVertices placed) {
        BarycentricCoordinates barycentricCoords = BarycentricCoordinates.getBarycentricCoordinatesForPoint(x, y, placed.toArray());
        double red = barycentricCoords.getAlpha() * placed.top.getColor().getRed() +
                barycentricCoords.getBeta() * placed.middle.getColor().getRed() +
                barycentricCoords.getGamma() * placed.bottom.getColor().getRed();

        double green = barycentricCoords.getAlpha() * placed.top.getColor().getGreen() +
                barycentricCoords.getBeta() * placed.middle.getColor().getGreen() +
                barycentricCoords.getGamma() * placed.bottom.getColor().getGreen();

        double blue = barycentricCoords.getAlpha() * placed.top.getColor().getBlue() +
                barycentricCoords.getBeta() * placed.middle.getColor().getBlue() +
                barycentricCoords.getGamma() * placed.bottom.getColor().getBlue();

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
