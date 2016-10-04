package services;

import models.Evenement;
import org.hibernate.*;
import java.util.Date;
import java.util.List;

public class EvenementService {
    private static EvenementService monInstance;

    private EvenementService(){

    }

    public static  EvenementService get(){
        if(monInstance == null){
            monInstance = new EvenementService();
        }
        return monInstance;
    }

    /**
     *  Enregistre un nouvelle évènement [nom] par [idCreateur] avec pour péridode [debut] à [fin]
     *  Fait appel à sa methode soeur addEvent(Evenement evenement)
     */
    public void addEvent(Date debut, Date fin, String nom, String idCreateur) throws Exception {
        //création de l'objet
        Evenement evenement = new Evenement();
        evenement.nom = nom;
        evenement.dateDebut = debut;
        evenement.dateFin = fin;
        evenement.idCreateur = idCreateur;
        addEvent(evenement);
    }

    //Enregistre un nouvelle évènement à partir d'un objet Evenement [evenemnt]
    public void addEvent(Evenement evenement) throws Exception {
        //vérifications
        boolean estOk = true;
        estOk = estOk && validateDates(evenement.dateDebut, evenement.dateFin);
        estOk = estOk && validateIdCreateur(evenement.idCreateur);
        estOk = estOk && validateNom(evenement.nom);
        if(!estOk){
            throw new Exception("Invalide argument exception.");
        }else {
            //enregistrement
            Session session = HibernateUtils.getSession();
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.save(evenement);
                tx.commit();
            }catch (HibernateException e) {
                if (tx!=null) tx.rollback();
                throw new Exception("HibernateException: " + e.getMessage() );
            }finally {
                session.close();
            }
        }
    }

    //Lister des évènement entre les dates [debut] et [fin]
    public List<Evenement> listEvent(Date debut, Date fin){
        Session session = HibernateUtils.getSession();
        List<Evenement> listResultats = session.createQuery("FROM Evenement WHERE dateDebut > :dtFin AND dateFin < :dtDebut")
                .setDate("dtDebut", debut)
                .setDate("dtFin", fin)
                .list();
        session.close();
        return listResultats;
    }

    //Récupération d'un évènement par son ID
    public Evenement getEvent(long id){
        Session session = HibernateUtils.getSession();
            Evenement resultat = (Evenement) session.get("Evenement", id);
        session.close();
        return resultat;
    }

    //Update evenement
    public void updateEvent(Evenement evenement) throws Exception {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.update(evenement);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            throw new Exception("HibernateException: " + e.getMessage() );
        }finally {
            session.close();
        }
    }

    //DELETE evenement
    public void deleteEvent(Evenement evenement) throws Exception {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.delete(evenement);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            throw new Exception("HibernateException: " + e.getMessage() );
        }finally {
            session.close();
        }
    }

//Règles de validité de la classe Evenement
    private boolean validateDates(Date debut, Date fin){
        //règle : date de début <= date de fin
        if( debut.after(fin) ) {
            return false;
        }
        return  true;
    }

    private boolean validateIdCreateur(String id){
        //règle : l'id ne peut être vide
        if(id == null | id == ""){
            return false;
        }
        //règle l'id doit faire partie des utilisateurs connus
        //TODO a compléter avec UtilisateurService
        return true;
    }

    private boolean validateNom(String nom){
        //règle : le nom ne peut être vide
        if(nom == null | nom == ""){
            return false;
        }
        return true;
    }
}
