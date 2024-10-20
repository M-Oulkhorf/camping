package com.example.camping;

import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;


public class Lieu {
    private int idLieu;
    private String libelleLieu;
    private String coordoneesLieu;

    public Lieu(int idLieu, String libelleLieu, String cordoneesLieu) {
        this.idLieu = idLieu;
        this.libelleLieu = libelleLieu;
        this.coordoneesLieu = cordoneesLieu;
    }

    public int getIdLieu() {
        return idLieu;
    }

    public void setIdLieu(int idLieu) {
        this.idLieu = idLieu;
    }

    public String getLibelleLieu() {
        return libelleLieu;
    }

    public void setLibelleLieu(String libelleLieu) {
        this.libelleLieu = libelleLieu;
    }

    public String getCordoneesLieu() {
        return coordoneesLieu;
    }

    public void setCordoneesLieu(String cordoneesLieu) {
        this.coordoneesLieu = cordoneesLieu;
    }

    public static ArrayList<Lieu> getAll() {
        Connection c = ConnexionBDD.initialiserConnexion();
        ArrayList<Lieu> leslieux = new ArrayList<Lieu>();
        if (c != null) {
            try {
                String requete = "SELECT * FROM lieu ORDER BY idLieu ASC";
                Statement stmt = c.createStatement();
                ResultSet res = stmt.executeQuery(requete);

                while (res.next()) {
                    int _idLieu = res.getInt("idLieu");
                    String _libelleLieu = res.getString("libelleLieu");
                    String _cordoneesLieu = res.getString("coordoneesLieu");
                    Lieu e = new Lieu(_idLieu, _libelleLieu, _cordoneesLieu);
                    leslieux.add(e);
                }
            } catch (SQLException ex) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Erreur");
                a.setContentText("Erreur survenue : " + ex.getMessage());
                a.showAndWait();
            }
        }

        return leslieux;
    }

    public static Lieu getById(Integer idLieuRecherche) {
        Connection c = ConnexionBDD.initialiserConnexion();
        Lieu unLieu = null;

        if (c != null) {
            try {
                String requete = "SELECT * FROM Lieu WHERE idLieu = ?";
                Statement stmt = c.createStatement();
                PreparedStatement prep = c.prepareStatement(requete);
                prep.setInt(1, idLieuRecherche);
                ResultSet res = prep.executeQuery();

                while (res.next()) {
                    int _idLieu = res.getInt("idLieu");
                    String _libelleLieu = res.getString("libelleLieu");
                    String _cordoneesLieu = res.getString("coordoneesLieu");
                    unLieu = new Lieu(_idLieu, _libelleLieu, _cordoneesLieu);
                }
            } catch (SQLException ex) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Erreur");
                a.setContentText("Erreur survenue : " + ex.getMessage());
                a.showAndWait();
            }
        }

        return unLieu;
    }

    public boolean save() {
        Connection c = ConnexionBDD.initialiserConnexion();
        if (c != null) {
            int etat = 0;

            try {
                String requete1 = "SELECT COUNT(*) AS existe FROM Lieu WHERE idLieu = ?";

                Statement stmt = c.createStatement();
                PreparedStatement prep = c.prepareStatement(requete1);
                prep.setInt(1, this.idLieu);
                ResultSet resultats = prep.executeQuery();
                resultats.next();

                int nb = resultats.getInt("existe");

                if (nb == 0) {
                    // L'espèce n'existe pas : on la créer
                    String requete2 = "INSERT INTO Lieu (libelleLieu, coordoneesLieu) VALUES (?,?)";
                    Statement stmt2 = c.createStatement();
                    PreparedStatement prep2 = c.prepareStatement(requete2);
                    prep2.setString(1, this.libelleLieu);
                    prep2.setString(2, this.coordoneesLieu);
                    etat = prep2.executeUpdate();
                    String requete3 = "SELECT idLieu FROM lieu WHERE libelleLieu=? Order by idLieu ASC;";
                    PreparedStatement prep3 = c.prepareStatement(requete3);
                    prep3.setInt(1, this.idLieu);
                    ResultSet res = prep3.executeQuery();
                    res.next();
                    int _idLieu = res.getInt("idLieu");
                    this.idLieu = _idLieu;
                } else {
                    String requete2 = "UPDATE lieu SET libelleLieu = ?, coordoneesLieu = ? WHERE idLieu = ?";
                    Statement stmt2 = c.createStatement();
                    PreparedStatement prep2 = c.prepareStatement(requete2);
                    prep2.setString(1, this.libelleLieu);
                    prep2.setString(2, this.coordoneesLieu);
                    prep2.setInt(3, this.idLieu);
                    etat = prep2.executeUpdate();
                }

                if (etat == 0) {
                    return false;
                } else {
                    return true;
                }
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

    public boolean delete() {
        Connection c = ConnexionBDD.initialiserConnexion();
        if (c != null) {
            try {
                String requete2 = "DELETE FROM creneau WHERE idLieu= ?";

                Statement stmt2 = c.createStatement();
                PreparedStatement prep2 = c.prepareStatement(requete2);
                prep2.setInt(1, this.idLieu);
                prep2.executeUpdate();

                String requete1 = "DELETE FROM Lieu WHERE idLieu = ?";

                Statement stmt = c.createStatement();
                PreparedStatement prep = c.prepareStatement(requete1);
                prep.setInt(1, this.idLieu);
                int resultat = prep.executeUpdate();

                if (resultat == 0) {
                    return false;
                } else {
                    return true;
                }
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

    @Override
    public String toString() {
        return this.libelleLieu+" "+this.coordoneesLieu ;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Lieu that = (Lieu) obj;
        return idLieu == that.idLieu;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLieu);
    }
}




