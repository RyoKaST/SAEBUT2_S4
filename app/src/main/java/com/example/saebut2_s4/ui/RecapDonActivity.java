package com.example.saebut2_s4.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.saebut2_s4.R;

public class RecapDonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recap_don);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Récupérer les données de l'intent
        String nom = getIntent().getStringExtra("nom");
        String prenom = getIntent().getStringExtra("prenom");
        String email = getIntent().getStringExtra("email");
        String montant = getIntent().getStringExtra("montant");

        // Initialiser les TextView et définir leur contenu
        TextView textViewLastName = findViewById(R.id.textViewLastName);
        TextView textViewFirstName = findViewById(R.id.textViewFirstName);
        TextView textViewEmail = findViewById(R.id.textViewEmail);
        TextView textViewAmount = findViewById(R.id.textViewAmount);

        // Définir les données récupérées
        textViewLastName.setText(nom);
        textViewFirstName.setText(prenom);
        textViewEmail.setText(email);
        textViewAmount.setText(montant + " €");

        // Il manque l'association, vous devrez l'ajouter à l'intent dans EffectuerDonActivity
        TextView textViewAssociation = findViewById(R.id.textViewAssociation);
        // Par défaut, on peut mettre un message indiquant que l'information est manquante
        textViewAssociation.setText("Non spécifiée");

        // Configurer les boutons
        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> {
            finish(); // Retourne à l'activité précédente
        });

        Button buttonNext = findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(v -> {
            // Rediriger vers la page de paiement ou de confirmation finale
            Intent intent = new Intent(RecapDonActivity.this, MoyenPaiementActivity.class);
            startActivity(intent);

            // En attendant cette activité, on peut afficher un message
            //Toast.makeText(RecapDonActivity.this, "Paiement en cours de développement", Toast.LENGTH_SHORT).show();
        });
    }
}