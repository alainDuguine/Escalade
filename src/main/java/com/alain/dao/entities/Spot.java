package com.alain.dao.entities;

import com.alain.dao.contract.EntityRepository;
import com.alain.dao.impl.SpotDaoImpl;
import com.alain.metier.Utilities;
import javax.persistence.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table
@MultipartConfig
public class Spot extends Entitie implements Serializable {
    public static final String CHAMP_NOM = "nom";
    public static final String CHAMP_ADRESSE = "adresse";
    public static final String CHAMP_VILLE = "ville";
    public static final String CHAMP_DEPARTEMENT = "departement";
    public static final String CHAMP_DESCRIPTION = "description";

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String adresse;
    @Column (length = 2000)
    private String description;
    private Boolean officiel = false;

    // Associations
    @ManyToOne
    private Utilisateur utilisateur;
    @ManyToOne
    private Departement departement;
    @ManyToOne
    private Ville ville;

    @OneToMany (mappedBy ="spot")
    private List<Secteur> secteurs;
    @OneToMany (mappedBy = "spot")
    private List<CommentaireSpot> commentaires;
    @OneToMany (mappedBy = "spot")
    private List<PhotoSpot> photos = new ArrayList<>();
    @OneToMany(mappedBy = "spot")
    private List<ComplementSpot> topo;
    @ManyToMany (mappedBy = "spot")
    private List<Topo> topos;

    public Spot() {
    }

    public Spot(String nom, String adresse, String description, Boolean officiel) {
        this.nom = nom;
        this.adresse = adresse;
        this.description = description;
        this.officiel = false;
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Boolean getOfficiel() {
        return officiel;
    }

    public void setOfficiel(Boolean officiel) {
        this.officiel = officiel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //    public Ville getVille() {
//        return ville;
//    }
//
//    public void setVille(Ville ville) {
//        this.ville = ville;
//    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<Secteur> getSecteurs() {
        return secteurs;
    }

    public void setSecteurs(List<Secteur> secteurs) {
        this.secteurs = secteurs;
    }

    public void addSecteur(Secteur secteur){
        this.secteurs.add(secteur);
    }

    public List<CommentaireSpot> getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(List<CommentaireSpot> commentaires) {
        this.commentaires = commentaires;
    }

    public List<ComplementSpot> getTopo() {
        return topo;
    }

    public void setTopo(List<ComplementSpot> topo) {
        this.topo = topo;
    }

    public List<Topo> getTopos() {
        return topos;
    }

    public void setTopos(List<Topo> topos) {
        this.topos = topos;
    }

    public List<PhotoSpot> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoSpot> photos) {
        this.photos = photos;
    }

    public void addPhoto(PhotoSpot photo){
        photo.setSpot(this);
        this.photos.add(photo);
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
        departement.addSpot(this);
    }

    public Ville getVille() {
        return ville;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
        ville.addSpot(this);
    }

    @Override
    public void hydrate(HttpServletRequest req) {
        this.setNom(Utilities.getValeurChamp(req, CHAMP_NOM));
        this.setAdresse(Utilities.getValeurChamp(req, CHAMP_ADRESSE));
        this.setDescription(Utilities.getValeurChamp(req, CHAMP_DESCRIPTION));
}

    @Override
    public Map<String, String> checkErreurs(EntityRepository dao, HttpServletRequest req) {
        Map<String, String> listErreur = new HashMap<String, String>();

        if (Utilities.isEmpty(this.nom)) {
            listErreur.put(CHAMP_NOM, "Veuillez entrer le nom du spot");
        }
        if (!checkSpotExist((SpotDaoImpl)dao, req).isEmpty()){
            listErreur.put(CHAMP_NOM, "Un spot du même nom existe déjà dans ce département");
        }
        if (Utilities.isEmpty(this.adresse)) {
            listErreur.put(CHAMP_ADRESSE, "Veuillez entrer l'adresse du spot");
        }
        if (Utilities.isEmpty(this.description) || this.description.length() < 10) {
            listErreur.put(CHAMP_DESCRIPTION, "Veuillez entrer une description d'au moins 50 caractères");
        }else if (this.description.length() > 2000){
            listErreur.put(CHAMP_DESCRIPTION, "Veuillez entrer une description de maximum 2000 caractères.");
        }

        return listErreur;
    }

    private List<Spot> checkSpotExist(SpotDaoImpl dao, HttpServletRequest req) {
        List<Spot> spots;
        return spots = dao.findSpotInDepartement(this.nom, req.getParameter(CHAMP_DEPARTEMENT));
    }
}
