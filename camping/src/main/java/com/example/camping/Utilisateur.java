package com.example.camping;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Utilisateur {
    private String identifiant;
    private String mdp;

    public Utilisateur(String identifiant, String mdp) {
        this.identifiant = identifiant;
        this.mdp = hacherMotDePasse(mdp);
    }

    private String hacherMotDePasse(String motDePasse) {
        return BCrypt.hashpw(motDePasse, BCrypt.gensalt());
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public String getMdp() {
        return mdp;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public void setMdp(String mdp) {
        this.mdp = hacherMotDePasse(mdp);
    }

    public boolean save() {
        Connection connection = ConnexionBDD.initialiserConnexion();
        if (connection != null) {
            String checkQuery = "SELECT COUNT(*) AS count FROM utilisateur WHERE identifiant = ?";
            String insertQuery = "INSERT INTO utilisateur (identifiant, mdp) VALUES (?, ?)";

            try {
                // Vérifier si l'utilisateur existe déjà
                PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
                checkStmt.setString(1, this.identifiant);
                ResultSet checkResult = checkStmt.executeQuery();
                checkResult.next();
                int count = checkResult.getInt("count");

                if (count > 0) {
                    // L'utilisateur existe déjà, on ne l'enregistre pas
                    System.out.println("L'utilisateur existe déjà.");
                    return false;
                } else {
                    // Insérer le nouvel utilisateur dans la base de données
                    PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
                    insertStmt.setString(1, this.identifiant);
                    insertStmt.setString(2, this.mdp);
                    int rowsInserted = insertStmt.executeUpdate();
                    return rowsInserted > 0; // Retourne true si l'insertion a réussi
                }
            } catch (SQLException ex) {
                System.err.println("Erreur lors de l'enregistrement de l'utilisateur : " + ex.getMessage());
                return false;
            } finally {
                try {
                    connection.close(); // Fermez la connexion
                } catch (SQLException e) {
                    System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
                }
            }
        }
        return false; // Retourne false si la connexion à la base de données a échoué
    }
}