package services;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtils {
	private static SessionFactory sessionFactory;
	
	//Créé une unique instance de l'objet
	static{
		try{
			sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		}catch(HibernateException e){
			throw new RuntimeException("Problème de configuration hybernate : " + e.getMessage(), e);
		}
	}

	public static void HibernateTest() {
		try {
			sessionFactory = new AnnotationConfiguration().configure()
					.setProperty("hibernate.connection.url", "jdbc:mysql://10.0.0.174:3306/calendarTest")
					.buildSessionFactory();
		} catch (HibernateException ex) {
			throw new RuntimeException("Probl?e de configuration : " + ex.getMessage(), ex);
		}
	}
	
	//Renvoie la session Hibernate
	public static Session getSession() throws HibernateException{
		return sessionFactory.openSession();
	}
}
