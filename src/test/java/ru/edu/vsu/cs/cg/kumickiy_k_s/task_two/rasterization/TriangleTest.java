package ru.edu.vsu.cs.cg.kumickiy_k_s.task_two.rasterization;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {
    @Test
    void sortingTest() {
        Triangle trig = new Triangle(
                10, 10, Color.AQUA,
                20, 8, Color.CHOCOLATE,
                15, 15, Color.RED);
        assertEquals(8,trig.getUpperY());
    }

}