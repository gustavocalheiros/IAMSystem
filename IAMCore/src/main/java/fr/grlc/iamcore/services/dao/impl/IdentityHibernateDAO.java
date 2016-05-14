package fr.grlc.iamcore.services.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import fr.grlc.iamcore.datamodel.Identity;
import fr.grlc.iamcore.services.dao.IdentityDAOInterface;

/**
 * Implementation of the DAO for Hibernate 
 * @author gustavo
 */
public class IdentityHibernateDAO implements IdentityDAOInterface {

	@Autowired
	public SessionFactory factory;

	final static private Logger logger = Logger.getLogger(IdentityHibernateDAO.class);

	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}

	/**
	 * Returns all the entries in the DB.
	 */
	@Override
	public List<Identity> readAll() {
		logger.info("readAll");
		Session session = factory.openSession();
		@SuppressWarnings("unchecked")
		List<Identity> list = session.createQuery("from Identity").list();

		return list;
	}

	/**
	 * Searches for specific identity
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Identity> search(Identity identity) {

		logger.info("Searching Identiy: " + identity.getFirstName() + " " + identity.getLastName() + " : "
				+ identity.getEmail());

		Session session = factory.openSession();
		String q = "from Identity identity where ";
		int lenBeforeEdition = q.length();

		int id = identity.getId();
		String fname = identity.getFirstName();
		String lname = identity.getLastName();
		String email = identity.getEmail();
		Date date = identity.getBirthDate();
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
			query.setDate("birthDate", identity.getBirthDate());
		}

		return query.list();
	}

	/**
	 * 
	 */
	@Override
	public void write(Identity identity) {
		logger.info("Writing Identiy: " + identity.getFirstName() + " " + identity.getLastName() + " : "
				+ identity.getEmail());
		
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		session.saveOrUpdate(identity);
		transaction.commit();
	}

	@Override
	public void update(Identity identity) {
		logger.info("Updating Identiy: " + identity.getFirstName() + " " + identity.getLastName() + " : "
				+ identity.getEmail());
		
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		session.update(identity);
		transaction.commit();

	}

	@Override
	public void delete(Identity identity) {
		logger.info("Deleting Identiy: " + identity.getFirstName() + " " + identity.getLastName() + " : "
				+ identity.getEmail());
		
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		session.delete(identity);
		transaction.commit();
	}
}
