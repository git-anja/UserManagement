package com.hyadav.usermgt.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.hyadav.usermgt.model.User;
import com.hyadav.usermgt.util.HibernateUtil;


public class UserDao {

	private ArrayList<SessionFactory> sessionFactoryList = new ArrayList<SessionFactory>();
	
    public void saveUser(User user, int db) {
    	
    	Session s = null;
    	Transaction transaction = null;
    	SessionFactory sf = null;
    	try {
        	sf = this.sessionFactoryList.get(db);
        	s = sf.openSession();
        	transaction = s.beginTransaction();
        	s.save(user);
        	transaction.commit();
        	s.close();
//        	HibernateUtil.closeSessionFactory();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    public void updateUser(User user, int db) {
    
    	Session s = null;
    	Transaction transaction = null;
    	SessionFactory sf = null;
    	try {
    		sf = this.sessionFactoryList.get(db);
        	s = sf.openSession();
        	transaction = s.beginTransaction();
        	s.update(user);
        	transaction.commit();
        	s.close();
//        	HibernateUtil.closeSessionFactory();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    public void deleteUser(int id, int db) {

    	Session s = null;
    	Transaction transaction = null;
    	SessionFactory sf = null;
    	try {
    		sf = this.sessionFactoryList.get(db);
        	s = sf.openSession();
        	transaction = s.beginTransaction();
        	User user = s.get(User.class, id);
        	if (user != null) {
              s.delete(user);
              System.out.println("user is deleted");
          }
        	transaction.commit();
        	s.close();
//        	HibernateUtil.closeSessionFactory();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    public User getUser(int id, int db) {

    	Session s = null;
    	Transaction transaction = null;
        User user = null;
        SessionFactory sf = null;
    	try {
    		sf = this.sessionFactoryList.get(db);
        	s = sf.openSession();
        	transaction = s.beginTransaction();
        	user = s.get(User.class, id);
        	transaction.commit();
        	s.close();
//        	HibernateUtil.closeSessionFactory();
    	} catch(Exception e) {
    		e.printStackTrace();
    	} 	
        
        return user;
    }

    @SuppressWarnings("unchecked")
    public List < User > getAllUser(int db) {

    	Session s = null;
    	Transaction transaction = null;
    	List < User > listOfUser = null;
    	SessionFactory sf = null;
    	try {
    		sf = this.sessionFactoryList.get(db);
        	s = sf.openSession();
        	transaction = s.beginTransaction();
        	listOfUser = s.createQuery("from User").getResultList();
        	transaction.commit();
        	s.close();
//        	HibernateUtil.closeSessionFactory();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	return listOfUser;
    }

	public void resetSessionFactory() {
		HibernateUtil.closeSessionFactory();
		
	}
	
	public int generateSessionFactory(String databaseType, String databaseUrl, String username, String password) {
		this.sessionFactoryList.add(HibernateUtil.getSessionFactory(databaseType, databaseUrl, username, password));
		return this.sessionFactoryList.size() - 1;
	}
}
