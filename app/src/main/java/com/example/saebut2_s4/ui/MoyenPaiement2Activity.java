package com.example.saebut2_s4.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.saebut2_s4.R;

public class MoyenPaiement2Activity extends AppCompatActivity {
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
                // Rediriger vers la page de finalisation
                Intent intent = new Intent(MoyenPaiement2Activity.this, DonFinaliseActivity.class);
                startActivity(intent);
            }
        });
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