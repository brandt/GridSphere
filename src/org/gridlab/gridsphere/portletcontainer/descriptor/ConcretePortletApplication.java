/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.portletcontainer.descriptor;

import java.util.Vector;
import java.util.List;

public class ConcretePortletApplication {

    private String uid = new String();
    private String name = new String();
    private List configParamList = new Vector();
    private ConcretePortletInfo portletInfo = new ConcretePortletInfo();

    public ConcretePortletApplication() {}

    /**
     * gets the UID of a ConcretePortletApplication
     *
     * @returns UID of the ConcretePortletApplication
     */
    public String getUID() {
        return uid;
    }

    /**
     * sets the UID of a ConcretePortletApplication
     *
     * @param uid the UID of the ConcretePortletApplication
     */
    public void setUID(String uid) {
        this.uid = uid;
    }

    /**
     * gets the name of a PortletApplication
     *
     * @returns name of the PortletApplication
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name of a PortletApplication
     *
     * @param name name of a PortletApplication
     */
    public void setName(String name) {
        this.name = name;
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
     * gets the portletInfo of a PortletApplication
     *
     * @see portlet
     * @returns portletInfo of a PortletApplication
     */
    public ConcretePortletInfo getConcretePortletInfo() {
        return portletInfo;
    }


    /**
     * sets the portlet of a PortletApplication
     *
     * @see portlet
     * @param portletInfo the portlet of a PortletApplication
     */
    public void setConcretePortletInfo(ConcretePortletInfo portletInfo) {
        this.portletInfo = portletInfo;
    }
}

