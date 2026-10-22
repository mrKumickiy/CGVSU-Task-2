package ru.edu.vsu.cs.cg.kumickiy_k_s.task_two.rasterizations;

import javafx.scene.canvas.GraphicsContext;
import ru.edu.vsu.cs.cg.kumickiy_k_s.task_two.shapes.Vertex;

public interface Rasterization {
    void draw(GraphicsContext graphicsContext, Vertex[] vertices);

}
