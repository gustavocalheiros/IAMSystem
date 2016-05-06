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

import org.json.JSONObject;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import fr.grlc.iamcore.datamodel.Identity;

/**
 * Servlet implementation class GenericSpringServlet
 */
public class GenericSpringServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//LINK THE WEBCONTEXT WITH THE SPRING CONTEXT
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}
	
	public Identity parseIdentity(HttpServletRequest request) throws IOException{

		String str = "";
		BufferedReader reader = request.getReader();
		String part = "";
		while (part !=  null)
		{
			str += part;
			part = reader.readLine();
		}
		
		JSONObject json = new JSONObject(str);
		
		String fname = json.getString("fname");
		String lname = json.getString("lname");
		String email = json.getString("email");
		String dateStr = json.getString("birthdate");
		
		dateStr = dateStr.replace("-", "/");
		
		DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		
		Date date = null;
		try {
			date = (Date)formatter.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return new Identity(fname, lname, email, date);
	}
}
