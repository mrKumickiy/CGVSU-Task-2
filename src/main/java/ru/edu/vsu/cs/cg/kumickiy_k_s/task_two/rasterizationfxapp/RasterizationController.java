package ru.edu.vsu.cs.cg.kumickiy_k_s.task_two.rasterizationfxapp;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;

import javafx.scene.paint.Color;
import ru.edu.vsu.cs.cg.kumickiy_k_s.task_two.rasterization.Rasterization;
import ru.edu.vsu.cs.cg.kumickiy_k_s.task_two.rasterization.RasterizedShape;
import ru.edu.vsu.cs.cg.kumickiy_k_s.task_two.rasterization.Triangle;

import java.util.ArrayList;
import java.util.List;

public class RasterizationController {

    @FXML
    AnchorPane anchorPane;
    @FXML
    private Canvas canvas;
    private final List<RasterizedShape> shapes = new ArrayList<>();
    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        RasterizedShape trig0 = new Triangle(
                200, 50, Color.RED,
                50,300,Color.GREEN,
                300, 500, Color.BLUE);
        RasterizedShape trig1 = new Triangle(
                400, 50, Color.BLACK,
                450,50,Color.BLACK,
                450, 150, Color.BLACK);
        trig0.draw(canvas.getGraphicsContext2D());
        trig1.draw(canvas.getGraphicsContext2D());
    }

}