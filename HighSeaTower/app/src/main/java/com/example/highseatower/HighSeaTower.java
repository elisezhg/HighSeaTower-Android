//Elise ZHENG (20148416), Yuyin DING (20125263)

package com.example.highseatower;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.ImageButton;


public class HighSeaTower extends SurfaceView implements Runnable {

    private Thread thread;
    public static int WIDTH = 350, HEIGHT = 480;
    public static double ratioX, ratioY;
    MainActivity controleur;

    public HighSeaTower(MainActivity controleur, int screenX, int screenY) {
        super(controleur);
        this.controleur = controleur;
        controleur.highSeaTower = this;
        controleur.res = getResources();

        ratioX = screenX / 350.0;
        ratioY = screenY / 480.0;
        WIDTH = screenX;
        HEIGHT = screenY;
    }


    @Override
    public void run() {

        while (true) {
            controleur.update(1.0 / 60.0);  // 60fps
            draw();
            sleep();
        }
    }


    /**
     * Dessine le jeu
     */
    public void draw() {
        if (getHolder().getSurface().isValid()) {

            Canvas canvas = getHolder().lockCanvas();

            // Réinitialise le canvas
            Paint paint = new Paint();
            paint.setColor(Color.rgb(0, 8, 144));
            canvas.drawRect(0, 0, WIDTH, HEIGHT, paint);

            // Demande les autres dessins sur le canvas
            controleur.draw(canvas);

            getHolder().unlockCanvasAndPost(canvas);
        }
    }


    /**
     * Détecte les mouvements de l'utilisateur
     * @param event mouvement effectué
     * @return boolean
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // Appuie sur l'écran
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            // sur la partie gauche de l'écran
            if (event.getX() < WIDTH / 3.0) {
                controleur.left();

            // sur la partie droite de l'écran
            } else if (event.getX() > 2 * WIDTH / 3.0) {
                controleur.right();

            // sinon, c'est sur la partie centrale
            } else {
                controleur.jump();
            }

        // Relâche le doigt de l'écran
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            controleur.endAx();
        }

        return true;
    }


    /**
     * Le thread ne fait rien pendant 1000/60 secondes
     */
    private void sleep () {
        try {
            Thread.sleep(1000 / 60);    // 60fps
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Reprend le jeu
     */
    public void resume () {
        thread = new Thread(this);
        thread.start();
    }


    /**
     * Met en pause le jeu
     */
    public void pause () {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}