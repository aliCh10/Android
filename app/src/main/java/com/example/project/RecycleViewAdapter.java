package com.example.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {

    // Tableau de données pour stocker les identifiants de ressources
    private int[] arr;

    // Constructeur pour initialiser l'adaptateur avec des données
    public RecycleViewAdapter(int[] arr) {
        this.arr = arr;
    }

    // onCreateViewHolder est appelée lorsque le RecyclerView a besoin d'un nouveau ViewHolder
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflation de la mise en page pour un élément unique dans le RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view, parent, false);
        // Création d'un nouveau ViewHolder avec la vue gonflée
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    // onBindViewHolder est appelée pour lier des données à un ViewHolder à une position spécifique
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Configuration de la ressource de l'image en fonction des données à la position actuelle
        holder.imageView.setImageResource(arr[position]);
        // Configuration d'un texte statique pour le TextView (vous voudrez peut-être personnaliser cela en fonction de vos données)
        holder.textView.setText("Télécharger");
    }

    // getItemCount est appelée pour déterminer le nombre d'éléments dans l'ensemble de données
    @Override
    public int getItemCount() {
        return arr.length;
    }

    // Classe ViewHolder pour stocker les références aux vues dans un élément unique
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        // Constructeur pour initialiser le ViewHolder avec les vues
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // Recherche et stockage des références à l'ImageView et au TextView dans la mise en page
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textV);
        }
    }
}
