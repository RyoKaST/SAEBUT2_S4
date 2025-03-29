package com.example.saebut2_s4.ui;

import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.example.saebut2_s4.R;

public class AccueilActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_acceuil);

        // Use WindowInsetsControllerCompat for managing system UI visibility
        WindowInsetsControllerCompat windowInsetsController =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());

        // Set the behavior of the system bars to be "transparent"
        windowInsetsController.setAppearanceLightNavigationBars(false); // Or true, depending on your design
        windowInsetsController.setAppearanceLightStatusBars(false); // Or true, depending on your design

        // Hide the system bars behind the content
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());

        // Set the layout to draw behind the system bars
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getWindow().setDecorFitsSystemWindows(false);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}