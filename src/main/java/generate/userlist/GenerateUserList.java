package generate.userlist;

import user.session.dao.*;
import java.util.*;

public class GenerateUserList {
	public String generateList() {
		String generatedHTML = "<ul class=\"userlist\" id=\"userlist\">";
		UserDAO userDao = new UserDAO();
		List<User> users = userDao.listUsers();
		for (User user : users) {
			generatedHTML += "<li><a role=\"button\" onclick=\"selectchat('" + user.getUsername()
					+ "')\" class=\"chatselection\">" + user.getUsername() + "</a></li>";
		}
		generatedHTML += "</ul>";
			
		return generatedHTML;
	}
}
