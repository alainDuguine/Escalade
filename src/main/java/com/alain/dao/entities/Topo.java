package com.alain.dao.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class Topo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private Date dateEdition;
    private String description;
    private Boolean disponible;

    // Associations
    @ManyToOne
    private Utilisateur utilisateur;
    @ManyToMany
    private List<Utilisateur> empruntUtilisateurs;
    @ManyToMany
    private List<Spot> spot;

    public Topo() {
    }

    public Topo(String nom, Date dateEdition, String description, Boolean disponible) {
        this.nom = nom;
        this.dateEdition = dateEdition;
        this.description = description;
        this.disponible = disponible;
    }

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

    public List<Spot> getSpot() {
        return spot;
    }

    public void setSpot(List<Spot> spot) {
        this.spot = spot;
    }
}
