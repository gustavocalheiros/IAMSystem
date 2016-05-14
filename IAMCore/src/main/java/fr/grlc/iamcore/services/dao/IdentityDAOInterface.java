package fr.grlc.iamcore.services.dao;

import java.util.List;

import fr.grlc.iamcore.datamodel.Identity;

/**
 * Interface for the DAO
 * @author gustavo
 */
public interface IdentityDAOInterface {

	/**
	 * Read all the Identities
	 * @return Return all the Identities
	 */
	public List<Identity> readAll();
	
	/**
	 * Seach a particular Identity
	 * @param identity to be searched
	 * @return identity
	 */
	public List<Identity> search(Identity identity);
	
	/**
	 * Writes an Identity in the DB
	 * @param identity to be written
	 */
	public void write(Identity identity);
	
	/**
	 * Updates one Identity
	 * @param identity to be updated
	 */
	public void update(Identity identity);
	
	/**
	 * Deletes one identity
	 * @param identity to be deleted.
	 */
	public void delete(Identity identity);
}
