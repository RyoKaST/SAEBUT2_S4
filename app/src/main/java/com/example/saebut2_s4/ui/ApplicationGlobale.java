package com.example.saebut2_s4.ui;

import android.app.Application;

public class ApplicationGlobale extends Application {
    private String montantTemporaire = "";

    public String getMontantTemporaire() {
        return montantTemporaire;
    }

    public void setMontantTemporaire(String montant) {
        this.montantTemporaire = montant;
    }
}