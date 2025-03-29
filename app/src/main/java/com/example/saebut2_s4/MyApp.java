package com.example.saebut2_s4;

import android.app.Application;

import com.example.saebut2_s4.data.db.AppDatabase;
import com.example.saebut2_s4.data.model.Association;

import java.util.List;

public class MyApp extends Application {

    private static MyApp instance;
    private AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        appDatabase = AppDatabase.getInstance(this);

        initialiserAssociations();
    }

    public static MyApp getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return appDatabase;
    }

    public List<Association> getAssociations() {
        return appDatabase.associationDao().getAllAssociations();
    }

    private void initialiserAssociations() {
        new Thread(() -> {
            if (appDatabase.associationDao().getAllAssociations().isEmpty()) {
                Association a1 = new Association();
                a1.setNomAssociation("Croix-Rouge");
                a1.setDescriptionAssociation("Aide humanitaire d'urgence");
                a1.setSiteweb("https://www.croix-rouge.fr");

                Association a2 = new Association();
                a2.setNomAssociation("UNICEF");
                a2.setDescriptionAssociation("Protection de l'enfance");
                a2.setSiteweb("https://www.unicef.fr");

                appDatabase.associationDao().inserer(a1);
                appDatabase.associationDao().inserer(a2);
            }
        }).start();
    }
}
