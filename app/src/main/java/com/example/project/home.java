package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;

public class home extends AppCompatActivity {
    private Button disconnect;
    private RecyclerView RV;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    // Supposons que vous ayez une correspondance entre les URL d'images et les identifiants de ressources dessinables
    private HashMap<String, Integer> urlToDrawableMap;

    // Tableau d'identifiants de ressources d'images
    private int[] arr = {
            R.drawable.messenger, R.drawable.insta, R.drawable.twitter, R.drawable.fb, R.drawable.whats4,
            R.drawable.maile, R.drawable.tik, R.drawable.net, R.drawable.spot,
            R.drawable.messenger, R.drawable.insta, R.drawable.twitter, R.drawable.tik, R.drawable.net,
            R.drawable.spot, R.drawable.tik, R.drawable.net, R.drawable.spot
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialiser l'authentification Firebase
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialiser le RecyclerView et son gestionnaire de disposition
        RV = findViewById(R.id.RVV);
        layoutManager = new GridLayoutManager(this, 3);
        RV.setLayoutManager(layoutManager);

        // Initialiser la référence à la base de données Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference().child("images");

        // Initialiser la correspondance entre les URL d'images et les identifiants de ressources dessinables
        urlToDrawableMap = new HashMap<>();
        urlToDrawableMap.put("url1", R.drawable.net);
        urlToDrawableMap.put("url2", R.drawable.messenger);
        urlToDrawableMap.put("url3", R.drawable.whats4);
        urlToDrawableMap.put("url4", R.drawable.insta);
        urlToDrawableMap.put("url5", R.drawable.tik);
        urlToDrawableMap.put("url6", R.drawable.twitter);

        // Initialiser l'adaptateur RecyclerView
        RecycleViewAdapter recycleViewAdapter = new RecycleViewAdapter(arr);
        RV.setAdapter(recycleViewAdapter);
        RV.setHasFixedSize(true);

        disconnect = findViewById(R.id.out);

        disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Déconnecter l'utilisateur
                firebaseAuth.signOut();

                // Rediriger vers l'activité de connexion
                startActivity(new Intent(home.this, login.class));
                finish(); // Fermer l'activité actuelle
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AdminActivity.PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.hasExtra("image_url")) {
            // Gérer le résultat de AdminActivity et mettre à jour le tableau "arr"
            String imageUrl = data.getStringExtra("image_url");

            // (Optionnel) Stocker l'URL de l'image dans la base de données Firebase Realtime
            storeImageUrlInFirebase(imageUrl);
        }
    }

    private void storeImageUrlInFirebase(String imageUrl) {
        // Stocker l'URL de l'image dans la base de données Firebase Realtime
        DatabaseReference newImageRef = databaseReference.push();
        newImageRef.setValue(imageUrl);
    }
}
