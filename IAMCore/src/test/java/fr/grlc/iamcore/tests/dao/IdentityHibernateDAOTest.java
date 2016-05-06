//package fr.grlc.iamcore.tests.dao;
//
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import fr.grlc.iamcore.datamodel.Identity;
//import fr.grlc.iamcore.services.dao.impl.IdentityHibernateDAO;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"../application-context.xml"})
//public class IdentityHibernateDAOTest {
//	
//	@Autowired
//	IdentityHibernateDAO dao;
//
//	@Test
//	public void testCreateRead(){
//		
//		//TODO MAKE MORE TESTS AND SEPARATE
//		
//		Identity newIdentity = new Identity("Jean-Luc", "Tholozan", "jltho@gmail.com");
//		newIdentity.setBirthDate(new java.util.Date());
//		dao.write(newIdentity);
//
//		List<Identity> identityList = dao.readAll();
//		System.out.println(identityList);
//		Identity toBeUpdated = identityList.get(0);
//		toBeUpdated.setFirstName("Jeremie");
//		dao.update(toBeUpdated);
//		System.out.println(dao.readAll());
//	}
//
//
//		
//}
