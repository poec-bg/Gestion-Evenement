package controllers;

import models.Utilisateur;
import play.data.validation.Email;
import play.data.validation.Required;
import play.data.validation.Valid;
import play.mvc.Controller;
import services.UtilisateurService;

public class Application extends Controller {

    public static void index() {
        flash.success("bien connecté");
        render();
    }

    public static void contact() {
        render();
    }

    public static void apropos() {
        render();
    }

    public static void newUser() {
        render();
    }

    public static void saveUser(String nom,
                                String prenom,
                                @Required@Valid@Email String email,
                                @Required String motDePasse) {
        Utilisateur utilisateur = new Utilisateur();
        try {
            if (nom != null && nom != ""){
                utilisateur.nom = nom;
            }
            if (prenom != null && prenom != "") {
                utilisateur.prenom = prenom;
            }
            utilisateur = UtilisateurService.get().create(email, motDePasse, nom, prenom);
        } catch (Exception e) {
            e.printStackTrace();
        }

        UtilisateurController.getUser(utilisateur.email);
    }
}