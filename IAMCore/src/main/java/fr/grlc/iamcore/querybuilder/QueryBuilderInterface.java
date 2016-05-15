package fr.grlc.iamcore.querybuilder;

import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Interface for building queries
 * @author gustavo
 *
 */
public interface QueryBuilderInterface {
	
	/**
	 * 	Build the query
	 * 
	 * @param obj Object for building the query
	 * @param session Session
	 * @return the query
	 */
	Query build(Object obj, Session session);
}
