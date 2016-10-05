import exceptions.InvalidArgumentException;
import models.Utilisateur;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import services.UtilisateurService;

import java.util.List;

import static org.junit.Assert.assertEquals;
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
       UtilisateurService.get().clear();
        try {
            utilisateur = UtilisateurService.get().create("luke.skywalker@hotmail.com", "iamyourfather");

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

    @Test
    public void testCreer_OK() {
        // Given
        String email = "anakin.skywalker@gmail.com";
        String password = "iamyourfather";

        // When
        try {
            Utilisateur utilisateur = UtilisateurService.get().create(email, password);
            // Then
            assertEquals("anakin.skywalker@gmail.com", utilisateur.email);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testLister_twoUtilisateur() throws Exception {
        // Given
        // enregistrer un premier Client
        Utilisateur utilisateur1 = UtilisateurService.get().create("yan.tot@msn.com", "azerty");
        // enregistrer un deuxième Client
       Utilisateur utilisateur2 = UtilisateurService.get().create("han.solo@gmail.com", "0123456789");


        // When
        List<Utilisateur>utilisateurs = UtilisateurService.get().listUtilisateurs();

        // Then
        assertEquals(3, utilisateurs.size());
    }

    @Test
    public void testRemove_utilisateurOk() {
        // Given

        // When


            // Then



    }


}
