package services;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtils {
	private static final SessionFactory sessionFactory;
	
	//Cr�� une unique instance de l'objet
	static{
		try{
			sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		}catch(HibernateException e){
			throw new RuntimeException("Probl�me de configuration hybernate : " + e.getMessage(), e);
		}
	}
	
	//Renvoie la session Hibernate
	public static Session getSession() throws HibernateException{
		return sessionFactory.openSession();
	}
}
