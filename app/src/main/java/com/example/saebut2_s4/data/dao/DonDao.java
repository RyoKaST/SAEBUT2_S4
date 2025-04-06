package com.example.saebut2_s4.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.saebut2_s4.data.model.Don;
import com.example.saebut2_s4.data.model.DonWithDetails;
import com.example.saebut2_s4.data.model.Utilisateur;
import com.example.saebut2_s4.data.model.Association;

import java.util.List;

@Dao
public interface DonDao {
    @Insert
    void inserer(Don don);

    @Update
    void mettreAJour(Don don);

    @Delete
    void supprimer(Don don);

    @Query("SELECT * FROM dons")
    LiveData<List<Don>> recupererTousLesDons();

    @Query("SELECT * FROM dons WHERE id_utilisateur = :utilisateurId")
    LiveData<List<Don>> recupererDonsParUtilisateur(long utilisateurId);

    @Query("SELECT * FROM dons WHERE id_association = :associationId")
    LiveData<List<Don>> recupererDonsParAssociation(long associationId);

    @Query("SELECT * FROM dons WHERE id_don = :donId")
    LiveData<Don> recupererDonParId(long donId);

    @Query("SELECT dons.*, utilisateurs.nom_utilisateur AS utilisateurNom, associations.nom_association AS associationNom " +
           "FROM dons " +
           "JOIN utilisateurs ON dons.id_utilisateur = utilisateurs.id_utilisateur " +
           "JOIN associations ON dons.id_association = associations.id_association " +
           "WHERE dons.id_utilisateur = :utilisateurId")
    LiveData<List<DonWithDetails>> recupererDonsAvecDetailsParUtilisateur(long utilisateurId);

    @Query("SELECT * FROM utilisateurs WHERE id_utilisateur = :userId")
    Utilisateur getUtilisateurById(long userId);

    @Query("SELECT * FROM associations WHERE id_association = :associationId")
    Association getAssociationById(long associationId);
}
