package fr.grlc.iamweb.services.spring.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import fr.grlc.iamcore.datamodel.Identity;

/**
 * Servlet implementation class GenericSpringServlet
 */
public class GenericSpringServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected final String sessionUser = "user";

	// LINK THE WEBCONTEXT WITH THE SPRING CONTEXT
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	protected boolean isLoggedIn(HttpServletRequest request) {
		HttpSession session = request.getSession(false); // false = do not
															// create

		return session != null && session.getAttribute(sessionUser) != null;
	}

	protected Identity parseIdentity(HttpServletRequest request) throws IOException {

		try {
			String str = "";
			BufferedReader reader = request.getReader();
			String part = "";
			while (part != null) {
				str += part;
				part = reader.readLine();
			}

			JSONObject json = new JSONObject(str);
			// String x= request.getParameter("data");
			// JSONObject json = new JSONObject(x);

			String fname = json.getString("fname");
			String lname = json.getString("lname");
			String email = json.getString("email");
			String dateStr = json.getString("birthdate");

			dateStr = dateStr.replace("-", "/");

			DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

			Date date = null;

			date = (Date) formatter.parse(dateStr);

			return new Identity(fname, lname, email, date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
