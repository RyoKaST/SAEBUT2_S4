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
    long inserer(Utilisateur utilisateur); // Return the generated ID

    @Update
    void mettreAJour(Utilisateur utilisateur);

    @Delete
    void supprimer(Utilisateur utilisateur);

    @Query("SELECT * FROM utilisateurs")
    List<Utilisateur> getAllUtilisateurs();

    @Query("SELECT * FROM utilisateurs WHERE email_utilisateur = :email LIMIT 1")
    Utilisateur getUtilisateurByEmail(String email);

    @Query("SELECT * FROM utilisateurs WHERE id_utilisateur = :userId")
    Utilisateur getUtilisateurById(long userId);

    @Update
    void update(Utilisateur utilisateur);
}