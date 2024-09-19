package com.example.camping;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloController {
    @FXML
    private TextField identifiantField;

    @FXML
    private TextField mdpField;

    @FXML
    private Button connexionButton;

    @FXML
    private void handleConnexion() {
        String identifiant = identifiantField.getText();
        String mdp = mdpField.getText();

        // Connexion à la BDD
        Connection connection = ConnexionBDD.initialiserConnexion();
        if (connection != null) {
            try {
                String sql = "SELECT * FROM utilisateur WHERE identifiant = ? AND mdp = ?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, identifiant);
                stmt.setString(2, mdp);

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    // Charger le fichier FXML du dashboard
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/camping/dashboard.fxml"));
                        Parent root = loader.load(); // Charger le FXML
                        Stage stage = (Stage) connexionButton.getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace(); // Affiche les erreurs dans la console
                    }
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Erreur");
                    errorAlert.setContentText("Identifiant ou mot de passe incorrect.");
                    errorAlert.showAndWait();
                }
            } catch (SQLException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur");
                errorAlert.setContentText("Erreur lors de la vérification des identifiants : " + e.getMessage());
                errorAlert.showAndWait();
            }
        }
    }
}