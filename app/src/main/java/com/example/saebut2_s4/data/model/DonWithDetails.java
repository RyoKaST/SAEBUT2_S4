package com.example.saebut2_s4.data.model;

import androidx.room.ColumnInfo;

public class DonWithDetails {
    @ColumnInfo(name = "id_don")
    private long idDon;

    @ColumnInfo(name = "montant_don")
    private double montantDon;

    @ColumnInfo(name = "date_don")
    private String dateDon;

    @ColumnInfo(name = "utilisateurNom")
    private String utilisateurNom;

    @ColumnInfo(name = "associationNom")
    private String associationNom;

    // Getters and Setters
    public long getIdDon() {
        return idDon;
    }

    public void setIdDon(long idDon) {
        this.idDon = idDon;
    }

    public double getMontantDon() {
        return montantDon;
    }

    public void setMontantDon(double montantDon) {
        this.montantDon = montantDon;
    }

    public String getDateDon() {
        return dateDon;
    }

    public void setDateDon(String dateDon) {
        this.dateDon = dateDon;
    }

    public String getUtilisateurNom() {
        return utilisateurNom;
    }

    public void setUtilisateurNom(String utilisateurNom) {
        this.utilisateurNom = utilisateurNom;
    }

    public String getAssociationNom() {
        return associationNom;
    }

    public void setAssociationNom(String associationNom) {
        this.associationNom = associationNom;
    }
}