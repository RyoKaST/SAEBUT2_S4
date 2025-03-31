package com.example.saebut2_s4.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

public class ConnexionActivity extends AppCompatActivity {

    private static final String TAG = "ConnexionActivity";
    String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    private EditText email, motdepasse;
    private Button connexion;
    private AppDatabase appDatabase;
    private UtilisateurDao utilisateurDao;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if the user is already logged in
        boolean isLoggedIn = getSharedPreferences("user_prefs", MODE_PRIVATE)
                .getBoolean("is_logged_in", false);

        if (isLoggedIn) {
            // Redirect logged-in users to AccueilActivity
            Intent intent = new Intent(this, AccueilActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_connexion);

        Log.d(TAG, "ConnexionActivity onCreate called");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        email = findViewById(R.id.email);
        motdepasse = findViewById(R.id.motdepasse);
        connexion = findViewById(R.id.btn_login);

        accessDatabase();
        setupListeners();
    }

    private void setupListeners() {
        findViewById(R.id.pass_text).setOnClickListener(v -> onSkipClicked());
        findViewById(R.id.btn_register).setOnClickListener(v -> navigateToRegister());
        connexion.setOnClickListener(v -> handleLogin());
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
                    // Save user info in SharedPreferences
                    getSharedPreferences("user_prefs", MODE_PRIVATE).edit()
                        .putBoolean("is_logged_in", true)
                        .putString("user_name", utilisateur.getNomUtilisateur() + " " + utilisateur.getPrenomUtilisateur())
                        .putString("user_email", utilisateur.getEmailUtilisateur())
                        .apply();

                    Toast.makeText(this, "Connexion réussie !", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, AccueilActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }

    private void accessDatabase() {
        appDatabase = AppDatabase.getInstance(this);
        utilisateurDao = appDatabase.utilisateurDao();
    }

    public void onSkipClicked() {
        Log.d(TAG, "Skip clicked");
        Intent intent = new Intent(this, AccueilActivity.class);
        startActivity(intent);
        finish(); // Termine l'activité courante
    }

    // Méthode pour naviguer vers Connexion
    private void navigateToRegister() {
        Log.d(TAG, "Register button clicked");
        Intent intent = new Intent(this, InscriptionActivity.class);
        startActivity(intent);
        finish(); // Termine l'activité courante
    }

}