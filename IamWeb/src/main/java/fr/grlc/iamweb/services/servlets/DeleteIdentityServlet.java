package fr.grlc.iamweb.services.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import fr.grlc.iamcore.datamodel.Identity;
import fr.grlc.iamcore.services.dao.IdentityDAOInterface;
import fr.grlc.iamweb.services.spring.servlets.GenericSpringServlet;

/**
 * Servlet implementation class DeletyeIdentity
 * @author gustavos
 */
@WebServlet("/DeleteIdentity")
public class DeleteIdentityServlet extends GenericSpringServlet {

	@Autowired
	IdentityDAOInterface dao;

	private static final long serialVersionUID = 1L;

	/**
	 * Handles the get request. if not logged in, redirect to "login" page.
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!isLoggedIn(request))
			getServletContext().getRequestDispatcher(LOGIN_PAGE).forward(request, response);
	}

	/**
	 * Handles the request and uses the DAO to delete the identity from the DB.
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (!isLoggedIn(request)) {
			writeLoginNeededInfo(response);
			return;
		}

		JSONObject status = new JSONObject();
		PrintWriter out = response.getWriter();
		
		try {
			String id = request.getParameter("data");
			
			Identity identity = new Identity();
			id = id.replace("\"", "");
			identity.setId(Integer.parseInt(id));
			
			dao.delete(identity);

			status.put("status", Integer.toString(HttpURLConnection.HTTP_OK));
			status.put("msg", "Identity deleted! :)");

		} catch (JSONException ex) {
			logger.error(ex.toString());
			
			status.put("status", Integer.toString(HttpURLConnection.HTTP_BAD_REQUEST));
			status.put("msg", ex.toString());
		}
		
		out.print(status);
	}
}
