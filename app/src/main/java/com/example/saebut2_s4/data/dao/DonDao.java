package com.example.saebut2_s4.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.saebut2_s4.data.model.Don;

import java.util.List;

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
}
