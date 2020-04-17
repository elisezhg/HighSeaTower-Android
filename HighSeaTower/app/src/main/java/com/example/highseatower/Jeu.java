package com.example.highseatower;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

public class Jeu {

    public static boolean vitesseAcceleree = false;

    private double fenetreX = 0, fenetreY = 0; // Origine de la fenêtre
    private double vitesse = 50 * HighSeaTower.ratioY;
    private double acceleration = 2 * HighSeaTower.ratioY;
    private double tempsTotal = 0;
    private static boolean debut = false;
    private int score = 0;
    private Resources res;

    // Entités dans le jeu
    private Meduse meduse;
    private ArrayList<Plateforme> plateformes = new ArrayList<>();
    private ArrayList<ArrayList<Bulle>> bulles;
    private int nbPf = 0; // nombre de plateformes générées


    public Jeu(Resources res) {
        this.res = res;
        nvPartie();
    }

    public void nvPartie() {
        fenetreX = 0;
        fenetreY = 0;
        vitesse = 50 * HighSeaTower.ratioY;
        acceleration = 2 * HighSeaTower.ratioY;
        tempsTotal = 0;
        debut = false;
        vitesseAcceleree = false;
        score = 0;

        plateformes = new ArrayList<>();
        nbPf = 0;

        // Création de la méduse
        meduse = new Meduse(res);

        // Plateforme de début (sous la méduse)
        plateformes.add(new Plateforme(-1));

        // Génère les 5 premières plateformes
        for (int i = 0; i < 5; i++) {
            genererPlateformes();
        }

        genererBulles();
    }


    /**
     * Génère des plateformes aléatoirement tout en supprimant celles qui ne sont plus utilisées
     */
    public void genererPlateformes() {

        // Ne génère pas de plateformes s'il y en a assez à afficher
        if (-fenetreY + HighSeaTower.HEIGHT < nbPf * 100 * HighSeaTower.ratioY) return;
        double random = Math.random();

        // Évite 2 plateformes solides d'affilée
        if (plateformes.get(plateformes.size() - 1) instanceof PlateformeSolide) {
            plateformes.add(new Plateforme(nbPf++));
            return;
        }

        // Probabilités des plateformes
        if (random < 0.65) {
            plateformes.add(new Plateforme(nbPf++));
        } else if (random < 0.85) {
            plateformes.add(new PlateformeRebondissante(nbPf++));
        } else if (random < 0.95) {
            plateformes.add(new PlateformeAccelerante(nbPf++));
        } else {
            plateformes.add(new PlateformeSolide(nbPf++));
        }

        // Enlève la plateforme qui n'est plus affichée
        if (plateformes.size() > 6) {
            plateformes.remove(0);
        }
    }


    /**
     * Génère 3 groupes de 5 bulles ayant une baseX aléatoire
     */
    public void genererBulles() {
        bulles = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            bulles.add(new ArrayList<Bulle>());
            double baseX = Math.random() * HighSeaTower.WIDTH; // entre 0 et width
            for (int j = 0; j < 5; j++) {
                bulles.get(i).add(new Bulle(baseX, fenetreY));
            }
        }
    }


    /**
     * On débute le jeu si ce n'est pas déjà fait
     * On fait sauter la méduse
     */
    public void jump() {
        if (!debut) debut = true;
        meduse.jump();
    }


    /**
     * On débute le jeu si ce n'est pas déjà fait
     * On fait bouger la méduse à gauche
     */
    public void left() {
        if (!debut) debut = true;
        meduse.left();
    }


    /**
     * On débute le jeu si ce n'est pas déjà fait
     * On fait bouger la méduse à droite
     */
    public void right() {
        if (!debut) debut = true;
        meduse.right();
    }


    /**
     * L'accélération horizontale de la méduse prend fin
     */
    public void endAx() {
        meduse.endAx();
    }


    /**
     * Met à jour les positions, vitesses, accélérations de toutes les entités
     * @param dt Temps écoulé depuis le dernier update() en secondes
     */
    public void update(double dt) {

        if (debut) {

            vitesse += dt * acceleration;

            // Si la méduse se trouve sur une plateforme accélérante
            if (vitesseAcceleree && meduse.isParterre()) {
                fenetreY -= vitesse * dt * 3;

            // Sinon, c'est qu'elle a quitté ou n'est pas sur la plateforme
            } else {
                vitesseAcceleree = false;
                fenetreY -= vitesse * dt;
            }

            // MAJ score
            score = (int) -fenetreY;

            // Génère des bulles toutes les 3 secondes
            tempsTotal += dt;
            if (tempsTotal >= 3) {
                tempsTotal = 0;
                genererBulles();
            }

            // MAJ bulles
            for (ArrayList<Bulle> grpBulles : bulles) {
                for (Bulle bulle : grpBulles) {
                    bulle.update(dt);
                }
            }

            // MAJ méduse
            meduse.update(dt);
            meduse.setParterre(false);
        }

        // MAJ plateformes
        for (Plateforme pf : plateformes) {
            pf.update(dt);
            pf.testCollision(meduse);
        }

        genererPlateformes();

        // La méduse dépasse les 75%
        if (meduse.getPosY() - fenetreY < HighSeaTower.HEIGHT * 0.25) {
            fenetreY  -= HighSeaTower.HEIGHT * 0.25 - meduse.getPosY() + fenetreY;

            // Perdu
        } else if (meduse.getPosY() - fenetreY > HighSeaTower.HEIGHT) {
            nvPartie();
        }
    }


    /**
     * Dessine toutes les entités (méduse, plateformes, items) sur le canvas
     * @param canvas canvas sur lequel dessiner
     */
    public void draw(Canvas canvas) {

        // Dessine les bulles
        for (ArrayList<Bulle> grpBulles : bulles) {
            for (Bulle bulle : grpBulles) {
                bulle.draw(canvas, fenetreX, fenetreY);
            }
        }

        // Dessine les plateformes
        for (Plateforme pf : plateformes) {
            pf.draw(canvas, fenetreX, fenetreY);
        }

        // Dessine la méduse
        meduse.draw(canvas, fenetreX, fenetreY);

        // Score actuel
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize((float) (30 * HighSeaTower.ratioX));
        paint.setTextAlign(Paint.Align.CENTER);
        float scoreX = (float) HighSeaTower.WIDTH / 2f;
        float scoreY = (float) (50 * HighSeaTower.ratioY);
        canvas.drawText(score + "m", scoreX, scoreY, paint);
    }
}
