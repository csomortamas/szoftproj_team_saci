module desert {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.tinylog.api;

    opens desert.gui to javafx.fxml;
    exports desert.gui;
}