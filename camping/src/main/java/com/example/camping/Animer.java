package com.example.camping;

public class Animer {
    private int idAnimateur;
    private int idCreneau;

    public Animer(int idAnimateur, int idCreneau) {
        this.idAnimateur = idAnimateur;
        this.idCreneau = idCreneau;
    }

    public int getIdAnimateur() {
        return idAnimateur;
    }

    public void setIdAnimateur(int idAnimateur) {
        this.idAnimateur = idAnimateur;
    }

    public int getIdCreneau() {
        return idCreneau;
    }

    public void setIdCreneau(int idCreneau) {
        this.idCreneau = idCreneau;
    }
}