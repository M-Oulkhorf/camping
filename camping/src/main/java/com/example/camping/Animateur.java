package com.example.camping;

/* vu par M. le développeur associé
Et par moi aussi
 */

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
}
