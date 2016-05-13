package fr.grlc.iamweb.services.spring.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
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

	final static protected Logger logger = Logger.getLogger(GenericSpringServlet.class);

	protected final String LOGIN_PAGE = "/index.html";
	protected final String sessionUser = "user";

	/**
	 * 	Init config and links THE WEBCONTEXT WITH THE SPRING CONTEXT
	 */
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

	/**
	 * Writes in the response information about the need to login
	 * 
	 * @param response HttpResponse
	 * @throws IOException
	 */
	protected void writeLoginNeededInfo(HttpServletResponse response) throws IOException {
		
		logger.info("not logged in (timeout)");
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		JSONObject status = new JSONObject();
		status.put("status", Integer.toString(HttpURLConnection.HTTP_CLIENT_TIMEOUT));
		jsonArray.put(status);
		out.print(jsonArray);
	}

	
	/**
	 * Parse JSON and returns an Identity
	 * 
	 * @param request HttpServletRequest
	 * @return Identity
	 * @throws IOException Malformed JSON
	 */
	protected Identity parseIdentity(HttpServletRequest request) throws IOException {

		logger.info("Parsing Identity...");
		
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
