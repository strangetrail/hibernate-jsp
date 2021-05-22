import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import java.util.*;
import user.session.dao.*;


@WebServlet("/login-user")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserDAO userDao;
    private SessionDAO sessionDao;
    
    public void init() {
        userDao = new UserDAO();
        sessionDao = new SessionDAO();
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		        logging_in(request, response);
		    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		        response.sendRedirect("login-user.jsp");
		    }
	
	private void logging_in(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		Boolean dispatchedSuccess = false;
		Boolean alreadyLoggedIn = false;
		String login = request.getParameter("login_field");
		String password = request.getParameter("password_field");
		String hashtext;
		Cookie []c = request.getCookies();
		for (Cookie item : c) {
			if (item.getName().compareTo("session_id") == 0) {
				alreadyLoggedIn = true;
				break;
			}
		}
		if (!alreadyLoggedIn) {
		try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
    	} catch (NoSuchAlgorithmException e) {
    		throw new RuntimeException(e);
    	}
		List<User> userList = userDao.findUser(login, hashtext);
		if (userList != null) {
			if (userList.size() > 0) {
				byte[] array = new byte[24];
			    new Random().nextBytes(array);
			    BigInteger genStr = new BigInteger(1, array);
			    Cookie cookieSessionId = new Cookie("session_id", genStr.toString(16));
			    cookieSessionId.setMaxAge(60*60);
			    response.addCookie(cookieSessionId);
			    Cookie cookieLogin = new Cookie("login", login);
			    cookieSessionId.setMaxAge(60*60);
			    response.addCookie(cookieLogin);
			    UserSession usCookie = new UserSession();
			    usCookie.setSessionId(genStr.toString(16));
			    usCookie.setUserName(login);
			    usCookie.setCreationTime(new Date(System.currentTimeMillis()));
			    sessionDao.saveUserSession(usCookie);
			    

	        	RequestDispatcher dispatcher = request.getRequestDispatcher("login-success.jsp");
        		dispatcher.forward(request, response);
        		dispatchedSuccess = true;
			}
		}
		if (!dispatchedSuccess) {
        	RequestDispatcher dispatcher = request.getRequestDispatcher("login-failure.jsp");
            dispatcher.forward(request, response);
		}
		}
		else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("already-logged-in.jsp");
            dispatcher.forward(request, response);
		}
	}
}
