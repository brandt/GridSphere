/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.layout;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.provider.portletui.beans.FileInputBean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The <code>LayoutManagerService</code> manages users layouts
 */
public interface LayoutManagerService extends PortletService {

    //public String[] getPortletClassNames(PortletRequest req);

    public String[] getTabNames(PortletRequest req);

    public void setTabNames(PortletRequest req, String[] tabNames);

    public String[] getSubTabNames(PortletRequest req, String tabName);

    public void setSubTabNames(PortletRequest req, String tabName, String[] subTabNames);

    public String[] getFrameClassNames(PortletRequest req, String subTabName);

    public void setFrameClassNames(PortletRequest req, String subTabName, String[] frameNames);

    public void removeTab(String tabName);

    public void removeFrame(String frameClassName);

}
