/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.grid.data.gass.impl;

import org.gridlab.gridsphere.services.grid.data.gass.GassManagerService;
import org.gridlab.gridsphere.services.grid.data.file.FileHandle;
import org.gridlab.gridsphere.services.grid.security.credential.CredentialException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceAuthorizer;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.User;
import org.globus.io.gass.server.GassServer;

import java.io.IOException;
import java.net.MalformedURLException;

public class GassManagerServiceImpl
        implements GassManagerService, PortletServiceProvider {

    private GridSphereGassManager gassManager = GridSphereGassManager.getInstance();
    private PortletServiceAuthorizer authorizer = null;

    public GassManagerServiceImpl(PortletServiceAuthorizer authorizer) {
        this.authorizer = authorizer;
    }

    public void init(PortletServiceConfig config)
            throws PortletServiceUnavailableException {
        gassManager.init(config);
    }

    public void destroy() {
    }

    public GassServer getGassServer(User user)
            throws CredentialException, IOException {
        return gassManager.getGassServer(user);
    }

    public String getGassServerUrl(User user)
            throws CredentialException, IOException {
        return gassManager.getGassServerUrl(user);
    }

    public void removeGassServer(User user) {
        gassManager.removeGassServer(user);
    }

    public boolean isGassRequired(FileHandle file) {
        return gassManager.isGassRequired(file);
    }

    public FileHandle createGassFileHandle(User user, String url)
            throws CredentialException, IOException, MalformedURLException {
        return gassManager.createGassFileHandle(user, url);
    }

    public FileHandle createGassStdoutHandle(User user)
            throws CredentialException, IOException {
        return gassManager.createGassStdoutHandle(user);
    }

    public FileHandle createGassStderrHandle(User user)
            throws CredentialException, IOException {
        return gassManager.createGassStderrHandle(user);
    }
}
