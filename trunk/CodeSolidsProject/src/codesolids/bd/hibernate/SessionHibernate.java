package codesolids.bd.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;



public class SessionHibernate {

	
	private static SessionHibernate instance = new SessionHibernate();
	
	private SessionFactory sessionFactory;
	
	private SessionHibernate()
	{
		Configuration configuration = new AnnotationConfiguration();
		configuration.configure("hibernate.cfg.xml");
		sessionFactory = configuration.buildSessionFactory();
	}	
	
	public static SessionHibernate getInstance()
	{
		return instance;
	}
	
	public Session getSession()
	{
		return sessionFactory.openSession();
	}
	
}
