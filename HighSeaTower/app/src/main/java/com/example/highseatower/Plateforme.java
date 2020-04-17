package com.example.highseatower;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Plateforme extends Entity{

    /**
     * Constructeur de la plateforme
     * @param i numéro de la plateforme
     */
    public Plateforme(int i) {
        this.color = Color.rgb(230, 134, 58);
        this.hauteur = 10 * HighSeaTower.ratioY;
        this.largeur = Math.random() * 95 + 80;
        this.largeur *= HighSeaTower.ratioX;
        this.posX = Math.random() * (HighSeaTower.WIDTH - this.largeur);
        this.posY = HighSeaTower.HEIGHT - 90 * HighSeaTower.ratioY - i * 100 * HighSeaTower.ratioY;

        // Plateforme du début
        if (i == -1) {
            posX = 0;
            posY = HighSeaTower.HEIGHT;
            largeur = HighSeaTower.WIDTH;   // Toute la largeur de l'écran
        }
    }


    /**
     * La collision avec une plateforme a lieu seulement si :
     * - Il y a une intersection entre la plateforme et le personnage
     * - La collision a lieu entre la plateforme et le *bas de la méduse*
     * seulement
     * - La vitesse va vers le bas (le personnage est en train de tomber,
     * pas en train de sauter)
     *
     * @param other la méduse
     */
    public void testCollision(Meduse other) {
        if (intersects(other) && Math.abs(other.posY + other.hauteur - posY) < hauteur && other.vy > 0) {
            pushOut(other);
            other.vy = 0;
            other.setParterre(true);
        }
    }


    /**
     * Repousse le personnage vers le haut (sans déplacer la
     * plateforme)
     */
    public void pushOut(Meduse other) {
        other.posY = this.posY - other.hauteur;
    }


    /**
     * Met à jour la position et vitesse de la plateforme
     * @param dt Temps écoulé depuis le dernier update() en secondes
     */
    public void update(double dt) {
        super.update(dt);
    }

    /**
     * Dessine la plateforme sur le canvas
     * @param canvas canvas sur laquelle dessiner
     * @param fenetreX origine X de la fenêtre
     * @param fenetreY origine Y de la fenêtre
     */
    @Override
    public void draw(Canvas canvas, double fenetreX, double fenetreY) {
        float xAffiche = (float) (posX - fenetreX);
        float yAffiche = (float) (posY - fenetreY);

        Paint paint = new Paint();
        paint.setColor(color);

        canvas.drawRect(xAffiche, yAffiche, xAffiche + (float) this.largeur, yAffiche + (float) hauteur, paint);
    }
}