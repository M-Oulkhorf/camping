package com.example.camping;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
    public boolean isedit = false; // Indique si on est en mode édition
    public int idTemporaire = 0;
    public void affichageListeViewCreneau() {
        if (listviewCreaneaux != null) {
            listviewCreaneaux.getItems().clear();
            ObservableList<Creneau> lesCreneaux = FXCollections.observableArrayList(Creneau.getAll());
            listviewCreaneaux.setItems(lesCreneaux);
        } else {
            System.out.println("Erreur: affichage Creneaux est null");
        }
    }

    @FXML
    public void initialize() {
        affichageListeViewCreneau();
        actualisationListeListeAnimateur();
    }


    public void actualisationListeListeAnimateur() {
        listviewAnimateur.getItems().clear();
        ObservableList<Animateur> lesAnimateurs = FXCollections.observableArrayList(Animateur.getAllAnimateur());
        if (!lesAnimateurs.isEmpty()) {
            listviewAnimateur.setItems(lesAnimateurs); // Lie la liste des animateurs au ListView
        } else {
            System.out.println("Aucun animateur trouvé.");
        }
    }


    @FXML
    public void buttonActualiserAnimateur() {
        actualisationListeListeAnimateur();
    }

    @FXML
    public void buttonModifierAnimateur() {
        Animateur a = (Animateur) listviewAnimateur.getSelectionModel().getSelectedItem();
        if(a != null) {
            nomField.setText(a.getNomAnimateur());
            prenomField.setText(a.getPrenomAnimateur());
            emailField.setText(a.getMailAnimateur());
            telephoneField.setText(a.getTelephoneAnimateur());
            this.isedit=true;
            this.idTemporaire=a.getIdAnimateur();
        }
        else {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("Attention");
            al.setContentText("Vous n'avez sélectionné aucun élément dans la liste");
            al.showAndWait();
        }
    }

    @FXML
    public void buttonAnnulerAnimateur() {
        Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setContentText("Êtes-vous sûr de vouloir annuler les modification en cours ?");
        ButtonType yesButton = new ButtonType("Oui", ButtonType.YES.getButtonData());
        ButtonType noButton = new ButtonType("Non", ButtonType.NO.getButtonData());
        confirmation.getButtonTypes().setAll(yesButton, noButton);
        confirmation.showAndWait();

        if (confirmation.getResult() == yesButton) {
            nomField.setText("");
            prenomField.setText("");
            emailField.setText("");
            telephoneField.setText("");
        }
    }

    @FXML
    public void buttonEnregistrerAnimateur() {
        boolean resultat;
        if (isedit){
            Animateur animateur = new Animateur(this.idTemporaire,nomField.getText(), prenomField.getText(), emailField.getText(), telephoneField.getText());
            resultat = animateur.saveAnimateur();
            this.isedit=false;
            this.idTemporaire=0;
        }else {
            Animateur animateur = new Animateur(this.idTemporaire, nomField.getText(), prenomField.getText(), emailField.getText(), telephoneField.getText());
            resultat = animateur.saveAnimateur();
        }
        if(resultat) {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle("Succès");
            a.setContentText("Animateur enregistrée avec succès !");
            a.showAndWait();
            actualisationListeListeAnimateur();
        }
        else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Erreur");
            a.setContentText("Une erreur est survenue pendant l'enregistrement");
            a.showAndWait();
        }
    }

    @FXML
    public void buttonSupprimerAnimateur() {
        Animateur animateur = (Animateur) listviewAnimateur.getSelectionModel().getSelectedItem();
        if(animateur != null) {
            Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setContentText("Êtes-vous sûr de vouloir supprimer cet animateur?");
            ButtonType yesButton = new ButtonType("Oui", ButtonType.YES.getButtonData());
            ButtonType noButton = new ButtonType("Non", ButtonType.NO.getButtonData());
            confirmation.getButtonTypes().setAll(yesButton, noButton);
            confirmation.showAndWait();

            if (confirmation.getResult() == yesButton) {
                boolean resultat = animateur.delete();
                if (resultat == true) {
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setTitle("Succès");
                    a.setContentText("Animateur supprimée avec succès !");
                    a.showAndWait();
                    actualisationListeListeAnimateur();
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Erreur");
                    a.setContentText("Une erreur est survenue pendant la suppression");
                    a.showAndWait();
                }
            }
        }
        else {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Attention");
            a.setContentText("Vous n'avez sélectionné aucun élément dans la liste");
            a.showAndWait();
        }
    }

}
