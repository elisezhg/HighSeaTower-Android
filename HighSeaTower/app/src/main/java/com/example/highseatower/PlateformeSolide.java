package com.example.highseatower;

import android.graphics.Color;

public class PlateformeSolide extends Plateforme {

    /**
     * Constructeur de la plateforme
     * @param i numéro de la plateforme
     */
    public PlateformeSolide(int i) {
        super(i);
        this.color = Color.rgb(184, 15, 36);
    }


    /**
     * Teste si la méduse est en collision avec la plateforme
     * Si oui, on la repousse et on la fait rebondir
     * @param other Méduse du jeu
     */
    @Override
    public void testCollision(Meduse other) {
        if (intersects(other)) {
            pushOut(other);

            // La méduse tombe du haut sur la plateforme
            if (other.vy > 0) {
                other.vy = 0;

                // Sinon, elle rebondit
            } else {
                other.vy *= -0.8;
            }

            // La méduse rebondit sur les côtés
            if (other.vx > 0 && Math.abs(other.posX + other.largeur - posX) < 10) {
                other.vx *= -0.8;
            } else if (other.vx < 0 && Math.abs(other.posX - posX - largeur) < 10) {
                other.vx *= -0.8;
            }

            other.setParterre(true);
        }
    }


    /**
     * Repousse la méduse dans la direction adaptatée (sans déplacer la plateforme)
     * @param other la méduse
     */
    @Override
    public void pushOut(Meduse other) {

        // à gauche de la plateforme
        if (other.vx > 0 && Math.abs(other.posX + other.largeur - this.posX) < 10) {
            other.posX = this.posX - other.largeur;

            // à droite
        } else if (other.vx < 0 && Math.abs(other.posX - this.posX - this.largeur) < 10){
            other.posX = this.posX + this.largeur;

            // en haut
        } else if (other.vy > 0) {
            other.posY = this.posY - other.hauteur;

            // en bas
        } else {
            other.posY = this.posY + this.hauteur;
        }
    }
}
