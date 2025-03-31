package com.example.saebut2_s4.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.saebut2_s4.data.dao.AssociationDao;
import com.example.saebut2_s4.data.dao.DonDao;
import com.example.saebut2_s4.data.dao.UtilisateurDao;
import com.example.saebut2_s4.data.model.Don;
import com.example.saebut2_s4.data.model.Utilisateur;
import com.example.saebut2_s4.data.model.Association;


@Database(entities = {Don.class, Utilisateur.class, Association.class}, version = 24)
@TypeConverters(Converters.class) // Register the Converters class
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract DonDao donDao();
    public abstract UtilisateurDao utilisateurDao();
    public abstract AssociationDao associationDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                    .fallbackToDestructiveMigration() // Use destructive migration for now
                    .build();
        }
        return instance;
    }
}
