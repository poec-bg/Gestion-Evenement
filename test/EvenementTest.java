import models.Evenement;
import models.Utilisateur;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import services.EvenementService;
import services.HibernateUtils;
import services.UtilisateurService;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class EvenementTest {
    private static  Evenement evenementTest1, evenementTest2;
    private static  Utilisateur user1;

    @BeforeClass
    public static void initTest(){
        //on se place dans la bdd test
        HibernateUtils.HibernateTest();

        user1 = new Utilisateur();
        user1.email = "testsEvenement@test.org";
        user1.motDePasse = "monMotDePasse";
        try {
            UtilisateurService.get().create(user1.email, user1.motDePasse);
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    @AfterClass
    public static void clearTests(){
//        UtilisateurService.get().remove(user1.email);
    }

//Tests sur les fonctions : addEvent
    @Test
    public void addEvent_OK(){
        Evenement resultat;
        try {
            resultat = EvenementService.get().addEvent(evenementTest1);
        } catch (Exception e) {
            fail();
            return;
        }
        assertTrue( resultat.nom.equals(evenementTest1.nom)
                & resultat.dateDebut == evenementTest1.dateDebut
                & resultat.dateFin == evenementTest1.dateFin );
    }
    @Test
    public void addEvent_DebutApresFin(){
        Evenement evenementTest = new Evenement();
        evenementTest.nom = "TEST 1";
        evenementTest.createur = user1;
        evenementTest.dateDebut = new DateTime().toDate();
        evenementTest.dateFin = new DateTime().minusHours(8).toDate();
        try {
            EvenementService.get().addEvent(evenementTest);
        } catch (Exception e) {
            assertTrue(1 == 1);
            return;
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
            EvenementService.get().addEvent(evenementTest);
        } catch (Exception e) {
            assertTrue(true);
            return;
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
            EvenementService.get().addEvent(evenementTest);
        } catch (Exception e) {
            assertTrue(true);
            return;
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
            EvenementService.get().addEvent(evenementTest);
        } catch (Exception e) {
            assertTrue(true);
            return;
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
            EvenementService.get().addEvent(evenementTest);
        } catch (Exception e) {
            assertTrue(true);
            return;
        }
        fail();
    }
//Tests sur la fonctions :listEvent
    @Test
    public void listEvent_ok(){
        //ajout d'un élément dans la bdd pour qu'elle soit non vide.
        try {
            EvenementService.get().addEvent(evenementTest1);
            EvenementService.get().addEvent(evenementTest2);
        } catch (Exception e) {
            System.out.println("listEvent_ok: ajouts des events incorrect");
            return;
        }
        DateTime debut = new DateTime().minusDays(1);
        DateTime fin = new DateTime().plusDays(3);
        List<Evenement> resultats = EvenementService.get().listEvent(debut.toDate(), fin.toDate());
        for(Evenement eventI : resultats) {
            System.out.println(eventI.nom + " [" + eventI.dateDebut + " au " + eventI.dateFin + " ] par " + eventI.createur.email);
        }
        assertTrue(resultats.size() > 0);
    }
    @Test
    public void listEvent_erreurDates(){
        DateTime debut = new DateTime().plusDays(3);
        DateTime fin = new DateTime().minusDays(1);
        List<Evenement> resultats = EvenementService.get().listEvent(debut.toDate(), fin.toDate());
        assertTrue(resultats.size() == 0);
    }
//Tests sur la fonctions : getEvent
    @Test
    public void getEvent_ok(){
        Evenement recherche, resultat;
        try {
            recherche = EvenementService.get().addEvent(evenementTest1);
        } catch (Exception e) {
            //l'initialisation a échoué, test arrêté
            e.printStackTrace();
            return;
        }
        resultat = EvenementService.get().getEvent(recherche.idEvenement);
        if(resultat == null){
            fail();
            return;
        }
        assertEquals(recherche.idEvenement, resultat.idEvenement);
    }
    @Test
    public void getEvent_Invalide(){
        Evenement result = EvenementService.get().getEvent(-99);
        assertNull(result);
    }
//Tests sur la fonctions : updateEvent
    @Test
    public void updateEvent_ok(){
        Evenement eventTest;
        try {
            eventTest = EvenementService.get().addEvent(evenementTest1);
        } catch (Exception e) {
            //l'initialisation a échoué, test arrêté
            e.printStackTrace();
            return;
        }
        eventTest.dateFin = evenementTest2.dateFin;
        eventTest.dateDebut = evenementTest2.dateDebut;
        eventTest.nom = evenementTest2.nom;
        try {
            EvenementService.get().updateEvent(eventTest);
        } catch (Exception e) {
            fail();
        }
        Evenement resultat = EvenementService.get().getEvent(eventTest.idEvenement);
        if(resultat == null){
            fail();
        }else{
            assertTrue(eventTest.idEvenement == resultat.idEvenement
                    & eventTest.dateDebut == resultat.dateDebut
                    & eventTest.dateFin == resultat.dateFin
                    & eventTest.nom.equals(resultat.nom) );
        }
    }
    @Test
    public void updateEvent_erreurDates(){
        Evenement eventTest;
        try {
            eventTest = EvenementService.get().addEvent(evenementTest1);
        } catch (Exception e) {
            //l'initialisation a échoué, test arrêté
            e.printStackTrace();
            return;
        }
        eventTest.dateFin = evenementTest2.dateDebut;
        eventTest.dateDebut = evenementTest2.dateFin;
        eventTest.nom = evenementTest2.nom;
        try {
            EvenementService.get().updateEvent(eventTest);
        } catch (Exception e) {
            assertTrue(true);
            return;
        }
        fail();
    }
    @Test
    public void updateEvent_erreurNom(){
        Evenement eventTest;
        try {
            eventTest = EvenementService.get().addEvent(evenementTest1);
        } catch (Exception e) {
            //l'initialisation a échoué, test arrêté
            e.printStackTrace();
            return;
        }
        eventTest.dateFin = evenementTest2.dateFin;
        eventTest.dateDebut = evenementTest2.dateDebut;
        eventTest.nom = "";
        try {
            EvenementService.get().updateEvent(eventTest);
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
            eventTest = EvenementService.get().addEvent(evenementTest1);
        } catch (Exception e) {
            //l'initialisation a échoué, test arrêté
            e.printStackTrace();
            return;
        }
        try {
            EvenementService.get().deleteEvent(eventTest);
        } catch (Exception e) {
            fail();
            return;
        }
        Evenement resultat = EvenementService.get().getEvent(eventTest.idEvenement);
        assertNull(resultat);
    }
    @Test
    public void deleteEvent_idInvalide(){
        Evenement eventTest;
        try {
            eventTest = EvenementService.get().addEvent(evenementTest1);
        } catch (Exception e) {
            //l'initialisation a échoué, test arrêté
            e.printStackTrace();
            return;
        }
        eventTest.idEvenement = -99;

        try {
            EvenementService.get().deleteEvent(eventTest);
        } catch (Exception e) {
            assertTrue(true);
            return;
        }
        fail();
    }
}
