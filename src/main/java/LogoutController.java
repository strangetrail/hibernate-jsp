import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.session.dao.SessionDAO;


@WebServlet("/logout-user")
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private SessionDAO sessionDao;
	
    public void init() {
        sessionDao = new SessionDAO();
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		        logging_in(request, response);
		    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		        response.sendRedirect("logout-user.jsp");
		    }
	
	private void logging_in(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		Cookie []c = request.getCookies();
		if (c != null) {
			for (Cookie c_item : c) {
				if (c_item.getName().compareTo("session_id") == 0) {
					sessionDao.deleteUserSession(c_item.getValue());
					c_item.setMaxAge(0);
					response.addCookie(c_item);
					break;
				}
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("redirect-index.jsp");
		dispatcher.forward(request, response);
	}
}
