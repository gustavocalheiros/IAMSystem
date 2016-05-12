package fr.grlc.iamweb.services.servlets;

import java.io.IOException;
import java.io.PrintWriter;

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
 */
@WebServlet("/CreateIdentity")
public class CreateIdentity extends GenericSpringServlet {
	
	@Autowired
	IdentityDAOInterface dao;
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!isLoggedIn(request))
			getServletContext().getRequestDispatcher("/index.html").forward(request, response);
		else
			getServletContext().getRequestDispatcher("/identity-create.html").forward(request, response);
	}

	/**
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
		status.put("status", "200");
		status.put("msg", "Identity created! :)");
		
		PrintWriter out = response.getWriter();
		out.print(status);
	}
}
