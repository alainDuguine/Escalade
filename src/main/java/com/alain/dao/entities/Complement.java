package com.alain.dao.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Non implémenté - On pourra dans le futur permettre aux utilisateurs de publier des compléments d'informations
 * pour chaque spot, secteur ou voie, en utilisant la même logique que la publication de commentaires ou de photos.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Complement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateHeure;
    @Column (length = 2000)
    private String contenu;

    // Associations
    @ManyToOne
    private Utilisateur utilisateur;

    /* ********************************************************************************************
     **** CONSTRUCTORS      ************************************************************************
     *********************************************************************************************** */

    public Complement() {
    }

    public Complement(Date dateHeure, String contenu, Utilisateur utilisateur) {
        this.dateHeure = dateHeure;
        this.contenu = contenu;
        this.utilisateur = utilisateur;
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

    public Date getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(Date dateHeure) {
        this.dateHeure = dateHeure;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public void setId(Long id) {
        this.id = id;
    }

}