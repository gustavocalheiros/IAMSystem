package fr.grlc.iamcore.tests.spring;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * Unit tests for Hibernate
 * @author gustavo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class) //This is to tell Junit to run with spring
@ContextConfiguration(locations={"classpath:application-context.xml"})
public class SpringHibernateTest {
	
	@Autowired
	DataSource ds;
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Test
	public void springSetup(){
		Assert.notNull(ds);
	}

	@Test
	public void dataSourceUsage() throws SQLException{
		Connection connection = ds.getConnection();
		Assert.notNull(connection.getSchema());
		connection.close();
	}
	
	@Test
	public void sessionNotNull() throws SQLException{
		Assert.notNull(sessionFactory);
	}
}
