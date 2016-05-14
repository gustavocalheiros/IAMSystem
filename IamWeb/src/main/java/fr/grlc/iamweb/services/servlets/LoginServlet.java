package fr.grlc.iamweb.services.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import fr.grlc.iamcore.services.dao.IdentityDAOInterface;
import fr.grlc.iamweb.services.spring.servlets.GenericSpringServlet;

/**
 * Servlet implementation class LoginServlet
 * @author gustavo
 */
@WebServlet("/Login")
public class LoginServlet extends GenericSpringServlet {

	@Autowired
	private Integer timeExpirationSession;
	
	@Autowired
	IdentityDAOInterface dao;

	private static final long serialVersionUID = 1L;

	/**
	 * If logged in, redirects to home.html
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(!isLoggedIn(request))
			getServletContext().getRequestDispatcher(LOGIN_PAGE).forward(request, response);
		else
			getServletContext().getRequestDispatcher("/home.html").forward(request, response);
	}

	/**
	 * Handles the request and makes all the Login protocol
	 *
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		JSONObject json = new JSONObject(request.getParameter("data"));
		String user = json.getString("user");

		if (user == null || user.isEmpty())
			return;

		JSONObject status = new JSONObject();
		PrintWriter out = response.getWriter();
		
		// loads the information stored from the user
		InputStream in = getClass().getClassLoader().getResourceAsStream("user.properties");
		Properties prop = new Properties();
		prop.load(in);
		in.close();

		String storedUser = prop.getProperty("user");
		String storedPassword = prop.getProperty("password");

		String password = json.getString("password");
		String passwordMd5 = getMd5(password);

		//compares the info. if OK, create the session and login is OK!!
		if (storedUser.equals(user) && storedPassword.equals(passwordMd5)) {
			HttpSession session = request.getSession();
			session.setAttribute(sessionUser, user);
			session.setMaxInactiveInterval(timeExpirationSession);

			status.put("status", Integer.toString(HttpURLConnection.HTTP_OK));
		}else{
			
			status.put("status", Integer.toString(HttpURLConnection.HTTP_UNAUTHORIZED));
		}
		out.print(status);
	}

	/**
	 * Gets the MD5 hash for the parameter
	 * 
	 * @param password parameter
	 * @return MD5 hash
	 */
	private String getMd5(String password) {

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.toString());
		}
		md.update(password.getBytes());

		byte byteData[] = md.digest();

		// convert the byte to hex format
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}
}
