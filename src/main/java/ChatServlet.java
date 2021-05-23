import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

import user.session.dao.*;

import java.util.*;

@WebServlet("/getchat")
public class ChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDao;
	private SessionDAO sessionDao;
	private ChatMessageDAO chatMessageDao;
	public void init() {
        userDao = new UserDAO();
        sessionDao = new SessionDAO();
        chatMessageDao = new ChatMessageDAO();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("action").compareTo("list") == 0) {
			String JSONChat = "{\"chats\":[";
			String user_1 = request.getParameter("user_1").trim();		
			String user_2 = request.getParameter("user_2").trim();
			Integer user1 = userDao.findUser(user_1).getId();
			Integer user2 = userDao.findUser(user_2).getId();
			List<ChatMessage> messages = chatMessageDao.listChatMessages(user1, user2);
			for (ChatMessage message : messages) {
				if (message.getUser1() == user1) {
					JSONChat += "{\"q\":\"" + message.getMessage() + "\"},";
				} else {
					JSONChat += "{\"a\":\"" + message.getMessage() + "\"},";
				}
			}
			JSONChat = JSONChat.substring(0, JSONChat.length() - 1);
			JSONChat += "]}";
			Cookie cookieRecipient = new Cookie("recipient", user_2);
		    cookieRecipient.setMaxAge(60*60);
		    response.addCookie(cookieRecipient);
			response.setContentType("text/json");
			response.getWriter().write(JSONChat);
		}
		if (request.getParameter("action").compareTo("add") == 0) {
			Cookie []c = request.getCookies();
			String login="", recipient="";
			if (c != null) {
				for (Cookie c_item : c) {
					if (c_item.getName().compareTo("login") == 0) {
						login = c_item.getValue();
						if (recipient.length() > 0)
							break;
					}
					if (c_item.getName().compareTo("recipient") == 0) {
						recipient = c_item.getValue();
						if (login.length() > 0)
							break;
					}
				}
			}
			String message = request.getParameter("message");
			User sender = userDao.findUser(login);
			User recipientUser = userDao.findUser(recipient);
			chatMessageDao.addChatMessage(sender.getId(), recipientUser.getId(), message);
		}
	}
}
