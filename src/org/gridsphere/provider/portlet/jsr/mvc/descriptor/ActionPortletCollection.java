/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: AuthModulesDescriptor.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.provider.portlet.jsr.mvc.descriptor;

import java.util.List;

public class ActionPortletCollection {

    private List actionPortletList = null;

    public List getActionPortletList() {
        return actionPortletList;
    }

    public void setActionPortletList(List actionPortletList) {
        this.actionPortletList = actionPortletList;
    }

}