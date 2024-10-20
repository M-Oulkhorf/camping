package com.example.camping;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.mindrot.jbcrypt.BCrypt;

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
    private ListView<Animateur> listviewAnimateurCreneau;
    @FXML
    private ListView listviewLieu;
    @FXML
    private TextField libelleLieu;
    @FXML
    private TextField coordonneesLieu;
    @FXML
    private TextField libelleAnimation;
    public boolean isedit = false; // Indique si on est en mode édition
    public int idTemporaire = 0;
    @FXML
    private TableView<Creneau> tableViewActivites;

    @FXML
    private TableColumn<Creneau, LocalDate> colDate;

    @FXML
    private TableColumn<Creneau, LocalTime> colHeure;

    @FXML
    private TableColumn<Creneau, Integer> colDuree;

    @FXML
    private TableColumn<Creneau, Integer> colNbPlaces;

    @FXML
    private TableColumn<Creneau, String> colAnimation; // Pour le nom d'animation

    @FXML
    private TableColumn<Creneau, String> colLieu; // Pour le nom de lieu
    @FXML
    private PasswordField currentMdp;
    @FXML
    private PasswordField nouveauMdp;
    @FXML
    private PasswordField confirmationMdp;
    /**cas ajouter un utilisateur
    @FXML
    private TextField idField;

    @FXML
    private TextField mdpField;

    @FXML
    private void handleSaveButtonAction() {
        // Récupérer les valeurs des champs de texte
        String identifiant = idField.getText();
        String motdepasse = mdpField.getText();

        // Créer un nouvel utilisateur
        Utilisateur nouvelUtilisateur = new Utilisateur(identifiant, motdepasse);

        // Tenter de sauvegarder l'utilisateur
        boolean isSaved = nouvelUtilisateur.save();

        if (isSaved) {
            System.out.println("Utilisateur enregistré avec succès !");
        } else {
            System.out.println("Erreur lors de l'enregistrement de l'utilisateur.");
        }
    }*/

    @FXML
    public void handleChangePassword() {
        String currentPassword = currentMdp.getText();
        String newPassword = nouveauMdp.getText();
        String confirmPassword = confirmationMdp.getText();
        // Vérification des champs
        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis.");
            return;
        }

        // Vérification que le nouveau mot de passe et sa confirmation correspondent
        if (!newPassword.equals(confirmPassword)) {
            showAlert("Erreur", "Le nouveau mot de passe et sa confirmation ne correspondent pas.");
            return;
        }

        // Vérification du mot de passe actuel
        if (!verifierMotDePasse(currentPassword)) {
            showAlert("Erreur", "Le mot de passe actuel est incorrect.");
            return;
        }

        // Hachage du nouveau mot de passe et mise à jour dans la base de données
        if (modifierMotDePasse(newPassword)) {
            showAlert("Succès", "Le mot de passe a été modifié avec succès !");
        } else {
            showAlert("Erreur", "Une erreur est survenue lors de la modification du mot de passe.");
        }
    }

    // Méthode pour vérifier le mot de passe actuel
    private boolean verifierMotDePasse(String motDePasse) {
        String motDePasseHache = obtenirMotDePasseHache(); // Implémentez cette méthode pour récupérer le mot de passe haché.
        return BCrypt.checkpw(motDePasse, motDePasseHache);
    }

    // Méthode pour modifier le mot de passe
    private boolean modifierMotDePasse(String nouveauMotDePasse) {
        String motDePasseHache = BCrypt.hashpw(nouveauMotDePasse, BCrypt.gensalt());
        return mettreAJourMotDePasseDansBDD(motDePasseHache); // Implémentez cette méthode pour mettre à jour le mot de passe.
    }
    private String obtenirMotDePasseHache() {
        // Connexion à la base de données et récupération du mot de passe haché
        // Assurez-vous de récupérer le mot de passe pour l'utilisateur connecté
        String motDePasseHache = null;
        Connection connection = ConnexionBDD.initialiserConnexion();
        if (connection != null) {
            String query = "SELECT mdp FROM utilisateur WHERE identifiant = ?"; // Utilisez l'identifiant de la directrice
            try {
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, "admin"); // Remplacez par l'identifiant de la directrice
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    motDePasseHache = rs.getString("mdp");
                }
            } catch (SQLException e) {
                System.err.println("Erreur lors de la récupération du mot de passe : " + e.getMessage());
            }
        }
        return motDePasseHache;
    }
    private boolean mettreAJourMotDePasseDansBDD(String motDePasseHache) {
        Connection connection = ConnexionBDD.initialiserConnexion();
        if (connection != null) {
            String updateQuery = "UPDATE utilisateur SET mdp = ? WHERE identifiant = ?"; // Assurez-vous d'utiliser l'identifiant approprié
            try {
                PreparedStatement stmt = connection.prepareStatement(updateQuery);
                stmt.setString(1, motDePasseHache);
                stmt.setString(2, "admin"); // Remplacez par l'identifiant de la directrice
                int rowsUpdated = stmt.executeUpdate();
                return rowsUpdated > 0; // Retourne true si la mise à jour a réussi
            } catch (SQLException e) {
                System.err.println("Erreur lors de la mise à jour du mot de passe : " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    // Méthode pour afficher des alertes
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void actualiserTable() {
        ObservableList<Creneau> listeCreneaux = FXCollections.observableArrayList(Creneau.getAllWeek());
        tableViewActivites.setItems(listeCreneaux);
        colDate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDateCreneau()));
        colHeure.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getHeureCreneau()));
        colDuree.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getDureeCreneau()).asObject());
        colNbPlaces.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNbPlacesCreneau()).asObject());

        colAnimation.setCellValueFactory(cellData -> {
            int idAnimation = cellData.getValue().getIdAnimation();
            return new SimpleObjectProperty<>(getNomAnimation(idAnimation));
        });

        colLieu.setCellValueFactory(cellData -> {
            int idLieu = cellData.getValue().getIdLieu();
            return new SimpleObjectProperty<>(getNomLieu(idLieu));
        });
    }

    // Méthode pour récupérer le nom de l'animation par ID
    private String getNomAnimation(int id) {
        String nom = "";
        Connection c = ConnexionBDD.initialiserConnexion();
        if (c != null) {
            try {
                String query = "SELECT libelleAnimation FROM animation WHERE idAnimation = ?";
                PreparedStatement stmt = c.prepareStatement(query);
                stmt.setInt(1, id);
                ResultSet res = stmt.executeQuery();
                if (res.next()) {
                    nom = res.getString("libelleAnimation");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return nom;
    }

    // Méthode pour récupérer le nom du lieu par ID
    private String getNomLieu(int id) {
        String nom = "";
        Connection c = ConnexionBDD.initialiserConnexion();
        if (c != null) {
            try {
                String query = "SELECT libelleLieu FROM lieu WHERE idLieu = ?";
                PreparedStatement stmt = c.prepareStatement(query);
                stmt.setInt(1, id);
                ResultSet res = stmt.executeQuery();
                if (res.next()) {
                    nom = res.getString("libelleLieu");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return nom;
    }

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

    public void affichagelistviewLieuCreneau() {
        if (listviewLieuCreneau != null) {
            listviewLieuCreneau.getItems().clear();
            ObservableList<Lieu> lesLieux = FXCollections.observableArrayList(Lieu.getAll());
            listviewLieuCreneau.setItems(lesLieux);
        } else {
            System.out.println("Erreur: affichage Animations est null");
        }
    }

    public void affichagelistviewAnimateurCreneau() {
        if (listviewAnimateurCreneau != null) {
            listviewAnimateurCreneau.getItems().clear();
            ObservableList<Animateur> lesAnimateurs = FXCollections.observableArrayList(Animateur.getAllAnimateur());
            listviewAnimateurCreneau.setItems(lesAnimateurs);
        } else {
            System.out.println("Erreur: affichage Animations est null");
        }
    }

    @FXML
    public void initialize() {
        affichageListeViewCreneau();
        affichageListeViewAnimation();
        actualisationListeLieu();
        actualisationListeListeAnimateur();
        actualiserTable();
        affichagelistviewLieuCreneau();
        affichagelistviewAnimateurCreneau();
        listviewAnimateurCreneau.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
    public void actualisationListeLieu() {
        listviewLieu.getItems().clear();
        ObservableList<Lieu> lesLieux = FXCollections.observableArrayList(Lieu.getAll());
        if (!lesLieux.isEmpty()) {
            listviewLieu.setItems(lesLieux); // Lie la liste des animateurs au ListView
        } else {
            System.out.println("Aucun animateur trouvé.");
        }
    }
    @FXML
    public void buttonActualiserLieu() {
      actualisationListeLieu();
    }
    @FXML
    public void buttonEnregistrerLieu() {
        boolean resultat;
        if (isedit){
            Lieu lieu = new Lieu(this.idTemporaire,libelleLieu.getText(),coordonneesLieu.getText());
            resultat = lieu.save();
            this.isedit=false;
            this.idTemporaire=0;
        }else {
            Lieu lieu = new Lieu(this.idTemporaire,libelleLieu.getText(),coordonneesLieu.getText());
            resultat = lieu.save();
        }
        if(resultat) {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle("Succès");
            a.setContentText("lieu enregistrée avec succès !");
            a.showAndWait();
            actualisationListeLieu();
        }
        else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Erreur");
            a.setContentText("Une erreur est survenue pendant l'enregistrement");
            a.showAndWait();
        }
    }

    @FXML
    public void buttonAjouterAnimation() {
        boolean resultat;
        Animation animation = new Animation(this.idTemporaire,libelleAnimation.getText());
        resultat = animation.save();
        if(resultat) {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle("Succès");
            a.setContentText("lieu enregistrée avec succès !");
            a.showAndWait();
            affichageListeViewAnimation();
        }
        else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Erreur");
            a.setContentText("Une erreur est survenue pendant l'enregistrement");
            a.showAndWait();
        }
    }

    @FXML
    public void buttonModifierLieu() {
        Lieu lieu = (Lieu) listviewLieu.getSelectionModel().getSelectedItem();
        if(lieu != null) {
            libelleLieu.setText(lieu.getLibelleLieu());
            coordonneesLieu.setText(lieu.getCordoneesLieu());
            this.isedit=true;
            this.idTemporaire=lieu.getIdLieu();
        }
        else {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("Attention");
            al.setContentText("Vous n'avez sélectionné aucun élément dans la liste");
            al.showAndWait();
        }
    }

    @FXML
    public void buttonAnnulerLieu() {
        Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setContentText("Êtes-vous sûr de vouloir annuler les modification en cours ?");
        ButtonType yesButton = new ButtonType("Oui", ButtonType.YES.getButtonData());
        ButtonType noButton = new ButtonType("Non", ButtonType.NO.getButtonData());
        confirmation.getButtonTypes().setAll(yesButton, noButton);
        confirmation.showAndWait();

        if (confirmation.getResult() == yesButton) {
            libelleLieu.setText("");
            coordonneesLieu.setText("");
            isedit=false;
            idTemporaire=0;
        }
    }

    @FXML
    public void buttonSupprimerLieu() {
        Lieu lieu = (Lieu) listviewLieu.getSelectionModel().getSelectedItem();
        if(lieu != null) {
            Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setContentText("Êtes-vous sûr de vouloir supprimer ce lieu ?");
            ButtonType yesButton = new ButtonType("Oui", ButtonType.YES.getButtonData());
            ButtonType noButton = new ButtonType("Non", ButtonType.NO.getButtonData());
            confirmation.getButtonTypes().setAll(yesButton, noButton);
            confirmation.showAndWait();

            if (confirmation.getResult() == yesButton) {
                boolean resultat = lieu.delete();
                if (resultat == true) {
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setTitle("Succès");
                    a.setContentText("Animateur supprimée avec succès !");
                    a.showAndWait();
                    actualisationListeLieu();
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
    // Méthode pour récupérer l'animation par son ID
    private Animation getAnimationById(int idAnimation) {
        Animation animation = null;
        Connection c = ConnexionBDD.initialiserConnexion();
        try {
            String query = "SELECT * FROM animation WHERE idAnimation = ?";
            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setInt(1, idAnimation);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                animation = new Animation(rs.getInt("idAnimation"), rs.getString("libelleAnimation"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (c != null) {
                    c.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return animation;
    }

    // Méthode pour récupérer le lieu par son ID
    private Lieu getLieuById(int idLieu) {
        Lieu lieu = null;
        Connection c = ConnexionBDD.initialiserConnexion();
        try {
            String query = "SELECT * FROM lieu WHERE idLieu = ?";
            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setInt(1, idLieu);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                lieu = new Lieu(rs.getInt("idLieu"), rs.getString("libelleLieu"), rs.getString("coordoneesLieu"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (c != null) {
                    c.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lieu;
    }

    // Méthode pour récupérer les animateurs associés à un créneau
    private List<Animateur> getAnimateursByCreneauId(int idCreneau) {
        List<Animateur> animateurs = new ArrayList<>();
        Connection c = ConnexionBDD.initialiserConnexion();
        try {
            String query = "SELECT a.* FROM animateur a JOIN animer an ON a.idAnimateur = an.idAnimateur WHERE an.idCreneau = ?";
            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setInt(1, idCreneau);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Animateur animateur = new Animateur(rs.getInt("idAnimateur"), rs.getString("nomAnimateur"), rs.getString("prenomAnimateur"), rs.getString("mailAnimateur"), rs.getString("telephoneAnimateur"));
                animateurs.add(animateur);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (c != null) {
                    c.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return animateurs;
    }

    @FXML
    public void clicBoutonModifierCreneau() {
        ObservableList<Creneau> selectedCreneaux = listviewCreneaux.getSelectionModel().getSelectedItems();
        if (!selectedCreneaux.isEmpty()) {
            Creneau c = selectedCreneaux.get(0); // Prendre le premier créneau sélectionné
            isedit=true;
            idTemporaire=c.getIdCreneau();
            heureField.setText(c.getHeureCreneau().toString());
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = c.getDateCreneau().format(dateFormatter);
            dateField.setText(formattedDate);
            dureeField.setText(String.valueOf(c.getDureeCreneau()));
            nbplaceField.setText(String.valueOf(c.getNbPlacesCreneau()));
            // Récupérer et sélectionner l'animation et le lieu associés
            Animation animationSelectionnee = getAnimationById(c.getIdAnimation());
            if (animationSelectionnee != null) {
                liteViewAnimation.getSelectionModel().select(animationSelectionnee);
            } else {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setTitle("Attention");
                a.setContentText("Animation non trouvée pour l'ID : " + c.getIdAnimation());
                a.showAndWait();
            }

            Lieu lieuSelectionne = getLieuById(c.getIdLieu());
            if (lieuSelectionne != null) {
                listviewLieuCreneau.getSelectionModel().select(lieuSelectionne);
            } else {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setTitle("Attention");
                a.setContentText("Lieu non trouvé pour l'ID : " + c.getIdLieu());
                a.showAndWait();
            }

            // Sélectionner les animateurs associés
            List<Animateur> animateursAssocies = getAnimateursByCreneauId(c.getIdCreneau());
            if (animateursAssocies != null && !animateursAssocies.isEmpty()) {
                for (Animateur animateur : animateursAssocies) {
                    listviewAnimateurCreneau.getSelectionModel().select(animateur);
                }
            } else {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setTitle("Attention");
                a.setContentText("Aucun animateur associé trouvé pour le créneau.");
                a.showAndWait();
            }
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Attention");
            a.setContentText("Vous n'avez sélectionné aucun créneau dans la liste.");
            a.showAndWait();
        }
    }

    @FXML
    public void clicBoutonSupprimerCreneau() {
        Creneau c = listviewCreneaux.getSelectionModel().getSelectedItem();
        if (c != null) {
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
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Attention");
            a.setContentText("Vous n'avez sélectionné aucun créneau dans la liste.");
            a.showAndWait();
        }
    }
    @FXML
    public void clicBoutonEnregistrerCreneau() {
        // Récupération des données des champs texte
        String heure = heureField.getText();
        String date = dateField.getText();
        String duree = dureeField.getText();
        String nbPlaces = nbplaceField.getText();

        // Validation des entrées
        if (heure.isEmpty() || date.isEmpty() || duree.isEmpty() || nbPlaces.isEmpty()) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Avertissement");
            a.setContentText("Veuillez remplir tous les champs.");
            a.showAndWait();
            return;
        }

        // Récupération de l'animation sélectionnée
        Animation animationSelectionnee = liteViewAnimation.getSelectionModel().getSelectedItem();
        int idAnimation = animationSelectionnee != null ? animationSelectionnee.getIdAnimation() : -1;

        // Récupération du lieu sélectionné
        Lieu lieuSelectionne = listviewLieuCreneau.getSelectionModel().getSelectedItem();
        int idLieu = lieuSelectionne != null ? lieuSelectionne.getIdLieu() : -1;

        // Création ou modification d'un objet Creneau
        LocalTime heureCr = LocalTime.parse(heure);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateCr = LocalDate.parse(date, dateFormatter);

        // Utilisation de idTemporaire si en mode édition
        Creneau c = new Creneau(isedit ? idTemporaire : 0, heureCr, dateCr, Integer.parseInt(duree), Integer.parseInt(nbPlaces), idAnimation, idLieu);

        // Enregistrement du créneau
        boolean resultat = c.save();
        isedit=false;
        idTemporaire=0;
        if (resultat) {
            // Récupération de l'ID du créneau enregistré
            int idCreneau = c.getIdCreneau();

            // Gestion des animateurs associés
            ObservableList<Animateur> animateursSelectionnes = listviewAnimateurCreneau.getSelectionModel().getSelectedItems();

            // Ajout des animateurs liés
            for (Animateur animateur : animateursSelectionnes) {
                updateAnimateurCreneau(animateur.getIdAnimateur(), idCreneau);
            }

            // Afficher une alerte de succès
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle("Succès");
            a.setContentText("Le créneau a été enregistré avec succès.");
            a.showAndWait();

            // Rafraîchir la liste des créneaux
            affichageListeViewCreneau();
            heureField.setText("");
            dateField.setText("");
            dureeField.setText("");
            nbplaceField.setText("");
            liteViewAnimation.getSelectionModel().clearSelection();
            listviewLieuCreneau.getSelectionModel().clearSelection();
            listviewAnimateurCreneau.getSelectionModel().clearSelection();
        } else {
            // Afficher une alerte d'erreur
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Erreur");
            a.setContentText("Une erreur est survenue pendant l'enregistrement.");
            a.showAndWait();
        }
    }

    // Méthode pour mettre à jour l'association animateur-créneau
    private void updateAnimateurCreneau(int idAnimateur, int idCreneau) {
        Connection c = ConnexionBDD.initialiserConnexion();
        if (c != null) {
            try {
                // Vérifier si l'association existe déjà
                String checkQuery = "SELECT COUNT(*) AS count FROM animer WHERE idAnimateur = ? AND idCreneau = ?";
                PreparedStatement checkStmt = c.prepareStatement(checkQuery);
                checkStmt.setInt(1, idAnimateur);
                checkStmt.setInt(2, idCreneau);
                ResultSet rs = checkStmt.executeQuery();
                rs.next();
                int count = rs.getInt("count");

                // Si l'association n'existe pas, l'ajouter
                if (count == 0) {
                    String insertQuery = "INSERT INTO animer (idAnimateur, idCreneau) VALUES (?, ?)";
                    PreparedStatement insertStmt = c.prepareStatement(insertQuery);
                    insertStmt.setInt(1, idAnimateur);
                    insertStmt.setInt(2, idCreneau);
                    insertStmt.executeUpdate();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Erreur");
                a.setContentText("Erreur d'association animateur-créneau : " + ex.getMessage());
                a.showAndWait();
            } finally {
                // Fermer la connexion
                try {
                    if (c != null) {
                        c.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @FXML
    public void clicBoutonAnnulerCreneau() {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setContentText("Êtes-vous sûr de vouloir annuler les modifications en cours ?");
        ButtonType yesButton = new ButtonType("Oui", ButtonType.YES.getButtonData());
        ButtonType noButton = new ButtonType("Non", ButtonType.NO.getButtonData());
        confirmation.getButtonTypes().setAll(yesButton, noButton);
        confirmation.showAndWait();

        if (confirmation.getResult() == yesButton) {
            heureField.setText("");
            dateField.setText("");
            dureeField.setText("");
            nbplaceField.setText("");
            liteViewAnimation.getSelectionModel().clearSelection();
            listviewLieuCreneau.getSelectionModel().clearSelection();
            listviewAnimateurCreneau.getSelectionModel().clearSelection();
            isedit=false;
            idTemporaire=0;
        }
    }
}
