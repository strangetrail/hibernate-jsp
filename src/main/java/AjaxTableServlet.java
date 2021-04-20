import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import generate.table.*;

@WebServlet("/gettable")	
public class AjaxTableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pageNumber = request.getParameter("page").trim();
		if(pageNumber == null || "".equals(pageNumber)){
			pageNumber = "0";
		}
		try {
			Integer iPageNumber = Integer.parseInt(pageNumber);
			TableGenerator tg = new TableGenerator();
			String tableHTML = tg.generateGeneral(iPageNumber*TableGenerator.numberOfRows, TableGenerator.numberOfRows, 100, iPageNumber);
			
			response.setContentType("text/html");
			response.getWriter().write(tableHTML);
		}
		catch (NumberFormatException e) {
			TableGenerator tg = new TableGenerator();
			String tableHTML = tg.generateGeneral(0, TableGenerator.numberOfRows, 100, 0);
			
			response.setContentType("text/html");
			response.getWriter().write(tableHTML);

			e.printStackTrace();
		}
	}
}
