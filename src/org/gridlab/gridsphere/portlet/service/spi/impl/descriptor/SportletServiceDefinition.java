/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.service.spi.impl.descriptor;

import java.util.Vector;
import java.util.List;

public class SportletServiceDefinition {

    protected String Name = "";
    protected String Description = "";
    protected String Interface = "";
    protected String Implementation = "";
    protected List ConfigParamList = new Vector();


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

    public void setConfigParamList(Vector ConfigParamList) {
        this.ConfigParamList = ConfigParamList;
    }

    public List getConfigParamList() {
        return ConfigParamList;
    }

}
