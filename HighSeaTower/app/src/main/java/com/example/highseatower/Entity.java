package com.example.highseatower;

import android.graphics.Canvas;

/**
 * Représente une entité dans le jeu
 */
public abstract class Entity {
    protected double largeur, hauteur;
    protected double posX, posY;
    protected double vx = 0, vy = 0;
    protected double ax = 0, ay = 0;

    protected int color;


    /**
     * Détermine s'il y a intersection entre cette entité et une autre
     * @param other l'autre entité
     * @return true s'il y a intersection, false sinon
     */
    public boolean intersects(Entity other) {
        return !( // Un des carrés est à gauche de l’autre
                other.posX + other.largeur < posX
                || posX + largeur < other.posX
                // Un des carrés est en haut de l’autre
                || other.posY + other.hauteur < posY
                || posY + hauteur < other.posY);
    }


    /**
     * Met à jour la position et la vitesse de l'entité
     * @param dt Temps écoulé depuis le dernier update() en secondes
     */
    public void update(double dt) {
        vx += dt * ax;
        vy += dt * ay;
        posX += dt * vx;
        posY += dt * vy;

        // Force à rester dans les bornes de l'écran
        if (posX + largeur > HighSeaTower.WIDTH || posX < 0) {
            vx *= -1;
        }

        posX = Math.min(posX, HighSeaTower.WIDTH - largeur);
        posX = Math.max(posX, 0);
    }


    /**
     * Dessine l'entité sur le canvas
     * @param canvas canvas sur laquelle dessiner
     * @param fenetreX origine X de la fenêtre
     * @param fenetreY origine Y de la fenêtre
     */
    public abstract void draw(Canvas canvas, double fenetreX, double fenetreY);


    // Getters et setters

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public double getAx() {
        return ax;
    }

    public double getAy() {
        return ay;
    }
}
