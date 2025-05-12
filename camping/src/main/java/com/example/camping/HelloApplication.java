package com.example.camping;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("connexion.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 820, 505);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        stage.setTitle("PlaniCamp");
        stage.setScene(scene);

        // Make the window non-resizable
        stage.setResizable(false);

        // Center the window on screen
        stage.centerOnScreen();

        // Maximize the window (alternative to full-screen)
        stage.setMaximized(true);

        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}