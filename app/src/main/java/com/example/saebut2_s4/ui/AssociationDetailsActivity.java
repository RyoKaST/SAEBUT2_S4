package com.example.saebut2_s4.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.saebut2_s4.R;

public class AssociationDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_association_details);

        ImageView logoImageView = findViewById(R.id.association_logo);
        TextView nameTextView = findViewById(R.id.association_name);
        TextView descriptionTextView = findViewById(R.id.association_description);
        TextView sitewebTextView = findViewById(R.id.association_siteweb);
        Button donnerButton = findViewById(R.id.donner_button);

        // Get data from intent
        String name = getIntent().getStringExtra("association_name");
        String description = getIntent().getStringExtra("association_description");
        String siteweb = getIntent().getStringExtra("association_siteweb");
        String logoUrl = getIntent().getStringExtra("association_logo");

        // Set data to views
        nameTextView.setText(name);
        descriptionTextView.setText(description);
        sitewebTextView.setText(siteweb != null ? siteweb : "No website available");

        // Load logo using Glide
        Glide.with(this)
            .load(logoUrl)
            .placeholder(R.drawable.placeholder_logo) // Add a placeholder image in res/drawable
            .error(R.drawable.error_logo) // Add an error image in res/drawable
            .into(logoImageView);

        // Set click listener for the "Donner" button
        donnerButton.setOnClickListener(v -> {
            Intent intent = new Intent(AssociationDetailsActivity.this, FirstPageDonActivity.class);
            startActivity(intent);
        });
    }
}
