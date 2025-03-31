package com.example.saebut2_s4.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.saebut2_s4.MyApp;
import com.example.saebut2_s4.R;
import com.example.saebut2_s4.data.model.Association;

import java.util.ArrayList;
import java.util.List;

public class TagAssociationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_associations);

        TextView tagTitle = findViewById(R.id.tag_title);
        ListView associationsListView = findViewById(R.id.associations_list);

        // Get the clicked tag from the intent
        String tag = getIntent().getStringExtra("tag");
        tagTitle.setText("Associations for: " + tag);

        // Filter associations by the tag
        List<Association> associations = MyApp.getInstance().getAssociations();
        List<Association> filteredAssociations = new ArrayList<>();
        for (Association association : associations) {
            if (association.getTags().contains(tag)) {
                filteredAssociations.add(association);
                if (filteredAssociations.size() == 3) break; // Limit to 3 associations
            }
        }

        // Set up the adapter for the ListView
        AssociationAdapter adapter = new AssociationAdapter(this, filteredAssociations);
        associationsListView.setAdapter(adapter);

        // Handle item clicks to open association details
        associationsListView.setOnItemClickListener((parent, view, position, id) -> {
            Association selectedAssociation = filteredAssociations.get(position);
            Intent intent = new Intent(this, AssociationDetailsActivity.class);
            intent.putExtra("association_name", selectedAssociation.getNomAssociation());
            intent.putExtra("association_description", selectedAssociation.getDescriptionAssociation());
            intent.putExtra("association_siteweb", selectedAssociation.getLien());
            intent.putExtra("association_logo", selectedAssociation.getLogoUrl());
            startActivity(intent);
        });
    }
}
