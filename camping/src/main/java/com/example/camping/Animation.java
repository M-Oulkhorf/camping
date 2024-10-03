package com.example.camping;

import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;

public class Animation {
    private int idAnimation;
    private String libelleAnimation;

    public Animation(int idAnimation, String libelleAnimation) {
        this.idAnimation = idAnimation;
        this.libelleAnimation = libelleAnimation;
    }

    // Getters et setters
    public int getIdAnimation() {
        return idAnimation;
    }

    public void setIdAnimation(int idAnimation) {
        this.idAnimation = idAnimation;
    }

    public String getLibelleAnimation() {
        return libelleAnimation;
    }

    public void setLibelleAnimation(String libelleAnimation) {
        this.libelleAnimation = libelleAnimation;
    }

    // Méthode pour récupérer toutes les animations
    public static ArrayList<Animation> getAll() {
        Connection c = ConnexionBDD.initialiserConnexion();
        ArrayList<Animation> lesAnimations = new ArrayList<>();
        if (c != null) {
            try {
                String requete = "SELECT * FROM animation";
                Statement stmt = c.createStatement();
                ResultSet res = stmt.executeQuery(requete);

                while (res.next()) {
                    int _idAnimation = res.getInt("idAnimation");
                    String _libelleAnimation = res.getString("libelleAnimation");
                    Animation anim = new Animation(_idAnimation, _libelleAnimation);
                    lesAnimations.add(anim);
                }
            } catch (SQLException ex) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Erreur");
                a.setContentText("Erreur survenue : " + ex.getMessage());
                a.showAndWait();
            }
        }

        return lesAnimations;
    }

    // Méthode pour enregistrer ou mettre à jour une animation
    public boolean save() {
        Connection c = ConnexionBDD.initialiserConnexion();
        if (c != null) {
            try {
                // Vérifier si l'animation existe déjà
                String requete1 = "SELECT COUNT(*) AS existe FROM animation WHERE idAnimation = ?";
                PreparedStatement prep1 = c.prepareStatement(requete1);
                prep1.setInt(1, this.idAnimation);
                ResultSet resultats = prep1.executeQuery();
                resultats.next();
                int nb = resultats.getInt("existe");

                if (nb == 0) {
                    // L'animation n'existe pas, on l'insère
                    String requete2 = "INSERT INTO animation (libelleAnimation) VALUES (?)";
                    PreparedStatement prep2 = c.prepareStatement(requete2);
                    prep2.setString(1, this.libelleAnimation);
                    int etat = prep2.executeUpdate();
                    return etat > 0; // Retourne vrai si l'insertion a réussi
                } else {
                    // L'animation existe déjà, afficher une alerte
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Duplicata");
                    a.setContentText("L'animation est déjà enregistrée!");
                    a.showAndWait();
                    return false;
                }

            } catch (SQLException ex) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Erreur");
                a.setContentText("Erreur survenue : " + ex.getMessage());
                a.showAndWait();
                return false; // Retourne faux en cas d'erreur
            }
        } else {
            return false;
        }
    }
    @Override
    public String toString() {
        return this.libelleAnimation;
    }
}