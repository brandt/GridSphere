/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

public interface PortletComponent extends ComponentLifecycle {

    public boolean isVisible();

    public void setVisible(boolean isVisible);

    public void setTheme(String theme);

    public String getTheme();

    public String getName();

    public void setName(String name);

    public String getHeight();

    public void setHeight(String height);

    public void setWidth(String width);

    public String getWidth();

}
