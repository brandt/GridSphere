/*
 * @author <a href="mailto:wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.portletcontainer.descriptor;

import org.gridlab.gridsphere.portletcontainer.GridSphere;

import java.util.Vector;
import java.util.List;

public class PortletInfo {

    private String Href = new String();
    private String Id = new String();
    private String Name = new String();
    private String Description = new String();
    private List configParamList = new Vector();
    private List groupList = new Vector();
    private List roleList = new Vector();

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

    /**
     * Returns the description of the portlet
     *
     * @returns description of the portlet
     */
    public List getConfigParamList() {
        return configParamList;
    }

    /**
     * sets the name of the portlet
     *
     * @param description the description of the portlet
     */
    public void setConfigParamList(Vector configParamList) {
        this.configParamList = configParamList;
    }

    /**
     * Returns the List of groups allowed access to this portlet
     *
     * @return vector of groups allowed access to this portlet
     */
    public List getGroupList() {
        return groupList;
    }

    /**
     * Sets the Vector of groups allowed access to this portlet
     *
     * @param groupList the Vector of groups allowed access to this portlet
     */
    public void setGroupList(Vector groupList) {
        this.groupList = groupList;
    }

    /**
     * Returns the List of roles allowed access to this portlet
     *
     * @return vector of roles allowed access to this portlet
     */
    public List getRoleList() {
        return roleList;
    }

    /**
     * Sets the Vector of roles allowed access to this portlet
     *
     * @param groupList the Vector of roles allowed access to this portlet
     */
    public void setRoleList(Vector roleList) {
        this.roleList = roleList;
    }

}

