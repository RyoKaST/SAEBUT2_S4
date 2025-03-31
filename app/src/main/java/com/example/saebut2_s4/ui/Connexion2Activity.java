package com.example.saebut2_s4.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.saebut2_s4.R;
import com.example.saebut2_s4.data.dao.UtilisateurDao;
import com.example.saebut2_s4.data.db.AppDatabase;
import com.example.saebut2_s4.data.model.Utilisateur;

public class Connexion2Activity extends AppCompatActivity {

    private static final String TAG = "Connexion2Activity";
    String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    private EditText email, motdepasse;
    private Button connexion, inscription, retour;
    private AppDatabase appDatabase;
    private UtilisateurDao utilisateurDao;
    private String montant = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_connexion2);

        email = findViewById(R.id.email);
        motdepasse = findViewById(R.id.motdepasse);
        connexion = findViewById(R.id.btn_login);
        inscription = findViewById(R.id.btn_register);
        retour = findViewById(R.id.btn_retour);

        // Récupération du montant
        montant = getIntent().getStringExtra("montant");


        accessDatabase();
        setupListeners();
    }

    private void setupListeners() {
        connexion.setOnClickListener(v -> handleLogin());
        inscription.setOnClickListener(v -> navigateToRegister());
        retour.setOnClickListener(v -> navigateBack());
    }

    private void navigateBack() {
        finish();
    }

    private void accessDatabase() {
        appDatabase = AppDatabase.getInstance(this);
        utilisateurDao = appDatabase.utilisateurDao();
    }

    private void navigateToRegister() {
        Intent intent = new Intent(this, Inscription2Activity.class);
        intent.putExtra("montant", montant);
        startActivity(intent);
    }

    private void handleLogin() {
        String emailText = email.getText().toString().trim();
        String mdpText = motdepasse.getText().toString().trim();

        if (emailText.isEmpty() || mdpText.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        } else if (!emailText.matches(emailRegex)) {
            Toast.makeText(this, "Adresse e-mail invalide", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            Utilisateur utilisateur = utilisateurDao.getUtilisateurByEmail(emailText);

            runOnUiThread(() -> {
                if (utilisateur != null && utilisateur.getMotDePasseUtilisateur().equals(mdpText)) {
                    Toast.makeText(this, "Connexion réussie !", Toast.LENGTH_SHORT).show();

                    // Stocker le montant dans une variable globale de l'application
                    // (une solution alternative si l'intent ne fonctionne pas)
                    if (getApplication() instanceof ApplicationGlobale) {
                        ((ApplicationGlobale) getApplication()).setMontantTemporaire(montant);
                    }

                    // Redirection vers Recap2Activity avec l'email ET le montant
                    Intent intent = new Intent(this, RecapDon2Activity.class);
                    intent.putExtra("EMAIL_UTILISATEUR", emailText);
                    intent.putExtra("montant", montant);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }
}