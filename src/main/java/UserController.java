
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import user.session.dao.*;

@WebServlet("/register")
public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDao;

    public void init() {
        userDao = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        register(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.sendRedirect("register.jsp");
    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	String hashtext;
    	String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String username = request.getParameter("login_field");
        String password1 = request.getParameter("pass1");
        String password2 = request.getParameter("pass2");
        if (password1.compareTo(password2) != 0) {
        	System.out.println("passwords: " + password1 + " and " + password2);
        	RequestDispatcher dispatcher = request.getRequestDispatcher("register-failure.jsp");
            dispatcher.forward(request, response);
        }
        else {
        	try {
	            MessageDigest md = MessageDigest.getInstance("SHA-512");
	            byte[] messageDigest = md.digest(password1.getBytes());
	            BigInteger no = new BigInteger(1, messageDigest);
	            hashtext = no.toString(16);
	            while (hashtext.length() < 32) {
	                hashtext = "0" + hashtext;
	            }
        	} catch (NoSuchAlgorithmException e) {
        		throw new RuntimeException(e);
        	}
	        User user = new User();
	        user.setFirstName(firstName);
	        user.setLastName(lastName);
	        user.setUsername(username);
	        user.setPassword(hashtext);
	
	        userDao.saveUser(user);
	
	        RequestDispatcher dispatcher = request.getRequestDispatcher("register-success.jsp");
	        dispatcher.forward(request, response);
        }
    }
}
