package org.gridsphere.services.core.shibboleth;

import java.util.List;

import org.gridsphere.portlet.service.PortletService;

public interface ShibbolethUserService extends PortletService {

	/**
	 *  adds / saves a user
	 *  @param uses a supplied ShibbolethUser object
	 */
	public void saveShibUser(ShibbolethUser shibUser);

	/**
	 *  delets a Shibbolets User
	 *  @param shibUser the shibUser
	 */
	public void deleteShibUser(ShibbolethUser shibUser);

	/**
	 *  Retrieves a shibbolethuser object with the id of the shibbolethuser
	 *  @param id the objectid of the shibbolethuser in question
	 *  @return shibbolethuserobject in question
	 */
	public ShibbolethUser getShibUser(String id);
	
	/**
	 *  Retrieves a list of Shibboleth Users in an unsorted list
	 *  @return a list of shibbolethusers
	 */
	public List<ShibbolethUser> getShibUser();

	/**
    *  Retrieves a shibbolethuser object with the given username
    *  @param loginname the username of the user in question
    *  @return shibbolethuserobject in question
    */
   public ShibbolethUser getShibUserByUserName(String loginname);

	/**
     * Retrieves the number of Shibboleth users.
     *
     * @return number of users.
     */
    public int getNumShibUsers();
    
}
