//Elise ZHENG (20148416), Yuyin DING (20125263)

package com.example.highseatower;

import androidx.appcompat.app.AppCompatActivity;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.Objects;


/**
 * Contrôleur du jeu High Sea Tower
 */
public class MainActivity extends AppCompatActivity {

    private Jeu jeu = null; // Modèle
    public HighSeaTower highSeaTower;
    public Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Plein écran
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Cache la barre
        Objects.requireNonNull(getSupportActionBar()).hide();

        // Obtention de la taille de l'écran
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);

        // Création de la vue
        HighSeaTower vue = new HighSeaTower(this, size.x, size.y);
        this.setContentView(vue);
    }


    // Manipulations et mise à jour du jeu

    void draw(Canvas canvas) {
        jeu.draw(canvas);
    }

    void update(double dt) {
        if (jeu == null) {jeu = new Jeu(res);}
        jeu.update(dt);
    }

    void jump() {
        jeu.jump();
    }

    void left() {
        jeu.left();
    }

    void right() {
        jeu.right();
    }

    void endAx() {
        jeu.endAx();
    }


    /**
     * Met en pause l'activité lorsque l'usager sort de l'application
     */
    @Override
    protected void onPause() {
        super.onPause();
        highSeaTower.pause();
    }


    /**
     * Reprend l'activité lorsque l'usager retourne sur l'application
     */
    @Override
    protected void onResume() {
        super.onResume();
        highSeaTower.resume();
    }
}
