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
import org.gridlab.gridsphere.core.persistence.castor.PersistenceManagerRdbms;
import org.gridlab.gridsphere.portlet.User;

import java.util.Date;
import java.util.List;
import java.util.Vector;

public class CredentialMap extends BaseObject {

    private User user = null;
    private String subject= null;
    private String tag = null;
    private String description = null;
    private Vector hosts = null;

    /**
     */
    public CredentialMap() {
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
    public CredentialMap(User user, String subject) {
        this.user = user;
        this.subject = subject;
        this.tag = user.getUserID();
        this.description = "";
        this.hosts = new Vector();
    }

    /**
     * Returns the portlet user to which this credential subject is mapped.
     *
     * @return <code>String</code> The portlet user id.
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Sets the portlet user to which this credential subject is mapped.
     *
     * @return <code>String</code> The portlet user id.
     */
    public void setUser(User user) {
        this.user = user;
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
     * Returns the credential subject in this map.
     *
     * @return <code>String</code> The credential subject.
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Returns the id used to retrieve the credential from a credential retrieval service.
     *
     * @return <code>String</code> The retrieval id.
     */
    public String getTag() {
        return this.tag;
    }

    /**
     * Sets the id used to retrieve the credential from a credential retrieval service.
     *
     * @param <code>String</code> The retrieval id.
     */
    public void setTag(String tag) {
        this.tag = tag;
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

    /**
     * Returns the list of hostnames to which this credential subject is mapped.
    /** 
     * Returns the list of hostnames to which this credential subject is mapped.
     *
     * @return <code>List</code> of <code>String</code> The hostnames.
     */
    public Vector getHosts() {
        return this.hosts;
    }
    

    public void setHosts(Vector hosts) {
        this.hosts = hosts;
    }

    /**
     * Adds the given hostname to the list of hostnames to which this credential subject
     * is mapped.
     *
     * @param <code>String</code> The hostname.
     */
    public void addHost(String host) {
        if (!this.hosts.contains(host)) {
            this.hosts.add(host);
        }
    }

    /** 
     * Adds the given list of hostnames to the list of hostnames to which this
     * credential subject is mapped.
     *
     * @param <code>List</code> of <code>String</code> The list of hostnames.
     */
    public void addHost(List hosts) {
        for (int ii = 0; ii < hosts.size(); ++ii) {
            String host = (String)hosts.get(ii);
            addHost(host);
        }
    }

    /** 
     * Removes the given hostname from the list of hostnames to which this credential
     * subject is mapped.
     *
     * @param <code>String</code> The hostname.
     */
    public void removeHost(String host) {
        if (this.hosts.contains(host)) {
            this.hosts.remove(host);
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
    public boolean hasHost(String host) {
        return (this.hosts.contains(host));
    }
}
