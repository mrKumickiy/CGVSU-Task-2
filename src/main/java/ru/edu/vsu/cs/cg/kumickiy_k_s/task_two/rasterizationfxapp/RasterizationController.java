package ru.edu.vsu.cs.cg.kumickiy_k_s.task_two.rasterizationfxapp;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;

import javafx.scene.paint.Color;
import ru.edu.vsu.cs.cg.kumickiy_k_s.task_two.rasterizations.Rasterization;
import ru.edu.vsu.cs.cg.kumickiy_k_s.task_two.rasterizations.TriangleRasterizationV1;
import ru.edu.vsu.cs.cg.kumickiy_k_s.task_two.shapes.Shape;
import ru.edu.vsu.cs.cg.kumickiy_k_s.task_two.shapes.Triangle;

public class RasterizationController {

    @FXML
    AnchorPane anchorPane;
    @FXML
    private Canvas canvas;
    //private final List<RasterizedShape> shapes = new ArrayList<>();
    @FXML
    private void initialize() {
        Rasterization triangleRasterization = new TriangleRasterizationV1();
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        Shape trig0 = new Triangle(
                200, 50, Color.RED,
                50,300,Color.GREEN,
                300, 500, Color.BLUE, triangleRasterization);
        Shape trig1 = new Triangle(
                400, 50, Color.ORANGE,
                600,50,Color.YELLOW,
                600, 350, Color.RED, triangleRasterization);
        Shape trig2 = new Triangle(
                350, 150, Color.CYAN,
                250,400,Color.DARKCYAN,
                550, 400, Color.BLUE, triangleRasterization);
        trig0.draw(canvas.getGraphicsContext2D());
        trig1.draw(canvas.getGraphicsContext2D());
        trig2.draw(canvas.getGraphicsContext2D());
    }

}