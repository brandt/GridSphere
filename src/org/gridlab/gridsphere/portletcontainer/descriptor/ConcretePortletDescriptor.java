/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.portletcontainer.descriptor;

import java.util.ArrayList;
import java.util.List;

public class ConcretePortletDescriptor {

    private String id = new String();
    //private String name = new String();
    private List contextParamList = new ArrayList();
    private ConcretePortletInfo portletInfo = new ConcretePortletInfo();

    public ConcretePortletDescriptor() {
    }

    /**
     * gets the UID of a ConcretePortletDescriptor
     *
     * @returns UID of the ConcretePortletDescriptor
     */
    public String getID() {
        return id;
    }

    /**
     * Sets the ID of a ConcretePortletDescriptor
     *
     * @param id the ID of the ConcretePortletDescriptor
     */
    public void setID(String id) {
        this.id = id;
    }

    /**
     * Returns the description of the portlet
     *
     * @returns description of the portlet
     */
    public List getContextParamList() {
        return contextParamList;
    }

    /**
     * sets the name of the portlet
     *
     * @param description the description of the portlet
     */
    public void setContextParamList(ArrayList contextParamList) {
        this.contextParamList = contextParamList;
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

