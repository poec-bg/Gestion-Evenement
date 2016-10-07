import exceptions.InvalidArgumentException;
import models.Utilisateur;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import services.HibernateUtils;
import services.UtilisateurService;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class UtilisateurServiceTest {
   Utilisateur utilisateur;

    @BeforeClass
    public static void avantClass() {
            HibernateUtils.HibernateTest();
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
           utilisateur = UtilisateurService.get().create("coxys@gmail.com", "iamyourfather");

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

        String email = "koko@m2i.com";
        String password = "iamyourfather";

        // When
        try {
            Utilisateur utilisateur = UtilisateurService.get().create(email, password);
            // Then
            assertEquals("koko@m2i.com", utilisateur.email);

        } catch (Exception e) {
            fail();
        }
    }


    @Test
    public void testLister_twoUtilisateur() throws Exception {
        // Given

        // enregistrer un premier Client
        utilisateur = UtilisateurService.get().create("azerty@test.fr", "12324");
        // enregistrer un deuxième Client
       Utilisateur utilisateur2 = UtilisateurService.get().create("coca@msn.com","azerty");

        // When
        List<Utilisateur>utilisateurs = UtilisateurService.get().listUtilisateurs();

        // Then
        assertEquals(4, utilisateurs.size());
    }

    @Test
    public void testSupprimer_clientNull() {
        // Given
        Utilisateur utilisateur1 = null;

        // When
        try {
            UtilisateurService.get().deleteUtilisateur(utilisateur1);
            fail();

        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void testSupprimer_utilisateurOk() {
        // Given
        //Utilisateur utilisateur;
        try {
            utilisateur = UtilisateurService.get().create("test@test.fr", "12324");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // When
        try {
            UtilisateurService.get().deleteUtilisateur(utilisateur);
        } catch (Exception e) {
            fail();
        }

        // Then
        assertEquals(true, utilisateur.isSupprime);
    }

    // authenticate
    @Test
    public void testAuthenticate_everythingWrong() {
        // Given
        String email = null;
        String motDePasse = null;

        // When
        try {
            boolean isAuthenticate = UtilisateurService.get().authenticate(email, motDePasse);
            fail();

        } catch (Exception e) {
            assertTrue(true);
            return;
        }
    }




}










