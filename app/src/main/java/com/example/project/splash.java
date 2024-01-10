package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Utilisation d'un gestionnaire pour retarder le lancement de l'activité suivante (login)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Créer une intention pour lancer l'activité de connexion (login)
                Intent intent = new Intent(splash.this, login.class);

                // Démarrer l'activité de connexion
                startActivity(intent);

                // Fermer l'activité actuelle (splash)
                finish();
            }
        }, 3000); // Définir le temps de retard en millisecondes (3 secondes dans cet exemple)
    }
}
