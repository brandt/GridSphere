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

public class ConcretePortletInfo {

    private String href = new String();
    private String defaultLocale = new String();
    private List languageList = new Vector();
    private String name = new String();
    private String Description = new String();
    private List groupList = new Vector();
    private List roleList = new Vector();

    /**
     * gets the href of a PortletInfo
     *
     * @returns href
     */
    public String getHref() {
        return href;
    }

    /**
     * sets the href of a portlet
     *
     * @param href the Href of the portlet
     */
    public void setHref(String href) {
        this.href = href;
    }

    /**
     * gets the default locale of a portlet
     *
     * @return the default locale of the portlet
     */
    public String getDefaultLocale() {
        return defaultLocale;
    }

    /**
     * Sets the default locale of a portlet
     *
     * @param defaultLocale the default locale of the portlet
     */
    public void setDefaultLocale(String defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    /**
     * Returns the language info of a portlet
     *
     * @return language info of the portlet
     */
    public List getLanguageList() {
        return languageList;
    }

    /**
     * sets the language info of a portlet
     *
     * @param languageInfo the language info of the portlet
     */
    public void setLanguageList(Vector languageList) {
        this.languageList = languageList;
    }

    /**
     * gets the name of the portlet
     *
     * @returns name of the portlet
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name of the portlet
     *
     * @param name name of the portlet
     */
    public void setName(String name) {
        this.name = name;
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

