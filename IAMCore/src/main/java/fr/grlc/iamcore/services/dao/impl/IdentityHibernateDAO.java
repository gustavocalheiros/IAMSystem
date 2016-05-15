package fr.grlc.iamcore.services.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import fr.grlc.iamcore.datamodel.Identity;
import fr.grlc.iamcore.querybuilder.QueryBuilderInterface;
import fr.grlc.iamcore.querybuilder.impl.IdentityQueryBuilder;
import fr.grlc.iamcore.services.dao.IdentityDAOInterface;

/**
 * Implementation of the DAO for Hibernate 
 * @author gustavo
 */
public class IdentityHibernateDAO implements IdentityDAOInterface {

	@Autowired
	public SessionFactory factory;
	
	@Autowired
	public QueryBuilderInterface builder;

	public void setBuilder(IdentityQueryBuilder builder) {
		this.builder = builder;
	}

	final static private Logger logger = Logger.getLogger(IdentityHibernateDAO.class);

	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}

	/**
	 * Returns all the entries in the DB.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Identity> readAll() {
		logger.info("readAll");
		Session session = factory.openSession();
		
		return session.createQuery("from Identity").list();
	}

	/**
	 * Searches for specific identity
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Identity> search(Identity identity) {

		logger.info("Searching Identiy: " + identity.getFirstName() + " " + identity.getLastName() + " : "
				+ identity.getEmail());

		Query q = builder.build(identity, factory.openSession());

		return q.list();
	}

	/**
	 * Writes an Identity in the DB
	 * @param identity to be written
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

	/**
	 * Updates one Identity
	 * @param identity to be updated
	 */
	@Override
	public void update(Identity identity) {
		logger.info("Updating Identiy: " + identity.getFirstName() + " " + identity.getLastName() + " : "
				+ identity.getEmail());
		
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		session.update(identity);
		transaction.commit();

	}

	/**
	 * Deletes one identity
	 * @param identity to be deleted.
	 */
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
