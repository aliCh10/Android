package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView register;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.Email);
        passwordEditText = findViewById(R.id.Password);
        register = findViewById(R.id.Registre);
        loginButton = findViewById(R.id.button);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une intention pour passer à l'activité de registre
                Intent intent = new Intent(login.this, register.class);

                // Lancer l'activité de registre
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer le texte d'email et de mot de passe
                String emailText = emailEditText.getText().toString().trim();
                String passwordText = passwordEditText.getText().toString().trim();

                // Utiliser Firebase Authentication pour connecter l'utilisateur
                firebaseAuth.signInWithEmailAndPassword(emailText, passwordText)
                        .addOnCompleteListener(login.this, task -> {
                            try {
                                if (task.isSuccessful()) {
                                    // Connexion réussie
                                    Toast.makeText(login.this, "Login successful", Toast.LENGTH_SHORT).show();

                                    // Vous pouvez naviguer vers l'activité principale ou toute autre activité ici
                                    Intent intent;

                                    // Vérifier si l'utilisateur a l'email spécial pour l'administrateur
                                    if (emailText.equals("alichlaifa66@gmail.com")) {
                                        intent = new Intent(login.this, AdminActivity.class);
                                    } else {
                                        intent = new Intent(login.this, home.class);
                                    }

                                    startActivity(intent);
                                    finish(); // Fermer l'activité de connexion pour qu'elle ne soit pas accessible via le bouton Retour
                                } else {
                                    // Si la connexion échoue, afficher un message à l'utilisateur
                                    Toast.makeText(login.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                // Afficher l'erreur dans la console
                                e.printStackTrace();
                                Toast.makeText(login.this, "An error occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }
}
