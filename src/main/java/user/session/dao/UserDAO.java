package user.session.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.criteria.*;
import java.util.*;




public class UserDAO {

    public void saveUser(User user) {
        Transaction transaction = null;
        try(Session session = HibernateModel.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the object
            session.save(user);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } 
    }
    
    public List<User> listUsers() {
        Transaction transaction = null;
        try(Session session = HibernateModel.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            List<User> userList = session.createQuery("FROM User ORDER BY id ASC").list();
            // commit transaction
            transaction.commit();
            return userList;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return null;
    }
    
    public User findUser(String login) {
    	Transaction transaction = null;
		List<User> ci_result = null;
        try(Session session = HibernateModel.getSessionFactory().openSession()) {
        transaction = session.beginTransaction();
    	CriteriaBuilder cb_users = session.getCriteriaBuilder();
		CriteriaQuery<User> cquery = cb_users.createQuery(User.class);
		Root<User> root = cquery.from(User.class);
		cquery.select(root).where(cb_users.equal(root.get("username").as(String.class), login));
		
		ci_result = session.createQuery(cquery).getResultList();
		transaction.commit();
		return ci_result.get(0);
        } catch (Exception e) {
        	if (transaction != null) {
        		transaction.rollback();
        	}
        	e.printStackTrace();
        }
    	return null;
    }
    
    public List<User> findUser(String login, String hashedPassword) {
    		Transaction transaction = null;
    		List<User> ci_result = null;
	        try(Session session = HibernateModel.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
        	CriteriaBuilder cb_users = session.getCriteriaBuilder();
    		CriteriaQuery<User> cquery = cb_users.createQuery(User.class);
    		Root<User> root = cquery.from(User.class);
    		Predicate pUserName = cb_users.equal(root.get("username").as(String.class), login);
    		Predicate pPassword = cb_users.equal(root.get("password").as(String.class), hashedPassword);
    		cquery.select(root).where(cb_users.and(pUserName, pPassword));
    		
    		ci_result = session.createQuery(cquery).getResultList();
    		transaction.commit();
	        } catch (Exception e) {
	        	if (transaction != null) {
	        		transaction.rollback();
	        	}
	        	e.printStackTrace();
	        }
	        return ci_result;
    }
}