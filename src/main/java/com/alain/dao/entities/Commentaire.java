package com.alain.dao.entities;

import com.alain.dao.contract.EntityRepository;
import com.alain.metier.Utilities;
import com.google.gson.annotations.Expose;
import org.apache.commons.lang.StringEscapeUtils;


import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public class Commentaire extends Entitie implements Serializable {
    private static final String CHAMP_CONTENU = "contenu";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateTime;
    @Expose()
    private String dateFormat;
    @Expose()
    private String contenu;

    // Associations
    @ManyToOne
    private Utilisateur utilisateur;

    /* ***********************************************************************************************
       **** CONSTRUCTORS      ************************************************************************
       *********************************************************************************************** */

    public Commentaire() {
    }

    /* ********************************************************************************************
    **** METHODS           ************************************************************************
    ******************************************************************************************** */

    @Override
    public void hydrate(HttpServletRequest req) {
        this.contenu = StringEscapeUtils.escapeHtml(req.getParameter(CHAMP_CONTENU));
        this.dateTime = LocalDateTime.now();
        this.dateFormat = this.setDateFormat();
    }

    @Override
    public Map<String, String> checkErreurs(EntityRepository dao, HttpServletRequest req) {
        Map<String, String> listErreur = new HashMap<>();

        if (Utilities.isEmpty(this.contenu)) {
            listErreur.put(CHAMP_CONTENU, "Le commentaire est vide");
        }else if (this.contenu.length() > 280){
            listErreur.put(CHAMP_CONTENU, "Un commentaire peut au maximum contenir 280 caractères");
        }
        return listErreur;
    }

    /* ***********************************************************************************************
     **** GETTERS & SETTERS ************************************************************************
     *********************************************************************************************** */


    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public String setDateFormat() {
        return Utilities.dateStringFr(this.dateTime);
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}