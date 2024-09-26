package com.example.camping;
import javafx.scene.control.Alert;

import java.sql.*;
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
    public static ArrayList<Creneau> getAll() {
        Connection c = ConnexionBDD.initialiserConnexion();
        ArrayList<Creneau> lesCrenaux = new ArrayList<Creneau>();

        if(c != null) {
            try {
                String requete = "SELECT * FROM creneau WHERE dateCreneau >= CURRENT_DATE";
                Statement stmt = c.createStatement();
                ResultSet res = stmt.executeQuery(requete);

                while (res.next())
                {
                    LocalDate _dateCreneau = res.getDate("dateCreneau").toLocalDate();
                    LocalTime _heureCreneau = res.getTime("dateCreneau").toLocalTime();
                    int _dureeCreneau = res.getInt("dureeCreneau");
                    int _nbPlacesCreneau = res.getInt("nbPlacesCreneau");
                    Creneau a = new Creneau(1,_heureCreneau, _dateCreneau, _dureeCreneau, _nbPlacesCreneau, 0,0);
                    lesCrenaux.add(a);
                }
            }
            catch (SQLException ex) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Erreur");
                a.setContentText("Erreur survenue : " + ex.getMessage());
                a.showAndWait();
            }
        }

        return lesCrenaux;
    }
    public boolean save() {
        Connection c = ConnexionBDD.initialiserConnexion();
        if (c != null) {
            int etat = 0;
            try {
                String requete1 = "INSERT INTO creneau (heureCreneau, dateCreneau, dureeCreneau, nbPlacesCreneau, idAnimation, idLieu) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement prep = c.prepareStatement(requete1);
                prep.setTime(1, Time.valueOf(this.heureCreneau));
                prep.setDate(2, Date.valueOf(this.dateCreneau));
                prep.setInt(3, this.dureeCreneau);
                prep.setInt(4, this.nbPlacesCreneau);
                prep.setInt(5, this.idAnimation);
                prep.setInt(6, this.idLieu);
                ResultSet resultats = prep.executeQuery();
                resultats.next();
                String requete2 = "SELECT idCreneau FROM `` WHERE heureCreneau=? and dateCreneau=?;";
                PreparedStatement prep2 = c.prepareStatement(requete2);
                prep2.setTime(1, Time.valueOf(this.heureCreneau));
                prep2.setDate(2, Date.valueOf(this.dateCreneau));
                ResultSet res = prep2.executeQuery();
                res.next();
                int _idCreneau = res.getInt("idCreneau");
                this.idCreneau = _idCreneau;
                return etat != 0;
            }
            catch (SQLException ex) {
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
    @Override
    public String toString() {
        return this.dateCreneau + " " + this.heureCreneau +" | "+ this.dureeCreneau + "min | " +this.nbPlacesCreneau+" places | "+ this.idAnimation;
    }
}