/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.PortletLog;

import java.awt.*;
import java.io.PrintWriter;

public interface PortletComponent {

    public String getBackground();

    public String getForeground();

    public PortletBorder getBorder();

    public PortletInsets getInsets();

    public String getName();

    public void doRender(PrintWriter out);

    public void doBeginRender(PrintWriter out);

    public void doEndRender(PrintWriter out);

    public int getHeight();

    public int getWidth();

    public void setBackground(String bgColor);

    public void setForeground(String ggColor);

    public void setBorder(PortletBorder border);

    public void setName(String name);

}
