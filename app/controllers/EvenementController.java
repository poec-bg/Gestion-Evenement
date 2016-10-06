package controllers;

import models.Evenement;
import models.Invite;
import models.Utilisateur;
import org.joda.time.DateTime;
import play.Logger;
import play.data.validation.Required;
import play.mvc.Controller;
import services.EvenementService;
import services.UtilisateurService;
import sun.util.calendar.CalendarDate;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EvenementController extends Controller{

    public static void eventList(){
        Date dateDebut = new Date();
        Date dateFin = new Date();
        DateTime dtFin = new DateTime(dateDebut);
        dtFin = dtFin.plusMonths(1);
        dateFin = dtFin.toDate();

        List<Evenement> evenementList;
        evenementList = EvenementService.get().listEvent(dateDebut, dateFin);

        render(evenementList, dateDebut, dateFin);
    }

    public static void eventListDate(Date dateDebut, Date dateFin){

//        Date dateDebut = null;
//        Date dateFin = null;
//        try {
//            dateDebut = new SimpleDateFormat("yyyy-MM-dd").parse(dateDebutString);
//            dateFin = new SimpleDateFormat("yyyy-MM-dd").parse(dateFinString);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        List<Evenement> evenementList;

        evenementList = EvenementService.get().listEvent(dateDebut, dateFin);
        renderTemplate("EvenementController/eventList.html", evenementList, dateDebut, dateFin);
    }

    public static void event(Long idEvenement){
        Evenement event = EvenementService.get().getEvent(idEvenement);

        //TODO enlever les invites fait a la mano quand le model sera fonctionnel
        Invite guest1 = new Invite();
        guest1.email = "toto@email.com";
        guest1.evenement = event;

        Invite guest2 = new Invite();
        guest2.email = "tata@email.com";
        guest2.evenement = event;

        Invite guest3 = new Invite();
        guest3.email = "titi@email.com";
        guest3.evenement = event;

        List<Invite> guestList = new ArrayList<>();
        guestList.add(guest1);
        guestList.add(guest2);
        guestList.add(guest3);

        event.invites = guestList;
        render(event);
    }

    public static void newEvent(){
        render();
    }

    public static void saveEvent(@Required String nom,
                                 String description,
                                 String lieu,
                                 @Required String dateDebutString,
                                 String heureDebut,
                                 @Required String dateFinString,
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

            if (heureDebut == null || heureDebut.length() == 0){
                heureDebut = "00:00";
                System.out.println("heure debut mod : " + heureDebut);
            }
            System.out.println("date{"+dateDebutString+"} heure{"+heureDebut+"}");
            Date dateDebut = sdf.parse("" + dateDebutString + " " + heureDebut + "");

            if (heureFin == null || heureFin.length() == 0){
                heureFin = "23:59";
                System.out.println("heure fin mod : " + heureFin);
            }
            Date dateFin = sdf.parse("" + dateFinString + " " + heureFin + "");

            Utilisateur utilisateur = new Utilisateur();
            utilisateur.email = "test@email.com";
            utilisateur.motDePasse = "test";
            EvenementService.get().addEvent(dateDebut, dateFin, nom, utilisateur);
        } catch (Exception e) {
            e.printStackTrace();
        }


        EvenementController.eventList();
    }


    public static void updateEvent(long idEvenement) {
        Evenement event = EvenementService.get().getEvent(idEvenement);
        render(event);
    }

    public static void saveModificatedEvent(
            @Required String nom,
            String description,
            String lieu,
            @Required String dateDebutString,
            String heureDebut,
            @Required String dateFinString,
            String heureFin,
            @Required long idEvenement,
            @Required String emailCreateur) {
        try {

            //formatage des dates(String) et heures(String) en (Date)
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            if (heureDebut == null || heureDebut.length() == 0){
                heureDebut = "00:00";
                System.out.println("heure debut mod : " + heureDebut);
            }
            System.out.println("date{"+dateDebutString+"} heure{"+heureDebut+"}");
            Date dateDebut = sdf.parse("" + dateDebutString + " " + heureDebut + "");

            if (heureFin == null || heureFin.length() == 0){
                heureFin = "23:59";
                System.out.println("heure fin mod : " + heureFin);
            }
            Date dateFin = sdf.parse("" + dateFinString + " " + heureFin + "");

            //recuperation du user depuis son id(email)
            Utilisateur user = UtilisateurService.get().getUtilisateurByEmail(emailCreateur);

            //initialisation de l'event pout eneregistrement
            Evenement event = new Evenement();
            event.idEvenement = idEvenement;
            event.nom = nom;
            event.description = description;
            event.lieu = lieu;
            event.dateDebut = dateDebut;
            event.dateFin = dateFin;
            event.createur = user;


            EvenementService.get().updateEvent(event);
            EvenementController.event(event.idEvenement);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
