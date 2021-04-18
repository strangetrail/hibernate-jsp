package user.session.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class SessionDAO {
	public void deleteUserSession(String sessionId) {
        Transaction transaction = null;
        List<UserSession> ci_result;
        try(Session session = HibernateModel.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder cb_userSessions = session.getCriteriaBuilder();
			CriteriaQuery<UserSession> cquery = cb_userSessions.createQuery(UserSession.class);
			Root<UserSession> root = cquery.from(UserSession.class);
			cquery.select(root).where(cb_userSessions.equal(root.get("sessionId").as(String.class), sessionId));
			
			ci_result = session.createQuery(cquery).getResultList();
			session.delete(ci_result.get(0));
            transaction.commit();
        } catch (Exception e) {
        	if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
		
	}
	public void saveUserSession(UserSession userSession) {
        Transaction transaction = null;
        try(Session session = HibernateModel.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student object
            session.save(userSession);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } 
    }

	public List<UserSession> findUserSession(String sessionId) {
		Transaction transaction = null;
		List<UserSession> ci_result = null;
	    try(Session session = HibernateModel.getSessionFactory().openSession()) {
		    transaction = session.beginTransaction();
			CriteriaBuilder cb_userSessions = session.getCriteriaBuilder();
			CriteriaQuery<UserSession> cquery = cb_userSessions.createQuery(UserSession.class);
			Root<UserSession> root = cquery.from(UserSession.class);
			cquery.select(root).where(cb_userSessions.equal(root.get("sessionId").as(String.class), sessionId));
			
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
