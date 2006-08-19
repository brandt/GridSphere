/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: ConcreteSportletDefinition.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.portletcontainer.impl.descriptor;

import org.gridsphere.portlet.service.spi.impl.descriptor.ConfigParamList;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * A <code>ConcreteSportletDefinition</code> defines the concrete portlet id,
 * any concrete portlet context attributes and a
 * <code>ConcretePortletConfig</code>.
 */
public class ConcreteSportletDefinition {

    private String concretePortletID;
    private ConfigParamList contextParamList = new ConfigParamList();
    private ConcreteSportletConfig concSportletConfig = new ConcreteSportletConfig();

    /**
     * Constructs an instance of ConcreteSportletDefinition
     */
    public ConcreteSportletDefinition() {
    }

    /**
     * Sets the concrete portlet id
     *
     * @param concretePortletID the concrete portlet id
     */
    public void setConcretePortletID(String concretePortletID) {
        this.concretePortletID = concretePortletID;
    }

    /**
     * Returns the concrete portlet id
     *
     * @return the concrete portlet id
     */
    public String getConcretePortletID() {
        return concretePortletID;
    }

    /**
     * Used internally by Castor. Clients should use #getPortletContextHash
     * <p/>
     * Returns the map of portlet context parameters that are used in the
     * PortletConfig class
     *
     * @return the portlet context parameters as a <code>List</code> with
     *         <code>ConfigParam</code> elements
     */
    public List getPortletContextList() {
        return this.contextParamList.getConfigParamList();
    }

    /**
     * Used internally by Castor.
     * <p/>
     * Sets the portlet context parameters
     *
     * @param contextList an <code>ArrayList</code> containing
     *                    <code>ConfigParam</code> elements
     */
    public void setPortletContextList(ArrayList contextList) {
        this.contextParamList.setConfigParamList(contextList);
    }

    /**
     * Returns the <code>Hashtable</code> of portlet context parameters
     * that are used in the PortletConfig class
     *
     * @return the portlet context parameters
     */
    public Hashtable getContextAttributes() {
        return contextParamList.getConfigParams();
    }

    /**
     * Sets the <code>Hashtable</code> of portlet context parameters
     * that are used in the PortletConfig class
     *
     * @param contextHash the portlet context parameters
     */
    public void setContextAttributes(Hashtable contextHash) {
        this.contextParamList.setConfigParams(contextHash);
    }

    /**
     * Sets the concrete portlet configuration
     *
     * @param concSportletConfig the concrete portlet configuration
     */
    public void setConcreteSportletConfig(ConcreteSportletConfig concSportletConfig) {
        this.concSportletConfig = concSportletConfig;
    }

    /**
     * Returns the concrete portlet configuration
     *
     * @return the concrete portlet configuration
     */
    public ConcreteSportletConfig getConcreteSportletConfig() {
        return concSportletConfig;
    }

}
