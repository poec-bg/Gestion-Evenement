package services;

import models.Evenement;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.joda.time.DateTime;

public class EvenementService {
    private static EvenementService monInstance;
    private static Session session = null;

    private EvenementService(){

    }

    public static  EvenementService get(){
        if(monInstance == null){
            monInstance = new EvenementService();
        }
        return monInstance;
    }

    //Créer un nouvelle évènement [nom] par [idCreateur] avec pour péridode [debut] à [fin]
    public void createEvent(DateTime debut, DateTime fin, String nom, String idCreateur) throws Exception {
        //vérifications
        boolean estOk = true;
        estOk = estOk && validateDates(debut, fin);
        estOk = estOk && validateIdCreateur(nom);
        if(!estOk){
            throw new Exception("Invalide argument exception.");
        }else {
            //création & enregistrement
            Evenement evenement = new Evenement();
            evenement.nom = nom;
            evenement.dateDebut = debut.toDate();
            evenement.dateFin = fin.toDate();
            evenement.idCreateur = idCreateur;
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


    //Règles de validité de la classe Evenement
    private boolean validateDates(DateTime debut, DateTime fin){
        if(debut.isBefore(fin)) {
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
