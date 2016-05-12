package fr.grlc.iamweb.services.spring.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import fr.grlc.iamcore.datamodel.Identity;

/**
 * Servlet implementation class GenericSpringServlet
 */
public class GenericSpringServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	final static Logger logger = Logger.getLogger(GenericSpringServlet.class);

	protected final String sessionUser = "user";

	// LINK THE WEBCONTEXT WITH THE SPRING CONTEXT
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());

		logger.info("INIT CALLED!!!!!!!!!!!!!!");
	}

	protected boolean isLoggedIn(HttpServletRequest request) {
		HttpSession session = request.getSession(false); // false = do not
															// create

		return session != null && session.getAttribute(sessionUser) != null;
	}

	protected void writeLoginNeededInfo(HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		JSONObject status = new JSONObject();
		status.put("status", "408");
		jsonArray.put(status);
		out.print(jsonArray);
	}

	protected Identity parseIdentity(HttpServletRequest request) throws IOException {

		try {
			String x = request.getParameter("data");
			JSONObject json = new JSONObject(x);
			
			int id = 0;
			if(json.has("id")){
				id = json.getInt("id");
			}
			
			String fname = json.getString("fname");
			String lname = json.getString("lname");
			String email = json.getString("email");
			String dateStr = json.getString("birthdate");

			Date date = null;
			if (!dateStr.isEmpty()) {
				dateStr = dateStr.replace("-", "/");

				DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

				date = (Date) formatter.parse(dateStr);
			}

			Identity identity = new Identity(fname, lname, email, date);
			identity.setId(id);
			
			return identity; 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
