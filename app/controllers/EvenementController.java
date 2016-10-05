package controllers;

import models.Evenement;
import models.Invite;
import models.Utilisateur;
import org.joda.time.DateTime;
import play.Logger;
import play.data.validation.Required;
import play.mvc.Controller;
import services.EvenementService;
import sun.util.calendar.CalendarDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EvenementController extends Controller{

    public static void eventList(){

//        Utilisateur user;
//        user = new Utilisateur();
//        user.nom = "Bomber";
//        user.prenom = "jean";
//        user.email = "jean@bomber.com";
//        user.motDePasse = "yolo1";
//
//        Evenement event1;
//        Evenement event2;
//        Evenement event3;
//        Evenement event4;
//
//        event1 = new Evenement();
//        event1.idEvenement = 1;
//        event1.createur = user;
//        event1.dateDebut = new Date(2016, 10, 4, 12, 30);
//        event1.dateFin = new Date(21016, 10, 4, 14, 0);
//        event1.nom = "RDV 1";
//        event1.description = "c'est super important !";
//        event1.lieu = "McDo";
//
//        event2 = new Evenement();
//        event1.idEvenement = 2;
//        event2.createur = user;
//        event2.dateDebut = new Date(2016, 10, 5, 9, 0);
//        event2.dateFin = new Date(21016, 10, 5, 18, 0);
//        event2.nom = "super RDV";
//        event2.description = "c'est pas important";
//        event2.lieu = "m2i";
//
//        event3 = new Evenement();
//        event1.idEvenement = 3;
//        event3.createur = user;
//        event3.dateDebut = new Date(2016, 10, 6, 17, 0);
//        event3.dateFin = new Date(21016, 10, 6, 18, 0);
//        event3.nom = "un évenement tou ce qu'il y a de plus normal";
//        event3.description = "ceci est une description d'évenement";
//        event3.lieu = "osef";
//
//        event4 = new Evenement();
//        event1.idEvenement = 4;
//        event4.createur = user;
//        event4.dateDebut = new Date(2016, 10, 5, 10, 0);
//        event4.dateFin = new Date(21016, 10, 5, 11, 30);
//        event4.nom = "RDV 4";
//        event4.description = "wahou quelle prouesse technologique";
//        event4.lieu = "là bas";

//        SimpleDateFormat sformat = new SimpleDateFormat("dd/mm/yyyy - HH:mm");
//        sformat.format(event1.dateDebut);
//        event1.dateFin.format();

//        List<Evenement> evenementList;
//        evenementList = new ArrayList<>();
//        evenementList.add(event1);
//        evenementList.add(event2);
//        evenementList.add(event3);
//        evenementList.add(event4);

        String date1 = "2016-10-05 01:01";
        String date2 = "2016-12-31 23:59";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        List<Evenement> evenementList;

        try {
            Date dateDebut = sdf.parse(date1);
            Date dateFin = sdf.parse(date2);
            evenementList = EvenementService.get().listEvent(dateDebut, dateFin);
            render(evenementList);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void event(Long idEvenement){
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
                Logger.debug("event id : " + event.idEvenement);
                Logger.debug("event : " + event.nom);
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
                                 @Required String dateDebutString,
                                 String heureDebut,
                                 @Required Date dateFinString,
                                 String heureFin){
        if (validation.hasErrors()) {
            params.flash(); // add http parameters to the flash scope
            validation.keep(); // keep the errors for the next request
            newEvent();
        }

        try {
            // String dateDebut = yyyy-MM-dd
            // String heureDebut = HH:mm
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            if (heureDebut == null || heureDebut == ""){
                heureDebut = "00:00";
            }
            Date dateDebut = sdf.parse(dateDebutString + " " + heureDebut);

            if (heureFin == null || heureFin == ""){
                heureFin = "00:00";
            }
            Date dateFin = sdf.parse(dateFinString + " " + heureFin);

            Utilisateur utilisateur = new Utilisateur();
            utilisateur.email = "test@email.com";
            utilisateur.motDePasse = "test";
            EvenementService.get().addEvent(dateDebut, dateFin, nom, utilisateur);
        } catch (Exception e) {
            e.printStackTrace();
        }


        EvenementController.eventList();
    }

}
