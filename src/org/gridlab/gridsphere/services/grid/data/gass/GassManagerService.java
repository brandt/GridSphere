package org.gridlab.gridsphere.services.grid.data.gass;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.io.IOException;
import java.io.FileReader;
import java.io.File;
import java.util.*;

import org.globus.io.gass.client.GassException;
import org.globus.io.gass.server.GassServer;
import org.globus.security.GlobusProxyException;
import org.globus.security.GlobusProxy;
import org.globus.util.GlobusURL;
import org.globus.gram.Gram;

import org.gridlab.gridsphere.services.grid.system.Local;
import org.gridlab.gridsphere.services.grid.data.file.FileHandle;
import org.gridlab.gridsphere.services.grid.security.credential.impl.GlobusCredential;
import org.gridlab.gridsphere.services.grid.security.credential.impl.GlobusCredentialManager;
import org.gridlab.gridsphere.services.grid.security.credential.CredentialException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;

public interface GassManagerService {

    public GassServer getGassServer(User user)
        throws CredentialException, IOException;

    public String getGassServerUrl(User user)
        throws CredentialException, IOException;

    public void removeGassServer(User user);

    public boolean isGassRequired(FileHandle file);

    public FileHandle createGassFileHandle(User user, String url)
            throws CredentialException, IOException, MalformedURLException;

    public FileHandle createGassStdoutHandle(User user)
            throws CredentialException, IOException;

    public FileHandle createGassStderrHandle(User user)
            throws CredentialException, IOException;
}
