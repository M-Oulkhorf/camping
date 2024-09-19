package com.example.camping;

public class Lieu {
    private int idLieu;
    private String libelleLieu;
    private String cordoneesLieu;

    public Lieu(int idLieu, String libelleLieu, String cordoneesLieu) {
        this.idLieu = idLieu;
        this.libelleLieu = libelleLieu;
        this.cordoneesLieu = cordoneesLieu;
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
        return cordoneesLieu;
    }

    public void setCordoneesLieu(String cordoneesLieu) {
        this.cordoneesLieu = cordoneesLieu;
    }
}
