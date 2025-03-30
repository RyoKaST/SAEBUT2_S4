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
        // Add mock data or fetch from a database
        associations.add(new Association("Association 1", "Description 1"));
        associations.add(new Association("Association 2", "aaa 2"));
    }

    public static MyApp getInstance() {
        return instance;
    }

    public List<Association> getAssociations() {
        return associations;
    }
}
