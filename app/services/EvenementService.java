package services;

import models.Evenement;
import models.Utilisateur;
import models.types.Categorie;
import org.hibernate.*;
import play.Logger;

import java.util.Date;
import java.util.List;

public class EvenementService {
    private static final String TAG = "EvenementService";
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
    public Evenement addEvent(Date debut, Date fin, String nom, Utilisateur createur) throws Exception {
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
    public Evenement addEvent(Evenement evenement) throws Exception {
        Logger.debug(TAG + " addEvent: [Event] %s", (evenement!=null?evenement.toString():"null") );
        //vérifications
        boolean estOk = true;
        estOk = estOk && validateDates(evenement.dateDebut, evenement.dateFin);
        estOk = estOk && validateIdCreateur(evenement.createur);
        estOk = estOk && validateNom(evenement.nom);
        if(!estOk){
            throw new Exception("Invalide argument exception.");
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
                throw new Exception("HibernateException: " + e.getMessage() );
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
    public void updateEvent(Evenement evenement) throws Exception {
        Logger.debug(TAG + " updateEvent: [%s]", (evenement!=null?evenement.toString() : "null") );
        //vérifications
        boolean estOk = true;
        estOk = estOk && validateDates(evenement.dateDebut, evenement.dateFin);
        estOk = estOk && validateIdCreateur(evenement.createur);
        estOk = estOk && validateNom(evenement.nom);
        if(!estOk){
            throw new Exception("Invalide argument exception.");
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
                throw new Exception("HibernateException: " + e.getMessage() );
            }finally {
                session.close();
            }
        }
    }

    //DELETE evenement
    public void deleteEvent(Evenement evenement) throws Exception {
        Logger.debug(TAG + " deleteEvent: [%s]", (evenement!=null?evenement.toString() : "null") );
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

    /*
    * Création de plusieurs évènements de manière répété, pour les NB_ANNEES_RECURENCE années avenir.
    */
    public void addEventRepeat(Evenement event, TypeRepetition typeRepetition){
        Logger.debug(TAG + " addEventRepeat: [%s %s]", (event!=null?event.toString() : "null"), (typeRepetition!=null?typeRepetition.getNb() + " " + typeRepetition.type : "null") );
        //vérification

    }

    public enum TypeRepetition{
        JOUR(1, 1), SEMAINE(1, 2), DEUX_SEMAINES(2, 2), MOIS(2, 3), ANNEES(1, 4);

        private int nb;
        private int type;

        private TypeRepetition(int nb, int type){
            this.nb = nb;
            this.type = type;
        }

        private int getNb(){
            return nb;
        }

        private int getType(){
            return type;
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
