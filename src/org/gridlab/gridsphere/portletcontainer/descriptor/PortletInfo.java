/*
 * @author <a href="mailto:wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.portletcontainer.descriptor;

import org.gridlab.gridsphere.portletcontainer.GridSphere;

public class PortletInfo {

    private String Href = new String();
    private String Id = new String();
    private String Name = new String();
    private String Description = new String();

    /**
     * gets the href of a PortletInfo
     *
     * @returns href
     */
    public String getHref() {
        return Href;
    }

    /**
     * sets the href of a portlet
     *
     * @param Href the Href of the portlet
     */
    public void setHref(String Href) {
        this.Href = Href;
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
    public void setId(String Id) {
        this.Id = Id;
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
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * Returns the description of the portlet
     *
     * @returns description of the portlet
     */
    public String getDescription() {
        return Description;
    }

    /**
     * sets the name of the portlet
     *
     * @param description the description of the portlet
     */
    public void setDescription(String Description) {
        this.Description = Description;
    }
}

