package user.session.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "chats")
public class ChatMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	
	@Column(name = "user_1")
	private int user_1;
	
	@Column(name = "user_2")
	private int user_2;
	
	@Column(name = "message")
	private String message;
	
	@Column(name = "deleted_for_sender")
	private Boolean deletedForSender;
	
	@Column(name = "deleted_for_recipient")
	private Boolean deletedForRecipient;
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getUser1() {
		return this.user_1;
	}
	
	public void setUser1(int user_1) {
		this.user_1 = user_1;
	}
	
	public int getUser2() {
		return this.user_2;
	}
	
	public void setUser2(int user_2) {
		this.user_2 = user_2;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Boolean getDeletedForSender() {
		return this.deletedForSender;
	}
	
	public void setDeletedForSender(Boolean deletedForSender) {
		this.deletedForSender = deletedForSender;
	}
	
	public Boolean getDeletedForRecipient() {
		return this.deletedForRecipient;
	}
	
	public void setDeletedForRecipient(Boolean deletedForRecipient) {
		this.deletedForRecipient = deletedForRecipient;
	}
}
