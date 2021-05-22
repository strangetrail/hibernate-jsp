package user.session.dao;
import java.util.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.*;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class ChatMessageDAO {
	public List<ChatMessage> listChatMessages(int user_1, int user_2){
		Transaction transaction = null;
        try(Session session = HibernateModel.getSessionFactory().openSession()) {
        	transaction = session.beginTransaction();
        	CriteriaBuilder cb_messages = session.getCriteriaBuilder();
    		CriteriaQuery<ChatMessage> cquery = cb_messages.createQuery(ChatMessage.class);
    		Root<ChatMessage> root = cquery.from(ChatMessage.class);
    		Predicate cond_1 = cb_messages.equal(root.get("user_1").as(Integer.class), user_1);
    		Predicate cond_2 = cb_messages.equal(root.get("user_2").as(Integer.class), user_2);
    		Predicate cond_3 = cb_messages.equal(root.get("user_1").as(Integer.class), user_2);
    		Predicate cond_4 = cb_messages.equal(root.get("user_2").as(Integer.class), user_1);
    		Predicate cond_12 = cb_messages.and(cond_1, cond_2);
    		Predicate cond_34 = cb_messages.and(cond_3, cond_4);
    		cquery.select(root).where(cb_messages.or(cond_12, cond_34)).orderBy(cb_messages.asc(root.get("id")));
    		List<ChatMessage> queryResult = session.createQuery(cquery).getResultList();
    		transaction.commit();
    		return queryResult;
        } catch (Exception e) {
        	e.printStackTrace();
        	if (transaction != null) {
        		transaction.rollback();
        	}
        	
        }
		return null;
	}
}
