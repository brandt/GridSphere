/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.GuestUser;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.services.layout.LayoutManagerService;
import org.gridlab.gridsphere.tags.web.model.DefaultListModel;

import javax.servlet.UnavailableException;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * The subscription portlet offers portlet access to the portlet registry service to enable users to
 * add and remove portlets from their current subscription list.
 */
public class SubscriptionPortlet extends AbstractPortlet {

    protected LayoutManagerService layoutService;

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        PortletContext context = config.getContext();
        try {
            layoutService = (LayoutManagerService) context.getService(LayoutManagerService.class);
        } catch (PortletServiceUnavailableException e) {
            throw new UnavailableException("layout service instance unavailable: " + e.toString());
        } catch (PortletServiceNotFoundException e) {
            throw new UnavailableException("layout service instance not found: " + e.toString());
        }
        System.err.println("init() in SubscriptionPortlet");
    }

    public void actionPerformed(ActionEvent evt) throws PortletException {

    }

    public void service(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        User user = request.getUser();
        //List tabList = layoutService.getTabbedLists(user);
        //DefaultListModel listModel = new DefaultListModel(tabList);
        getPortletConfig().getContext().include("/jsp/subscription/list.jsp", request, response);
    }

}
