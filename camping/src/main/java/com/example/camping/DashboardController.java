package com.example.camping;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class DashboardController {
    @FXML
    private ListView listviewAnimateur;
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField telephoneField;
    @FXML
    private ListView listviewCreaneaux;
    public void affichageListeViewCreneau() {
        if (listviewCreaneaux != null) {
            listviewCreaneaux.getItems().clear();
            ObservableList<Creneau> lesCreneaux = FXCollections.observableArrayList(Creneau.getAll());
            listviewCreaneaux.setItems(lesCreneaux);
        } else {
            System.out.println("Erreur : affichage Creneaux est null");
        }
    }

    @FXML
    public void initialize() {
        affichageListeViewCreneau();
    }
}
