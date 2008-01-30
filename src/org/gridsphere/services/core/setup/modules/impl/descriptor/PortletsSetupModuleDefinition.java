/**
 * @author <a href="mailto:docentt@man.poznan.pl">Tomasz Kuczynski</a>, PSNC
 * @version $Id$
 */
package org.gridsphere.services.core.setup.modules.impl.descriptor;

import org.gridsphere.portlet.service.spi.impl.descriptor.ConfigParam;

import java.util.*;

/**
 * The <code>PortletsSetupModuleDefinition</code> defines
 */
public class PortletsSetupModuleDefinition {
    protected PortletsSetupModulesDescriptor portletsSetupDescriptor = null;
//    private String oid = null;
    protected String moduleName = "";

    private List moduleDescriptions = new Vector();
    private List moduleErrors = new Vector();

    protected int priority = 100;
    protected String moduleImplementation = "";
    protected String portletName = "";
    protected String defaultJSP = "";
    protected String contextName = "";
    protected boolean moduleActive = false;
    protected boolean preInitModule = false;
    protected boolean postInitModule = false;

    protected List configParamList = new Vector();
    protected Map attributes = new HashMap();
    protected Properties configProps = null;

/*
    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }
*/

    /**
     * Sets the portlets setup module descriptor
     *
     * @param descriptor the portlets setup module descriptor
     */
    public void setDescriptor(PortletsSetupModulesDescriptor descriptor) {
        this.portletsSetupDescriptor = descriptor;
    }

    /**
     * Returns the portlets setup module descriptor
     *
     * @return descriptor the portlets setup module descriptor
     */
    public PortletsSetupModulesDescriptor getDescriptor() {
        return portletsSetupDescriptor;
    }

    /**
     * Sets the portlets setup module name
     *
     * @param moduleName the portlets setup module name
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * Returns the portlets setup module name
     *
     * @return the portlets setup module name
     */
    public String getModuleName() {
        return this.moduleName;
    }

    /**
     * Returns the name of portlet which the module is for (if null or empty invoke method of module will receive null PortletDefinition or null Portlet object as a parameter)
     *
     * @return the name of the portlet
     */
    public String getPortletName() {
        return portletName;
    }

    /**
     * Sets the name of portlet which the module is for (if null or empty invoke method of module will receive null PortletDefinition or null Portlet object as a parameter)
     *
     * @param portletName the name of the portlet
     */
    public void setPortletName(String portletName) {
        this.portletName = portletName;
    }


    /**
     * Returns the default JSP location used by the module
     *
     * @return the default JSP location
     */
    public String getDefaultJSP() {
        return defaultJSP;
    }

    /**
     * Sets the default JSP location used by the module
     *
     * @param defaultJSP the default JSP location
     */
    public void setDefaultJSP(String defaultJSP) {
        this.defaultJSP = defaultJSP;
    }

    /**
     * Returns the context name
     *
     * @return the context name
     */
    public String getContextName() {
        return contextName;
    }

    /**
     * Sets the context name
     *
     * @param contextName context name
     */
    protected void setContextName(String contextName) {
        this.contextName = contextName;
    }

    /**
     * Sets the list of module descriptions
     *
     * @param moduleDescriptions the list of module descriptions
     */
    public void setModuleDescriptions(List moduleDescriptions) {
        this.moduleDescriptions = moduleDescriptions;
    }

    /**
     * Returns the module descriptions
     *
     * @return the module descriptions
     */
    public List getModuleDescriptions() {
        return this.moduleDescriptions;
    }

    /**
     * Sets the list of module errors
     *
     * @param moduleErrors the list of module errors
     */
    public void setModuleErrors(List moduleErrors) {
        this.moduleErrors = moduleErrors;
    }

    /**
     * Returns the module errors
     *
     * @return the module errors
     */
    public List getModuleErrors() {
        return this.moduleErrors;
    }

    /**
     * Returns the portlet service implementation
     *
     * @return the portlet service implementation
     */
    public String getModuleImplementation() {
        return this.moduleImplementation;
    }

    /**
     * Sets the portlets setup module implementation
     *
     * @param moduleImplementation the portlets setup module implementation
     */
    public void setModuleImplementation(String moduleImplementation) {
        this.moduleImplementation = moduleImplementation;
    }

    /**
     * Returns the module priority
     *
     * @return the module priority
     */
    public int getModulePriority() {
        return priority;
    }

    /**
     * Sets the module priority
     *
     * @param priority priority
     */
    public void setModulePriority(int priority) {
        this.priority = priority;
    }

    /**
     * Returns true of this module is turned on for all users
     *
     * @return true of this module is turned on for all users
     */
    public boolean getModuleActive() {
        return moduleActive;
    }

    /**
     * If true, this module will be active for all users
     *
     * @param moduleActive if true, this module will be active for all users
     */
    public void setModuleActive(boolean moduleActive) {
        this.moduleActive = moduleActive;
    }

   /**
     * Returns true if this module is pre portlets initialization module
     *
     * @return true if this module is pre portlets initialization module
     */            
    public boolean getPreInitModule() {
        return preInitModule;
    }

    /**
     * If true, this module will be pre portlets initialization module
     *
     * @param preInitModule if true, this module will be pre portlets initialization module
     */
    public void setPreInitModule(boolean preInitModule) {
        this.preInitModule = preInitModule;
    }

   /**
     * Returns true if this module is post portlets initialization module
     *
     * @return true if this module is post portlets initialization module
     */
    public boolean getPostInitModule() {
        return postInitModule;
    }

    /**
     * If true, this module will be post portlets initialization module
     *
     * @param postInitModule if true, this module will be post portlets initialization module
     */
    public void setPostInitModule(boolean postInitModule) {
        this.postInitModule = postInitModule;
    }

    /**
     * Sets the service configuration parameter list
     *
     * @param configParamList the configuration parameter list
     */
    public void setConfigParamList(List configParamList) {
        this.configParamList = configParamList;
    }

    /**
     * Returns the service configuration parameter list
     *
     * @return the configuration parameter list
     */
    public List getConfigParamList() {
        return this.configParamList;
    }

    public String getAttribute(String name) {
        return (String) attributes.get(name);
    }

    public Map getAttributes() {
        return attributes;
    }

    public void setAttributes(Map attributes) {
        this.attributes = attributes;
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
            configParamList = new Vector();
        }
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            ConfigParam param = new ConfigParam(key, props.getProperty(key));
            configParamList.add(param);
        }
    }

    /**
     * Returns a <code>String</code> representation of this portlets setup module
     * definition
     *
     * @return the portlets setup module definition as a <code>String</code>
     */
    public String toString() {
        StringBuffer sb = new StringBuffer("\n");
        sb.append("portlets setup module name: " + this.moduleName + "\n");
        sb.append("portlets setup module description: " + this.moduleDescriptions.get(0) + "\n");
        sb.append("portlets setup module implementation: " + this.moduleImplementation + "\n");
        if(null != portletName)
            sb.append("portlets setup module portlet name: " + this.portletName + "\n");
        sb.append("portlets setup module context: " + this.contextName + "\n");
        sb.append("portlets setup module priority: " + this.priority + "\n");
        sb.append("portlets setup module pre portlets initialization : " + this.preInitModule + "\n");
        sb.append("portlets setup module post portlets initialization : " + this.postInitModule + "\n");
        sb.append("config properties: ");
        Iterator it = this.configParamList.iterator();
        ConfigParam c;
        while (it.hasNext()) {
            c = (ConfigParam) it.next();
            sb.append("\tname: " + c.getParamName() + "\tvalue: " + c.getParamValue());
        }
        return sb.toString();
    }
}
