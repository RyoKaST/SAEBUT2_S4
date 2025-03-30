package com.example.saebut2_s4.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.saebut2_s4.R;

public class AssociationDetailsActivity extends AppCompatActivity {

    private static final String TAG = "AssociationDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_association_details);

        ImageView logoImageView = findViewById(R.id.association_logo);
        TextView nameTextView = findViewById(R.id.association_name);
        TextView descriptionTextView = findViewById(R.id.association_description);
        Button sitewebButton = findViewById(R.id.association_siteweb_button);
        Button donnerButton = findViewById(R.id.donner_button);

        // Get data from intent
        String name = getIntent().getStringExtra("association_name");
        String description = getIntent().getStringExtra("association_description");
        String lien = getIntent().getStringExtra("association_siteweb"); // Updated to use "lien"
        String logoUrl = getIntent().getStringExtra("association_logo");

        // Set data to views
        nameTextView.setText(name);
        descriptionTextView.setText(description);

        if (lien != null && !lien.isEmpty()) {
            // Ensure the URL is properly formatted
            final String formattedUrl = lien.startsWith("http://") || lien.startsWith("https://")
                ? lien
                : "https://" + lien;

            sitewebButton.setOnClickListener(v -> {
                Log.d(TAG, "Website button clicked: " + formattedUrl); // Log the click event
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(formattedUrl));
                    startActivity(browserIntent);
                } catch (Exception e) {
                    Log.e(TAG, "Error opening website: " + formattedUrl, e);
                }
            });
        } else {
            sitewebButton.setText("No website available");
            sitewebButton.setEnabled(false);
        }

        // Load logo using Glide
        Glide.with(this)
            .load(logoUrl)
            .placeholder(R.drawable.placeholder_logo)
            .error(R.drawable.error_logo)
            .into(logoImageView);

        // Set click listener for the "Donner" button
        donnerButton.setOnClickListener(v -> {
            Log.d(TAG, "Donner button clicked"); // Log the click event
            Intent intent = new Intent(AssociationDetailsActivity.this, FirstPageDonActivity.class);
            startActivity(intent);
        });
    }
}
