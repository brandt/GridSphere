/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;



public interface PortletComponent extends PortletRender {

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
