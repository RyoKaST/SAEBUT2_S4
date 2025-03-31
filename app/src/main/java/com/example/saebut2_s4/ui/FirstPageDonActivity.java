package com.example.saebut2_s4.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.saebut2_s4.R;

public class FirstPageDonActivity extends AppCompatActivity {
    private RadioButton donUnique, donRecurrent;
    private EditText montantEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_first_page_don);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialisation des vues
        donUnique = findViewById(R.id.don_unique);
        donRecurrent = findViewById(R.id.don_recurrent);
        montantEditText = findViewById(R.id.montant_edit_text);

        // Configuration de l'EditText pour n'accepter que des chiffres
        montantEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstPageDonActivity.this, AccueilActivity.class);
                startActivity(intent);
            }
        });

        Button buttonNext = findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateForm()) {
                    String montantValue = montantEditText.getText().toString();

                    // Si validation réussie, rediriger selon le type de don
                    if (donUnique.isChecked()) {
                        Intent intent = new Intent(FirstPageDonActivity.this, EffectuerDonActivity.class);
                        intent.putExtra("montant", montantEditText.getText().toString());
                        startActivity(intent);
                    } else if (donRecurrent.isChecked()) {
                        Intent intent = new Intent(FirstPageDonActivity.this, Connexion2Activity.class);
                        intent.putExtra("montant", montantEditText.getText().toString());
                        startActivity(intent);

                    }
                }
            }
        });
    }

    // Fonction de validation du formulaire
    private boolean validateForm() {
        boolean isValid = true;

        // Vérification de la sélection d'un RadioButton
        if (!donUnique.isChecked() && !donRecurrent.isChecked()) {
            Toast.makeText(this, "Veuillez sélectionner un type de don", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        // Vérification de la saisie du montant
        String montant = montantEditText.getText().toString().trim();
        if (montant.isEmpty()) {
            montantEditText.setError("Veuillez saisir un montant");
            isValid = false;
        }

        return isValid;
    }
}