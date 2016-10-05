import models.Evenement;
import models.Utilisateur;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import play.test.UnitTest;
import services.HibernateUtils;

import javax.validation.constraints.AssertTrue;

public class BasicTest extends UnitTest {

    @Test
    public void aVeryImportantThingToTest() {
        assertEquals(2, 1 + 1);
    }

    @Test
    public void hybernateSaveEvent(){
        long idResultat = -1;
        Evenement evenement = new Evenement();
        evenement.nom = "TEST000";
        Session session = HibernateUtils.getSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            idResultat = (long) session.save(evenement);
            tx.commit();
        }catch (Exception e) {
            e.printStackTrace();
            fail();
        }finally {
            session.close();
        }
        assertTrue(idResultat >= 0);
    }

    @Test
    public void hybernateSaveUtilisateur(){
        HibernateUtils.HibernateTest();
        String idResultat = "";
        Utilisateur user = new Utilisateur();
        user.email = "test@test.fr";
        Session session = HibernateUtils.getSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
                idResultat = (String) session.save(user);
            tx.commit();
        }catch (Exception e) {
            e.printStackTrace();
            fail();
        }finally {
            session.close();
        }
        assertTrue(idResultat.length() > 0);
    }
}
