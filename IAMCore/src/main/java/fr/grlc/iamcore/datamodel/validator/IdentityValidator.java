package fr.grlc.iamcore.datamodel.validator;

import fr.grlc.iamcore.datamodel.Identity;

/**
 * Validates the Identity Fields
 *
 * @author gustavo
 */
public class IdentityValidator {
	
	/**
	 * Tests if the identity:
	 * has blank fields
	 * email has "@" and "."
	 * 
	 * @param id identity
	 * @return true if the Identity is valid
	 */
	public static boolean isValid(Identity id){
		if(id.getEmail().indexOf('@') == -1)
			return false;
		else if(id.getEmail().indexOf('.') == -1)
			return false;
		
		return true;
	}
}
