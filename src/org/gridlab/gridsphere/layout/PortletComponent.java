/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import java.util.List;


public interface PortletComponent extends PortletLifecycle {

    public boolean isVisible();

    public void setVisible(boolean isVisible);

    public int getComponentID();

    public String getClassName();

    public String getBackground();

    public String getForeground();

    public PortletBorder getPortletBorder();

    public PortletInsets getPortletInsets();

    public String getName();

    public String getHeight();

    public String getWidth();

    public void setBackground(String bgColor);

    public void setForeground(String ggColor);

    public void setPortletBorder(PortletBorder border);

    public void setName(String name);

    public void setPortletInsets(PortletInsets insets);

    public void setHeight(String height);

    public void setWidth(String width);

}
