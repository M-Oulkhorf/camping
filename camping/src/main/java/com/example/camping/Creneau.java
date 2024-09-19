package com.example.camping;
import java.time.LocalDate;
import java.time.LocalTime;

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
}