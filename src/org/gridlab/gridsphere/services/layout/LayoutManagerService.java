/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.layout;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.layout.PortletLayoutException;
import org.gridlab.gridsphere.layout.PortletContainer;

import java.util.List;


/**
 * The LayoutManagerService provies customization support for user layouts
 */
public interface LayoutManagerService extends PortletService {

    public void setPortletContainer(PortletContainer container, User user);

    public PortletContainer getPortletContainer(User user);



   /*
    public PortletTabModel getPortletTabModel() {

    }

    public void setPortletTabModel(PortletTabModel portletTabModel) {

    }
    */

    /*
    public void addPortletFrame(User user, String concretePortletID) throws PortletLayoutException;

    public void removePortletFrame(User user, String concretePortletID);

    public List getPortletFrames(User user);

    public List getPortletFrames(User user, String tab);

    public void addPortletTab(User user, String tabName);

    public void removePortletTab(User user, String tabName);

    public List getPortletTabs(User user);
    */

}
