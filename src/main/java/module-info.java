module ru.edu.vsu.cs.cg.kumickiy_k_s.task_two.rasterizationfxapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens ru.edu.vsu.cs.cg.kumickiy_k_s.task_two.rasterizationfxapp to javafx.fxml;
    exports ru.edu.vsu.cs.cg.kumickiy_k_s.task_two.rasterizationfxapp;
}