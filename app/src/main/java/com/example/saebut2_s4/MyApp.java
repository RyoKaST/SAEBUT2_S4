package com.example.saebut2_s4;

import android.app.Application;
import android.util.Log;

import com.example.saebut2_s4.data.model.Association;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MyApp extends Application {

    private static MyApp instance;
    private List<Association> associations;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // Initialize the associations list
        associations = new ArrayList<>();
        loadAssociationsFromJson();
    }

    public static MyApp getInstance() {
        return instance;
    }

    public List<Association> getAssociations() {
        return associations;
    }

    private void loadAssociationsFromJson() {
        try {
            // Load the JSON file from assets
            InputStream inputStream = getAssets().open("asso.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            // Convert the JSON file content to a string
            String json = new String(buffer, StandardCharsets.UTF_8);

            // Parse the JSON
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("associations");

            // Populate the associations list
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject associationJson = jsonArray.getJSONObject(i);
                String name = associationJson.getString("name");
                String desc = associationJson.getString("desc");
                String logo = associationJson.getString("logo");
                String lien = associationJson.optString("lien", ""); // Get the website link

                // Extract tags
                JSONArray tagsArray = associationJson.getJSONArray("tags");
                List<String> tags = new ArrayList<>();
                for (int j = 0; j < tagsArray.length(); j++) {
                    tags.add(tagsArray.getString(j));
                }

                // Create Association object with tags
                associations.add(new Association(name, desc, logo, lien, tags));
            }
        } catch (Exception e) {
            Log.e("MyApp", "Error loading associations from JSON", e);
        }
    }
}
