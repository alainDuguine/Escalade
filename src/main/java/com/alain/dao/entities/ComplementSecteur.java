package com.alain.dao.entities;

import javax.persistence.*;

@Entity
@Table
@PrimaryKeyJoinColumn(name="id")
public class ComplementSecteur extends Complement {

    @ManyToOne
    private Secteur secteur;

    public ComplementSecteur() {
    }

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }
}
