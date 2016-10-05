package controllers;

import models.Utilisateur;
import play.data.validation.Required;
import services.UtilisateurService;
import play.Logger;
import play.mvc.Controller;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurController extends Controller{


    public static void userList() {
        List<Utilisateur> utilisateurs;

//        Utilisateur utilisateur1;
//        Utilisateur utilisateur2;
//        Utilisateur utilisateur3;
//
//        utilisateur1 = new Utilisateur();
//        utilisateur1.nom = "Bomber";
//        utilisateur1.prenom = "jean";
//        utilisateur1.email = "jean@bomber.com";
//        utilisateur1.motDePasse = "yolo1";
//
//        utilisateur2 = new Utilisateur();
//        utilisateur2.nom = "tom";
//        utilisateur2.prenom = "mot";
//        utilisateur2.email = "mot@tom.com";
//        utilisateur2.motDePasse = "yolo2";
//
//        utilisateur3 = new Utilisateur();
//        utilisateur3.nom = "rebmob";
//        utilisateur3.prenom = "naej";
//        utilisateur3.email = "verlant@yolo.com";
//        utilisateur3.motDePasse = "yolo3";
//
//        utilisateurs = new ArrayList<>();
//        utilisateurs.add(utilisateur1);
//        utilisateurs.add(utilisateur2);
//        utilisateurs.add(utilisateur3);
        //TODO utiliser le service lister utilisateur pour cette fonction
        utilisateurs = UtilisateurService.get().listUtilisateurs();

        render(utilisateurs);
    }

    public static void user(String email) {
//        List<Utilisateur> utilisateurs;
//
//        Utilisateur utilisateur1;
//        Utilisateur utilisateur2;
//        Utilisateur utilisateur3;
//
//        utilisateur1 = new Utilisateur();
//        utilisateur1.nom = "Bomber";
//        utilisateur1.prenom = "jean";
//        utilisateur1.email = "jean@bomber.com";
//        utilisateur1.motDePasse = "yolo1";
//
//        utilisateur2 = new Utilisateur();
//        utilisateur2.nom = "tom";
//        utilisateur2.prenom = "mot";
//        utilisateur2.email = "mot@tom.com";
//        utilisateur2.motDePasse = "yolo2";
//
//        utilisateur3 = new Utilisateur();
//        utilisateur3.nom = "rebmob";
//        utilisateur3.prenom = "naej";
//        utilisateur3.email = "verlant@yolo.com";
//        utilisateur3.motDePasse = "yolo3";
//
//        utilisateurs = new ArrayList<>();
//        utilisateurs.add(utilisateur1);
//        utilisateurs.add(utilisateur2);
//        utilisateurs.add(utilisateur3);
//
//        for (Utilisateur user :
//                utilisateurs) {
//            if (user.email.contains(email)){
//                Logger.debug("user.nom : " + user.nom);
//                Logger.debug("user.prenom : " + user.prenom);
//                Logger.debug("user.email : " + user.email);
//                Logger.debug("user.motDePasse : " + user.motDePasse);
//                render(user);
//            }
//        }
        Utilisateur user = UtilisateurService.get().getUtilisateurByEmail(email);
        render(user);
    }

    public static void newUser() {
        render();
    }

    public static void saveUser(String nom,
                                String prenom,
                                @Required String email,
                                @Required String motDePasse) {
        Utilisateur utilisateur = new Utilisateur();
        try {
            if (nom != null && nom != ""){
                utilisateur.nom = nom;
            }
            if (prenom != null && prenom != "") {
                utilisateur.prenom = prenom;
            }
            utilisateur = UtilisateurService.get().create(email, motDePasse);
        } catch (Exception e) {
            e.printStackTrace();
        }

        UtilisateurController.user(utilisateur.email);
    }

}
