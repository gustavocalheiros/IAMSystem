package fr.grlc.iamcore.tests.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import fr.grlc.iamcore.services.dao.IdentityDAOInterface;

/**
 * Unit tests for Spring
 * @author gustavo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class) //This is to tell Junit to run with spring
@ContextConfiguration(locations={"classpath:application-context.xml"})
public class SpringTest {
	
	@Autowired
	IdentityDAOInterface dao;

	@Test
	public void springSetup(){
		Assert.notNull(dao);
	}

	@Test
	public void daoUsage(){
		System.out.println(dao.readAll());
	}
}
