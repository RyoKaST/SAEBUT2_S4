package com.example.saebut2_s4;

import android.app.Application;

import com.example.saebut2_s4.data.model.Association;

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
        // Add mock data with logo URLs
        associations.add(new Association("Association 1", "Description 1 la desc est tres long de facon expres premedite parce que je suis homosexuel", "https://example.com/logo1.png"));
        associations.add(new Association("Association 2", "Description 2", "https://example.com/logo2.png"));
    }

    public static MyApp getInstance() {
        return instance;
    }

    public List<Association> getAssociations() {
        return associations;
    }
}
