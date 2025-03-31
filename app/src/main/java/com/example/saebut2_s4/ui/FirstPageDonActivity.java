package com.example.saebut2_s4.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.saebut2_s4.R;

public class FirstPageDonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_first_page_don); // Assurez-vous que R.layout.activity_first_page_don est correct
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> { // Assurez-vous que R.id.main est correct
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Check login status
        boolean isLoggedIn = getSharedPreferences("user_prefs", MODE_PRIVATE)
                .getBoolean("is_logged_in", false);

        RadioButton donRecurrent = findViewById(R.id.don_recurrent);
        Button loginForRecurrentDonation = findViewById(R.id.login_for_recurrent_donation);

        if (isLoggedIn) {
            // Show the RadioButton for recurring donations
            donRecurrent.setVisibility(View.VISIBLE);
            loginForRecurrentDonation.setVisibility(View.GONE);
        } else {
            // Show the login button
            donRecurrent.setVisibility(View.GONE);
            loginForRecurrentDonation.setVisibility(View.VISIBLE);

            // Set click listener for the login button
            loginForRecurrentDonation.setOnClickListener(v -> {
                Intent intent = new Intent(this, ConnexionActivity.class);
                startActivity(intent);
            });
        }

        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstPageDonActivity.this, AccueilActivity.class);
                startActivity(intent);
            }
        });

        Button buttonNext = findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(v -> {
            EditText montantInput = findViewById(R.id.montant_input);

            String montantText = montantInput.getText().toString().trim();
            if (montantText.isEmpty()) {
                Toast.makeText(this, "Veuillez saisir un montant", Toast.LENGTH_SHORT).show();
                return;
            }

            double montant = Double.parseDouble(montantText);

            if (isLoggedIn) {
                // Redirect logged-in users to MoyenPaiementActivity
                Intent intent = new Intent(FirstPageDonActivity.this, MoyenPaiementActivity.class);
                intent.putExtra("montant", montant);
                startActivity(intent);
            } else {
                // Redirect non-logged-in users to EffectuerDonActivity
                Intent intent = new Intent(FirstPageDonActivity.this, EffectuerDonActivity.class);
                intent.putExtra("montant", montant);
                startActivity(intent);
            }
        });

    }
}