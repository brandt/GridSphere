/*
 * Created by IntelliJ IDEA.
 * User: novotny
 * Date: Dec 16, 2002
 * Time: 11:30:30 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.layout;

public class ComponentIdentifier {

    private int id;
    private String className;
    private String portletClass;
    private PortletComponent component;

    public void setComponentID(int id) {
        this.id = id;
    }

    public int getComponentID() {
        return id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean hasPortlet() {
        if (portletClass != null) return true;
        return false;
    }

    public String getPortletClass() {
        return portletClass;
    }

    public void setPortletClass(String portletClass) {
        this.portletClass = portletClass;
    }

    public void setPortletComponent(PortletComponent component) {
        this.component = component;
    }

    public PortletComponent getPortletComponent() {
        return component;
    }

}
