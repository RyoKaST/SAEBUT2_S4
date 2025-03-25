package com.example.saebut2_s4.data.model;

import androidx.annotation.NonUiContext;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "utilisateurs")
public class Utilisateur {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id_utilisateur")
    private long idUtilisateur;

    @ColumnInfo(name = "nom_utilisateur")
    private String nomUtilisateur;

    @ColumnInfo(name="prenom_utilisateur")
    private String prenomUtilisateur;

    @ColumnInfo(name="email_utilisateur")
    private String emailUtilisateur;

    @ColumnInfo(name="telephone_utilisateur")
    private int telephoneUtilisateur;

    @ColumnInfo(name="motdepasse_utilisateur")
    private String motDePasseUtilisateur;

    // Constructeur public sans argument pour Room
    public Utilisateur() {
    }

    // Getters et Setters

    public long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getPrenomUtilisateur() {
        return prenomUtilisateur;
    }

    public void setPrenomUtilisateur(String prenomUtilisateur) {
        this.prenomUtilisateur = prenomUtilisateur;
    }

    public String getEmailUtilisateur() {
        return emailUtilisateur;
    }

    public void setEmailUtilisateur(String emailUtilisateur) {
        this.emailUtilisateur = emailUtilisateur;
    }

    public int getTelephoneUtilisateur() {
        return telephoneUtilisateur;
    }

    public void setTelephoneUtilisateur(int telephoneUtilisateur) {
        this.telephoneUtilisateur = telephoneUtilisateur;
    }

    public String getMotDePasseUtilisateur() {
        return motDePasseUtilisateur;
    }

    public void setMotDePasseUtilisateur(String motDePasseUtilisateur) {
        this.motDePasseUtilisateur = motDePasseUtilisateur;
    }
}