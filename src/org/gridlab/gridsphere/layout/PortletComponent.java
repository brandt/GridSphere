/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

public interface PortletComponent extends PortletLifecycle {

    public boolean isVisible();

    public void setVisible(boolean isVisible);

    public PortletBorder getPortletBorder();

    public PortletInsets getPortletInsets();

    public String getName();

    public String getHeight();

    public String getWidth();

    public void setPortletBorder(PortletBorder border);

    public void setName(String name);

    public void setPortletInsets(PortletInsets insets);

    public void setHeight(String height);

    public void setWidth(String width);

}
