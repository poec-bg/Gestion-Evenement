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
        render(UtilisateurService.get().listUtilisateurs());
    }

    public static void user(String email) {
        render(UtilisateurService.get().getUtilisateurByEmail(email));
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
