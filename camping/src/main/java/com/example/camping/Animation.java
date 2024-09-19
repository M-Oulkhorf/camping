package com.example.camping;

public class Animation {
    private int idAnimation;
    private String libelleAnimation;

    public Animation(int idAnimation, String libelleAnimation) {
        this.idAnimation = idAnimation;
        this.libelleAnimation = libelleAnimation;
    }

    public int getIdAnimation() {
        return idAnimation;
    }

    public void setIdAnimation(int idAnimation) {
        this.idAnimation = idAnimation;
    }

    public String getLibelleAnimation() {
        return libelleAnimation;
    }

    public void setLibelleAnimation(String libelleAnimation) {
        this.libelleAnimation = libelleAnimation;
    }
}