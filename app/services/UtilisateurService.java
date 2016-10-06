package services;


import exceptions.InvalidArgumentException;
import models.Utilisateur;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import validators.EmailValidator;
import java.util.ArrayList;
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
        utilisateur.isSupprime = false;


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


    public Utilisateur create(String email, String motDePasse, String nom, String prenom) throws Exception {


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
        utilisateur.isSupprime = false;

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
        Session session = HibernateUtils.getSession();
        Transaction t = session.beginTransaction();
        session.update(utilisateur);
        t.commit();
        session.close();
    }

   // Authentification

    public boolean authenticate(String email, String motDePasse) throws Exception {

        List<String> validationMessages = new ArrayList<>();
        if (email== null || email== "") {
            validationMessages.add("L'email ne peut être null ou vide");
        }
        if (motDePasse== null || motDePasse== "")  {
            validationMessages.add("Le motDePasse ne peut être null ou vide");
        }
        if (validationMessages.size() > 0) {
            throw new InvalidArgumentException((String[]) validationMessages.toArray(new String[0]));
        }
        Session session = HibernateUtils.getSession();
        Query query = session.createQuery("from Utilisateur where email =:email");
        query.setString("email", email);
        Utilisateur utilisateur = (Utilisateur) query.uniqueResult();
        if (utilisateur == null) {
            return false;
        }
        session.close();
        return false;
    }


      // Lister les utilisateurs

    public   List <Utilisateur> listUtilisateurs( ){
        Session session = HibernateUtils.getSession();

        List<Utilisateur> utilisateurs = new ArrayList<>();
        Boolean isSupprime =false;
        Query query = session.createQuery("from Utilisateur where isSupprime =:isSupprime");
        query.setBoolean("isSupprime", isSupprime);
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

    //Récuperer un utilisateur par nom
    public Utilisateur getUtilisateurByName(String nom) {
        Session session= HibernateUtils.getSession();
        Utilisateur utilisateur=(Utilisateur) session.get(Utilisateur.class, nom);
        return utilisateur;
    }

    public void deleteUtilisateur(Utilisateur utilisateur) throws Exception {
        if (utilisateur == null) {
            throw new Exception( "L'utilisateur ne peut ?re null" );
        }
        utilisateur.isSupprime=true;
        Session session = HibernateUtils.getSession();
        Transaction t = session.beginTransaction();
        session.update(utilisateur);
        t.commit();
        session.close();
    }

//    public void  deleteUtilisateur(Utilisateur utilisateur) throws Exception {
//        Session session = HibernateUtils.getSession();
//        Transaction tx = null;
//        utilisateur.isSupprime = true;
//        try{
//            tx = session.beginTransaction();
//            session.update(utilisateur);
//            tx.commit();
//        }catch (HibernateException e) {
//            if (tx!=null) tx.rollback();
//            throw new Exception("HibernateException: " + e.getMessage() );
//        }finally {
//            session.close();
//        }
//    }


}