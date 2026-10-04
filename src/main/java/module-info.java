module com.cgvsu.rasterizationfxapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.edu.vsu.cs.cg.kumickiy_k_s.task_two.rasterizationfxapp to javafx.fxml;
    exports ru.edu.vsu.cs.cg.kumickiy_k_s.task_two.rasterizationfxapp;
}