package com.example.saebut2_s4.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.saebut2_s4.data.model.Association;

import java.util.List;

@Dao
public interface AssociationDao {

    @Insert
    void inserer(Association association);

    @Update
    void mettreAJour(Association association);

    @Delete
    void supprimer(Association association);

    @Query("SELECT * FROM associations")
    List<Association> getAllAssociations();

    @Query("SELECT * FROM associations WHERE id_association = :associationId")
    Association getAssociationById(long associationId);
}