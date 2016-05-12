package fr.grlc.iamweb.services.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
 */
@WebServlet("/Login")
public class LoginServlet extends GenericSpringServlet {

	@Autowired
	IdentityDAOInterface dao;

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		getServletContext().getRequestDispatcher("/index.html").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		JSONObject json = new JSONObject(request.getParameter("data"));
		String user = json.getString("user");

		if (user == null)
			return;

		InputStream in = getClass().getClassLoader().getResourceAsStream("user.properties");
		Properties prop = new Properties();
		prop.load(in);
		in.close();

		String storedUser = prop.getProperty("user");
		String storedPassword = prop.getProperty("password");

		String password = json.getString("password");
		String passwordMd5 = getMd5(password);

		if (storedUser.equals(user) && storedPassword.equals(passwordMd5)) {
			HttpSession session = request.getSession();
			session.setAttribute(sessionUser, user);
			session.setMaxInactiveInterval(60 * 5); // TODO COMMENT

			JSONObject status = new JSONObject();
			status.put("status", "200");

			PrintWriter out = response.getWriter();
			out.print(status);
		}
	}

	private String getMd5(String password) {

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md.update(password.getBytes());

		byte byteData[] = md.digest();

		// convert the byte to hex format method 1
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}
}
