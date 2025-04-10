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
    String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    String telephoneRegex = "^\\d{10,15}$";
    private EditText nom, prenom, email, telephone, motdepasse;
    private Button inscription, connexionButton;
    private TextView passText;
    private AppDatabase appDatabase;
    private UtilisateurDao utilisateurDao;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inscription);

        initViews();
        setupListeners();

        // Gestion des marges système
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button retourButton = findViewById(R.id.btn_retour);
        retourButton.setOnClickListener(v -> finish());
    }

    private void initViews() {
        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        email = findViewById(R.id.email);
        telephone = findViewById(R.id.telephone);
        motdepasse = findViewById(R.id.motdepasse);
        inscription = findViewById(R.id.btn_inscription);
        connexionButton = findViewById(R.id.btn_connexion);
        passText = findViewById(R.id.pass_text);
    }

    private void setupListeners() {
        inscription.setOnClickListener(v -> handleInscription());
        connexionButton.setOnClickListener(v -> navigateToConnexion());
        passText.setOnClickListener(this::onSkipClicked);
    }

    private void handleInscription() {
        String nomText = nom.getText().toString().trim();
        String prenomText = prenom.getText().toString().trim();
        String emailText = email.getText().toString().trim();
        String telText = telephone.getText().toString().trim();
        String mdpText = motdepasse.getText().toString().trim();

        if (nomText.isEmpty() || prenomText.isEmpty() || emailText.isEmpty() || telText.isEmpty() || mdpText.isEmpty()) {
            Toast.makeText(this, "Tous les champs doivent être remplis", Toast.LENGTH_SHORT).show();
            return;
        } else if (!emailText.matches(emailRegex)) {
            Toast.makeText(this, "Adresse e-mail invalide", Toast.LENGTH_SHORT).show();
            return;
        } else if (!telText.matches(telephoneRegex)) {
            Toast.makeText(this, "Telephone invalide", Toast.LENGTH_SHORT).show();
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
                long userId = utilisateurDao.inserer(u); // Insert user and get the generated ID

                // Save user info in SharedPreferences
                getSharedPreferences("user_prefs", MODE_PRIVATE).edit()
                    .putBoolean("is_logged_in", true)
                    .putLong("user_id", userId) // Store the user ID
                    .putString("user_name", nomText + " " + prenomText)
                    .putString("user_email", emailText)
                    .apply();

                runOnUiThread(() -> {
                    Toast.makeText(this, "Inscription réussie !", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, AccueilActivity.class);
                    startActivity(intent);
                    finish();
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() ->
                        Toast.makeText(this, "Cet email est déjà utilisé !", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void accessDatabase() {
        appDatabase = AppDatabase.getInstance(this);
        utilisateurDao = appDatabase.utilisateurDao();
    }

    public void onSkipClicked(View view) {
        Log.d(TAG, "Skip clicked");
        Intent intent = new Intent(this, AccueilActivity.class);
        startActivity(intent);
        finish();
    }

    private void navigateToConnexion() {
        Log.d(TAG, "Connexion button clicked");
        Intent intent = new Intent(this, ConnexionActivity.class);
        startActivity(intent);
        finish();
    }
}