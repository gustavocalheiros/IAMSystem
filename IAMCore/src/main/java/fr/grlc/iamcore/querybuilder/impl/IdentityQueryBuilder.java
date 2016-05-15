package fr.grlc.iamcore.querybuilder.impl;

import java.util.Date;

import org.hibernate.Query;
import org.hibernate.Session;

import fr.grlc.iamcore.datamodel.Identity;
import fr.grlc.iamcore.querybuilder.QueryBuilderInterface;

/**
 * Identity Implementation for the QueryBuilder 
 * @author gustavo
 */
public class IdentityQueryBuilder implements QueryBuilderInterface {

	public IdentityQueryBuilder() {
	}
	
	/**
	 * 	Build the query
	 * 
	 * @param identity The Identity for building the query
	 * @param session Current Session
	 * @return the query
	 */
	@Override
	public Query build(Object identity, Session session) {
		Identity iden = (Identity)identity;
		
		String q = "from Identity identity where ";
		int lenBeforeEdition = q.length();

		int id = iden.getId();
		String fname = iden.getFirstName();
		String lname = iden.getLastName();
		String email = iden.getEmail();
		Date date = iden.getBirthDate();
		String and = " and ";

		if (id != 0) {
			q += "identity.id = " + id;
			q += and;
		}

		if (fname != null && !fname.isEmpty()) {
			q += "identity.firstName = " + "\'" + fname + "\'";
			q += and;
		}

		if (lname != null && !lname.isEmpty()) {
			q += "identity.lastName = " + "\'" + lname + "\'";
			q += and;
		}

		if (email != null && !email.isEmpty()) {
			q += "identity.email = " + "\'" + email + "\'";
			q += and;
		}

		if (date != null) {
			q += "identity.birthDate = :birthDate";
		} else {
			int end = q.lastIndexOf(and);
			if (end == q.length() - and.length()) {
				q = q.substring(0, end);
			}
		}

		//Search with empty fields!
		if(q.length() == lenBeforeEdition){
			return null;
		}
		
		Query query = session.createQuery(q);

		if (date != null) {
			query.setDate("birthDate", iden.getBirthDate());
		}
		
		return query;
	}
}
