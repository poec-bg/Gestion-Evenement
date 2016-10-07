package controllers;

import com.google.common.base.Strings;
import controllers.secure.Check;
import controllers.secure.Secure;
import models.Evenement;
import models.Invite;
import models.Utilisateur;
import models.types.Categorie;
import org.joda.time.DateTime;
import play.Logger;
import play.data.validation.Required;
import play.mvc.Controller;
import play.mvc.With;
import services.EvenementService;
import services.UtilisateurService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@With(Secure.class)
@Check({"ADMIN", "USER"})
public class EvenementController extends Controller{
    private static final String TAG = "EvenementController: ";

    public static void findEvents(){
        Logger.debug(TAG + "findEvents: []");
        Date dateDebut = new Date();
        Date dateFin = new Date();
        DateTime dtFin = new DateTime(dateDebut);
        dtFin = dtFin.plusMonths(1);
        dateFin = dtFin.toDate();

        List<Evenement> evenementList;
        evenementList = EvenementService.get().listEvent(dateDebut, dateFin);

        render(evenementList, dateDebut, dateFin);
    }

    public static void findEventsByCategorie(Date dateDebut, Date dateFin, String optionsRadios){
        Logger.debug(TAG + "findEventsByCategorie: [%s %s %s]", dateDebut, dateFin, optionsRadios);
        List<Evenement> evenementList;

        if(Strings.isNullOrEmpty(optionsRadios) || optionsRadios.contains("ALL")) {
            evenementList = EvenementService.get().listEvent(dateDebut, dateFin);
        } else {
            evenementList = EvenementService.get().listEvent(dateDebut, dateFin, Categorie.valueOf(optionsRadios));
        }
        renderTemplate("EvenementController/findEvents.html", evenementList, dateDebut, dateFin, optionsRadios);
    }

    public static void getEvent(Long idEvenement){
        Logger.debug(TAG + "getEvent: [%s]", idEvenement);
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
        Logger.debug(TAG + "newEvent: []");
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
        Logger.debug(TAG + "saveEvent: [%s %s %s %s %s %s %s %s]", nom, description, lieu, dateDebutString, heureDebut, dateFinString, heureFin, (couleur!=null?couleur.getLabel() : "null"));
        if (validation.hasErrors()) {
            params.flash(); // add http parameters to the flash scope
            validation.keep(); // keep the errors for the next request
            newEvent();
        }

        try {
            // String dateDebut = yyyy-MM-dd
            // String heureDebut = HH:mm
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            if (Strings.isNullOrEmpty(heureDebut)){heureDebut = "00:00";}
            Date dateDebut = sdf.parse("" + dateDebutString + " " + heureDebut + "");

            if (Strings.isNullOrEmpty(heureFin)){heureFin = "23:59";}
            Date dateFin = sdf.parse("" + dateFinString + " " + heureFin + "");

            Utilisateur utilisateur = controllers.secure.Security.connectedUser();
            Evenement event = new Evenement();
            event.nom = nom;
            event.dateDebut = dateDebut;
            event.dateFin = dateFin;
            event.createur = utilisateur;

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

            event = EvenementService.get().addEvent(event);
            EvenementController.getEvent(event.idEvenement);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void updateEvent(long idEvenement) {
        Logger.debug(TAG + "updateEvent: [%s]", idEvenement);
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
            Categorie couleur,
            @Required long idEvenement) {
        Logger.debug(TAG + "saveModificatedEvent: [%s %s %s %s %s %s %s %s %s]", nom, description, lieu, dateDebutString, heureDebut, dateFinString, heureFin, (couleur!=null?couleur.getLabel() : "null"), idEvenement);
        try {

            //formatage des dates(String) et heures(String) en date*(Date)
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            if (Strings.isNullOrEmpty(heureDebut)){heureDebut = "00:00";}
            Date dateDebut = sdf.parse("" + dateDebutString + " " + heureDebut + "");

            if (Strings.isNullOrEmpty(heureFin)){heureFin = "23:59";}
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

}
