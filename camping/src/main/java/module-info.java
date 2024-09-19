module com.example.camping {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.camping to javafx.fxml;
    exports com.example.camping;
}