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
        List<Utilisateur> utilisateurs = new ArrayList<>();
        utilisateurs = UtilisateurService.get().listUtilisateurs();
        render(utilisateurs);
    }

    public static void user(String email) {
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
            utilisateur = UtilisateurService.get().create(email, motDePasse, nom, prenom);
        } catch (Exception e) {
            e.printStackTrace();
        }

        UtilisateurController.user(utilisateur.email);
    }

}
