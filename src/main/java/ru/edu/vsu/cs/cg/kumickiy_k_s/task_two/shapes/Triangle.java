package ru.edu.vsu.cs.cg.kumickiy_k_s.task_two.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.edu.vsu.cs.cg.kumickiy_k_s.task_two.rasterizations.Rasterization;

public class Triangle implements Shape {
    private final Vertex[] vertices;
    private final Rasterization rasterization;

    public Triangle(
            int x1, int y1, Color c1,
            int x2, int y2, Color c2,
            int x3, int y3, Color c3,
            Rasterization rasterization)
    {
        Vertex p1 = new Vertex(x1, y1, c1);
        Vertex p2 = new Vertex(x2, y2, c2);
        Vertex p3 = new Vertex(x3, y3, c3);

       this.vertices = new Vertex[]{p1, p2, p3};
       this.rasterization = rasterization;
    }

    public Triangle(
            Vertex[] vertices,
            Rasterization rasterization)
    {
        this.vertices = vertices;
        this.rasterization = rasterization;
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        rasterization.draw(graphicsContext, vertices);
    }
}
