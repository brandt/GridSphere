/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: AuthModulesDescriptor.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.provider.portlet.jsr.mvc.descriptor;

import java.util.List;

public class ActionPortletDefinition {

    private String name = "";
    private List pageList = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getPageList() {
        return pageList;
    }

    public void setPageList(List pageList) {
        this.pageList = pageList;
    }
    

}