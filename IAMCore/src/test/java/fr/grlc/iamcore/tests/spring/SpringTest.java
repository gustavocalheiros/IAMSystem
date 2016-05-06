//package fr.grlc.iamcore.tests.spring;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.util.Assert;
//
//import fr.grlc.iamcore.services.dao.IdentityDAOInterface;
//
//@RunWith(SpringJUnit4ClassRunner.class) //This is to tell Junit to run with spring
//@ContextConfiguration(locations={"../application-context.xml"}) // to tell spring to load the required context
//public class SpringTest {
//	@Autowired
//	IdentityDAOInterface dao;
//	//TODO testes
//	@Test
//	public void springSetup(){
//		Assert.notNull(dao);
//	}
//
//	@Test
//	public void daoUsage(){
//		System.out.println(dao.readAll());
//	}
//}
