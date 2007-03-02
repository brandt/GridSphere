/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: PortletComponent.java 4986 2006-08-04 09:54:38Z novotny $
 */
package org.gridsphere.layout;

import javax.portlet.PortletRequest;


/**
 * The <code>PortletComponent</code> defines the interfaces provided by a portlet component.
 */
public interface PortletComponent extends ComponentLifecycle {

    /**
     * Returns the portlet component label
     *
     * @return the portlet component label
     */
    public String getLabel();

    /**
     * Sets the portlet component label
     *
     * @param label the portlet component label
     */
    public void setLabel(String label);

    /**
     * Sets the portlet component width
     *
     * @param width the portlet component width
     */
    public void setWidth(String width);

    /**
     * Returns the portlet component width
     *
     * @return the portlet component width
     */
    public String getWidth();

    /**
     * Returns the default portlet component width
     *
     * @return the default portlet component width
     */
    public String getDefaultWidth();

    /**
     * When set to true the portlet component is visible and will be rendered
     *
     * @param isVisible if <code>true</code> portlet component is rendered,
     *                  <code>false</code> otherwise
     */
    public void setVisible(boolean isVisible);

    /**
     * When isVisible is true the portlet component is visible and will be rendered
     *
     * @return the portlet component visibility
     */
    public boolean getVisible();

    /**
     * Allows a required role to be associated with viewing this portlet
     *
     * @return the required portlet role expresses as a <code>PortletRole</code>
     */
    public String getRequiredRole();

    /**
     * Allows a required role to be associated with viewing this portlet
     *
     * @param reqRole the required portlet role expresses as a <code>PortletRole</code>
     */
    public void setRequiredRole(String reqRole);

    public void setCanModify(boolean canModify);

    public boolean getCanModify();

    public void addComponentListener(PortletComponent component);

    public PortletComponent getParentComponent();

    public void setParentComponent(PortletComponent parent);

    public void remove(PortletComponent pc);

    public void remove();

    public void setBufferedOutput(PortletRequest req, StringBuffer sb);

    public StringBuffer getBufferedOutput(PortletRequest req);

    /**
     * Returns the portlet component css.
     *
     * @return portlet component css
     */
    public String getStyle();

    /**
     * Sets the portlet component css
     *
     * @param style style to be set
     */
    public void setStyle(String style);


}
