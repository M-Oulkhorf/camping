package com.example.camping;

import javafx.scene.control.Alert;
import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Creneau {
    private int idCreneau;
    private LocalTime heureCreneau;
    private LocalDate dateCreneau;
    private int dureeCreneau;
    private int nbPlacesCreneau;
    private int idAnimation;
    private int idLieu;

    public Creneau(int idCreneau, LocalTime heureCreneau, LocalDate dateCreneau, int dureeCreneau, int nbPlacesCreneau, int idAnimation, int idLieu) {
        this.idCreneau = idCreneau;
        this.heureCreneau = heureCreneau;
        this.dateCreneau = dateCreneau;
        this.dureeCreneau = dureeCreneau;
        this.nbPlacesCreneau = nbPlacesCreneau;
        this.idAnimation = idAnimation;
        this.idLieu = idLieu;
    }

    // Getters and Setters
    public int getIdCreneau() {
        return idCreneau;
    }

    public void setIdCreneau(int idCreneau) {
        this.idCreneau = idCreneau;
    }

    public LocalTime getHeureCreneau() {
        return heureCreneau;
    }

    public void setHeureCreneau(LocalTime heureCreneau) {
        this.heureCreneau = heureCreneau;
    }

    public LocalDate getDateCreneau() {
        return dateCreneau;
    }

    public void setDateCreneau(LocalDate dateCreneau) {
        this.dateCreneau = dateCreneau;
    }

    public int getDureeCreneau() {
        return dureeCreneau;
    }

    public void setDureeCreneau(int dureeCreneau) {
        this.dureeCreneau = dureeCreneau;
    }

    public int getNbPlacesCreneau() {
        return nbPlacesCreneau;
    }

    public void setNbPlacesCreneau(int nbPlacesCreneau) {
        this.nbPlacesCreneau = nbPlacesCreneau;
    }

    public int getIdAnimation() {
        return idAnimation;
    }

    public void setIdAnimation(int idAnimation) {
        this.idAnimation = idAnimation;
    }

    public int getIdLieu() {
        return idLieu;
    }

    public void setIdLieu(int idLieu) {
        this.idLieu = idLieu;
    }

    // Méthode pour récupérer tous les créneaux à partir de la base de données
    public static ArrayList<Creneau> getAll() {
        Connection c = ConnexionBDD.initialiserConnexion();
        ArrayList<Creneau> lesCreneaux = new ArrayList<>();

        if(c != null) {
            try {
                String requete = "SELECT * FROM creneau WHERE dateCreneau >= CURRENT_DATE";
                Statement stmt = c.createStatement();
                ResultSet res = stmt.executeQuery(requete);

                while (res.next()) {
                    int idCreneau = res.getInt("idCreneau");
                    LocalDate _dateCreneau = res.getDate("dateCreneau").toLocalDate();
                    LocalTime _heureCreneau = res.getTime("heureCreneau").toLocalTime();
                    int _dureeCreneau = res.getInt("dureeCreneau");
                    int _nbPlacesCreneau = res.getInt("nbPlacesCreneau");
                    int _idAnimation = res.getInt("idAnimation");
                    int _idLieu = res.getInt("idLieu");

                    Creneau a = new Creneau(idCreneau, _heureCreneau, _dateCreneau, _dureeCreneau, _nbPlacesCreneau, _idAnimation, _idLieu);
                    lesCreneaux.add(a);
                }
            } catch (SQLException ex) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Erreur");
                a.setContentText("Erreur survenue : " + ex.getMessage());
                a.showAndWait();
            }
        }

        return lesCreneaux;
    }
    public static ArrayList<Creneau> getAllWeek() {
        Connection c = ConnexionBDD.initialiserConnexion();
        ArrayList<Creneau> lesCreneaux = new ArrayList<>();

        if (c != null) {
            try {
                LocalDate debutSemaine = LocalDate.now().with(DayOfWeek.MONDAY);
                LocalDate finSemaine = LocalDate.now().with(DayOfWeek.SUNDAY);
                String requete = "SELECT * FROM creneau WHERE dateCreneau BETWEEN ? AND ?";
                PreparedStatement stmt = c.prepareStatement(requete);
                stmt.setDate(1, Date.valueOf(debutSemaine));
                stmt.setDate(2, Date.valueOf(finSemaine));
                ResultSet res = stmt.executeQuery();

                while (res.next()) {
                    int idCreneau = res.getInt("idCreneau");
                    LocalDate _dateCreneau = res.getDate("dateCreneau").toLocalDate();
                    LocalTime _heureCreneau = res.getTime("heureCreneau").toLocalTime();
                    int _dureeCreneau = res.getInt("dureeCreneau");
                    int _nbPlacesCreneau = res.getInt("nbPlacesCreneau");
                    int _idAnimation = res.getInt("idAnimation");
                    int _idLieu = res.getInt("idLieu");

                    Creneau a = new Creneau(idCreneau, _heureCreneau, _dateCreneau, _dureeCreneau, _nbPlacesCreneau, _idAnimation, _idLieu);
                    lesCreneaux.add(a);
                }
            } catch (SQLException ex) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Erreur");
                a.setContentText("Erreur survenue : " + ex.getMessage());
                a.showAndWait();
            }
        }

        return lesCreneaux;
    }

    // Méthode pour enregistrer ou mettre à jour un créneau
    public boolean save() {
        Connection c = ConnexionBDD.initialiserConnexion();
        if (c != null) {
            try {
                // Vérifier si le créneau existe déjà
                String checkQuery = "SELECT COUNT(*) AS existe FROM creneau WHERE idCreneau = ?";
                PreparedStatement checkStmt = c.prepareStatement(checkQuery);
                checkStmt.setInt(1, this.idCreneau);
                ResultSet res = checkStmt.executeQuery();
                res.next();
                int exists = res.getInt("existe");
                if (exists == 0) {
                    // Si le créneau n'existe pas, l'insérer
                    String insertQuery = "INSERT INTO creneau (heureCreneau, dateCreneau, dureeCreneau, nbPlacesCreneau, idAnimation, idLieu) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement insertStmt = c.prepareStatement(insertQuery);
                    insertStmt.setTime(1, Time.valueOf(this.heureCreneau));
                    insertStmt.setDate(2, Date.valueOf(this.dateCreneau));
                    insertStmt.setInt(3, this.dureeCreneau);
                    insertStmt.setInt(4, this.nbPlacesCreneau);
                    insertStmt.setInt(5, this.idAnimation);
                    insertStmt.setInt(6, this.idLieu);
                    insertStmt.executeUpdate();
                } else {
                    // Si le créneau existe, le mettre à jour
                    String updateQuery = "UPDATE creneau SET heureCreneau = ?, dateCreneau = ?, dureeCreneau = ?, nbPlacesCreneau = ?, idAnimation = ?, idLieu = ? WHERE idCreneau = ?";
                    PreparedStatement updateStmt = c.prepareStatement(updateQuery);
                    updateStmt.setTime(1, Time.valueOf(this.heureCreneau));
                    updateStmt.setDate(2, Date.valueOf(this.dateCreneau));
                    updateStmt.setInt(3, this.dureeCreneau);
                    updateStmt.setInt(4, this.nbPlacesCreneau);
                    updateStmt.setInt(5, this.idAnimation);
                    updateStmt.setInt(6, this.idLieu);
                    updateStmt.setInt(7, this.idCreneau);
                    updateStmt.executeUpdate();
                }

                return true;
            } catch (SQLException ex) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Erreur");
                a.setContentText("Erreur survenue : " + ex.getMessage());
                a.showAndWait();
                return false;
            }
        } else {
            return false;
        }
    }

    // Méthode pour supprimer un créneau
    public boolean delete() {
        Connection c = ConnexionBDD.initialiserConnexion();
        if (c != null) {
            try {
                String deleteQuery = "DELETE FROM creneau WHERE idCreneau = ?";
                PreparedStatement deleteStmt = c.prepareStatement(deleteQuery);
                deleteStmt.setInt(1, this.idCreneau);
                int result = deleteStmt.executeUpdate();

                return result > 0;
            } catch (SQLException ex) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Erreur");
                a.setContentText("Erreur survenue : " + ex.getMessage());
                a.showAndWait();
                return false;
            }
        } else {
            return false;
        }
    }

    // Redéfinition de la méthode toString pour afficher un créneau de façon lisible
    @Override
    public String toString() {
        return this.dateCreneau + " " + this.heureCreneau + " | " + this.dureeCreneau + " min | " + this.nbPlacesCreneau + " places | Animation: " + this.idAnimation + " | Lieu: " + this.idLieu;
    }
}
