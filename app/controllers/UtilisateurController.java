package controllers;

import controllers.secure.Check;
import controllers.secure.Secure;
import models.Utilisateur;
import play.data.validation.Required;
import play.mvc.Controller;
import play.mvc.With;
import services.UtilisateurService;

import java.util.ArrayList;
import java.util.List;

@With(Secure.class)
@Check({"ADMIN", "USER"})
public class UtilisateurController extends Controller{


    public static void findUsers() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        utilisateurs = UtilisateurService.get().listUtilisateurs();
        render(utilisateurs);
    }

    public static void getUser(String email) {
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

        UtilisateurController.getUser(utilisateur.email);
    }

}
