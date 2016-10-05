package services;


import exceptions.InvalidArgumentException;
import models.Utilisateur;
import org.bouncycastle.util.Strings;
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


    public Utilisateur createUtilisateur(String email,String nom, String prenom, String motDePasse) throws Exception {


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
        utilisateur.nom=nom;
        utilisateur.prenom=prenom;

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

      //modifier utilisateur

    public void updateUtilisateur(Utilisateur utilisateur, String nom, String prenom) throws InvalidArgumentException {

        List<String> validationMessages = new ArrayList<>();
        if (utilisateur == null) {
            validationMessages.add("L'utilisateur ne peut être null");
        }
        if (validationMessages.size() > 0) {
            throw new InvalidArgumentException((String[]) validationMessages.toArray(new String[0]));
        }
        if (utilisateur.nom != nom) {
            utilisateur.nom = nom;
        }
        if (utilisateur.prenom != prenom) {
            utilisateur.prenom = prenom;
        }
    }

    // Enregistrer les modifications

    public void saveUpdateUtilisateur(Utilisateur utilisateur) throws InvalidArgumentException {

        List<String> validationMessages = new ArrayList<>();
        if (utilisateur == null) {
            validationMessages.add("L'utilisateur ne peut ?re null ou vide");
        }
        if (validationMessages.size() > 0) {
            throw new InvalidArgumentException((String[]) validationMessages.toArray(new String[0]));
        }

        Session session = HibernateUtils.getSession();
        Transaction t = session.beginTransaction();
        session.update(utilisateur);
        t.commit();
        session.close();
    }

      // Lister les utilisateurs

    public   List <Utilisateur> listUtilisateurs( ){
        Session session = HibernateUtils.getSession();

        List<Utilisateur> utilisateurs = new ArrayList<>();
        Query query = session.createQuery("from Utilisateur");
        utilisateurs = (List<Utilisateur>)query.list();
        System.out.println("taille user : " + utilisateurs.size());
        session.close();
        return utilisateurs;
    }


    //Récuperer un utilisateur par son email

    public Utilisateur getUtilisateurByEmail(String email){
        Session session = HibernateUtils.getSession();
        Utilisateur utilisateur=(Utilisateur) session.get(Utilisateur.class, email);
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