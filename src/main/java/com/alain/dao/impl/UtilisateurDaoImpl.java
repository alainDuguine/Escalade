package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.Utilisateur;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class UtilisateurDaoImpl implements EntityRepository<Utilisateur>{

    EntityManager entityManager = EntityManagerUtil.getEntityManager();

    public Utilisateur save(Utilisateur utilisateur, HttpServletRequest req) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(utilisateur);
            transaction.commit();
        } catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
        return utilisateur;
    }

    public Utilisateur update(Utilisateur utilisateur) {
        entityManager.merge(utilisateur);
        return utilisateur;
    }

    public void delete(Long id) {
        Utilisateur utilisateur = entityManager.find(Utilisateur.class, id);
        entityManager.remove(utilisateur);
    }

    public List<Utilisateur> findAll() {
        Query query = entityManager.createQuery("select u from Utilisateur u");
        return query.getResultList();
    }

    public Utilisateur findOne(Long id) {
        return entityManager.find(Utilisateur.class, id);
    }

    public Utilisateur findByEmail(String email) {
        Query query = entityManager.createQuery( "select u from Utilisateur u where u.email = :email");
        query.setParameter("email", email);
        try {
            return (Utilisateur) query.getSingleResult();
        } catch (Exception e){
            return null;
        }
    }

    public Utilisateur findByUsername(String username) {
        Query query = entityManager.createQuery("select u from Utilisateur u where u.username= :username");
        query.setParameter("username", username );
        try {
            return (Utilisateur) query.getSingleResult();
        } catch (Exception e){
            return null;
        }
    }
}
