package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class register extends AppCompatActivity {
    private EditText Name;
    private EditText email;
    private EditText Password;
    private EditText C_Password;
    private Button button;
    private TextView registerT;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Name = findViewById(R.id.Name);
        email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        C_Password = findViewById(R.id.Confirm);
        registerT = findViewById(R.id.textR);
        button = findViewById(R.id.button);

        // Initialiser FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        // Définir un OnClickListener pour le texte de connexion
        registerT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une intention pour naviguer vers l'activité de connexion
                Intent intent = new Intent(register.this, login.class);

                // Lancer l'activité de connexion
                startActivity(intent);
            }
        });

        // Définir un OnClickListener pour le bouton d'inscription
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Appeler la méthode pour enregistrer un nouvel utilisateur
                registerUser();
            }
        });
    }

    private void registerUser() {
        // Récupérer tous les champs d'entrée
        String nameText = Name.getText().toString().trim();
        String emailText = email.getText().toString().trim();
        String passwordText = Password.getText().toString().trim();
        String confirmPasswordText = C_Password.getText().toString().trim();

        // Vérifier si un champ est vide
        if (nameText.isEmpty() || emailText.isEmpty() || passwordText.isEmpty() || confirmPasswordText.isEmpty()) {
            Toast.makeText(register.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return; // Arrêter le processus d'inscription si un champ est vide
        }

        // Vérifier si le mot de passe et la confirmation du mot de passe correspondent
        if (!passwordText.equals(confirmPasswordText)) {
            Toast.makeText(register.this, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
            return; // Arrêter le processus d'inscription si les mots de passe ne correspondent pas
        }

        // Utiliser l'authentification Firebase pour créer un nouvel utilisateur
        firebaseAuth.createUserWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Inscription réussie
                            Toast.makeText(register.this, "Inscription réussie", Toast.LENGTH_SHORT).show();

                            // Vous pouvez naviguer vers l'activité de connexion ou toute autre activité ici
                            Intent intent = new Intent(register.this, login.class);
                            startActivity(intent);
                        } else {
                            // Si l'inscription échoue, afficher un message à l'utilisateur
                            Toast.makeText(register.this, "Échec de l'inscription : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // Classe statique pour représenter un élément photo (peut être déplacée à l'extérieur de cette classe si nécessaire)
    public static class PhotoItem {
        private int imageResource;

        public PhotoItem(int imageResource) {
            this.imageResource = imageResource;
        }

        public int getImageResource() {
            return imageResource;
        }
    }
}
