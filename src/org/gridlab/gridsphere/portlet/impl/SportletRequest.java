/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.impl;

import org.gridlab.gridsphere.portlet.*;

import java.util.List;

/**
 * A <code>SportletRequest</code> turns the immutable <code>PortletRequest</code>
 * into a stateful mutable object with all the additional set methods not defined
 * by the <code>PortletRequest</code> interface.
 */
public interface SportletRequest extends PortletRequest {

    /**
     * Sets the client device that the user connects to the portal with.
     *
     * @return the client device
     */
    public void setClient(Client client);

    /**
     * Sets the data of the concrete portlet instance
     * If the portlet is run in <code>CONFIGURE</code> mode, the portlet data
     * is not accessible and this method will return <code>null</code>
     *
     * @param portlet data the <code>PortletData</code>
     */
    public void setData(PortletData data);

    /**
     * Sets the roles this user has in the supplied <code>PortletGroup</code>.
     * If no group is specified, the roles the user has in the <code>BASE</code>
     * group are returned.
     *
     * @param group the PortletGroup to query the user's roles or null if
     * <code>BASE</code> group
     * @param roles an array of PortletRole objects
     *
     * @see PortletRole
     */
    public void setRoles(PortletGroup group, List roles);

    /**
     * Returns the roles this user has in the supplied <code>PortletGroup</code>.
     * If no group is specified, the roles the user has in the <code>BASE</code>
     * group are returned.
     *
     * @param group the <code>PortletGroup</code> to query the user's roles or null if
     * <code>BASE</code> group
     * @returns an array of <code>PortletRole</code> objects
     *
     * @see PortletRole
     */
    public List getRoles(PortletGroup group);

    /**
     * Returns the <code>PortletGroup</code> objects representing the users
     * group membership
     *
     * @returns an array of <code>PortletGroup</code> objects.
     * This method is guaranteed to at least return the <code>BASE</code> group.
     *
     * @see PortletGroup
     */
    public List getGroups();

    /**
     * Returns the <code>PortletGroup</code> objects representing the users group membership
     *
     * @param list a list of <code>PortletGroup</code> objects.
     *
     * @see PortletGroup
     */
    public void setGroups(List groups);

    /**
     * Sets the PortletSettings object of the concrete portlet.
     *
     * @param settings the portlet settings
     */
    public void setPortletSettings(PortletSettings settings);

    /**
     * Sets the mode that the portlet is running in.
     *
     * @param mode the portlet mode
     */
    public void setMode(Portlet.Mode mode);

    /**
     * Sets the previos portlet mode.
     *
     * @param the previous portlet mode
     */
    public void setPreviousMode(Portlet.Mode previousMode);

    /**
     * Returns the window that the portlet is running in.
     *
     * @return the portlet window
     */
    public void setWindow(PortletWindow window);

}
