/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.core.persistence.castor.descriptor;

import java.util.List;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * A <code>ConfigParamList</code> is a container for <code>ConfigParam</code>
 * elemts that provies a <code>Hashtable</code> view.
 */
public class ConfigParamList {

    private List configList = null;
    private Hashtable configHash = new Hashtable();

    public ConfigParamList() {}

    /**
     * Constructs an instance of ConfigParamList
     */
    public ConfigParamList(List configList) {
        this.configList = configList;
    }

    public void setConfigParamList(List configList) {
        this.configList = configList;
        createConfigHash();
    }

    public List getConfigParamList() {
        return configList;
    }

    public void setConfigParams(Hashtable configHash) {
        this.configHash = configHash;
        createConfigList();
    }

    public Hashtable getConfigParams() {
        return configHash;
    }

    /**
     * Used internally to convert a <code>ConfigParam</code> list to
     * a <code>Hashtable</code> used by clients
     */
    protected void createConfigHash() {
        if (configList == null) return;
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
        Enumeration enum = configHash.keys();
        ConfigParam configParam = null;
        String paramName = null;
        String paramVal = null;
        while (enum.hasMoreElements()) {
            paramName = (String)enum.nextElement();
            paramVal = (String)configHash.get(paramName);
            configParam = new ConfigParam(paramName, paramVal);
            configList.add(configParam);
        }
    }


}

