package com.example.highseatower;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class Bulle extends Entity {

    /**
     * Constructeur de la bulle
     * @param baseX position horizontale sur laquelle se base le groupe de bulles
     * @param fenetreY origine Y de la fenêtre
     */
    public Bulle(double baseX, double fenetreY) {
        this.color = Color.argb(102, 0, 0, 255);
        this.largeur = Math.random() * 30 + 10;
        this.largeur *= HighSeaTower.ratioX;
        this.hauteur = this.largeur;
        this.vy = -(Math.random() * 100 + 350) * HighSeaTower.ratioY;
        this.posX = (baseX + Math.random() * 40 - 20) * HighSeaTower.ratioX;
        this.posY = fenetreY + HighSeaTower.HEIGHT;  // positionné juste en bas de l'écran
    }


    /**
     * Met à jour la position de la bulle
     * @param dt Temps écoulé depuis le dernier update() en secondes
     */
    public void update(double dt) {
        super.update(dt);
    }


    /**
     * Dessine la bulle sur le canvas
     * @param canvas canvas sur laquelle dessiner
     * @param fenetreX origine X de la fenêtre
     * @param fenetreY origine Y de la fenêtre
     */
    public void draw(Canvas canvas, double fenetreX, double fenetreY) {

        float xAffiche = (float) (posX - fenetreX);
        float yAffiche = (float) (posY - fenetreY);

        Paint paint = new Paint();
        paint.setColor(color);

        canvas.drawOval(xAffiche, yAffiche, xAffiche + (float) this.largeur, yAffiche + (float) hauteur, paint);
    }
}
