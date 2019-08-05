package com.alain.dao.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Voie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    @Column (length = 2)
    private String cotation;
    private double altitude;
    private int nbLongueurs;
    private String description;

    // Associations
    @ManyToOne
    private Utilisateur utilisateur;
    @ManyToOne
    private Secteur secteur;

    @OneToMany (mappedBy = "voie")
    private List<CommentaireVoie> commentaires;
    @OneToMany (mappedBy = "voie")
    private List<ComplementVoie> complements;
    @OneToMany (mappedBy = "voie")
    private List<PhotoVoie> photos;

    /* ********************************************************************************************
     **** CONSTRUCTORS      ************************************************************************
     *********************************************************************************************** */

    public Voie() {
    }

    public Voie(String nom, String cotation, double altitude, int nbLongueurs, String commentaire) {
        this.nom = nom;
        this.cotation = cotation;
        this.altitude = altitude;
        this.nbLongueurs = nbLongueurs;
        this.description = commentaire;
    }

    /* ********************************************************************************************
     **** METHODS           ************************************************************************
     ******************************************************************************************** */

    /* ***********************************************************************************************
     **** GETTERS & SETTERS ************************************************************************
     *********************************************************************************************** */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public String getCotation() {
        return cotation;
    }

    public void setCotation(String cotation) {
        this.cotation = cotation;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public int getNbLongueurs() {
        return nbLongueurs;
    }

    public void setNbLongueurs(int nbLongueurs) {
        this.nbLongueurs = nbLongueurs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<CommentaireVoie> getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(List<CommentaireVoie> commentaires) {
        this.commentaires = commentaires;
    }

    public List<ComplementVoie> getComplements() {
        return complements;
    }

    public void setComplements(List<ComplementVoie> complements) {
        this.complements = complements;
    }

    public List<PhotoVoie> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoVoie> photos) {
        this.photos = photos;
    }
}
