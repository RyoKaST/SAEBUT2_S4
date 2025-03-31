package com.example.saebut2_s4.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.saebut2_s4.MyApp;
import com.example.saebut2_s4.R;
import com.example.saebut2_s4.data.model.Association;
import com.example.saebut2_s4.ui.AssociationAdapter;
import com.example.saebut2_s4.ui.AssociationDetailsActivity;
import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private AssociationAdapter adapter;
    private List<Association> associations;
    private List<Association> filteredAssociations;

    public SearchFragment() {
        super(R.layout.fragment_search);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        ListView listView = view.findViewById(R.id.list_associations_search);
        EditText searchBar = view.findViewById(R.id.search_bar);

        associations = MyApp.getInstance().getAssociations();
        filteredAssociations = new ArrayList<>();

        // Truncate descriptions to 60 characters for display
        for (Association association : associations) {
            String truncatedDescription = association.getDescriptionAssociation();
            if (truncatedDescription.length() > 60) {
                truncatedDescription = truncatedDescription.substring(0, 60) + "...";
            }
            filteredAssociations.add(new Association(
                association.getNomAssociation(),
                truncatedDescription,
                association.getLogoUrl(),
                association.getLien() // Include the website link
            ));
        }

        adapter = new AssociationAdapter(requireContext(), filteredAssociations);
        listView.setAdapter(adapter);

        // Add item click listener
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            // Use the original description and link from the associations list
            Association selectedAssociation = associations.get(position);
            Intent intent = new Intent(requireContext(), AssociationDetailsActivity.class);
            intent.putExtra("association_name", selectedAssociation.getNomAssociation());
            intent.putExtra("association_description", selectedAssociation.getDescriptionAssociation());
            intent.putExtra("association_siteweb", selectedAssociation.getLien()); // Pass the website link
            intent.putExtra("association_logo", selectedAssociation.getLogoUrl());
            startActivity(intent);
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterAssociations(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No action needed
            }
        });

        return view;
    }

    private void filterAssociations(String query) {
        filteredAssociations.clear();
        for (Association association : associations) {
            if (association.getNomAssociation().toLowerCase().contains(query.toLowerCase())) {
                String truncatedDescription = association.getDescriptionAssociation();
                if (truncatedDescription.length() > 60) {
                    truncatedDescription = truncatedDescription.substring(0, 60) + "...";
                }
                filteredAssociations.add(new Association(
                    association.getNomAssociation(),
                    truncatedDescription,
                    association.getLogoUrl(),
                    association.getLien() // Include the website link
                ));
            }
        }
        adapter.notifyDataSetChanged();
    }
}
