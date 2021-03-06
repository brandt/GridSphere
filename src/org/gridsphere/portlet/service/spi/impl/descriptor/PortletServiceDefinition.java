/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.portlet.service.spi.impl.descriptor;

import java.util.*;

/**
 * The <code>SportletServiceDefinition</code> defines a portlet service
 * definition that is defined in the portlet service descripor.
 */
public class PortletServiceDefinition {

    protected String serviceName = "";
    protected List serviceDescriptions = new Vector();
    protected String serviceInterface = "";
    protected String serviceImplementation = "";
    protected boolean userRequired = false;
    protected List<ConfigParam> configParamList = new Vector<ConfigParam>();
    protected Properties configProps = null;
    protected boolean loadOnStartup = false;

    /**
     * Sets the portlet service name
     *
     * @param serviceName the portlet service name
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * Returns the portlet service name
     *
     * @return the portlet service name
     */
    public String getServiceName() {
        return this.serviceName;
    }

    /**
     * Sets the portlet service descriptions
     *
     * @param serviceDescriptions the list of portlet service descriptions
     */
    public void setServiceDescription(List serviceDescriptions) {
        this.serviceDescriptions = serviceDescriptions;
    }

    /**
     * Returns the portlet service descriptions
     *
     * @return the portlet service descriptions
     */
    public List getServiceDescription() {
        return this.serviceDescriptions;
    }

    /**
     * Sets the portlet service interface
     *
     * @param serviceInterface the portlet service interface
     */
    public void setServiceInterface(String serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    /**
     * Returns the portlet service interface
     *
     * @return the portlet service interface
     */
    public String getServiceInterface() {
        return this.serviceInterface;
    }

    /**
     * Returns the portlet service implementation
     *
     * @return the portlet service implementation
     */
    public String getServiceImplementation() {
        return this.serviceImplementation;
    }

    /**
     * Sets the portlet service implementation
     *
     * @param serviceImplementation the portlet service implementation
     */
    public void setServiceImplementation(String serviceImplementation) {
        this.serviceImplementation = serviceImplementation;
    }

    /**
     * Determines if this service is a user service or not
     *
     * @param userRequired if <code>true</code> then portlet service is
     *                     an instance of a user service, <code>false</code> otherwise
     */
    public void setUserRequired(boolean userRequired) {
        this.userRequired = userRequired;
    }

    /**
     * Determines if this service is a user service or not
     *
     * @return <code>true</code> if portlet service is
     *         an instance of a user service, <code>false</code> otherwise
     */
    public boolean getUserRequired() {
        return this.userRequired;
    }

    /**
     * Sets the service configuration parameter list
     *
     * @param configParamList the configuration parameter list
     */
    public void setConfigParamList(List<ConfigParam> configParamList) {
        this.configParamList = configParamList;
    }

    /**
     * Returns the service configuration parameter list
     *
     * @return the configuration parameter list
     */
    public List<ConfigParam> getConfigParamList() {
        return this.configParamList;
    }

    /**
     * Creates a properties from the  ConfigParamList
     *
     * @see #getConfigParamList
     */
    private void createProperties() {
        configProps = new Properties();
        Iterator it = this.configParamList.iterator();
        ConfigParam param;
        while (it.hasNext()) {
            param = (ConfigParam) it.next();
            configProps.setProperty(param.getParamName(), param.getParamValue());
        }
    }

    /**
     * Return the configuration properties
     *
     * @return the configuration properties
     */
    public Properties getConfigProperties() {
        if (configProps == null)
            createProperties();
        return configProps;
    }

    /**
     * Sets the configuration properties
     *
     * @param props the configuration properties
     */
    public void setConfigProperties(Properties props) {
        Enumeration e = props.keys();
        if (!props.isEmpty()) {
            configParamList = new Vector<ConfigParam>();
        }
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            ConfigParam param = new ConfigParam(key, props.getProperty(key));
            configParamList.add(param);
        }
    }

    /**
     * Returns true if this service should be initialized on startup
     *
     * @return true if this service should be initialized on startup
     */
    public boolean isLoadOnStartup() {
        return loadOnStartup;
    }

    /**
     * Sets whether this service should be initialized on startup
     *
     * @param loadOnStartup true if this service should be initialized on startup
     */
    public void setLoadOnStartup(boolean loadOnStartup) {
        this.loadOnStartup = loadOnStartup;
    }

    /**
     * Returns a <code>String</code> representation if this portlet service
     * definition
     *
     * @return the service definition as a <code>String</code>
     */
    public String toString() {
        StringBuffer sb = new StringBuffer("\n");
        sb.append("service name: ").append(serviceName).append("\n");
        sb.append("service description: ").append(this.serviceDescriptions.get(0)).append("\n");
        sb.append("service interface: ").append(this.serviceInterface).append("\n");
        sb.append("service implementation: ").append(this.serviceImplementation).append("\n");
        sb.append("user required: ").append(this.userRequired).append("\n");
        sb.append("load on startup: ").append(this.loadOnStartup).append("\n");
        sb.append("config properties: ");
        Iterator it = this.configParamList.iterator();
        ConfigParam c;
        while (it.hasNext()) {
            c = (ConfigParam) it.next();
            sb.append("\tname: ").append(c.getParamName()).append("\tvalue: ").append(c.getParamValue());
        }
        return sb.toString();
    }

}
