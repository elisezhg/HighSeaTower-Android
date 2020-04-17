package com.example.highseatower;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Meduse extends Entity {

    private Resources res;
    private Bitmap[] framesG, framesD;
    private Bitmap img;
    private boolean gauche = false;
    private double frameRate = 8;
    private double tempsTotal = 0;
    private boolean parterre = true;

    /**
     * Constructeur de la méduse
     * @param res ressources
     */
    public Meduse(Resources res) {
        this.res = res;
        this.largeur = 50 * HighSeaTower.ratioX;
        this.hauteur = 50 * HighSeaTower.ratioY;
        this.posX = (HighSeaTower.WIDTH - largeur) / 2;
        this.posY = HighSeaTower.HEIGHT - hauteur;
        this.ay = 1200 * HighSeaTower.ratioY;
        getImg();
        this.img = framesD[0];
    }

    /**
     * Charge les images de la méduse
     */
    public void getImg() {
        framesG = new Bitmap[]{
                BitmapFactory.decodeResource(res, R.drawable.jellyfish1g),
                BitmapFactory.decodeResource(res, R.drawable.jellyfish2g),
                BitmapFactory.decodeResource(res, R.drawable.jellyfish3g),
                BitmapFactory.decodeResource(res, R.drawable.jellyfish4g),
                BitmapFactory.decodeResource(res, R.drawable.jellyfish5g),
                BitmapFactory.decodeResource(res, R.drawable.jellyfish6g)
        };

        framesD = new Bitmap[]{
                BitmapFactory.decodeResource(res, R.drawable.jellyfish1),
                BitmapFactory.decodeResource(res, R.drawable.jellyfish2),
                BitmapFactory.decodeResource(res, R.drawable.jellyfish3),
                BitmapFactory.decodeResource(res, R.drawable.jellyfish4),
                BitmapFactory.decodeResource(res, R.drawable.jellyfish5),
                BitmapFactory.decodeResource(res, R.drawable.jellyfish6)
        };
    }


    /**
     * Met à jour la position et la vitesse de la méduse
     * @param dt Temps écoulé depuis le dernier update() en secondes
     */
    public void update(double dt) {

        // Physique du personnage
        super.update(dt);

        // Friction
        vx *= 0.95;

        // Mise à jour de l'image affichée
        tempsTotal += dt;
        int frame = (int) (tempsTotal * frameRate);

        if (gauche) {
            img = framesG[frame % framesG.length];
        } else {
            img = framesD[frame % framesD.length];
        }
    }


    /**
     * La méduse regarde à gauche et prend une accélération de 1200px/s vers la gauche
     */
    public void left() {
        this.gauche = true;
        ax = -1200 * HighSeaTower.ratioX;
    }


    /**
     * La méduse regarde à droite et prend une accélération de 1200px/s vers la droite
     */
    public void right() {
        this.gauche = false;
        ax = 1200 * HighSeaTower.ratioX;
    }


    /**
     * L'accélération horizontale prend fin
     */
    public void endAx() {
        ax = 0;
    }


    /**
     * La méduse saute si elle se trouve sur une plateforme
     */
    public void jump() {
        if (parterre) {
            vy = -600 * HighSeaTower.ratioY;  //vitesse instantanée de 600px/s vers le haut
        }
    }

    @Override
    public void draw(Canvas canvas, double fenetreX, double fenetreY) {
        double xAffiche = posX - fenetreX;
        double yAffiche = posY - fenetreY;

        img = Bitmap.createScaledBitmap(img, (int) largeur, (int) hauteur, false);
        canvas.drawBitmap(img, (int) xAffiche, (int) yAffiche, new Paint());
    }


    // Getters et setters

    public void setParterre(boolean parterre) {
        this.parterre = parterre;
    }

    public boolean isParterre() {
        return this.parterre;
    }
}
