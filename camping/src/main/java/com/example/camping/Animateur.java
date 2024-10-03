package com.example.camping;

import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;



public class Animateur {
    //sqdfyqkuikre
    private int idAnimateur;
    private String nomAnimateur;
    private String prenomAnimateur;
    private String mailAnimateur;
    private String telephoneAnimateur;

    public Animateur(int idAnimateur, String nomAnimateur, String prenomAnimateur, String mailAnimateur, String telephoneAnimateur) {
        this.idAnimateur = idAnimateur;
        this.nomAnimateur = nomAnimateur;
        this.prenomAnimateur = prenomAnimateur;
        this.mailAnimateur = mailAnimateur;
        this.telephoneAnimateur = telephoneAnimateur;
    }

    public int getIdAnimateur() {
        return idAnimateur;
    }

    public void setIdAnimateur(int idAnimateur) {
        this.idAnimateur = idAnimateur;
    }

    public String getNomAnimateur() {
        return nomAnimateur;
    }

    public void setNomAnimateur(String nomAnimateur) {
        this.nomAnimateur = nomAnimateur;
    }

    public String getPrenomAnimateur() {
        return prenomAnimateur;
    }

    public void setPrenomAnimateur(String prenomAnimateur) {
        this.prenomAnimateur = prenomAnimateur;
    }

    public String getMailAnimateur() {
        return mailAnimateur;
    }

    public void setMailAnimateur(String mailAnimateur) {
        this.mailAnimateur = mailAnimateur;
    }

    public String getTelephoneAnimateur() {
        return telephoneAnimateur;
    }

    public void setTelephoneAnimateur(String telephoneAnimateur) {
        this.telephoneAnimateur = telephoneAnimateur;
    }

    public static ArrayList<Animateur> getAllAnimateur() {
        Connection c = ConnexionBDD.initialiserConnexion();
        ArrayList<Animateur> lesAnimateurs = new ArrayList<Animateur>();

        if(c != null) {
            try {
                String requete = "SELECT * FROM animateur ORDER BY idAnimateur ASC";
                Statement stmt = c.createStatement();
                ResultSet res = stmt.executeQuery(requete);

                while (res.next())
                {
                    int _idAnimateur = Integer.parseInt(res.getString("idAnimateur"));
                    String _nomAnimateur = res.getString("nomAnimateur");
                    String _prenomAnimateur = res.getString("prenomAnimateur");
                    String _mailAnimateur = res.getString("mailAnimateur");
                    String _telephoneAnimateur = res.getString("telephoneAnimateur");
                    Animateur a = new Animateur(_idAnimateur, _nomAnimateur, _prenomAnimateur, _mailAnimateur,_telephoneAnimateur);
                    lesAnimateurs.add(a);
                }
            }
            catch (SQLException ex) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Erreur");
                a.setContentText("Erreur survenue : "+ex.getMessage());
                a.showAndWait();
            }
        }

        return lesAnimateurs;
    }
    public static Animateur getByIdAnimateur(int idAnimateurRecherche) {
        Connection c = ConnexionBDD.initialiserConnexion();
        Animateur unAnimateur = null;

        if(c != null) {
            try {
                String requete = "SELECT * FROM animateur WHERE idAnimateur = ?";
                Statement stmt = c.createStatement();
                PreparedStatement prep = c.prepareStatement(requete);
                prep.setInt(1, idAnimateurRecherche);
                ResultSet res = prep.executeQuery();

                res.next();

                int _idAnimateur = Integer.parseInt(res.getString("idAnimateur"));
                String _nomAnimateur = res.getString("nomAnimateur");
                String _prenomAnimateur = res.getString("prenomAnimateur");
                String _mailAnimateur = res.getString("mailAnimateur");
                String _telephoneAnimateur = res.getString("telephoneAnimateur");
                unAnimateur = new Animateur(_idAnimateur, _nomAnimateur, _prenomAnimateur, _mailAnimateur,_telephoneAnimateur);
            }
            catch (SQLException ex) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Erreur");
                a.setContentText("Erreur survenue : "+ex.getMessage());
                a.showAndWait();
            }
        }

        return unAnimateur;
    }

    public boolean saveAnimateur() {
        Connection c = ConnexionBDD.initialiserConnexion();
        if(c != null) {
            int etat = 0;
            try {
                String requete1 = "SELECT COUNT(*) AS existe FROM animateur WHERE idAnimateur = ?";

                Statement stmt = c.createStatement();
                PreparedStatement prep = c.prepareStatement(requete1);
                prep.setInt(1, this.idAnimateur);
                ResultSet resultats = prep.executeQuery();
                resultats.next();

                int nb = resultats.getInt("existe");

                if(nb == 0) {
                    // L'animateur n'existe pas : on la créer
                    String requete2 = "INSERT INTO animateur (nomAnimateur, PrenomAnimateur, mailAnimateur,telephoneAnimateur) VALUES (?, ?, ?, ?)";
                    Statement stmt2 = c.createStatement();
                    PreparedStatement prep2 = c.prepareStatement(requete2);
                    prep2.setString(1, this.nomAnimateur);
                    prep2.setString(2, this.prenomAnimateur);
                    prep2.setString(3, this.mailAnimateur);
                    prep2.setString(4, this.telephoneAnimateur);
                    etat = prep2.executeUpdate();
                    String requete3 = "SELECT idAnimateur FROM animateur WHERE nomAnimateur=? Order by idAnimateur ASC;";
                    PreparedStatement prep3 = c.prepareStatement(requete3);
                    prep3.setString(1, this.nomAnimateur);
                    ResultSet res = prep3.executeQuery();
                    res.next();
                    int _idAnimateur = res.getInt("idAnimateur");
                    this.idAnimateur = _idAnimateur;
                }
                else {

                    String requete2 = "UPDATE animateur SET nomAnimateur = ?, PrenomAnimateur = ?, mailAnimateur = ?,telephoneAnimateur=? WHERE idAnimateur = ?";
                    Statement stmt2 = c.createStatement();

                    PreparedStatement prep2 = c.prepareStatement(requete2);
                    prep2.setString(1, this.nomAnimateur);
                    prep2.setString(2, this.prenomAnimateur);
                    prep2.setString(3, this.mailAnimateur);
                    prep2.setString(4, this.telephoneAnimateur);
                    prep2.setInt(5, this.idAnimateur);
                    etat = prep2.executeUpdate();
                }

                if(etat == 0) {
                    return false;
                }
                else {
                    return true;
                }
            }
            catch(SQLException ex) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Erreur");
                a.setContentText("Erreur survenue : "+ex.getMessage());
                a.showAndWait();
                return false;
            }
        }
        else {
            return false;
        }
    }

    public boolean delete() {
        Connection c = ConnexionBDD.initialiserConnexion();
        if(c != null) {
            try {
                String requete2 = "DELETE FROM animer WHERE idAnimateur = ?";

                Statement stmt2 = c.createStatement();
                PreparedStatement prep2 = c.prepareStatement(requete2);
                prep2.setInt(1, this.idAnimateur);
                prep2.executeUpdate();

                String requete1 = "DELETE FROM animateur WHERE idAnimateur = ?";

                Statement stmt = c.createStatement();
                PreparedStatement prep = c.prepareStatement(requete1);
                prep.setInt(1, this.idAnimateur);
                int resultat = prep.executeUpdate();

                if(resultat == 0) {
                    return false;
                }
                else {
                    return true;
                }
            }
            catch(SQLException ex) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Erreur");
                a.setContentText("Erreur survenue : "+ex.getMessage());
                a.showAndWait();
                return false;
            }
        }
        else {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.nomAnimateur+" "+this.prenomAnimateur+" "+this.mailAnimateur+" "+this.telephoneAnimateur;
    }

}
