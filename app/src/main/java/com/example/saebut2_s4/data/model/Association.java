package com.example.saebut2_s4.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "associations")
public class Association {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_association")
    private long idAssociation;

    @ColumnInfo(name = "nom_association")
    private String nomAssociation;

    @ColumnInfo(name = "description_association")
    private String descriptionAssociation;

    @ColumnInfo(name = "siteweb_association")
    private String siteweb;

    @ColumnInfo(name = "logo_url")
    private String logoUrl;

    @ColumnInfo(name = "lien")
    private String lien; // New field for the website link

    @ColumnInfo(name = "tags")
    private List<String> tags; // Add tags field

    // Default constructor for Room
    public Association() {
    }

    // Constructor
    public Association(String nomAssociation, String descriptionAssociation, String logoUrl, String lien, List<String> tags) {
        this.nomAssociation = nomAssociation;
        this.descriptionAssociation = descriptionAssociation;
        this.logoUrl = logoUrl;
        this.lien = lien;
        this.tags = tags; // Initialize tags
    }

    // Getters and Setters
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

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public List<String> getTags() {
        return tags; // Getter for tags
    }

    public void setTags(List<String> tags) {
        this.tags = tags; // Setter for tags
    }
}
