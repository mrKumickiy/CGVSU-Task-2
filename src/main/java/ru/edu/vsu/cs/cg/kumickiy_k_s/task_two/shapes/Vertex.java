package ru.edu.vsu.cs.cg.kumickiy_k_s.task_two.shapes;

import javafx.scene.paint.Color;

public class Vertex {
    private int x;
    private int y;
    private Color color;

    public Vertex(final int x, final int y, final Color color) {
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
