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

import org.gridlab.gridsphere.portlet.User;

import java.util.List;

public interface CredentialMapping {

    /**
     */
    public User getUser();

    /**
     */
    public void setUser(User user);

    /**
     */
    public String getSubject();

    /**
     */
    public void setSubject(String subject);

    /**
     */
    public String getTag();

    /**
     */
    public void setTag(String tag);

    /**
     */
    public String getDescription();

    /**
     */
    public void setDescription(String description);

    /**
     */
    public List getHosts();

    /**
     */
    public void addHost(String host);

    /** 
     */
    public void addHost(List hosts);

    /** 
     */
    public void removeHost(String host);

    /** 
     */
    public boolean hasHost(String host);
}
