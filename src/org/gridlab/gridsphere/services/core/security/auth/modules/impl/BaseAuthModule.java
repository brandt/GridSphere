package org.gridlab.gridsphere.services.core.security.auth.modules.impl;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.services.core.security.auth.AuthorizationException;
import org.gridlab.gridsphere.services.core.security.auth.modules.LoginAuthModule;
import org.gridlab.gridsphere.services.core.security.auth.modules.impl.descriptor.AuthModuleDefinition;
import org.gridlab.gridsphere.core.persistence.castor.descriptor.ConfigParam;
import org.gridlab.gridsphere.core.persistence.castor.descriptor.Description;

import java.util.*;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public abstract class BaseAuthModule implements Comparable {

    protected Map attributes = new HashMap();
    protected AuthModuleDefinition moduleDef = null;

    public BaseAuthModule(AuthModuleDefinition moduleDef) {
        this.moduleDef = moduleDef;
        List configList = moduleDef.getConfigParamList();
        Iterator it = configList.iterator();
        while (it.hasNext()) {
            ConfigParam param = (ConfigParam)it.next();
            attributes.put(param.getParamName(), param.getParamValue());
        }        
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

    public String getModuleName() {
        return moduleDef.getModuleName();
    }

    public String getModuleDescription(Locale locale) {
        System.err.println("hello");
        List modDescs = moduleDef.getModuleDescriptions();
        if (locale == null) throw new IllegalArgumentException("locale is NULL");
        Iterator it = modDescs.iterator();
        String defTitle = "";
        while (it.hasNext()) {
            Description t = (Description) it.next();
            if (t.getLang() == null) t.setLang(Locale.ENGLISH.getLanguage());
            if (locale.getLanguage().equals(new Locale(t.getLang(), "", "").getLanguage())) return t.getText();
            if (t.getLang().equals(Locale.ENGLISH.getLanguage())) defTitle = t.getText();
        }
        return defTitle;
    }

    public int getModulePriority() {
        return moduleDef.getModulePriority();
    }

    public boolean isModuleActive() {
        return moduleDef.getModuleActive();
    }
    
    public int compareTo(Object obj) {
        if ((obj != null) && (obj instanceof LoginAuthModule)) {
            LoginAuthModule l = (LoginAuthModule) obj;
            if (this.moduleDef.getModulePriority() < l.getModulePriority()) {
                return -1;
            } else if (this.moduleDef.getModulePriority() > l.getModulePriority()) {
                return 1;
            } else {
                return 0;
            }
        }
        throw new ClassCastException("Unable to compare supplied object to this module");
    }


}
