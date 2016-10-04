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
        estOk = estOk && validateIdCreateur(evenement.nom);
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



    //Règles de validité de la classe Evenement
    private boolean validateDates(Date debut, Date fin){
        if( debut.before(fin) ) {
            return  true;
        }else {
            return false;
        }
    }

    private boolean validateIdCreateur(String id){
        //TODO
        return true;
    }
}
