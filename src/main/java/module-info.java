module com.example.robotgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.robotgui to javafx.fxml;
    exports com.example.robotgui;
}