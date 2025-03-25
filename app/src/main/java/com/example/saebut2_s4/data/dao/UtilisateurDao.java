package com.example.saebut2_s4.data.dao;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.saebut2_s4.data.model.Utilisateur;
import java.util.List;

@Dao
public interface UtilisateurDao {
    @Insert
    void inserer(Utilisateur utilisateur);

    @Update
    void mettreAJour(Utilisateur utilisateur);

    @Delete
    void supprimer(Utilisateur utilisateur);

    @Query("SELECT * FROM utilisateurs")
    List<Utilisateur> getAllUtilisateurs();
}