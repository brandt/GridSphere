/*
 * @author <a href="mailto:wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.portletcontainer.descriptor;

import java.util.ArrayList;
import java.util.List;

public class ConcretePortletInfo {

    private String defaultLocale = new String();
    private List languageList = new ArrayList();
    private String name = new String();
    private String Description = new String();
    private Owner owner = new Owner();
    private List groupList = new ArrayList();
    private List roleList = new ArrayList();
    private List configParamList = new ArrayList();

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
    public void setLanguageList(ArrayList languageList) {
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
     * Returns the Owner defining who has authorization to configure this portlet
     *
     * @return owner the Owner defining who has authorization to configure this portlet
     */
    public Owner getOwner() {
        return owner;
    }

    /**
     * Sets the Owner defining who has authorization to configure this portlet
     *
     * @param owner the Owner defining who has authorization to configure this portlet
     */
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * Returns the List of groups allowed access to this portlet
     *
     * @return ArrayList of groups allowed access to this portlet
     */
    public List getGroupList() {
        return groupList;
    }

    /**
     * Sets the ArrayList of groups allowed access to this portlet
     *
     * @param groupList the ArrayList of groups allowed access to this portlet
     */
    public void setGroupList(ArrayList groupList) {
        this.groupList = groupList;
    }

    /**
     * Returns the List of roles allowed access to this portlet
     *
     * @return ArrayList of roles allowed access to this portlet
     */
    public List getRoleList() {
        return roleList;
    }

    /**
     * Sets the ArrayList of roles allowed access to this portlet
     *
     * @param groupList the ArrayList of roles allowed access to this portlet
     */
    public void setRoleList(ArrayList roleList) {
        this.roleList = roleList;
    }

    /**
     * Returns the configuration parameters of the portlet
     *
     * @returns the configuration parameters of the portlet
     */
    public List getConfigParamList() {
        return configParamList;
    }

    /**
     * Sets the configuration parameters of the portlet
     *
     * @param the configuration parameters of the portlet
     */
    public void setConfigParamList(ArrayList configParamList) {
        this.configParamList = configParamList;
    }

}

