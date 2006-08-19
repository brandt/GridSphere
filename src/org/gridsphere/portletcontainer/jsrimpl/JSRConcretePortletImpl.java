/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: JSRConcretePortletImpl.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.portletcontainer.jsrimpl;

import org.gridsphere.portlet.PortletLog;
import org.gridsphere.portlet.PortletSettings;
import org.gridsphere.portlet.impl.SportletLog;
import org.gridsphere.portletcontainer.ConcretePortlet;
import org.gridsphere.portletcontainer.ConcretePortletConfig;
import org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletDefinition;
import org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletDeploymentDescriptor2;
import org.gridsphere.services.core.persistence.PersistenceManagerException;

import java.io.IOException;
import java.util.Locale;

/**
 * The <code>ConcreteSportlet</code> is an implementation of the <code>ConcretePortlet</code> that provides methods
 * for accessing concrete portlet objects obtained from the portlet deployment descriptors.
 */
public class JSRConcretePortletImpl implements ConcretePortlet {

    private static PortletLog log = SportletLog.getInstance(JSRConcretePortletImpl.class);

    private PortletDeploymentDescriptor2 portletDD = null;
    private JSRConcretePortletConfigImpl concConfig = null;
    private String concreteID = null;
    private String portletName = null;

    /**
     * Constructs an instance of ConcreteSportlet
     *
     * @param pdd        a <code>PortletDeploymentDescriptor2</code>
     * @param portletDef a concrete portlet descriptor
     */
    public JSRConcretePortletImpl(PortletDeploymentDescriptor2 pdd, PortletDefinition portletDef, JSRConcretePortletConfigImpl concConfig, String webappName) {
        this.portletDD = pdd;
        this.concConfig = concConfig;
        portletName = portletDef.getPortletName().getContent();
        concreteID = webappName + "#" + portletName;
    }

    /**
     * Returns the sportlet settings for this concrete portlet
     *
     * @return the sportlet settings
     */
    public PortletSettings getPortletSettings() {
        return null;
    }

    /**
     * Return the concrete portlet configuration
     *
     * @return the concrete portlet configuration
     */
    public ConcretePortletConfig getConcretePortletConfig() {
        return concConfig;
    }

    /**
     * Sets the concrete portlet configuration
     *
     * @param concPortletConfig the concrete portlet configuration
     */
    public void setConcretePortletConfig(ConcretePortletConfig concPortletConfig) {
        this.concConfig = (JSRConcretePortletConfigImpl) concPortletConfig;
    }

    /**
     * Returns the concrete portlet id
     *
     * @return the concrete portlet id
     */
    public String getConcretePortletID() {
        return concreteID;
    }

    /**
     * Return the name of this portlet
     *
     * @return the portlet name
     */
    public String getPortletName() {
        return portletName;
    }

    public String getDescription(Locale locale) {
        return concConfig.getDescription(locale);
    }

    public String getDisplayName(Locale locale) {
        return concConfig.getDisplayName(locale);
    }

    /**
     * Saves any concrete portlet changes to the descriptor
     *
     * @throws IOException if an I/O error occurs
     */
    public void save() throws IOException {
        try {
            portletDD.save();
        } catch (PersistenceManagerException e) {
            throw new IOException("Unable to save JSR concrete portlet: " + e.getMessage());
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("\t name: " + portletName + "\n");
        sb.append("\t concrete ID: " + concreteID + "\n");
        return sb.toString();
    }

}
