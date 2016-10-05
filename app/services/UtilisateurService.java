package services;


import exceptions.InvalidArgumentException;
import models.Utilisateur;
import org.hibernate.*;
import validators.EmailValidator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class UtilisateurService {


    private List<Utilisateur> utilisateurs;

    /**
     * Singleton
     */
    private static UtilisateurService instance;

    /**
     * Constructeur privé = personne ne peut faire de new UtilisateurService()
     */
    private UtilisateurService() {
        utilisateurs = new ArrayList<>();
    }

    public static UtilisateurService get() {
        if (instance == null) {
            instance = new UtilisateurService();
        }
        return instance;
    }

    // Création d'un nouvel utilisateur: email et mots de passe requis

    public Utilisateur create(String email, String motDePasse) throws Exception {


        List<String> validationMessages = new ArrayList<>();
        if (email == null || email.equals("")) {
            validationMessages.add("Le email ne peut être null ou vide");
        } else {
            if (!EmailValidator.validate(email)) {
                validationMessages.add("Le format de l'email est invalide");
            }
        }
        if (motDePasse == null || motDePasse.equals("")) {
            validationMessages.add("Le motDePasse ne peut être null ou vide");
        }
        if (validationMessages.size() > 0) {
            throw new InvalidArgumentException((String[]) validationMessages.toArray(new String[0]));
        }

        Utilisateur utilisateur=new Utilisateur();
        utilisateur.email = email;
        utilisateur.motDePasse=motDePasse;


        Session session = HibernateUtils.getSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.save(utilisateur);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            throw new Exception("HibernateException:  " + e.getMessage() );
        }finally {
            session.close();
        }
        return utilisateur;

    }

      // Lister les utilisateurs

    public   List <Utilisateur> listUtilisateurs( ){
        Session session = HibernateUtils.getSession();


            List<Utilisateur> utilisateurs = session.createQuery("FROM Utilisateur").list();
            for (Iterator iterator =
                 utilisateurs.iterator(); iterator.hasNext();){
                Utilisateur utilisateur = (Utilisateur) iterator.next();

            }
        session.close();
        return utilisateurs;

    }


      // Supprimer un utilisateur

    public void remove(String UtilisateurID){
        Session session = HibernateUtils.getSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Utilisateur utilisateur =
                    (Utilisateur)session.get(Utilisateur.class, UtilisateurID);
            session.delete(utilisateur);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    //Récuperer un utilisateur par son email

    public Utilisateur getUtilisateurByEmail(String email){
        Session session = HibernateUtils.getSession();
        Utilisateur utilisateur=(Utilisateur) session.get("Utilisateur", email);
        return utilisateur;

    }


    public void clear() {
        Session session = HibernateUtils.getSession();
        Transaction tx=session.beginTransaction();

        //creation de la requette
        Query q =session.createQuery("delete Utilisateur ");
        //Exécution de la requete
        q.executeUpdate();
        tx.commit();
    }
}