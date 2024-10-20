package com.example.camping;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

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
                String sql = "SELECT * FROM utilisateur WHERE identifiant = ?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, identifiant);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    String motDePasseHache = rs.getString("mdp");
                    if (BCrypt.checkpw(mdp, motDePasseHache)) {
                        // Charger le fichier FXML du dashboard
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/camping/dashboard.fxml"));
                        Parent root = loader.load();
                        Stage stage = (Stage) connexionButton.getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Erreur");
                        errorAlert.setContentText("Identifiant ou mot de passe incorrect.");
                        errorAlert.showAndWait();
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
                errorAlert.setContentText("Erreur de la vérification des identifiants : " + e.getMessage());
                errorAlert.showAndWait();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}