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
 * Servlet implementation class UpdateIdentity
 * @author gustavo
 */
@WebServlet("/UpdateIdentity")
public class UpdateIdentityServlet extends GenericSpringServlet {

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
		else {
			String s = request.getParameter("id");

			Identity identity = new Identity(Integer.parseInt(s));
			identity = dao.search(identity).get(0);

			request.setAttribute("identity", identity);

			getServletContext().getRequestDispatcher("/identity-update.jsp").forward(request, response);
		}
	}

	/**
	 * Handles the request and uses the DAO to update the identity in the DB.
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
		
		try {
			Identity id = parseIdentity(request);
			dao.update(id);

			JSONObject status = new JSONObject();
			status.put("status", Integer.toString(HttpURLConnection.HTTP_OK));
			status.put("msg", "Identity updated! :)");

			PrintWriter out = response.getWriter();
			out.print(status);
		} catch (JSONException ex) {
			// do nothing
		}
	}
}
