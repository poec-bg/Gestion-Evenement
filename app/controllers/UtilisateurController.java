package controllers;

import models.Utilisateur;
import play.Logger;
import play.mvc.Controller;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurController extends Controller{


    public static void utilisateurList() {
        List<Utilisateur> utilisateurs;
        Utilisateur utilisateur;

        Utilisateur utilisateur1;
        Utilisateur utilisateur2;
        Utilisateur utilisateur3;

        utilisateur1 = new Utilisateur();
        utilisateur1.nom = "Bomber";
        utilisateur1.prenom = "jean";
        utilisateur1.email = "jean@bomber.com";
        utilisateur1.motDePasse = "yolo1";

        utilisateur2 = new Utilisateur();
        utilisateur2.nom = "tom";
        utilisateur2.prenom = "mot";
        utilisateur2.email = "mot@tom.com";
        utilisateur2.motDePasse = "yolo2";

        utilisateur3 = new Utilisateur();
        utilisateur3.nom = "rebmob";
        utilisateur3.prenom = "naej";
        utilisateur3.email = "verlant@yolo.com";
        utilisateur3.motDePasse = "yolo3";

        utilisateurs = new ArrayList<>();
        utilisateurs.add(utilisateur1);
        utilisateurs.add(utilisateur2);
        utilisateurs.add(utilisateur3);
        //TODO utiliser le service lister utilisateur pour cette fonction

        render(utilisateurs);
    }

    public static void utilisateur(String email) {
        List<Utilisateur> utilisateurs;

        Utilisateur utilisateur1;
        Utilisateur utilisateur2;
        Utilisateur utilisateur3;

        utilisateur1 = new Utilisateur();
        utilisateur1.nom = "Bomber";
        utilisateur1.prenom = "jean";
        utilisateur1.email = "jean@bomber.com";
        utilisateur1.motDePasse = "yolo1";

        utilisateur2 = new Utilisateur();
        utilisateur2.nom = "tom";
        utilisateur2.prenom = "mot";
        utilisateur2.email = "mot@tom.com";
        utilisateur2.motDePasse = "yolo2";

        utilisateur3 = new Utilisateur();
        utilisateur3.nom = "rebmob";
        utilisateur3.prenom = "naej";
        utilisateur3.email = "verlant@yolo.com";
        utilisateur3.motDePasse = "yolo3";

        utilisateurs = new ArrayList<>();
        utilisateurs.add(utilisateur1);
        utilisateurs.add(utilisateur2);
        utilisateurs.add(utilisateur3);

        for (Utilisateur user :
                utilisateurs) {
            if (user.email.contains(email)){
                System.out.println("user.nom : " + user.nom);
                System.out.println("user.prenom : " + user.prenom);
                System.out.println("user.email : " + user.email);
                System.out.println("user.motDePasse : " + user.motDePasse);
                render(user);
            }
        }
//        utilisateurs.stream().filter(utilisateur -> utilisateur.email == email).forEach(Controller::render);
    }

    public static Utilisateur utilisateurCreation() {
        //TODO implementer le formulaire de création d'un utilisateur
        render();
        return null;
    }

    public static Utilisateur utilisateurModification(String email) {
        //TODO implementer le formulaire de modification d'un utilisateur
        render();
        return null;
    }

}
