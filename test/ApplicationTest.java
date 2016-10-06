import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.Test;
import play.mvc.Http.Response;
import play.test.FunctionalTest;
import services.HibernateUtils;

public class ApplicationTest extends FunctionalTest {

    @Test
    public void testThatIndexPageWorks() {
        Response response = GET("/");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset(play.Play.defaultWebEncoding, response);
    }

    //Test connection à la base de données
    @Test
    public void testHybernateSession(){
        try{
            Session session = HibernateUtils.getSession();
            session.close();
        }catch (HibernateException e){
            fail();
            return;
        }
        assertTrue(true);
    }

    //Test connection à la base de données test
    @Test
    public void testHybernateSessionTest(){
        try{
            HibernateUtils.HibernateTest();
            Session session = HibernateUtils.getSession();
            session.close();
        }catch (HibernateException e){
            fail();
            return;
        }
        assertTrue(true);
    }
}