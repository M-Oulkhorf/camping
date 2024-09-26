package com.example.camping;

import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;

import static java.lang.StringTemplate.STR;

public class Animateur {
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
                String requete = "SELECT * FROM animateur ORDER BY id ASC";
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
    public static Animateur getByIdAnimateur(String idEspeceRecherche) {
        Connection c = ConnexionBDD.initialiserConnexion();
        Animateur unAnimateur = null;

        if(c != null) {
            try {
                String requete = "SELECT * FROM espece WHERE idAnimateur = ?";
                Statement stmt = c.createStatement();
                PreparedStatement prep = c.prepareStatement(requete);
                prep.setString(1, idEspeceRecherche);
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
                String requete1 = "SELECT COUNT(*) AS existe FROM espece WHERE id = ?";

                Statement stmt = c.createStatement();
                PreparedStatement prep = c.prepareStatement(requete1);
                prep.setString(1, this.id);
                ResultSet resultats = prep.executeQuery();
                resultats.next();

                int nb = resultats.getInt("existe");

                if(nb == 0) {
                    // L'espèce n'existe pas : on la créer
                    String requete2 = "INSERT INTO espece (id, nom, nomScientifique, idContinent) VALUES (?, ?, ?, ?)";
                    Statement stmt2 = c.createStatement();
                    PreparedStatement prep2 = c.prepareStatement(requete2);
                    prep2.setString(1, this.id);
                    prep2.setString(2, this.nom);
                    prep2.setString(3, this.nomScientifique);
                    prep2.setString(4, this.idContinent);
                    etat = prep2.executeUpdate();
                }
                else {
                    // L'espèce existe déjà : on la modifie
                    String requete2 = "UPDATE espece SET nom = ?, nomScientifique = ?, idContinent = ? WHERE id = ?";
                    Statement stmt2 = c.createStatement();
                    PreparedStatement prep2 = c.prepareStatement(requete2);
                    prep2.setString(1, this.nom);
                    prep2.setString(2, this.nomScientifique);
                    prep2.setString(3, this.idContinent);
                    prep2.setString(4, this.id);
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
                a.setContentText(STR."Erreur survenue : \{ex.getMessage()}");
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
                String requete2 = "DELETE FROM arbre WHERE idEspece = ?";

                Statement stmt2 = c.createStatement();
                PreparedStatement prep2 = c.prepareStatement(requete2);
                prep2.setString(1, this.id);
                prep2.executeUpdate();

                String requete1 = "DELETE FROM espece WHERE id = ?";

                Statement stmt = c.createStatement();
                PreparedStatement prep = c.prepareStatement(requete1);
                prep.setString(1, this.id);
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
                a.setContentText(STR."Erreur survenue : \{ex.getMessage()}");
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
        return STR."\{this.id} - \{this.nom} (\{this.nomScientifique})";
    }
}


}
