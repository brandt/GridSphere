/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.service.spi.impl.descriptor;

import org.gridlab.gridsphere.core.persistence.castor.descriptor.ConfigParam;

import java.util.Vector;
import java.util.List;
import java.util.Properties;
import java.util.Iterator;
import java.io.PrintWriter;
import java.io.PrintStream;

public class SportletServiceDefinition {

    protected String Name = "";
    protected String Description = "";
    protected String Interface = "";
    protected String Implementation = "";
    protected String SecureWrapper = "";
    protected List ConfigParamList = new Vector();
    protected Properties configProps = null;

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getName() {
        return Name;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getDescription() {
        return Description;
    }

    public void setInterface(String Interface) {
        this.Interface = Interface;
    }

    public String getInterface() {
        return Interface;
    }

    public String getImplementation() {
        return Implementation;
    }

    public void setImplementation(String Implementation) {
        this.Implementation = Implementation;
    }

    public String getSecureWrapper() {
        return SecureWrapper;
    }

    public void setSecureWrapper(String SecureWrapper) {
        this.SecureWrapper = SecureWrapper;
    }


    public void setConfigParamList(Vector ConfigParamList) {
        this.ConfigParamList = ConfigParamList;
    }

    public List getConfigParamList() {
        return ConfigParamList;
    }

    private void makeProperties() {
        configProps = new Properties();
        Iterator it = ConfigParamList.iterator();
        ConfigParam param;
        while (it.hasNext()) {
            param = (ConfigParam)it.next();
            configProps.setProperty(param.getParamName(), param.getParamValue());
        }
    }

    public Properties getConfigProperties() {
        if (configProps == null)
            makeProperties();
        return configProps;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("\n");
        sb.append("service name: " + Name + "\n");
        sb.append("description: " + Description + "\n");
        sb.append("interface: " + Interface + "\n");
        sb.append("implementation: " + Implementation + "\n");
        sb.append("config properties: ");
        Iterator it = ConfigParamList.iterator();
        ConfigParam c;
        while (it.hasNext()) {
            c = (ConfigParam)it.next();
            sb.append("\tname: " + c.getParamName() + "\tvalue: " + c.getParamValue());
        }
        return sb.toString();
    }

}
