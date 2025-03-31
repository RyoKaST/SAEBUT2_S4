package com.example.saebut2_s4.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.saebut2_s4.MyApp;
import com.example.saebut2_s4.R;
import com.example.saebut2_s4.data.model.Association;
import com.example.saebut2_s4.ui.TagAssociationsActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FavoritesFragment extends Fragment {
    private static final String TAG = "FavoritesFragment";

    public FavoritesFragment() {
        super(R.layout.fragment_favorites);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        GridLayout gridLayout = view.findViewById(R.id.grid_layout_tags);
        LinearLayout associationsLayout = view.findViewById(R.id.associations_layout);
        EditText searchBar = view.findViewById(R.id.search_bar);

        try {
            // Load tags from asso.json
            Set<String> tags = loadTagsFromJson();

            // Create a list to hold all tag views
            List<TextView> tagViews = new ArrayList<>();

            // Dynamically create tiles for each tag
            for (String tag : tags) {
                TextView tile = new TextView(requireContext());
                tile.setText(tag);
                tile.setTextSize(16);
                tile.setPadding(20, 20, 20, 20);
                tile.setBackgroundResource(R.drawable.tile_background);
                tile.setTextColor(getResources().getColor(R.color.white));
                tile.setGravity(View.TEXT_ALIGNMENT_CENTER);

                // Set click listener for each tile
                tile.setOnClickListener(v -> {
                    // Log and Toast to confirm the click
                    Log.d(TAG, "Tag clicked: " + tag);

                    // Open TagAssociationsActivity
                    Intent intent = new Intent(requireContext(), TagAssociationsActivity.class);
                    intent.putExtra("tag", tag);
                    startActivity(intent);
                });

                // Add tile to the grid layout and the list
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.setMargins(10, 10, 10, 10);
                tile.setLayoutParams(params);

                gridLayout.addView(tile);
                tagViews.add(tile);
            }

            // Add text watcher to filter tags
            searchBar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // No action needed
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String query = s.toString().toLowerCase();
                    for (TextView tagView : tagViews) {
                        if (tagView.getText().toString().toLowerCase().contains(query)) {
                            tagView.setVisibility(View.VISIBLE);
                        } else {
                            tagView.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // No action needed
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Failed to load tags", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private Set<String> loadTagsFromJson() throws Exception {
        InputStream inputStream = requireContext().getAssets().open("asso.json");
        byte[] buffer = new byte[inputStream.available()]; // Removed the extra closing parenthesis
        inputStream.read(buffer);
        inputStream.close();

        String json = new String(buffer, StandardCharsets.UTF_8);
        JSONObject jsonObject = new JSONObject(json);

        // Extract all unique tags from the associations
        JSONArray associations = jsonObject.getJSONArray("associations");
        Set<String> tags = new HashSet<>();
        for (int i = 0; i < associations.length(); i++) {
            JSONObject association = associations.getJSONObject(i);
            JSONArray associationTags = association.getJSONArray("tags");
            for (int j = 0; j < associationTags.length(); j++) {
                tags.add(associationTags.getString(j));
            }
        }

        return tags;
    }

    private void displayAssociationsForTag(String tag, LinearLayout associationsLayout) {
        // Clear previous results
        associationsLayout.removeAllViews();

        // Get associations from MyApp
        List<Association> associations = MyApp.getInstance().getAssociations();
        List<Association> filteredAssociations = new ArrayList<>();

        // Filter associations by tag
        for (Association association : associations) {
            if (association.getTags().contains(tag)) {
                filteredAssociations.add(association);
                if (filteredAssociations.size() == 3) break; // Limit to 3 associations
            }
        }

        // Display filtered associations
        if (filteredAssociations.isEmpty()) {
            TextView noResults = new TextView(requireContext());
            noResults.setText("Aucune association trouvée pour ce tag.");
            noResults.setTextColor(getResources().getColor(R.color.white));
            associationsLayout.addView(noResults);
        } else {
            for (Association association : filteredAssociations) {
                TextView associationView = new TextView(requireContext());
                associationView.setText(association.getNomAssociation()); // Use getNomAssociation()
                associationView.setTextSize(16);
                associationView.setPadding(10, 10, 10, 10);
                associationView.setTextColor(getResources().getColor(R.color.white));
                associationsLayout.addView(associationView);
            }
        }
    }
}
