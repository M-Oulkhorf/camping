package com.example.camping;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.LocalTime;

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
    private TextField heureField;
    @FXML
    private TextField dateField;
    @FXML
    private TextField dureeField;
    @FXML
    private TextField nbplaceField;
    @FXML
    private ListView<Creneau> listviewCreneaux;
    @FXML
    private ListView<Animation> liteViewAnimation;
    @FXML
    private ListView<Lieu> listviewLieuCreneau;
    @FXML
    private ListView listviewLieu;
    @FXML
    private TextField libelleLieu;
    @FXML
    private TextField CoordonneesLieu;
    public boolean isedit = false; // Indique si on est en mode édition
    public int idTemporaire = 0;
    public void affichageListeViewCreneau() {
        if (listviewCreneaux != null) {
            listviewCreneaux.getItems().clear();
            ObservableList<Creneau> lesCreneaux = FXCollections.observableArrayList(Creneau.getAll());
            listviewCreneaux.setItems(lesCreneaux);
        } else {
            System.out.println("Erreur: affichage Creneaux est null");
        }
    }
    public void affichageListeViewAnimation() {
        if (liteViewAnimation != null) {
            liteViewAnimation.getItems().clear();
            ObservableList<Animation> lesAnimations = FXCollections.observableArrayList(Animation.getAll());
            liteViewAnimation.setItems(lesAnimations);
        } else {
            System.out.println("Erreur: affichage Animations est null");
        }
    }

    @FXML
    public void initialize() {
        affichageListeViewCreneau();
        affichageListeViewAnimation();
        actualisationListeLieu();
    }
    public void actualisationListeLieu() {
        listviewAnimateur.getItems().clear();
        ObservableList<Animateur> lesAnimateurs = FXCollections.observableArrayList(Animateur.getAllAnimateur());
        if (!lesAnimateurs.isEmpty()) {
            listviewAnimateur.setItems(lesAnimateurs); // Lie la liste des animateurs au ListView
        } else {
            System.out.println("Aucun animateur trouvé.");
        }
    }
    @FXML
    public void buttonActualiserLieu() {
        actualisationListeListeAnimateur();
    }
    @FXML
    public void buttonEnregistrerLieu() {
        boolean resultat;
        if (isedit){
            Lieu lieu = new Lieu(this.idTemporaire,libelleLieu.getText(), CoordonneesLieu.getText());
            resultat = lieu.save();
            this.isedit=false;
            this.idTemporaire=0;
        }else {
            Lieu lieu = new Lieu(this.idTemporaire,libelleLieu.getText(), CoordonneesLieu.getText());
            resultat = lieu.save();
        }
        if(resultat) {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle("Succès");
            a.setContentText("lieu enregistrée avec succès !");
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
    @FXML
    public void clicBoutonActualiserCreneaux() {
        affichageListeViewCreneau();
    }
    @FXML
    public void clicBoutonModifierCreneau() {
        Creneau c = (Creneau) listviewCreneaux.getSelectionModel().getSelectedItem();
        if (c != null) {
            // Charger les informations du créneau sélectionné dans les champs
            heureField.setText(c.getHeureCreneau().toString());
            dateField.setText(c.getDateCreneau().toString());
            dureeField.setText(String.valueOf(c.getDureeCreneau()));  // Assurez-vous que duree est de type String ou convertissez-le
            nbplaceField.setText(String.valueOf(c.getNbPlacesCreneau())); // Convertir en String
        } else {
            // Afficher une alerte si aucun créneau n'est sélectionné
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Attention");
            a.setContentText("Vous n'avez sélectionné aucun créneau dans la liste.");
            a.showAndWait();
        }
    }
    @FXML
    public void clicBoutonSupprimerCreneau() {
        Creneau c = (Creneau) listviewCreneaux.getSelectionModel().getSelectedItem();
        if (c != null) {
            // Demande de confirmation avant de supprimer
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setContentText("Êtes-vous sûr de vouloir supprimer ce créneau ?");
            ButtonType yesButton = new ButtonType("Oui", ButtonType.YES.getButtonData());
            ButtonType noButton = new ButtonType("Non", ButtonType.NO.getButtonData());
            confirmation.getButtonTypes().setAll(yesButton, noButton);
            confirmation.showAndWait();

            if (confirmation.getResult() == yesButton) {
                boolean resultat = c.delete(); // Suppression du créneau
                if (resultat) {
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setTitle("Succès");
                    a.setContentText("Le créneau a été supprimé avec succès.");
                    a.showAndWait();
                    affichageListeViewCreneau(); // Rafraîchit la liste des créneaux
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Erreur");
                    a.setContentText("Une erreur est survenue pendant la suppression.");
                    a.showAndWait();
                }
            }
        } else {
            // Alerte si aucun créneau n'est sélectionné
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Attention");
            a.setContentText("Vous n'avez sélectionné aucun créneau dans la liste.");
            a.showAndWait();
        }
    }
    @FXML
    public void clicBoutonEnregistrerCreneau() {
        // Récupérer les données des champs texte
        String heure = heureField.getText();
        String date = dateField.getText();
        String duree = dureeField.getText();
        String nbPlaces = nbplaceField.getText();
        Animation animationSelectionnee = liteViewAnimation.getSelectionModel().getSelectedItem();
        int idAnimation = animationSelectionnee != null ? animationSelectionnee.getIdAnimation() : -1;
        Lieu lieuSelectionne = listviewLieuCreneau.getSelectionModel().getSelectedItem();
        int idLieu = lieuSelectionne != null ? lieuSelectionne.getIdLieu() : -1;
        // Créer un nouvel objet Creneau ou modifier un existant
        LocalTime heureCr=LocalTime.parse(heure);
        LocalDate dateCr=LocalDate.parse(date);
        Creneau c = new Creneau(0, heureCr, dateCr, Integer.parseInt(duree), Integer.parseInt(nbPlaces), idAnimation, idLieu);

        boolean resultat = c.save(); // Enregistrement du créneau
        if (resultat) {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle("Succès");
            a.setContentText("Le créneau a été enregistré avec succès.");
            a.showAndWait();
            affichageListeViewCreneau(); // Rafraîchit la liste des créneaux
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Erreur");
            a.setContentText("Une erreur est survenue pendant l'enregistrement.");
            a.showAndWait();
        }
    }
    @FXML
    public void clicBoutonAnnulerCreneau() {
        // Demande confirmation avant d'annuler
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setContentText("Êtes-vous sûr de vouloir annuler les modifications en cours ?");
        ButtonType yesButton = new ButtonType("Oui", ButtonType.YES.getButtonData());
        ButtonType noButton = new ButtonType("Non", ButtonType.NO.getButtonData());
        confirmation.getButtonTypes().setAll(yesButton, noButton);
        confirmation.showAndWait();

        if (confirmation.getResult() == yesButton) {
            // Effacer les champs texte si l'utilisateur confirme
            heureField.setText("");
            dateField.setText("");
            dureeField.setText("");
            nbplaceField.setText("");
            liteViewAnimation.getSelectionModel().clearSelection();
            listviewLieuCreneau.getSelectionModel().clearSelection();
        }
    }
}
