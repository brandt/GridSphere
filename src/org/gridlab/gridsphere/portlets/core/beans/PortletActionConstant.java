/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Feb 5, 2003
 * Time: 3:09:33 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.portlets.core.beans;

import org.gridlab.gridsphere.portlet.PortletURI;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.DefaultPortletAction;

public class PortletActionConstant {

    private String name = null;

    private PortletActionConstant() {
    }

    protected PortletActionConstant(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public PortletURI createDefaultPortletActionURI(PortletRequest request, PortletResponse response) {
        PortletURI actionURI = response.createReturnURI();
        actionURI.addAction(new DefaultPortletAction(name));
        return actionURI;
    }

    public boolean equals(Object object) {
        if (object == null || ! (object instanceof PortletActionConstant) ) {
            return false;
        }
        String thatName = ((PortletActionConstant)object).name;
        return this.name.equals(thatName);
    }

    public int hashCode() {
        return ((Object)this.name).hashCode();
    }

    public String toString() {
        return this.name;
    }
}
