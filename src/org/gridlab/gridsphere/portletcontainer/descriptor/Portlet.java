/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.portletcontainer.descriptor;

import org.gridlab.gridsphere.portletcontainer.GridSphere;
import org.gridlab.gridsphere.portlet.PortletLog;

public class Portlet {
    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(Portlet.class);


    private String Href = new String();
    private String Id = new String();
    private String Name = new String();

    /**
     * gets the href of a Portlet
     *
     * @returns href
     */
    public String getHref() {
        return Href;
    }

    /**
     * sets the href of a portlet
     *
     * @param href href of the portlet
     */
    public void setHref(String href) {
        this.Href = href;
    }

    /**
     * gets the id of a portlet
     *
     * @return id of the portlet
     */
    public String getId() {
        return Id;
    }

    /**
     * sets the id of a portlet
     *
     * @param id id of the portlet
     */
    public void setId(String id) {
        this.Id = id;
    }


    /**
     * gets the name of the portlet
     *
     * @returns name of the portlet
     */
    public String getName() {
        return Name;
    }

    /**
     * sets the name of the portlet
     *
     * @param name name of the portlet
     */
    public void setName(String name) {
        this.Name = name;
    }
}

