package com.example.project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AdminActivity extends AppCompatActivity {

    private Button disconnect;
    private Button selectApp;
    private Button addApp;
    private FirebaseAuth firebaseAuth;
    private ImageView imageView;
    public static final int PICK_IMAGE_REQUEST = 1;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Uri imageUri;  // Ajouter cette ligne pour déclarer imageUri

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Initialiser l'authentification Firebase
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialiser le stockage Firebase
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference().child("images/app_image.jpg");

        // Initialiser les boutons et l'ImageView
        disconnect = findViewById(R.id.out);
        selectApp = findViewById(R.id.select);
        addApp = findViewById(R.id.add);
        imageView = findViewById(R.id.imageView2);

        // Définir un OnClickListener pour le bouton de déconnexion
        disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Déconnecter l'utilisateur
                firebaseAuth.signOut();

                // Rediriger vers l'activité de connexion
                Intent intent = new Intent(AdminActivity.this, login.class);
                startActivity(intent);

                // Fermer l'activité actuelle
                finish();
            }
        });

        // Définir un OnClickListener pour le bouton de sélection d'application
        selectApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ouvrir la galerie pour sélectionner une image
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent, "Sélectionner une image"), PICK_IMAGE_REQUEST);
            }
        });

        // Définir un OnClickListener pour le bouton d'ajout d'application
        addApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Télécharger l'image sélectionnée sur Firebase
                if (imageView.getDrawable() != null) {
                    uploadImageToFirebase();
                } else {
                    Toast.makeText(AdminActivity.this, "Veuillez d'abord sélectionner une image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("MyApp", "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Gérer l'URI de l'image sélectionnée
            // Afficher l'image sélectionnée dans l'ImageView
            imageUri = data.getData();
            imageView.setImageURI(imageUri);

            // Journaliser l'URI de l'image
            Log.d("MyApp", "URI de l'image sélectionnée : " + imageUri.toString());

            // Ne terminez pas l'activité ici
        } else {
            // Si la sélection de l'image n'a pas réussi, vous voudrez peut-être gérer ce cas
            Toast.makeText(this, "Sélection de l'image annulée", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImageToFirebase() {
        if (imageUri != null) {
            // Télécharger l'image sur le stockage Firebase
            UploadTask uploadTask = storageReference.putFile(imageUri);

            // Enregistrer des observateurs pour écouter la fin du téléchargement ou s'il échoue
            uploadTask.addOnSuccessListener(taskSnapshot -> {
                // Image téléchargée avec succès
                Toast.makeText(AdminActivity.this, "Image téléchargée sur Firebase", Toast.LENGTH_SHORT).show();

                // Obtenir l'URL de téléchargement de l'image
                storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    // Transmettre l'URL de l'image à l'activité appelante (home.java)
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("image_url", uri.toString());
                    setResult(RESULT_OK, resultIntent);
                });
            }).addOnFailureListener(e -> {
                // Gérer les échecs de téléchargement
                Toast.makeText(AdminActivity.this, "Échec du téléchargement de l'image", Toast.LENGTH_SHORT).show();
            });
        } else {
            Toast.makeText(AdminActivity.this, "Veuillez d'abord sélectionner une image", Toast.LENGTH_SHORT).show();
        }
    }
}
