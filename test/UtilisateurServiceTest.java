import exceptions.InvalidArgumentException;
import models.Utilisateur;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import services.UtilisateurService;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by Administrateur on 03/10/2016.
 */
public class UtilisateurServiceTest {

    Utilisateur utilisateur;

    @BeforeClass
    public static void avantClass() {
        System.out.println("Avant la classe UtilisateurServiceTest\n");
    }

    @AfterClass
    public static void apresClasse() {

      //  UtilisateurService.get().clear();

    }

    @Before
    public void avantToutTest() {
       // UtilisateurService.get().clear();
        try {
            utilisateur = UtilisateurService.get().create("luke.skywalker@gmail.com", "iamyourfather");
            //UtilisateurService.get().modifier(client, "Skywalker", "Luke", "2 rue de Mos Eisley, Tatooine", "0123456789");
        } catch (Exception e) {
            fail();
        }
    }


    // creer
    @Test
    public void testCreate_everythingWrong() {
        // Given
        String email = null;
        String password = null;

        // When
        try {
           Utilisateur utilisateur = UtilisateurService.get().create(email, password);
            fail();
        } catch (Exception e) {
            // Then
            assertTrue(true);
        }
    }

    @Test
    public void testCreer_emailPasswordVide() {
        // Given
        String email = "";
        String password = "";

        // When
        try {
            Utilisateur utilisateur = UtilisateurService.get().create(email, password);
            fail();
        } catch (InvalidArgumentException e) {
            // Then
            assertTrue(e.getRealMessage().contains("Le email ne peut être null ou vide"));
            assertTrue(e.getRealMessage().contains("Le motDePasse ne peut être null ou vide"));
        } catch (Exception e) {
            fail();
        }
    }


    @Test
    public void testCreer_wrongEmail() {
        // Given
        String email = "luke.skywalker";
        String password = "iamyourfather";

        // When
        try {
            Utilisateur utilisateur = UtilisateurService.get().create(email, password);
            fail();
        } catch (InvalidArgumentException e) {
            // Then
            assertTrue(e.getRealMessage().contains("Le format de l'email est invalide"));
        } catch (Exception e) {
            fail();
        }
    }
}
