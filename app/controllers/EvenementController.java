package controllers;

import models.Evenement;
import models.Invite;
import models.Utilisateur;
import play.Logger;
import play.data.binding.types.DateTimeBinder;
import play.data.validation.Required;
import play.mvc.Controller;
import services.EvenementService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EvenementController extends Controller{

    public static void evenementList(){

        Utilisateur user;
        user = new Utilisateur();
        user.nom = "Bomber";
        user.prenom = "jean";
        user.email = "jean@bomber.com";
        user.motDePasse = "yolo1";

        Evenement event1;
        Evenement event2;
        Evenement event3;
        Evenement event4;

        event1 = new Evenement();
        event1.idEvenement = 1;
        event1.createur = user;
        event1.dateDebut = new Date(2016, 10, 4, 12, 30);
        event1.dateFin = new Date(21016, 10, 4, 14, 0);
        event1.nom = "RDV 1";
        event1.description = "c'est super important !";
        event1.lieu = "McDo";

        event2 = new Evenement();
        event1.idEvenement = 2;
        event2.createur = user;
        event2.dateDebut = new Date(2016, 10, 5, 9, 0);
        event2.dateFin = new Date(21016, 10, 5, 18, 0);
        event2.nom = "super RDV";
        event2.description = "c'est pas important";
        event2.lieu = "m2i";

        event3 = new Evenement();
        event1.idEvenement = 3;
        event3.createur = user;
        event3.dateDebut = new Date(2016, 10, 6, 17, 0);
        event3.dateFin = new Date(21016, 10, 6, 18, 0);
        event3.nom = "un évenement tou ce qu'il y a de plus normal";
        event3.description = "ceci est une description d'évenement";
        event3.lieu = "osef";

        event4 = new Evenement();
        event1.idEvenement = 4;
        event4.createur = user;
        event4.dateDebut = new Date(2016, 10, 5, 10, 0);
        event4.dateFin = new Date(21016, 10, 5, 11, 30);
        event4.nom = "RDV 4";
        event4.description = "wahou quelle prouesse technologique";
        event4.lieu = "là bas";
//        SimpleDateFormat sformat = new SimpleDateFormat("dd/mm/yyyy - HH:mm");
//        sformat.format(event1.dateDebut);
//        event1.dateFin.format();

        List<Evenement> evenementList;
        evenementList = new ArrayList<>();
        evenementList.add(event1);
        evenementList.add(event2);
        evenementList.add(event3);
        evenementList.add(event4);

        render(evenementList);
    }

    public static void evenement(Long idEvenement){
        Utilisateur user;
        user = new Utilisateur();
        user.nom = "Bomber";
        user.prenom = "jean";
        user.email = "jean@bomber.com";
        user.motDePasse = "yolo1";

        Evenement event1;
        Evenement event2;
        Evenement event3;
        Evenement event4;

        event1 = new Evenement();
        event1.idEvenement = 1;
        event1.createur = user;
        event1.dateDebut = new Date(2016, 10, 4, 12, 30);
        event1.dateFin = new Date(21016, 10, 4, 14, 0);
        event1.nom = "RDV 1";
        event1.description = "c'est super important !";
        event1.lieu = "McDo";

        event2 = new Evenement();
        event1.idEvenement = 2;
        event2.createur = user;
        event2.dateDebut = new Date(2016, 10, 5, 9, 0);
        event2.dateFin = new Date(21016, 10, 5, 18, 0);
        event2.nom = "super RDV";
        event2.description = "c'est pas important";
        event2.lieu = "m2i";

        event3 = new Evenement();
        event1.idEvenement = 3;
        event3.createur = user;
        event3.dateDebut = new Date(2016, 10, 6, 17, 0);
        event3.dateFin = new Date(21016, 10, 6, 18, 0);
        event3.nom = "un évenement tou ce qu'il y a de plus normal";
        event3.description = "ceci est une description d'évenement";
        event3.lieu = "osef";

        event4 = new Evenement();
        event1.idEvenement = 4;
        event4.createur = user;
        event4.dateDebut = new Date(2016, 10, 5, 10, 0);
        event4.dateFin = new Date(21016, 10, 5, 11, 30);
        event4.nom = "RDV 4";
        event4.description = "wahou quelle prouesse technologique";
        event4.lieu = "là bas";

        List<Evenement> evenementList;
        evenementList = new ArrayList<>();
        evenementList.add(event1);
        evenementList.add(event2);
        evenementList.add(event3);
        evenementList.add(event4);

        Invite invite1 = new Invite();
        invite1.email = user.email;
        invite1.evenement = event1;

        Invite invite2 = new Invite();
        invite2.email = user.email;
        invite2.evenement = event1;

        Invite invite3 = new Invite();
        invite3.email = user.email;
        invite3.evenement = event1;

        List<Invite> invites = new ArrayList<>();
        invites.add(invite1);
        invites.add(invite2);
        invites.add(invite3);
        event1.invites = invites;

        for (Evenement event : evenementList) {
            if (event.idEvenement == idEvenement){
                Logger.debug("evenement id : " + event.idEvenement);
                Logger.debug("evenement : " + event.nom);
                Logger.debug("date de début : " + event.dateDebut);
                render(event);
            }
        }
    }

    public static void newEvent(){
        render();
    }

    public static void saveEvent(@Required String nom,
                                 String description,
                                 String lieu,
                                 @Required Date dateDebut,
                                 String heureDebut/*,
                                 @Required Date dateFin,
                                 String heureFin,
                                 @Required String idCreateur*/){
        if (validation.hasErrors()) {
            params.flash(); // add http parameters to the flash scope
            validation.keep(); // keep the errors for the next request
            newEvent();
        }
        try {
            System.out.println("date debut : " + dateDebut);
            System.out.println("heure debut : " + heureDebut);
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(dateDebut));
            System.out.println("parse de date debut : " + date);
//            EvenementService.get().addEvent(dateDebut, dateFin, nom, "1");
        } catch (Exception e) {
            e.printStackTrace();
        }


        EvenementController.evenementList();
    }

}
