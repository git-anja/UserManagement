package com.hyadav.usermgt.util;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import com.hyadav.usermgt.model.User;

public class HibernateUtil {
 private static SessionFactory sessionFactory;

 public static SessionFactory getSessionFactory() { 
	 try {
		    Configuration configuration = new Configuration();
		    System.out.println("sessionfactory 1");
		    Properties settings = new Properties();
		    settings.put(Environment.DRIVER, "org.postgresql.Driver");
		    settings.put(Environment.URL, "jdbc:postgresql://localhost:5432/usermgt");
		    settings.put(Environment.USER, "postgres");
		    settings.put(Environment.PASS, "root");
		    settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");

		    settings.put(Environment.SHOW_SQL, "true");

		    settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

		    settings.put(Environment.HBM2DDL_AUTO, "update");

		    configuration.setProperties(settings);
		    configuration.addAnnotatedClass(User.class);

		    return configuration.buildSessionFactory();

		   } catch (Exception e) {
		    e.printStackTrace();
		    return null;
		   }
 }
 
 public static SessionFactory getSessionFactory2() {
	 try {
		    Configuration configuration = new Configuration();
		    System.out.println("sessionfactory 2");
		    Properties settings = new Properties();
		    settings.put(Environment.DRIVER, "org.postgresql.Driver");
		    settings.put(Environment.URL, "jdbc:postgresql://localhost:5432/usermgt2");
		    settings.put(Environment.USER, "postgres");
		    settings.put(Environment.PASS, "root");
		    settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");

		    settings.put(Environment.SHOW_SQL, "true");

		    settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

		    settings.put(Environment.HBM2DDL_AUTO, "update");

		    configuration.setProperties(settings);
		    configuration.addAnnotatedClass(User.class);

		    return configuration.buildSessionFactory();

		   } catch (Exception e) {
		    e.printStackTrace();
		    return null;
		   }
	 }
 
 public static void closeSessionFactory() {
	 if(sessionFactory != null) {
		 sessionFactory.close();
		 sessionFactory = null;
	 }
 }
 
 public static SessionFactory getSessionFactory(String databaseType, String databaseUrl, String username, String password) {
	 
	 String driver = null;
	 String dialect = null;
	 
	 switch(databaseType) {
	 	case "PSQL": driver = "org.postgresql.Driver";
	 				 dialect = "org.hibernate.dialect.PostgreSQLDialect";
	 				 break;
	 	
	 	case "MYSQL": driver = "com.mysql.jdbc.Driver";
		 			  dialect = "org.hibernate.dialect.MySQLDialect";
		 			  break;
	 }
	 
	 try {
		    Configuration configuration = new Configuration();
		    Properties settings = new Properties();
		    settings.put(Environment.DRIVER, driver);
		    settings.put(Environment.URL, databaseUrl);
		    settings.put(Environment.USER, username);
		    settings.put(Environment.PASS, password);
		    settings.put(Environment.DIALECT, dialect);

		    settings.put(Environment.SHOW_SQL, "true");

		    settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

		    settings.put(Environment.HBM2DDL_AUTO, "update");

		    configuration.setProperties(settings);
		    configuration.addAnnotatedClass(User.class);

		    return configuration.buildSessionFactory();

		   } catch (Exception e) {
		    e.printStackTrace();
		    return null;
		   }
 }
 
 
 
 
 
 
 
 
}