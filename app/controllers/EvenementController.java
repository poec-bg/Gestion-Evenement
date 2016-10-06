package controllers;

import com.google.common.base.Strings;
import controllers.secure.Check;
import controllers.secure.Secure;
import models.Evenement;
import models.Invite;
import models.Utilisateur;
import models.types.Categorie;
import org.joda.time.DateTime;
import play.data.validation.Required;
import play.mvc.Controller;
import play.mvc.With;
import services.EvenementService;
import services.UtilisateurService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@With(Secure.class)
@Check({"ADMIN", "USER"})
public class EvenementController extends Controller{
    //TODO ajouter des logger a toutes les méthodes

    public static void findEvents(){
        Date dateDebut = new Date();
        Date dateFin = new Date();
        DateTime dtFin = new DateTime(dateDebut);
        dtFin = dtFin.plusMonths(1);
        dateFin = dtFin.toDate();

        List<Evenement> evenementList;
        evenementList = EvenementService.get().listEvent(dateDebut, dateFin);

        render(evenementList, dateDebut, dateFin);
    }

    public static void findEventsByDate(Date dateDebut, Date dateFin){
        List<Evenement> evenementList;

        evenementList = EvenementService.get().listEvent(dateDebut, dateFin);
        renderTemplate("EvenementController/findEvents.html", evenementList, dateDebut, dateFin);
    }

    public static void findEventsByCategorie(Date dateDebut, Date dateFin, Categorie categorie){
        List<Evenement> evenementList;

        evenementList = EvenementService.get().listEvent(dateDebut, dateFin, categorie);
        renderTemplate("EvenementController/findEvents.html", evenementList, dateDebut, dateFin, categorie);
    }

    public static void getEvent(Long idEvenement){
        Evenement event = EvenementService.get().getEvent(idEvenement);

        if(event != null){
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
        }
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
                                 String heureFin,
                                 Categorie couleur){
        if (validation.hasErrors()) {
            params.flash(); // add http parameters to the flash scope
            validation.keep(); // keep the errors for the next request
            newEvent();
        }

        try {
            // String dateDebut = yyyy-MM-dd
            // String heureDebut = HH:mm
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            if (Strings.isNullOrEmpty(heureDebut)){
                heureDebut = "00:00";
            }
            Date dateDebut = sdf.parse("" + dateDebutString + " " + heureDebut + "");

            if (Strings.isNullOrEmpty(heureFin)){
                heureFin = "23:59";
            }
            Date dateFin = sdf.parse("" + dateFinString + " " + heureFin + "");

            Utilisateur utilisateur = controllers.secure.Security.connectedUser();
            Evenement event = EvenementService.get().addEvent(dateDebut, dateFin, nom, utilisateur);

            if (Strings.isNullOrEmpty(event.description) || Strings.isNullOrEmpty(description)) {
                event.description = description;
            }
            if (Strings.isNullOrEmpty(event.lieu) || Strings.isNullOrEmpty(lieu)) {
                event.lieu = lieu;
            }
            if (event.categorie == null) {
                event.categorie = Categorie.GREEN;
            } else {
                //TODO changer la couleur en fonction du paramète choisi
                event.categorie = couleur;
            }
            EvenementService.get().updateEvent(event);
            EvenementController.getEvent(event.idEvenement);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
            @Required long idEvenement) {
        try {

            //formatage des dates(String) et heures(String) en date*(Date)
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            if (Strings.isNullOrEmpty(heureDebut)){
                heureDebut = "00:00";
            }
            Date dateDebut = sdf.parse("" + dateDebutString + " " + heureDebut + "");

            if (Strings.isNullOrEmpty(heureFin)){
                heureFin = "23:59";
            }
            Date dateFin = sdf.parse("" + dateFinString + " " + heureFin + "");

            //initialisation de l'event pout eneregistrement
            Evenement event = new Evenement();
            event.idEvenement = idEvenement;
            event.nom = nom;
            event.description = description;
            event.lieu = lieu;
            event.dateDebut = dateDebut;
            event.dateFin = dateFin;
            event.createur = controllers.secure.Security.connectedUser();

            EvenementService.get().updateEvent(event);
            EvenementController.getEvent(event.idEvenement);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
