/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.layout.impl;

import org.gridlab.gridsphere.layout.*;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.services.layout.LayoutManagerService;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;


/**
 * The LayoutManagerService provies customization support for user layouts
 */
public class LayoutManagerServiceImpl implements LayoutManagerService, PortletServiceProvider {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(LayoutManagerServiceImpl.class);
    private PortletLayoutEngine layoutEngine = null;

    /**
     * The init method is responsible for parsing portlet.xml and creating ConcretePortlet objects based on the
     * entries. The RegisteredPortlets are managed by the PortletRegistryService.
     */
    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        log.info("in init()");
        // Need a portlet registry service
        layoutEngine = PortletLayoutEngine.getInstance();
    }

    public void destroy() {
        log.info("in destroy()");
    }

    public void addPortletFrame(User user, String concretePortletID) throws PortletLayoutException {
        PortletContainer pc = layoutEngine.getPortletContainer(user);
        List compList = pc.getPortletComponents();
        Iterator it = compList.iterator();
        while (it.hasNext()) {
            PortletComponent c = (PortletComponent)it.next();
            if (c instanceof PortletFrame) {
                PortletFrame f = (PortletFrame)c;
                if (concretePortletID.equals(f.getConcretePortletClass())) {

                }
            }
        }
    }

    public void removePortletFrame(User user, String concretePortletID) {

    }

    public List getPortletFrames(User user) {
        return null;
    }

    public List getPortletFrames(User user, String tab) {
        return null;
    }

    public void addPortletTab(User user, String tabName) {

    }

    public void removePortletTab(User user, String tabName) {

    }

    public List getPortletTabs(User user) {
        return null;
    }

}
