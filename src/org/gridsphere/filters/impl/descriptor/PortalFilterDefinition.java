package org.gridsphere.filters.impl.descriptor;

import org.gridsphere.portlet.service.spi.impl.descriptor.ConfigParam;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
public class PortalFilterDefinition {

    private String name = "";
    private List descriptions = new Vector();
    private String implementation = "";
    private List configParamList = new Vector();
    private Properties configProps;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List descriptions) {
        this.descriptions = descriptions;
    }

    public String getImplementation() {
        return implementation;
    }

    public void setImplementation(String implementation) {
        this.implementation = implementation;
    }

    public List getConfigParamList() {
        return configParamList;
    }

    public void setConfigParamList(List configParamList) {
        this.configParamList = configParamList;
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
        if (configProps == null) createProperties();
        return configProps;
    }

}
