package fr.grlc.iamweb.services.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import fr.grlc.iamcore.datamodel.Identity;
import fr.grlc.iamcore.services.dao.IdentityDAOInterface;
import fr.grlc.iamweb.services.spring.servlets.GenericSpringServlet;

/**
 * Servlet implementation class CreateIdentity
 */
@WebServlet("/UpdateIdentity")
public class UpdateIdentityServlet extends GenericSpringServlet {

	@Autowired
	IdentityDAOInterface dao;

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!isLoggedIn(request))
			getServletContext().getRequestDispatcher("/index.html").forward(request, response);
		else {
			String s = request.getParameter("id");

			Identity identity = new Identity(Integer.parseInt(s));
			identity = dao.search(identity).get(0);

			request.setAttribute("identity", identity);

			getServletContext().getRequestDispatcher("/identity-update.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// do nothing
	}
}
