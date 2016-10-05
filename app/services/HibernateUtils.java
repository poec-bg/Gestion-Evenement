package services;

import models.Utilisateur;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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

	//Initilisation de la base de données
	public static void initBdd(){
		Session session = getSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
				session.save( new Utilisateur() );
			tx.commit();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
	}
}
