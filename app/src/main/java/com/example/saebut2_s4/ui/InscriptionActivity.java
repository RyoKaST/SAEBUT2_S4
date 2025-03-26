package com.example.saebut2_s4.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class InscriptionActivity extends AppCompatActivity {

    private static final String TAG = "InscriptionActivity";
    private EditText nom, prenom, email, telephone, motdepasse;
    private Button inscription;
    private AppDatabase appDatabase;
    private UtilisateurDao utilisateurDao;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inscription);

        // Inserer un compte
        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        email = findViewById(R.id.email);
        telephone = findViewById(R.id.telephone);
        motdepasse = findViewById(R.id.motdepasse);

        inscription = findViewById(R.id.btn_inscription);

        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nomText = nom.getText().toString().trim();
                String prenomText = prenom.getText().toString().trim();
                String emailText = email.getText().toString().trim();
                String telText = telephone.getText().toString().trim();
                String mdpText = motdepasse.getText().toString().trim();

                if (nomText.isEmpty() || prenomText.isEmpty() || emailText.isEmpty() || telText.isEmpty() || mdpText.isEmpty()) {
                    // Afficher un message d'erreur si un champ est vide
                    Toast.makeText(InscriptionActivity.this, "Tous les champs doivent être remplis", Toast.LENGTH_SHORT).show();
                    return;
                }

                new Thread(() -> {

                    accessDatabase();
                    Utilisateur u = new Utilisateur();
                    u.setNomUtilisateur(nomText);
                    u.setPrenomUtilisateur(prenomText);
                    u.setEmailUtilisateur(emailText);
                    u.setTelephoneUtilisateur(telText);
                    u.setMotDePasseUtilisateur(mdpText);

                    try {
                        utilisateurDao.inserer(u);
                        runOnUiThread(() -> Toast.makeText(InscriptionActivity.this, "Inscription réussie !", Toast.LENGTH_SHORT).show());
                    } catch (Exception e) {
                        e.printStackTrace(); // Log l'erreur pour debug
                        runOnUiThread(() -> Toast.makeText(InscriptionActivity.this, "Cet email est déjà utilisé !", Toast.LENGTH_SHORT).show());
                        return;
                    }

                    // Naviguer vers l'activité liste
                    runOnUiThread(() -> {
                        Intent intent = new Intent(InscriptionActivity.this, UtilisateurListActivity.class);
                        startActivity(intent);
                        finish(); // Optionnel, pour fermer l'activité d'inscription
                    });
                }).start();
            }
        });


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

    // Acceder à la base de donnée pour l'inscription
    private void accessDatabase() {
        appDatabase = AppDatabase.getInstance(InscriptionActivity.this);
        utilisateurDao = appDatabase.utilisateurDao();
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