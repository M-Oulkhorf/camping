module com.example.camping {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires jbcrypt;
    requires javafx.graphics;

    opens com.example.camping to javafx.fxml;
    exports com.example.camping;
}