/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
 * <p>
 * This class describes the relationship in GridSphere between a credential subject, a 
 * portlet user, and the hostnames for which the credential subject is known to 
 * map the given portlet user to a local account. Note that in GridSphere a credential subject 
 * can map to only one portlet user. See <code>CredentialManager</code> for more details on 
 * the usage of this class.
 */
package org.gridlab.gridsphere.core.security;

import org.gridlab.gridsphere.core.persistence.BaseObject;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerInterface;
import org.gridlab.gridsphere.core.persistence.castor.PersistenceManagerRdbms;

import java.util.Date;
import java.util.List;
import java.util.Vector;

public class CredentialMap extends BaseObject {

    private String subject= null;
    private String userID = null;
    private Vector hostNames = null;
    private String description = null;

    /**
     * The default constructor is private so that it can never be called. 
     */
    private CredentialMap() {
    }
    
    /** 
     * Constructs a <code>CredentialMap</code> with a credential subject and a user ID.
     * A credential subject can map to only one portlet user, therefore we establish 
     * this relationship in the construction of <code>CredentialMap</code> objects.
     * Furthermore, we prevent this relationship from being altered with set methods, so
     * that a <code>CredentialManager</code> need not include logic for establishing 
     * whether this relationship has changed when saving updates to <code>CredentialMap</code>
     * objects.
     *
     * @param <code>String</code> The credential subject.
     * @param <code>String</code> The portlet user id.
     */
    public CredentialMap(String subject, String userID) {
        this.subject = subject;
        this.userID = userID;
        this.hostNames = new Vector();
        this.description = "";
    }

    /** 
     * Returns the credential subject in this map.
     *
     * @return <code>String</code> The credential subject.
     */
    public String getSubject() {
        return this.subject;
    }

    /** 
     * Returns the portlet user id to which this credential subject is mapped.
     *
     * @return <code>String</code> The portlet user id.
     */
    public String getUserID() {
        return this.userID;
    }
    
    /** 
     * Returns the list of hostnames to which this credential subject is mapped.
     *
     * @return <code>List</code> of <code>String</code> The hostnames.
     */
    public List getHostNames() {
        return this.hostNames;
    }
    
    /** 
     * Adds the given hostname to the list of hostnames to which this credential subject
     * is mapped.
     *
     * @param <code>String</code> The hostname.
     */
    public void addHostName(String hostName) {
        if (!this.hostNames.contains(hostName)) {
            this.hostNames.add(hostName);
        }
    }

    /** 
     * Adds the given list of hostnames to the list of hostnames to which this
     * credential subject is mapped.
     *
     * @param <code>List</code> of <code>String</code> The list of hostnames.
     */
    public void addHostName(List hostNames) {
        for (int ii = 0; ii < hostNames.size(); ++ii) {
            String hostName = (String)hostNames.get(ii);
            addHostName(hostName);
        }
    }

    /** 
     * Removes the given hostname from the list of hostnames to which this credential
     * subject is mapped.
     *
     * @param <code>String</code> The hostname.
     */
    public void removeHostName(String hostName) {
        if (this.hostNames.contains(hostName)) {
            this.hostNames.remove(hostName);
        }
    }
    
    /** 
     * Returns true if the given hostname exists in the list of hostnames to which
     * this credential subject is mapped, false otherwise.
     *
     * @param <code>String</code> The hostname.
     *
     * @return <code>boolean</code>
     */
    public boolean hasHostName(String hostName) {
        return (this.hostNames.contains(hostName));
    }

    /** 
     * Returns a description of this credential mapping.
     *
     * @return <code>String</code> The description.
     */
    public String getDescription() {
        return this.description;
    }

    /** 
     * Sets the description of this credential mapping.
     *
     * @param <code>String</code> The description.
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
