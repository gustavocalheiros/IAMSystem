package fr.grlc.iamcore.tests.dao;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.grlc.iamcore.datamodel.Identity;
import fr.grlc.iamcore.services.dao.IdentityDAOInterface;

/**
 * Unit test for the DAO 
 * 
 * @author gustavo
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"../application-context.xml"})
public class IdentityHibernateDAOTest {
	
	private final String fname = "Gustavo";
	private final String fname2 = "Gustavo2";
	private final String lname = "Calheiros";
	private final String email = "gustavo@epita.com";
	private Date birthdate = new Date();
	private Identity identity = new Identity(fname, lname, email, birthdate);
	
	@Autowired
	IdentityDAOInterface dao;


	@Before
	public void testSetupDB(){
		
		dao.write(identity);
	}
	
	@After
	public void testCleanUp(){
		try{
			dao.delete(identity);
		}
		catch (Exception e) {
		}
	}
	
	/**
	 * identity creation and search
	 */
	@Test
	public void testCreateSearchIdentity(){
		
		List<Identity> list = dao.search(identity);
		Identity id = list.get(0);
		
		Assert.assertEquals(identity.getEmail(), id.getEmail());
	}
	
	/**
	 * identity edition
	 */
	@Test
	public void testUpdateIdentity(){

		List<Identity> list = dao.search(identity);
		Identity id = list.get(0);

		id.setFirstName(fname2);
		dao.update(id);
		
		list = dao.search(id);
		id = list.get(0);

		Assert.assertEquals(id.getFirstName(), fname2);
		
		id.setFirstName(fname);
		dao.update(id);
	}
	
	/**
	 * identity deletion
	 */
	@Test
	public void testDeleteIdentity(){

		List<Identity> list = dao.search(identity);
		Identity id = list.get(0);

		dao.delete(id);
		
		list = dao.readAll();
		int size = list.size();

		Assert.assertEquals(size, 0);
	}
}
