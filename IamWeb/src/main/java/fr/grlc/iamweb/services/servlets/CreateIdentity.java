package fr.grlc.iamweb.services.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import fr.grlc.iamcore.datamodel.Identity;
import fr.grlc.iamcore.services.dao.IdentityDAOInterface;
import fr.grlc.iamweb.services.spring.servlets.GenericSpringServlet;

/**
 * Servlet implementation class CreateIdentity
 * @author gustavo
 */
@WebServlet("/CreateIdentity")
public class CreateIdentity extends GenericSpringServlet {
	
	@Autowired
	IdentityDAOInterface dao;
	
	private static final long serialVersionUID = 1L;

	/**
	 * Handles the get request. if not logged in, redirect to "login" page.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(!isLoggedIn(request))
			getServletContext().getRequestDispatcher(LOGIN_PAGE).forward(request, response);
		else
			getServletContext().getRequestDispatcher("/identity-create.html").forward(request, response);
	}

	/**
	 * Handles the request and uses the DAO to create the identity in the DB.
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(!isLoggedIn(request)){
			writeLoginNeededInfo(response);
			return;
		}
		
		Identity id = parseIdentity(request);
		dao.write(id);
		
		JSONObject status = new JSONObject();
		status.put("status", Integer.toString(HttpURLConnection.HTTP_OK));
		status.put("msg", "Identity created! :)");
		
		PrintWriter out = response.getWriter();
		out.print(status);
	}
}
