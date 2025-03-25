package com.example.saebut2_s4.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.saebut2_s4.R;

public class ConnexionActivity extends AppCompatActivity {

    private static final String TAG = "ConnexionActivity";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_connexion);

            Log.d(TAG, "ConnexionActivity onCreate called");

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
                    Intent intent = new Intent(ConnexionActivity.this, AccueilActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            // Configurer le listener pour le bouton Inscription
            Button registerButton = findViewById(R.id.btn_register);
            registerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ConnexionActivity.this, InscriptionActivity.class);
                    startActivity(intent);
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate", e);
        }
    }
}