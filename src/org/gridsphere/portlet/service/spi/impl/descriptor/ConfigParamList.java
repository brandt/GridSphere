/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: ConfigParamList.java 4496 2006-02-08 20:27:04Z wehrens $
 */

package org.gridsphere.portlet.service.spi.impl.descriptor;

import java.util.*;

/**
 * A <code>ConfigParamList</code> is a container for <code>ConfigParam</code>
 * elements that provides a <code>Hashtable</code> view.
 */
public class ConfigParamList {

    private List<ConfigParam> configList = new ArrayList<ConfigParam>();
    private Hashtable<String, String> configHash = new Hashtable<String, String>();

    public ConfigParamList() {
    }

    /**
     * Constructs an instance of ConfigParamList
     *
     * @param configList the list of configuration parameters
     */
    public ConfigParamList(List<ConfigParam> configList) {
        this.configList = configList;
    }

    public void setConfigParamList(ArrayList<ConfigParam> configList) {
        this.configList = configList;
    }

    public List<ConfigParam> getConfigParamList() {
        return configList;
    }

    public void setConfigParams(Hashtable<String, String> configHash) {
        this.configHash = configHash;
        this.createConfigList();
    }

    public Hashtable<String, String> getConfigParams() {
        this.createConfigHash();
        return configHash;
    }

    /**
     * Used internally to convert a <code>ConfigParam</code> list to
     * a <code>Hashtable</code> used by clients
     */
    protected void createConfigHash() {
        configHash = new Hashtable<String, String>();
        for (ConfigParam configParam : configList) {
            configHash.put(configParam.getParamName(), configParam.getParamValue());
        }
    }

    /**
     * Used internally to convert a <code>Hashtable</code> to
     * a <code>ConfigParam</code> list used by Castor
     */
    protected void createConfigList() {
        configList = new ArrayList<ConfigParam>();
        Enumeration e = configHash.keys();
        ConfigParam configParam = null;
        String paramName = null;
        String paramVal = null;
        while (e.hasMoreElements()) {
            paramName = (String) e.nextElement();
            paramVal = (String) configHash.get(paramName);
            configParam = new ConfigParam(paramName, paramVal);
            configList.add(configParam);
        }
    }


}

