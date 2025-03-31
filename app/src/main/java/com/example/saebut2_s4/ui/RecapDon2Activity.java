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

import com.example.saebut2_s4.ui.ApplicationGlobale;
import com.example.saebut2_s4.R;
import com.example.saebut2_s4.data.dao.UtilisateurDao;
import com.example.saebut2_s4.data.db.AppDatabase;
import com.example.saebut2_s4.data.model.Utilisateur;

public class RecapDon2Activity extends AppCompatActivity {

    private TextView textViewLastName, textViewFirstName, textViewEmail;
    private TextView textViewAssociation, textViewAmount;
    private Button buttonBack, buttonNext;
    private AppDatabase appDatabase;
    private UtilisateurDao utilisateurDao;
    private String userEmail;
    private String montant = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recap_don2);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Récupérer l'email
        userEmail = getIntent().getStringExtra("EMAIL_UTILISATEUR");
        if (userEmail == null) {
            Toast.makeText(this, "Erreur: Information utilisateur manquante", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Essayer d'obtenir le montant de plusieurs façons
        // 1. Essayer d'obtenir le montant de l'intent
        if (getIntent().hasExtra("montant")) {
            montant = getIntent().getStringExtra("montant");
        }
        // 2. Si le montant est vide, essayer d'obtenir le montant depuis l'application globale
        else if (montant == null || montant.isEmpty()) {
            if (getApplication() instanceof ApplicationGlobale) {
                montant = ((ApplicationGlobale) getApplication()).getMontantTemporaire();
            }
        }

        // Initialiser les vues
        initViews();

        // Configurer les boutons
        setupButtons();

        // Charger les données de l'utilisateur
        loadUserData();
    }

    private void initViews() {
        textViewLastName = findViewById(R.id.textViewLastName);
        textViewFirstName = findViewById(R.id.textViewFirstName);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewAssociation = findViewById(R.id.textViewAssociation);
        textViewAmount = findViewById(R.id.textViewAmount);
        buttonBack = findViewById(R.id.buttonBack);
        buttonNext = findViewById(R.id.buttonNext);
    }

    private void setupButtons() {
        buttonBack.setOnClickListener(v -> finish());
        buttonNext.setOnClickListener(v -> {
            Intent intent = new Intent(RecapDon2Activity.this, MoyenPaiement2Activity.class);
            startActivity(intent);
        });
    }

    private void loadUserData() {
        appDatabase = AppDatabase.getInstance(this);
        utilisateurDao = appDatabase.utilisateurDao();

        new Thread(() -> {
            final Utilisateur utilisateur = utilisateurDao.getUtilisateurByEmail(userEmail);

            runOnUiThread(() -> {
                if (utilisateur != null) {
                    textViewLastName.setText(utilisateur.getNomUtilisateur());
                    textViewFirstName.setText(utilisateur.getPrenomUtilisateur());
                    textViewEmail.setText(utilisateur.getEmailUtilisateur());
                    textViewAssociation.setText("À sélectionner");

                    // Afficher le montant
                    if (montant != null && !montant.isEmpty()) {
                        textViewAmount.setText(montant + " €");
                    } else {
                        textViewAmount.setText("Montant non spécifié");
                    }
                } else {
                    Toast.makeText(RecapDon2Activity.this, "Erreur: Utilisateur non trouvé", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }
}