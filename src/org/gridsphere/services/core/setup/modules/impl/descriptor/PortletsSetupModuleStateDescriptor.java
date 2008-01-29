package org.gridsphere.services.core.setup.modules.impl.descriptor;

import org.gridsphere.services.core.setup.modules.PortletsSetupModule;

import java.util.Map;
import java.util.HashMap;

/**
 * @author <a href="mailto:docentt@man.poznan.pl">Tomasz Kuczynski</a>, PSNC
 * @version $Id$
 */
public class PortletsSetupModuleStateDescriptor {
    protected String title = "";
    protected String description = "";
    protected String jspFile = "";
    protected String context = "";
    protected Map<String,Object> attributes = new HashMap<String, Object>();

    public PortletsSetupModuleStateDescriptor(PortletsSetupModule portletsSetupModule) {
        this.context = portletsSetupModule.getContextName();
    }

    public PortletsSetupModuleStateDescriptor(String title, String description, String jspFile, PortletsSetupModule portletsSetupModule) {
        this.title = title;
        this.description = description;
        this.jspFile = jspFile;
        this.context = portletsSetupModule.getContextName();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJspFile() {
        return jspFile;
    }

    public void setJspFile(String jspFile) {
        this.jspFile = jspFile;
    }

    public String getContext() {
        return context;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }
}
