import models.Evenement;
import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;
import services.EvenementService;

import java.util.Date;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class EvenementTest {
    EvenementService evenementService = EvenementService.get();
    Evenement evenementTest1, evenementTest2;

    @BeforeClass
    public void initilisation(){
        evenementTest1.nom = "TEST 1";
        evenementTest1.idCreateur = "test@test.org";
        evenementTest1.dateDebut = new Date();
        evenementTest1.dateFin = new DateTime().plusDays(2).toDate();

        evenementTest2.nom = "TEST 1";
        evenementTest2.idCreateur = "test@test.org";
        evenementTest2.dateDebut = new DateTime().plusHours(26).toDate();
        evenementTest2.dateFin = new DateTime().plusHours(28).toDate();
    }

//Tests sur les fonctions : addEvent
    @Test
    public void addEvent_OK(){
        try {
            evenementService.addEvent(evenementTest1);
        } catch (Exception e) {
            fail();
        }
        assertTrue(true);
    }
    @Test
    public void addEvent_DebutApresFin(){
        Evenement evenementTest = new Evenement();
        evenementTest.nom = "TEST 1";
        evenementTest.idCreateur = "test@test.org";
        evenementTest.dateDebut = new DateTime().toDate();
        evenementTest.dateFin = new DateTime().minusHours(8).toDate();
        try {
            evenementService.addEvent(evenementTest);
        } catch (Exception e) {
            assertTrue(true);
        }
        fail();
    }
    @Test
    public void addEvent_nomNull(){
        Evenement evenementTest = new Evenement();
        evenementTest.nom = null;
        evenementTest.idCreateur = "test@test.org";
        evenementTest.dateDebut = new DateTime().toDate();
        evenementTest.dateFin = new DateTime().minusHours(8).toDate();
        try {
            evenementService.addEvent(evenementTest);
        } catch (Exception e) {
            assertTrue(true);
        }
        fail();
    }
    @Test
    public void addEvent_nomVide(){
        Evenement evenementTest = new Evenement();
        evenementTest.nom = "";
        evenementTest.idCreateur = "test@test.org";
        evenementTest.dateDebut = new DateTime().toDate();
        evenementTest.dateFin = new DateTime().minusHours(8).toDate();
        try {
            evenementService.addEvent(evenementTest);
        } catch (Exception e) {
            assertTrue(true);
        }
        fail();
    }
    @Test
    public void addEvent_idCreateurNull(){
        Evenement evenementTest = new Evenement();
        evenementTest.nom = "TEST_addEvent";
        evenementTest.idCreateur = null;
        evenementTest.dateDebut = new DateTime().toDate();
        evenementTest.dateFin = new DateTime().minusHours(8).toDate();
        try {
            evenementService.addEvent(evenementTest);
        } catch (Exception e) {
            assertTrue(true);
        }
        fail();
    }
    @Test
    public void addEvent_idCreateurVide(){
        Evenement evenementTest = new Evenement();
        evenementTest.nom = "TEST_addEvent";
        evenementTest.idCreateur = "";
        evenementTest.dateDebut = new DateTime().toDate();
        evenementTest.dateFin = new DateTime().minusHours(8).toDate();
        try {
            evenementService.addEvent(evenementTest);
        } catch (Exception e) {
            assertTrue(true);
        }
        fail();
    }
//Tests sur la fonctions :listEvent

//Tests sur la fonctions : getEvent
//Tests sur lA fonctions : updateEvent
//Tests sur lA fonctions : deleteEvent
}
