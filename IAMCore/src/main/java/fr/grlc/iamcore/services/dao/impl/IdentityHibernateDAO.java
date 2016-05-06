package fr.grlc.iamcore.services.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import fr.grlc.iamcore.datamodel.Identity;
import fr.grlc.iamcore.services.dao.IdentityDAOInterface;

public class IdentityHibernateDAO implements IdentityDAOInterface {

	@Autowired
	public SessionFactory factory;

	@Autowired
	@Qualifier("selectIdentityByEmail")
	public String selectIdentityByEmail;

	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}

	@Override
	public List<Identity> readAll() {

		Session session = factory.openSession();
		List<Identity> list = session.createQuery("from Identity").list();

		return list;
	}

	@Override
	public List<Identity> search(Identity identity) {
		// TODO WHERECLAUSEBUILDER adriana
		Session session = factory.openSession();
		String q = "from Identity identity where identity.email = :email and "
				+ "identity.firstName = :firstName and identity.lastName = :lastName and "
				+ "identity.birthDate = :birthDate";
		
		Query query = session.createQuery(q);
		
		query.setString("email", identity.getEmail());
		query.setString("firstName", identity.getFirstName());
		query.setString("lastName", identity.getLastName());
		query.setDate("birthDate", identity.getBirthDate());
		
		return query.list();
	}

	@Override
	public void write(Identity identity) {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		session.saveOrUpdate(identity);
		transaction.commit();
	}

	@Override
	public void update(Identity identity) {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		session.update(identity);
		transaction.commit();

	}

	@Override
	public void delete(Identity identity) {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		session.delete(identity);
		transaction.commit();
	}
}
