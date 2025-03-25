package com.example.saebut2_s4.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.saebut2_s4.R;

public class InscriptionActivity extends AppCompatActivity {

    private static final String TAG = "InscriptionActivity";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inscription);

        // Gestion des marges système
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Configurer le listener pour "Passer"
        TextView passText = findViewById(R.id.pass_text);
        passText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSkipClicked(v);
            }
        });

        // Configurer le listener pour le bouton Connexion
        Button connexionButton = findViewById(R.id.btn_connexion);
        connexionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToConnexion();
            }
        });
    }

    // Méthode pour gérer le "Passer"
    public void onSkipClicked(View view) {
        Log.d(TAG, "Skip clicked");
        Intent intent = new Intent(this, AccueilActivity.class);
        startActivity(intent);
        finish(); // Termine l'activité courante
    }

    // Méthode pour naviguer vers Connexion
    private void navigateToConnexion() {
        Log.d(TAG, "Connexion button clicked");
        Intent intent = new Intent(this, ConnexionActivity.class);
        startActivity(intent);
        finish(); // Termine l'activité courante
    }
}