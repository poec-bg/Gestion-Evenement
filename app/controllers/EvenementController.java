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

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EvenementController extends Controller{

    public static void eventList(){
        String date1 = "2016-10-04 01:01";
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
        Evenement event = EvenementService.get().getEvent(idEvenement);

        //TODO enlever les invites a la mano quand le model sera fonctionnel
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

}
