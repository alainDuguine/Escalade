package com.alain.dao.entities;

import com.alain.dao.contract.EntityRepository;
import com.alain.dao.impl.TopoDaoImpl;
import com.alain.dao.impl.UtilisateurDaoImpl;
import com.alain.metier.Utilities;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table
@Access(AccessType.FIELD)
public class Reservation extends Entitie implements Serializable {
    private static final String CHAMP_EMPRUNTEUR = "user";
    private static final String ID_TOPO = "idTopo";

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String dernierStatut;
    private String dateDernierStatut;

    // Associations

    @ManyToOne
    private Utilisateur emprunteur;
    @ManyToOne
    private Utilisateur preteur;
    @ManyToOne
    private Topo topo;

    @OneToMany (mappedBy = "reservation", cascade = CascadeType.PERSIST)
    private List<ReservationHistorique> listHistorique = new ArrayList<>();


    /* *********************************************************************************************
     **** CONSTRUCTORS      ************************************************************************
     *********************************************************************************************** */

    public Reservation() {

    }

    public Reservation(String dernierStatut, String dateDernierStatut, Utilisateur emprunteur, Utilisateur preteur, Topo topo) {
        this.dernierStatut = dernierStatut;
        this.dateDernierStatut = dateDernierStatut;
        this.emprunteur = emprunteur;
        this.preteur = preteur;
        this.topo = topo;
    }

    /* *********************************************************************************************
     **** METHODS           ************************************************************************
     *********************************************************************************************** */

    @Override
    public void hydrate(HttpServletRequest req) {
        UtilisateurDaoImpl utilisateurDao = new UtilisateurDaoImpl();
        this.emprunteur = utilisateurDao.findByUsername(req.getParameter(CHAMP_EMPRUNTEUR));
        TopoDaoImpl topoDao = new TopoDaoImpl();
        this.topo = topoDao.findOne(Long.valueOf(req.getParameter(ID_TOPO)));
        this.preteur = this.topo.getUtilisateur();
    }

    @Override
    public Map<String, String> checkErreurs(EntityRepository dao, HttpServletRequest req) {
        Map<String, String> listErreur = new HashMap<>();

        if (this.emprunteur.getUsername() == this.preteur.getUsername()) {
            listErreur.put("erreur", "Vous ne pouvez pas réserver vos propres topos.");
        }else if (!this.topo.isDisponible()){
            listErreur.put("erreur", "Le topo est déjà réservé par un utilisateur");
        }
        return listErreur;
    }

    public void addEvent(ReservationHistorique reservationHistorique){
        this.listHistorique.add(reservationHistorique);
        this.setDernierStatut(reservationHistorique.getReservationStatut().toString());
        this.setDateDernierStatut(Utilities.dateStringFr(reservationHistorique.getDateTime()));
    }

    /* *********************************************************************************************
     **** GETTERS & SETTERS ************************************************************************
     *********************************************************************************************** */

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }

    public Utilisateur getEmprunteur() {
        return emprunteur;
    }

    public void setEmprunteur(Utilisateur emprunteur) {
        this.emprunteur = emprunteur;
    }

    public Utilisateur getPreteur() {
        return preteur;
    }

    public void setPreteur(Utilisateur preteur) {
        this.preteur = preteur;
    }

    public Topo getTopo() {
        return topo;
    }

    public void setTopo(Topo topo) {
        this.topo = topo;
    }

    public List<ReservationHistorique> getListHistorique() {
        return listHistorique;
    }

    public void setListHistorique(List<ReservationHistorique> listHistorique) {
        this.listHistorique = listHistorique;
    }

    public String getDernierStatut() {
        return dernierStatut;
    }

    public void setDernierStatut(String dernierStatut) {
        this.dernierStatut = dernierStatut;
    }

    public String getDateDernierStatut() {
        return dateDernierStatut;
    }

    public void setDateDernierStatut(String dateDernierStatut) {
        this.dateDernierStatut = dateDernierStatut;
    }
}
