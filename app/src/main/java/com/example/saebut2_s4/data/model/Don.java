package com.example.saebut2_s4.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "dons")
public class Don {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_don")
    private long idDon;

    @ColumnInfo(name = "montant_don")
    private double montantDon;

    @ColumnInfo(name = "date_don")
    private String dateDon;

    @ColumnInfo(name = "id_utilisateur")
    private long utilisateurIdDon;

    @ColumnInfo(name = "id_association")
    private long associationIdDon;

    private boolean isRecurrent;
    private String nextPaymentDate; // Store as a timestamp string

    // Constructeur public sans argument pour Room
    public Don() {
    }

    // Constructeur avec paramètres pour faciliter la création d'un don
    public Don(double montantDon, String dateDon, long utilisateurIdDon, long associationIdDon) {
        this.montantDon = montantDon;
        this.dateDon = dateDon;
        this.utilisateurIdDon = utilisateurIdDon;
        this.associationIdDon = associationIdDon;
    }

    // Add a new constructor with all parameters
    public Don(double montantDon, String dateDon, long utilisateurIdDon, long associationIdDon, boolean isRecurrent, String nextPaymentDate) {
        this.montantDon = montantDon;
        this.dateDon = dateDon;
        this.utilisateurIdDon = utilisateurIdDon;
        this.associationIdDon = associationIdDon;
        this.isRecurrent = isRecurrent;
        this.nextPaymentDate = nextPaymentDate;
    }

    // Getters et Setters

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

    public long getUtilisateurIdDon() {
        return utilisateurIdDon;
    }

    public void setUtilisateurIdDon(long utilisateurIdDon) {
        this.utilisateurIdDon = utilisateurIdDon;
    }

    public long getAssociationIdDon() {
        return associationIdDon;
    }

    public void setAssociationIdDon(long associationIdDon) {
        this.associationIdDon = associationIdDon;
    }

    public boolean isRecurrent() {
        return isRecurrent;
    }

    public void setRecurrent(boolean recurrent) {
        isRecurrent = recurrent;
    }

    public String getNextPaymentDate() {
        return nextPaymentDate;
    }

    public void setNextPaymentDate(String nextPaymentDate) {
        this.nextPaymentDate = nextPaymentDate;
    }
}
