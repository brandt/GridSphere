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


    protected static final int MAX_UPLOAD_SIZE = 1024 * 1024;
    protected static final String TEMP_DIR = "/tmp";

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        PortletContext context = config.getContext();

    }

    public void actionPerformed(ActionEvent evt) throws PortletException {
        PortletAction _action = evt.getAction();
        System.err.println("actionPerformed() in SubscriptionPortlet");

        if (_action instanceof DefaultPortletAction) {
            DefaultPortletAction action = (DefaultPortletAction) _action;
            if (action.getName().equals("reload")) {
                /*
                try {
                    userRegistryService.reloadPortlets();
                } catch (PortletRegistryServiceException e) {
                    throw new PortletException("Unable to reload classes: " + e.getMessage());
                }
                */
            } else if (action.getName().equals("upload")) {
                PortletRequest req = evt.getPortletRequest();
                /* Check if request is a file upload */
                String contType = req.getContentType();
                if ((contType != null) && contType.startsWith("multipart/form-data")) {
                    loadPortletWar(req);
                }
            }
        }
    }

    protected void loadPortletWar(PortletRequest request) throws PortletException {
        // Create a new file upload handler
        FileUpload upload = new FileUpload();

        // Set upload parameters
        upload.setSizeMax(MAX_UPLOAD_SIZE);
        upload.setRepositoryPath(TEMP_DIR);

        // Parse the request
        List items = null;
        try {
            items = upload.parseRequest(request);
        } catch (FileUploadException e) {
            throw new PortletException("Unable to parse uploaded file: ", e);
        }
        // Process the uploaded fields
        Iterator iter = items.iterator();
        try {
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (!item.isFormField()) {
                    if (portletConfig.getContext() == null) {
                        System.err.println("ARGGHHHH MOTHERFUCKERT");
                    }
                    String root = (getPortletConfig().getContext().getRealPath(""));

                    item.write(root + "/WEB-INF/lib/portlets/" + item.getName());
                    File file = item.getStoreLocation();
                    System.err.println("file is : " + file.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            throw new PortletException("Unable to save file", e);
        }
    }


    public void service(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        // Create URI's for upload and reload of portlets
        PortletURI reloadURI = response.createURI();
        DefaultPortletAction reloadAction = new DefaultPortletAction("reload");
        reloadURI.addAction(reloadAction);
        request.setAttribute("reload", reloadURI.toString());

        PortletURI uploadURI = response.createURI();
        DefaultPortletAction uploadAction = new DefaultPortletAction("upload");
        reloadURI.addAction(uploadAction);
        request.setAttribute("upload", uploadURI.toString());

        getPortletConfig().getContext().include("/jsp/subscription.jsp", request, response);
    }

}
