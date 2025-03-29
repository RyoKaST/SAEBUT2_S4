package com.example.saebut2_s4.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "associations")
public class Association {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id_association")
    private long idAssociation;

    @ColumnInfo(name="nom_association")
    private String nomAssociation;

    @ColumnInfo(name="description_association")
    private String descriptionAssociation;

    @ColumnInfo(name="siteweb_association")
    private String siteweb;

    // Constructeur public sans argument pour Room
    public Association() {
    }

    // Getters et Setters

    public long getIdAssociation() {
        return idAssociation;
    }

    public void setIdAssociation(long idAssociation) {
        this.idAssociation = idAssociation;
    }

    public String getNomAssociation() {
        return nomAssociation;
    }

    public void setNomAssociation(String nomAssociation) {
        this.nomAssociation = nomAssociation;
    }

    public String getDescriptionAssociation() {
        return descriptionAssociation;
    }

    public void setDescriptionAssociation(String descriptionAssociation) {
        this.descriptionAssociation = descriptionAssociation;
    }

    public String getSiteweb() {
        return siteweb;
    }

    public void setSiteweb(String siteweb) {
        this.siteweb = siteweb;
    }
}
