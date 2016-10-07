package services;

import models.Evenement;
import models.Utilisateur;
import models.types.Categorie;
import org.hibernate.*;
import org.hibernate.criterion.Projections;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import play.Logger;

import java.util.Date;
import java.util.List;

public class EvenementService {
    private static final String TAG = "EvenementService";
    private static final int NB_ANNEES_RECURENCE = 25;
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
    public Evenement addEvent(Date debut, Date fin, String nom, Utilisateur createur) throws IllegalArgumentException {
        Logger.debug(TAG + " addEvent: [%s %s %s]", debut, fin, (createur!=null?createur.email:"null"));
        //création de l'objet
        Evenement evenement = new Evenement();
        evenement.nom = nom;
        evenement.dateDebut = debut;
        evenement.dateFin = fin;
        evenement.createur = createur;
        Evenement resultat = addEvent(evenement);
        return resultat;
    }

    //Enregistre un nouvelle évènement à partir d'un objet Evenement [evenemnt]
    public Evenement addEvent(Evenement evenement) throws IllegalArgumentException, HibernateException {
        Logger.debug(TAG + " addEvent: [Event] %s", (evenement!=null?evenement.toString():"null") );
        //vérifications
        if(!validateEvent(evenement)){
            throw new IllegalArgumentException("Invalide argument IllegalArgumentException.");
        }else {
            //enregistrement
            Session session = HibernateUtils.getSession();
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                    evenement.idEvenement = (long) session.save(evenement);
                tx.commit();
            }catch (HibernateException e) {
                if (tx!=null) tx.rollback();
                throw e;
            }finally {
                session.close();
            }
        }
        return evenement;
    }

    //Lister des évènement entre les dates [debut] et [fin]
    public List<Evenement> listEvent(Date debut, Date fin){
        Logger.debug(TAG + " listEvent: [%s %s]", debut, fin);
        Session session = HibernateUtils.getSession();
        Query query = session.createQuery("FROM Evenement WHERE dateDebut <= :dtFin AND dateFin >= :dtDebut ORDER BY dateDebut ASC");
        query.setTimestamp("dtDebut", debut);
        query.setTimestamp("dtFin", fin);
        List<Evenement> listResultats = query.list();
        session.close();
        return listResultats;
    }
    //Lister des évènement entre les dates [debut] et [fin]
    public List<Evenement> listEvent(Date debut, Date fin, Categorie categorie){
        Logger.debug(TAG + " listEvent: [%s %s %s]", debut, fin, (categorie!=null?categorie.getLabel():"null"));
        Session session = HibernateUtils.getSession();
        Query query = session.createQuery("FROM Evenement WHERE dateDebut <= :dtFin AND dateFin >= :dtDebut AND categorie = :cat ORDER BY dateDebut ASC");
        query.setTimestamp("dtDebut", debut);
        query.setTimestamp("dtFin", fin);
        query.setString("cat", categorie.getLabel() );
        List<Evenement> listResultats = query.list();
        session.close();
        return listResultats;
    }

    /* Récupération d'un évènement par son ID
     * RETURN : Null si non trouvé.
     */
    public Evenement getEvent(long id){
        Logger.debug(TAG + " getEvent: [%d]", id );
        Session session = HibernateUtils.getSession();
            Evenement resultat = (Evenement) session.get(Evenement.class, id);
        session.close();
        return resultat;
    }

    //Update evenement
    public void updateEvent(Evenement evenement) throws IllegalArgumentException, HibernateException {
        Logger.debug(TAG + " updateEvent: [%s]", (evenement!=null?evenement.toString() : "null") );
        //vérifications
        if(!validateEvent(evenement) ){
            throw new IllegalArgumentException("Invalide argument IllegalArgumentException.");
        }else {
            //sauvegarde
            Session session = HibernateUtils.getSession();
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.update(evenement);
                tx.commit();
            }catch (HibernateException e) {
                if (tx!=null) tx.rollback();
                throw e;
            }finally {
                session.close();
            }
        }
    }

    //DELETE evenement
    public void deleteEvent(Evenement evenement) throws IllegalArgumentException {
        Logger.debug(TAG + " deleteEvent: [%s]", (evenement!=null?evenement.toString() : "null") );
        Session session = HibernateUtils.getSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.delete(evenement);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            throw e;
        }finally {
            session.close();
        }
    }

    /*
    * Création de plusieurs évènements de manière répété, pour les NB_ANNEES_RECURENCE années avenir.
    * IN : event = evenement de reference, typeRepetition = type de répétition des évènements
    * RETURN : le premier evenement enregistré.
    */
    public Evenement addEventsRepeat(Evenement event, TypeRepetition typeRepetition) throws IllegalArgumentException {
        Logger.debug(TAG + " addEventRepeat: [%s %s]", (event!=null?event.toString() : "null"), (typeRepetition!=null?typeRepetition.getNb() + " " + typeRepetition.type : "null") );
        //vérifications
        if( !validateEvent(event) | typeRepetition == null){
            throw new IllegalArgumentException("Invalide argument IllegalArgumentException.");
        }else {
            Evenement premiereEvent = null;
            event.idRepetition = generateIdRepetition();
            DateTime dateFinRepetition = new DateTime(event.dateDebut).plusYears(NB_ANNEES_RECURENCE);
            DateTime dateDebut = new DateTime(event.dateDebut);
            Duration dureeEvent = new Duration(dateDebut, new DateTime(event.dateFin));

            Session session = HibernateUtils.getSession();
            Transaction tx = session.beginTransaction();
            int i = 0;
            long idPremier = 0;
            while(dateDebut.isBefore(dateFinRepetition)){
                //enregistrement du nouvel evenement
                Evenement eventI = new Evenement(event);
                eventI.dateDebut = dateDebut.toDate();
                eventI.dateFin = dateDebut.plus(dureeEvent).toDate();
                if(i == 0){
                    idPremier = (long) session.save(event);
                }else {
                    session.save(eventI);
                }

                //on pousse dans la base de données par packet de 20
                i++;
                if(i%30 == 0){
                    session.flush();
                    session.clear();
                }

                //préparation du prochain
                switch (typeRepetition.type){
                    case "DAY":
                        dateDebut = dateDebut.plusDays( typeRepetition.getNb() );
                        break;
                    case "WEEK":
                        dateDebut = dateDebut.plusWeeks( typeRepetition.getNb() );
                        break;
                    case "MONTH":
                        dateDebut = dateDebut.plusMonths( typeRepetition.getNb() );
                        break;
                    case "YEAR":
                        dateDebut = dateDebut.plusYears( typeRepetition.getNb() );
                        break;
                }
            }
            tx.commit();
            session.close();
            if(idPremier == 0){
                return null;
            }else {
                return getEvent(idPremier);
            }
        }
    }

    /*
     * Supprime l'évènement en entré et ses frères suivant (même catégorie + dateDebut > ).
     */
    public void deleteEventsRepeatFrom(Evenement event) throws IllegalArgumentException, HibernateException {
        Logger.debug(TAG + " deleteEventsRepeatFrom : [%s]", (event!=null?event.toString():"null") );
        //verifications :
        if(event == null){
            Logger.error(TAG + " deleteEventsRepeatFrom : Null input argument IllegalArgumentException.");
            throw new IllegalArgumentException("Null argument IllegalArgumentException.");
        }
        //Cas pas de répéitions
        if(event.idRepetition == null){
            Logger.debug(TAG + "deleteEventsRepeatFrom : idRepetition is null, redirect to deleteEvent.");
            deleteEvent(event);
            return;
        }

        //Préparation et execution de la requête:
        int result = 0;
        Session session = HibernateUtils.getSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Query query = session.createSQLQuery("DELETE FROM Evenement WHERE idRepetition = :idRep AND dateDebut >= :dtDebut");
            query.setLong("idRep", event.idRepetition);
            query.setDate("dtDebut", event.dateDebut);
            result = query.executeUpdate();
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            Logger.error(TAG + "deleteEventsRepeatFrom : HibernateException, " + e.getMessage());
            throw e;
        }finally {
            session.close();
        }
        Logger.debug(TAG + "deleteEventsRepeatFrom : %d rows delete.", result);
    }

    
    //Donne idRepetition non utilisé (max +1) dans la bdd
    private Long generateIdRepetition(){
        Logger.debug(TAG + " generateIdRepetition : []");
        Session session = HibernateUtils.getSession();
            Criteria criteria = session.createCriteria(Evenement.class)
                    .setProjection(Projections.max("idRepetition"));
            Long id = 1L;
            if(criteria.uniqueResult() != null){
                id = (long) criteria.uniqueResult() + 1;
            }
        session.close();
        Logger.debug(TAG + " generateIdRepetition : return %d", id );
        return id;
    }

    public enum TypeRepetition{
        JOURNALIER(1, "DAY"), HEBDOMADAIRE(1, "WEEK"), BIMENSUEL(2, "WEEK"), MENSUEL(1, "MONTH"), ANNUEL(1, "YEAR");

        private int nb;
        private String type;

        private TypeRepetition(int nb, String type){
            this.nb = nb;
            this.type = type;
        }

        private int getNb(){
            return nb;
        }

        private String getType(){
            return type;
        }
    }

    // vide la table Evenement de la base de test
    public void clearTest(){
        Logger.debug(TAG + " clearTest: []");
        HibernateUtils.HibernateTest();
        Session session = HibernateUtils.getSession();
            Transaction tx=session.beginTransaction();
            Query q =session.createQuery("delete Utilisateur ");
            q.executeUpdate();
            tx.commit();
        session.close();
    }

//Règles de validité de la classe Evenement
    private boolean validateEvent(Evenement event){
        if(event == null){
            return false;
        }
        boolean estOk = true;
        estOk = estOk && validateDates(event.dateDebut, event.dateFin);
        estOk = estOk && validateIdCreateur(event.createur);
        estOk = estOk && validateNom(event.nom);
        return estOk;
    }

    private boolean validateDates(Date debut, Date fin){
        //règle : date de début <= date de fin
        if( debut.after(fin) ) {
            return false;
        }
        return  true;
    }

    private boolean validateIdCreateur(Utilisateur utilisateur){
        //règle : l'id ne peut être vide
        if(utilisateur == null | utilisateur.email == null | utilisateur.email == ""){
            return false;
        }
        //règle l'id doit faire partie des utilisateurs connus
        Utilisateur reponse = UtilisateurService.get().getUtilisateurByEmail(utilisateur.email);
        if(reponse == null){
            return false;
        }else {
            return true;
        }
    }

    private boolean validateNom(String nom){
        //règle : le nom ne peut être vide
        if(nom == null | nom == ""){
            return false;
        }
        return true;
    }

}
