/*
 * Created by IntelliJ IDEA.
 * User: novotny
 * Date: Dec 13, 2002
 * Time: 1:58:26 PM
 * To change template for new interface use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.portlet.impl;

import org.gridlab.gridsphere.portlet.*;

public interface SportletRequest extends PortletRequest {

    /**
     * Sets the client device that the user connects to the portal with.
     *
     * @return the client device
     */
    public void setClient(Client client);

    /**
     * Sets the data of the concrete portlet instance
     * If the portlet is run in CONFIGURE mode, the portlet data is not accessible and this method will return null.
     *
     * @param portlet data the PortletData
     */
    public void setData(PortletData data);

    /**
     * Sets the roles this user has in the supplied PortletGroup. If no group
     * is specified, the roles the user has in the BASE group are returned.
     *
     * @param group the PortletGroup to query the user's roles or null if BASE group
     * @param roles an array of PortletRole objects
     *
     * @see PortletRole
     */
    public void setRoles(PortletGroup group, PortletRole[] roles);

    /**
     * Returns the PortletGroup objects representing the users group membership
     *
     * @returns an array of PortletGroup objects. This method is guaranteed to at least
     * return the PortletGroup.BaseGroup
     *
     * @see PortletGroup
     */
    public PortletGroup[] getGroups();

    /**
     * Sets the PortletSettings object of the concrete portlet.
     *
     * @param settings the portlet settings
     */
    public void setPortletSettings(PortletSettings settings);

    /**
     * Returns the mode that the portlet is running in.
     *
     * @return the portlet mode
     */
    public void setMode(Portlet.Mode mode);

    /**
     * Returns the mode that the portlet was running at last, or null if no previous mode exists.
     *
     * @return the previous portlet mode
     */
    public void setPreviousMode(Portlet.Mode previousMode);

    /**
     * Returns the window that the portlet is running in.
     *
     * @return the portlet window
     */
    public void setWindow(PortletWindow window);

}
