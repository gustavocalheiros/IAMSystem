package fr.grlc.iamweb.services.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import fr.grlc.iamcore.datamodel.Identity;
import fr.grlc.iamcore.services.dao.IdentityDAOInterface;
import fr.grlc.iamweb.services.spring.servlets.GenericSpringServlet;

/**
 * Servlet implementation class SearchIdentity
 * 
 * @author gustavo
 */
@WebServlet("/SearchIdentity")
public class SearchIdentityServlet extends GenericSpringServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	IdentityDAOInterface dao;

	private static final int ID_READ_ALL = -1;

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
		else
			getServletContext().getRequestDispatcher("/identity-search.html").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (!isLoggedIn(request)) {
			writeLoginNeededInfo(response);
			return;
		}
		
		PrintWriter out = response.getWriter();
		
		Identity id = parseIdentity(request);
		JSONArray jsonArray = new JSONArray();
		JSONObject status = new JSONObject();

		if (id != null) {

			List<Identity> list = null;

			if (id.getId() == ID_READ_ALL) {
				list = dao.readAll();
			} else {
				list = dao.search(id);
			}

			if (list != null) {
				status.put("status", Integer.toString(HttpURLConnection.HTTP_OK));

				jsonArray.put(status);

				for (Identity identity : list) {
					JSONObject idJson = new JSONObject();
					idJson.put("id", identity.getId());
					idJson.put("fname", identity.getFirstName());
					idJson.put("lname", identity.getLastName());
					idJson.put("email", identity.getEmail());
					idJson.put("birthdate", identity.getBirthDate());
					jsonArray.put(idJson);
				}

				out.print(jsonArray);
			} else {
				status.put("status", Integer.toString(HttpURLConnection.HTTP_BAD_REQUEST));
				status.put("msg", "at least one field has to be filled!");
				jsonArray.put(status);
				out.print(jsonArray);
			}
		} else {

			status.put("status", Integer.toString(HttpURLConnection.HTTP_BAD_REQUEST));
			status.put("msg", "internal error");
			jsonArray.put(status);
			out.print(jsonArray);
		}
	}

}
