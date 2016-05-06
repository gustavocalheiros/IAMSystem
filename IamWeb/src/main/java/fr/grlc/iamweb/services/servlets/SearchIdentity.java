package fr.grlc.iamweb.services.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import fr.grlc.iamcore.services.dao.impl.IdentityHibernateDAO;
import fr.grlc.iamweb.services.spring.servlets.GenericSpringServlet;

/**
 * Servlet implementation class SearchIdentity
 */
@WebServlet("/SearchIdentity")
public class SearchIdentity extends GenericSpringServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	IdentityHibernateDAO dao;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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

		Identity id = new Identity(fname, lname, email, date);
		
		List<Identity> list = dao.search(id);

		JSONArray jsonArray = new JSONArray();
		
		for (Identity identity : list) {
			JSONObject idJson = new JSONObject();
			idJson.put("fname", identity.getFirstName());
			idJson.put("lname", identity.getLastName());
			idJson.put("email", identity.getEmail());
			idJson.put("birthdate", identity.getBirthDate());
			jsonArray.put(idJson);
		}

		PrintWriter out = response.getWriter();
		out.print(jsonArray);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Identity id = parseIdentity(request);
		
		List<Identity> list = dao.search(id);

		JSONArray jsonArray = new JSONArray();
		JSONObject status = new JSONObject();
		status.put("status", "200");
		
		jsonArray.put(status);
		
		
		for (Identity identity : list) {
			JSONObject idJson = new JSONObject();
			idJson.put("fname", identity.getFirstName());
			idJson.put("lname", identity.getLastName());
			idJson.put("email", identity.getEmail());
			idJson.put("birthdate", identity.getBirthDate());
			jsonArray.put(idJson);
		}

		PrintWriter out = response.getWriter();
		out.print(jsonArray);
	}

}
