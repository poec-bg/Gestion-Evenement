import models.Evenement;
import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;
import services.EvenementService;

import java.util.Date;

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

//Tests sur les fonctions d'ajout
    
}
