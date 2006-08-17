/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: ConfigParamList.java 4496 2006-02-08 20:27:04Z wehrens $
 */

package org.gridlab.gridsphere.portlet.service.spi.impl.descriptor;

import java.util.*;

/**
 * A <code>ConfigParamList</code> is a container for <code>ConfigParam</code>
 * elemts that provies a <code>Hashtable</code> view.
 */
public class ConfigParamList {

    private List configList = new ArrayList();
    private Hashtable configHash = new Hashtable();

    public ConfigParamList() {
    }

    /**
     * Constructs an instance of ConfigParamList
     */
    public ConfigParamList(List configList) {
        this.configList = configList;
    }

    public void setConfigParamList(ArrayList configList) {
        this.configList = configList;
    }

    public List getConfigParamList() {
        return configList;
    }

    public void setConfigParams(Hashtable configHash) {
        this.configHash = configHash;
        this.createConfigList();
    }

    public Hashtable getConfigParams() {
        this.createConfigHash();
        return configHash;
    }

    /**
     * Used internally to convert a <code>ConfigParam</code> list to
     * a <code>Hashtable</code> used by clients
     */
    protected void createConfigHash() {
        configHash = new Hashtable();
        Iterator it = configList.iterator();
        while (it.hasNext()) {
            ConfigParam configParam = (ConfigParam) it.next();
            configHash.put(configParam.getParamName(), configParam.getParamValue());
        }
    }

    /**
     * Used internally to convert a <code>Hashtable</code> to
     * a <code>ConfigParam</code> list used by Castor
     */
    protected void createConfigList() {
        configList = new ArrayList();
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

