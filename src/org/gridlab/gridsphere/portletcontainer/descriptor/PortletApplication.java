/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.portletcontainer.descriptor;

import org.gridlab.gridsphere.portlet.PortletLog;

public class PortletApplication {
    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletApplication.class);

    private String Uid = new String();                 // Uid of the Portletapplication
    private String Name = new String();                // Name of the
    private Portlet Portlet = new Portlet();            // Portlet

    /**
     * gets the Uid of a PortletApplication
     *
     * @returns Uid of the PortletApplication
     */
    public String getUid() {
        return Uid;
    }

    /**
     * sets the Uid of a PortletApplication
     *
     * @param uid uid of the PortletApplication
     */
    public void setUid(String uid) {
        this.Uid = uid;
    }

    /**
     * gets the name of a PortletApplication
     *
     * @returns name of the PortletApplication
     */
    public String getName() {
        return Name;
    }

    /**
     * sets the name of a PortletApplication
     *
     * @param name name of a PortletApplication
     */
    public void setName(String name) {
        this.Name = name;
    }

    /**
     * gets the portlet of a PortletApplication
     *
     * @see portlet
     * @returns portlet of a PortletApplication
     */
    public Portlet getPortlet() {
        return Portlet;
    }


    /**
     * sets the portlet of a PortletApplication
     *
     * @see portlet
     * @param portlet portlet of a PortletApplication
     */
    public void setPortlet(Portlet portlet) {
        this.Portlet = portlet;
    }
}

