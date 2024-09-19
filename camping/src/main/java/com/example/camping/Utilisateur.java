package com.example.camping;

public class Utilisateur {
    private String identifiant;
    private String mdp;

    public Utilisateur(String identifiant, String mdp) {
        this.identifiant = identifiant;
        this.mdp = mdp;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
}
