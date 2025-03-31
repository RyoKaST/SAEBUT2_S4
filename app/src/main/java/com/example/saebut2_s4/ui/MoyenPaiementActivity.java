package com.example.saebut2_s4.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.saebut2_s4.R;
import com.example.saebut2_s4.data.db.AppDatabase; // Corrected import path
import com.example.saebut2_s4.data.dao.DonDao;
import com.example.saebut2_s4.data.model.Don; // Import the Don class
import com.example.saebut2_s4.data.model.Association; // Add this import
import java.util.List; // Ensure this import is present

public class MoyenPaiementActivity extends AppCompatActivity {
    private EditText editTextCardNumber, editTextExpiry, editTextCVC;
    private Button buttonNext, buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_moyen_paiement);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialiser les vues
        editTextCardNumber = findViewById(R.id.editTextCardNumber);
        editTextExpiry = findViewById(R.id.editTextExpiry);
        editTextCVC = findViewById(R.id.editTextCVC);
        buttonNext = findViewById(R.id.buttonNext);
        buttonBack = findViewById(R.id.buttonBack);

        // Formater automatiquement le numéro de carte
        editTextCardNumber.addTextChangedListener(new TextWatcher() {
            private boolean isFormatting;
            private String lastFormattedText = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (isFormatting) return;
                isFormatting = true;

                // Supprimer les espaces existants
                String text = s.toString().replaceAll("\\s", "");

                // Limiter à 16 chiffres
                if (text.length() > 16) {
                    text = text.substring(0, 16);
                }

                // Ajouter des espaces tous les 4 chiffres
                StringBuilder formatted = new StringBuilder();
                for (int i = 0; i < text.length(); i++) {
                    if (i > 0 && i % 4 == 0) {
                        formatted.append(' ');
                    }
                    formatted.append(text.charAt(i));
                }

                String formattedText = formatted.toString();

                // Important: remplacer tout le texte, même si ça semble identique
                s.replace(0, s.length(), formattedText);
                lastFormattedText = formattedText;

                isFormatting = false;
            }
        });
        // Formater automatiquement la date d'expiration (MM/YY)
        editTextExpiry.addTextChangedListener(new TextWatcher() {
            private boolean isFormatting;
            private String lastFormattedText = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (isFormatting) return;
                isFormatting = true;

                String text = s.toString().replaceAll("/", "");

                // Limiter à 4 chiffres (MMYY)
                if (text.length() > 4) {
                    text = text.substring(0, 4);
                }

                // Formater en MM/YY
                StringBuilder formatted = new StringBuilder();
                for (int i = 0; i < text.length(); i++) {
                    if (i == 2) {
                        formatted.append('/');
                    }
                    formatted.append(text.charAt(i));
                }

                String formattedText = formatted.toString();
                if (!formattedText.equals(lastFormattedText)) {
                    lastFormattedText = formattedText;
                    s.replace(0, s.length(), formattedText);
                }

                isFormatting = false;
            }
        });

        // Limiter le CVC à 3 chiffres
        editTextCVC.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 3) {
                    s.delete(3, s.length());
                }
            }
        });

        // Configurer le bouton retour
        buttonBack.setOnClickListener(v -> {
            finish(); // Retour à l'écran précédent
        });

        // Configurer le bouton de validation
        buttonNext.setOnClickListener(v -> {
            if (validatePaymentForm()) {
                // Save the donation in the database
                saveDonation();

                // Navigate back to AccueilActivity
                Intent intent = new Intent(MoyenPaiementActivity.this, AccueilActivity.class);
                intent.putExtra("navigate_to", "home_fragment"); // Optional: Pass data to indicate navigation to HomeFragment
                startActivity(intent);
                finish();
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

            if (associationId == -1) {
                Log.e("MoyenPaiementActivity", "No association selected");
                runOnUiThread(() -> Toast.makeText(this, "Veuillez sélectionner une association.", Toast.LENGTH_SHORT).show());
                return;
            }

            String montant = getIntent().getStringExtra("montant");

            // Log the retrieved association ID
            Log.d("MoyenPaiementActivity", "Retrieved association ID: " + associationId);

            // Log a detailed recap before saving the donation
            Log.d("MoyenPaiementActivity", "Recap before saving donation:");
            Log.d("MoyenPaiementActivity", "User ID: " + userId);
            Log.d("MoyenPaiementActivity", "Association ID: " + associationId);
            Log.d("MoyenPaiementActivity", "Montant: " + montant);

            // Verify foreign key relationships
            boolean isUserValid = appDatabase.utilisateurDao().getUtilisateurById(userId) != null;
            boolean isAssociationValid = appDatabase.associationDao().getAssociationById(associationId) != null;

            Log.d("MoyenPaiementActivity", "Is User Valid: " + isUserValid);
            Log.d("MoyenPaiementActivity", "Is Association Valid: " + isAssociationValid);

            if (!isUserValid || !isAssociationValid) {
                Log.e("MoyenPaiementActivity", "Foreign key constraint failed: Invalid user or association ID");
                runOnUiThread(() -> Toast.makeText(this, "Utilisateur ou association invalide.", Toast.LENGTH_SHORT).show());
                return;
            }

            if (userId != -1 && associationId != -1 && montant != null) {
                try {
                    Don don = new Don(
                        Double.parseDouble(montant), // Donation amount
                        String.valueOf(System.currentTimeMillis()), // Current timestamp as the donation date
                        userId,
                        associationId
                    );
                    donDao.inserer(don); // Save the donation in the database
                    Log.d("MoyenPaiementActivity", "Donation saved successfully");
                } catch (Exception e) {
                    Log.e("MoyenPaiementActivity", "Error saving donation", e);
                    runOnUiThread(() -> Toast.makeText(this, "Erreur lors de l'enregistrement du don.", Toast.LENGTH_SHORT).show());
                }
            } else {
                Log.e("MoyenPaiementActivity", "Invalid data: userId=" + userId + ", associationId=" + associationId + ", montant=" + montant);
                runOnUiThread(() -> Toast.makeText(this, "Données utilisateur ou association manquantes.", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private boolean validatePaymentForm() {
        boolean isValid = true;

        // Valider le numéro de carte (16 chiffres sans les espaces)
        String cardNumber = editTextCardNumber.getText().toString().replaceAll("\\s", "");
        if (cardNumber.length() != 16) {
            editTextCardNumber.setError("Le numéro de carte doit contenir 16 chiffres");
            isValid = false;
        }

        // Valider la date d'expiration
        String expiry = editTextExpiry.getText().toString();
        if (expiry.length() != 5 || !expiry.contains("/")) {
            editTextExpiry.setError("Format invalide (MM/YY)");
            isValid = false;
        } else {
            try {
                // Vérifier que le format est bien MM/YY
                String[] parts = expiry.split("/");
                if (parts.length != 2 || parts[0].length() != 2 || parts[1].length() != 2) {
                    editTextExpiry.setError("Format invalide (MM/YY)");
                    isValid = false;
                } else {
                    // Vérifier que le mois est entre 01 et 12
                    int monthValue = Integer.parseInt(parts[0]);
                    if (monthValue < 1 || monthValue > 12) {
                        editTextExpiry.setError("Mois invalide (01-12)");
                        isValid = false;
                    }

                    // Optionnel: vérifier que l'année n'est pas dans le passé
                    // Cette partie peut être ajoutée si nécessaire
                }
            } catch (NumberFormatException e) {
                editTextExpiry.setError("Format invalide (MM/YY)");
                isValid = false;
            }
        }

        // Valider le CVC (3 chiffres)
        String cvc = editTextCVC.getText().toString();
        if (cvc.length() != 3) {
            editTextCVC.setError("Le CVC doit contenir 3 chiffres");
            isValid = false;
        }

        return isValid;
    }
}