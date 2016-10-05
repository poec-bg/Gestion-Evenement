import models.Evenement;
import models.Utilisateur;
import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;
import services.EvenementService;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class EvenementTest {
    private static  Evenement evenementTest1, evenementTest2;
    private static  Utilisateur user1;

    @BeforeClass
    public static void initTest(){
        user1 = new Utilisateur();
        user1.email = "test@test.org";
        user1.nom = "TEST";
        user1.prenom = "Mon Super";

        evenementTest1 = new Evenement();
        evenementTest1.nom = "TEST 1";
        evenementTest1.createur = user1;
        evenementTest1.dateDebut = new Date();
        evenementTest1.dateFin = new DateTime().plusDays(2).toDate();

        evenementTest2 = new Evenement();
        evenementTest2.nom = "TEST 1";
        evenementTest2.createur = user1;
        evenementTest2.dateDebut = new DateTime().plusHours(26).toDate();
        evenementTest2.dateFin = new DateTime().plusHours(28).toDate();
    }

//Tests sur les fonctions : addEvent
    @Test
    public void addEvent_OK(){
        try {
            services.EvenementService.get().addEvent(evenementTest1);
        } catch (Exception e) {
            fail();
        }
        assertTrue(true);
    }
    @Test
    public void addEvent_DebutApresFin(){
        Evenement evenementTest = new Evenement();
        evenementTest.nom = "TEST 1";
        evenementTest.createur = user1;
        evenementTest.dateDebut = new DateTime().toDate();
        evenementTest.dateFin = new DateTime().minusHours(8).toDate();
        try {
            services.EvenementService.get().addEvent(evenementTest);
        } catch (Exception e) {
            assertTrue(true);
        }
        fail();
    }
    @Test
    public void addEvent_nomNull(){
        Evenement evenementTest = new Evenement();
        evenementTest.nom = null;
        evenementTest.createur = user1;
        evenementTest.dateDebut = new DateTime().toDate();
        evenementTest.dateFin = new DateTime().minusHours(8).toDate();
        try {
            services.EvenementService.get().addEvent(evenementTest);
        } catch (Exception e) {
            assertTrue(true);
        }
        fail();
    }
    @Test
    public void addEvent_nomVide(){
        Evenement evenementTest = new Evenement();
        evenementTest.nom = "";
        evenementTest.createur = user1;
        evenementTest.dateDebut = new DateTime().toDate();
        evenementTest.dateFin = new DateTime().minusHours(8).toDate();
        try {
            services.EvenementService.get().addEvent(evenementTest);
        } catch (Exception e) {
            assertTrue(true);
        }
        fail();
    }
    @Test
    public void addEvent_idCreateurNull(){
        Evenement evenementTest = new Evenement();
        evenementTest.nom = "TEST_addEvent";
        evenementTest.createur = user1;
        evenementTest.dateDebut = new DateTime().toDate();
        evenementTest.dateFin = new DateTime().minusHours(8).toDate();
        try {
            services.EvenementService.get().addEvent(evenementTest);
        } catch (Exception e) {
            assertTrue(true);
        }
        fail();
    }
    @Test
    public void addEvent_idCreateurVide(){
        Evenement evenementTest = new Evenement();
        evenementTest.nom = "TEST_addEvent";
        evenementTest.createur = user1;
        evenementTest.dateDebut = new DateTime().toDate();
        evenementTest.dateFin = new DateTime().minusHours(8).toDate();
        try {
            services.EvenementService.get().addEvent(evenementTest);
        } catch (Exception e) {
            assertTrue(true);
        }
        fail();
    }
//Tests sur la fonctions :listEvent
    @Test
    public void listEvent_ok(){
        //ajout d'un élément dans la bdd pour qu'elle soit non vide.
        try {
            services.EvenementService.get().addEvent(evenementTest1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DateTime debut = new DateTime().minusDays(1);
        DateTime fin = new DateTime().plusDays(3);
        List<Evenement> resultats = services.EvenementService.get().listEvent(debut.toDate(), fin.toDate());
        assertTrue(resultats.size() > 0);
    }
    @Test
    public void listEvent_erreurDates(){
        //ajout d'un élément dans la bdd pour qu'elle soit non vide.
        try {
            services.EvenementService.get().addEvent(evenementTest1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DateTime debut = new DateTime().plusDays(3);
        DateTime fin = new DateTime().minusDays(1);
        List<Evenement> resultats = services.EvenementService.get().listEvent(debut.toDate(), fin.toDate());
        assertTrue(resultats.size() == 0);
    }
//Tests sur la fonctions : getEvent
    @Test
    public void getEvent_ok(){
        Evenement result = services.EvenementService.get().getEvent(1);
        assertNotNull(result);
    }
    @Test
    public void getEvent_ok2(){
        Evenement recherche, resultat;
        try {
            recherche = services.EvenementService.get().addEvent(evenementTest1);
        } catch (Exception e) {
            //l'initialisation a échoué, test arrêté
            e.printStackTrace();
            return;
        }
        resultat = services.EvenementService.get().getEvent(recherche.idEvenement);
        if(resultat == null){
            fail();
        }
        assertEquals(recherche.idEvenement, resultat.idEvenement);
    }
    @Test
    public void getEvent_Invalide(){
        Evenement result = services.EvenementService.get().getEvent(-99);
        assertNull(result);
    }
//Tests sur la fonctions : updateEvent
    @Test
    public void updateEvent_ok(){
        Evenement eventTest;
        try {
            eventTest = services.EvenementService.get().addEvent(evenementTest1);
        } catch (Exception e) {
            //l'initialisation a échoué, test arrêté
            e.printStackTrace();
            return;
        }
        eventTest.dateFin = evenementTest2.dateFin;
        eventTest.dateDebut = evenementTest2.dateDebut;
        eventTest.nom = evenementTest2.nom;
        try {
            services.EvenementService.get().updateEvent(eventTest);
        } catch (Exception e) {
            fail();
        }
        Evenement resultat = services.EvenementService.get().getEvent(eventTest.idEvenement);
        if(resultat == null){
            fail();
        }else{
            assertTrue(eventTest.idEvenement == resultat.idEvenement
                    & eventTest.dateDebut == resultat.dateDebut
                    & eventTest.dateFin == resultat.dateFin
                    & eventTest.nom == resultat.nom );
        }
    }
    @Test
    public void updateEvent_erreurDates(){
        Evenement eventTest;
        try {
            eventTest = services.EvenementService.get().addEvent(evenementTest1);
        } catch (Exception e) {
            //l'initialisation a échoué, test arrêté
            e.printStackTrace();
            return;
        }
        eventTest.dateFin = evenementTest2.dateDebut;
        eventTest.dateDebut = evenementTest2.dateFin;
        eventTest.nom = evenementTest2.nom;
        try {
            services.EvenementService.get().updateEvent(eventTest);
        } catch (Exception e) {
            assertTrue(true);
        }
        fail();
    }
    @Test
    public void updateEvent_erreurNom(){
        Evenement eventTest;
        try {
            eventTest = services.EvenementService.get().addEvent(evenementTest1);
        } catch (Exception e) {
            //l'initialisation a échoué, test arrêté
            e.printStackTrace();
            return;
        }
        eventTest.dateFin = evenementTest2.dateFin;
        eventTest.dateDebut = evenementTest2.dateDebut;
        eventTest.nom = "";
        try {
            services.EvenementService.get().updateEvent(eventTest);
        } catch (Exception e) {
            assertTrue(true);
        }
        fail();
    }
//Tests sur la fonctions : deleteEvent
    @Test
    public void deleteEvent_ok(){
        Evenement eventTest;
        try {
            eventTest = services.EvenementService.get().addEvent(evenementTest1);
        } catch (Exception e) {
            //l'initialisation a échoué, test arrêté
            e.printStackTrace();
            return;
        }
        try {
            services.EvenementService.get().deleteEvent(eventTest);
        } catch (Exception e) {
            fail();
        }
        Evenement resultat = services.EvenementService.get().getEvent(eventTest.idEvenement);
        assertNull(resultat);
    }
    @Test
    public void deleteEvent_idInvalide(){
        Evenement eventTest;
        try {
            eventTest = services.EvenementService.get().addEvent(evenementTest1);
        } catch (Exception e) {
            //l'initialisation a échoué, test arrêté
            e.printStackTrace();
            return;
        }
        eventTest.idEvenement = -99;

        try {
            services.EvenementService.get().deleteEvent(eventTest);
        } catch (Exception e) {
            assertTrue(true);
        }
        fail();
    }
}
