package user.session.dao;
import java.util.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.*;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class ChatMessageDAO {
	
	public void removeChatMessages(int user_1, int user_2) {
		Transaction tr = null;
		try(Session session = HibernateModel.getSessionFactory().openSession()) {
        	tr = session.beginTransaction();
        	CriteriaBuilder cb_messages = session.getCriteriaBuilder();
    		CriteriaQuery<ChatMessage> cquery = cb_messages.createQuery(ChatMessage.class);
    		Root<ChatMessage> root = cquery.from(ChatMessage.class);
    		Predicate cond_1 = cb_messages.equal(root.get("user_1").as(Integer.class), user_1);
    		Predicate cond_2 = cb_messages.equal(root.get("user_2").as(Integer.class), user_2);
    		//Predicate cond_3 = cb_messages.equal(root.get("user_1").as(Integer.class), user_2);
    		//Predicate cond_4 = cb_messages.equal(root.get("user_2").as(Integer.class), user_1);
    		//Predicate cond_12 = cb_messages.and(cond_1, cond_2);
    		//Predicate cond_34 = cb_messages.and(cond_3, cond_4);
    		cquery.select(root).where(cb_messages.and(cond_1, cond_2)).orderBy(cb_messages.asc(root.get("id")));
    		List<ChatMessage> queryResult = session.createQuery(cquery).getResultList();
    		for (ChatMessage message : queryResult) {
    			session.delete(message);
    		}
    			
        	tr.commit();
		} catch (Exception e) {
        	e.printStackTrace();
        	if (tr != null) {
        		tr.rollback();
        	}
		}
	}

	public void deleteChatMessages(int senderId, int recipientId) {
		Transaction tr = null;
		try(Session session = HibernateModel.getSessionFactory().openSession()) {
        	tr = session.beginTransaction();
        	boolean messageDeleted;
        	CriteriaBuilder cb_messages = session.getCriteriaBuilder();
    		CriteriaQuery<ChatMessage> cquery = cb_messages.createQuery(ChatMessage.class);
    		Root<ChatMessage> root = cquery.from(ChatMessage.class);
    		Predicate cond_1 = cb_messages.equal(root.get("user_1").as(Integer.class), senderId);
    		Predicate cond_2 = cb_messages.equal(root.get("user_2").as(Integer.class), recipientId);
    		Predicate cond_3 = cb_messages.equal(root.get("user_1").as(Integer.class), recipientId);
    		Predicate cond_4 = cb_messages.equal(root.get("user_2").as(Integer.class), senderId);
    		Predicate cond_12 = cb_messages.and(cond_1, cond_2);
    		Predicate cond_34 = cb_messages.and(cond_3, cond_4);
    		cquery.select(root).where(cb_messages.or(cond_12, cond_34)).orderBy(cb_messages.asc(root.get("id")));
    		List<ChatMessage> queryResult = session.createQuery(cquery).getResultList();
    		for (ChatMessage message: queryResult) {
    			if (message.getUser1() == senderId)
    				message.setDeletedForSender(true);
    			else
    				message.setDeletedForRecipient(true);
    			messageDeleted = false;
    			if ((message.getDeletedForSender() != null)
    					&&(message.getDeletedForRecipient() != null)) {
    				if (message.getDeletedForSender()
    					&& message.getDeletedForRecipient()) {
    					session.delete(message);
    					messageDeleted = true;
    				}
    			}
    			if (!messageDeleted)
    				session.save(message);
    		}
        	tr.commit();
		} catch (Exception e) {
        	e.printStackTrace();
        	if (tr != null) {
        		tr.rollback();
        	}
        }
	}
	
	public void addChatMessage (int user_1, int user_2, String message) {
		Transaction tr = null;
		try(Session session = HibernateModel.getSessionFactory().openSession()) {
        	tr = session.beginTransaction();
        	ChatMessage chatMessage = new ChatMessage();
        	chatMessage.setUser1(user_1);
        	chatMessage.setUser2(user_2);
        	chatMessage.setMessage(message);
        	session.save(chatMessage);
        	tr.commit();
		} catch (Exception e) {
        	e.printStackTrace();
        	if (tr != null) {
        		tr.rollback();
        	}
        	
        }
		
	}
	
	public List<ChatMessage> listChatMessages(int user_1, int user_2){
		Transaction transaction = null;
        try(Session session = HibernateModel.getSessionFactory().openSession()) {
        	transaction = session.beginTransaction();
    		List<ChatMessage> visibleChatMessages = new ArrayList<ChatMessage>();
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
    		for (ChatMessage message: queryResult) {
    			if ((user_1 == message.getUser1()) && (message.getDeletedForSender()==null)) {
					visibleChatMessages.add(message);
    			}
    			else if ((user_1 == message.getUser1()) && (message.getDeletedForSender()!=null)) {
    				if(!message.getDeletedForSender())
    					visibleChatMessages.add(message);
    			}
    			if ((user_1 == message.getUser2()) && (message.getDeletedForRecipient()==null)) {
    				visibleChatMessages.add(message);
    			}
    			else if ((user_1 == message.getUser2()) && (message.getDeletedForRecipient()!=null)) {
    				if (!message.getDeletedForRecipient())
    					visibleChatMessages.add(message);
    			}
    		}
    		transaction.commit();
    		return visibleChatMessages;
        } catch (Exception e) {
        	e.printStackTrace();
        	if (transaction != null) {
        		transaction.rollback();
        	}
        	
        }
		return null;
	}
}
