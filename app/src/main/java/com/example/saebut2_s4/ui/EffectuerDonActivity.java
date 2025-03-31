package com.example.saebut2_s4.ui;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saebut2_s4.R;
import com.example.saebut2_s4.data.db.AppDatabase; // Import AppDatabase
import com.example.saebut2_s4.data.dao.DonDao;
import com.example.saebut2_s4.data.model.Don;

public class EffectuerDonActivity extends AppCompatActivity {
    private EditText editTextLastName, editTextFirstName, editTextEmail;
    private String montant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if the user is logged in
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
        setContentView(R.layout.activity_effectuer_don);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Récupérer le montant passé depuis la première page
        montant = getIntent().getStringExtra("montant");

        // Initialisation des champs de saisie
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextEmail = findViewById(R.id.editTextEmail);

        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EffectuerDonActivity.this, FirstPageDonActivity.class);
                startActivity(intent);
            }
        });

        Button buttonNext = findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateForm()) {
                    // Save the donation in the database
                    saveDonation();

                    // Navigate to the next activity
                    Intent intent = new Intent(EffectuerDonActivity.this, MoyenPaiementActivity.class);
                    intent.putExtra("montant", montant);
                    intent.putExtra("nom", editTextLastName.getText().toString().trim());
                    intent.putExtra("prenom", editTextFirstName.getText().toString().trim());
                    intent.putExtra("email", editTextEmail.getText().toString().trim());
                    startActivity(intent);
                }
            }
        });
    }

    private void saveDonation() {
        new Thread(() -> {
            AppDatabase appDatabase = AppDatabase.getInstance(this);
            DonDao donDao = appDatabase.donDao();

            // Retrieve the logged-in user ID and selected association ID
            long userId = getSharedPreferences("user_prefs", MODE_PRIVATE).getLong("user_id", -1);
            long associationId = getSharedPreferences("user_prefs", MODE_PRIVATE).getLong("selected_association_id", -1);

            if (userId != -1 && associationId != -1) {
                try {
                    Don don = new Don(
                        Double.parseDouble(montant), // Donation amount
                        String.valueOf(System.currentTimeMillis()), // Current timestamp as the donation date
                        userId,
                        associationId
                    );
                    donDao.inserer(don); // Save the donation in the database
                } catch (Exception e) {
                    runOnUiThread(() -> Toast.makeText(this, "Erreur lors de l'enregistrement du don.", Toast.LENGTH_SHORT).show());
                }
            } else {
                runOnUiThread(() -> Toast.makeText(this, "Utilisateur ou association invalide.", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    /**
     * Valide que tous les champs du formulaire sont remplis
     * @return true si tous les champs sont valides, false sinon
     */
    private boolean validateForm() {
        boolean isValid = true;

        // Validation du nom de famille
        String lastName = editTextLastName.getText().toString().trim();
        if (lastName.isEmpty()) {
            editTextLastName.setError("Veuillez saisir votre nom");
            isValid = false;
        } else if (!isValidName(lastName)) {
            editTextLastName.setError("Le nom ne doit contenir que des lettres");
            isValid = false;
        }

        // Validation du prénom
        String firstName = editTextFirstName.getText().toString().trim();
        if (firstName.isEmpty()) {
            editTextFirstName.setError("Veuillez saisir votre prénom");
            isValid = false;
        } else if (!isValidName(firstName)) {
            editTextFirstName.setError("Le prénom ne doit contenir que des lettres");
            isValid = false;
        }

        // Validation de l'email
        String email = editTextEmail.getText().toString().trim();
        if (email.isEmpty()) {
            editTextEmail.setError("Veuillez saisir votre e-mail");
            isValid = false;
        } else if (!isValidEmail(email)) {
            editTextEmail.setError("Veuillez saisir un e-mail valide");
            isValid = false;
        }

        // Si un des champs n'est pas valide, afficher un message
        if (!isValid) {
            Toast.makeText(this, "Veuillez remplir tous les champs correctement", Toast.LENGTH_SHORT).show();
        }

        return isValid;
    }

    /**
     * Vérifie si l'email est de format valide
     * @param email l'email à valider
     * @return true si l'email est valide, false sinon
     */
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
        return email.matches(emailPattern);
    }

    /**
     * Vérifie si le nom/prénom contient uniquement des lettres
     * @param name le nom ou prénom à vérifier
     * @return true si le nom est valide (lettres uniquement), false sinon
     */
    private boolean isValidName(String name) {
        // Accepte les lettres, les espaces, les tirets et les apostrophes
        // Ces caractères sont courants dans les noms (ex: Jean-Pierre, O'Connor)
        return name.matches("^[a-zA-ZÀ-ÿ\\s'-]+$");
    }
}