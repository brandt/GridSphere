/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.grid.data.gass.impl;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.io.IOException;
import java.io.FileReader;
import java.io.File;
import java.util.*;

import org.globus.io.gass.client.GassException;
import org.globus.io.gass.server.GassServer;
import org.globus.util.GlobusURL;
import org.globus.gram.Gram;
import org.globus.gsi.gssapi.GlobusGSSException;

import org.gridlab.gridsphere.services.grid.system.Local;
import org.gridlab.gridsphere.services.grid.data.file.FileHandle;
import org.gridlab.gridsphere.services.grid.data.gass.GassManagerService;
import org.gridlab.gridsphere.services.grid.security.credential.impl.GlobusCredential;
import org.gridlab.gridsphere.services.grid.security.credential.impl.GlobusCredentialManager;
import org.gridlab.gridsphere.services.grid.security.credential.CredentialException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.ietf.jgss.GSSCredential;

public class GridSphereGassManager implements GassManagerService {

    private static PortletLog _logger = null;
    private static GridSphereGassManager _instance = null;
    private static GlobusCredentialManager credentialManager = GlobusCredentialManager.getInstance();
    private Map gassServers = null;

    public static GridSphereGassManager getInstance() {
        if (_instance == null) {
            _instance = new GridSphereGassManager();
        }
        return _instance;
    }

    private GridSphereGassManager() {
    }

    public void init(PortletServiceConfig config) {
        this.gassServers = Collections.synchronizedMap(new TreeMap());
    }

    public GassServer getGassServer(User user)
            throws CredentialException, IOException {
        System.out.println("GassServer: getGassServer(user)");
        String userId = user.getID();
        GassServer gassServer = (GassServer)this.gassServers.get(userId);
        if (gassServer == null) {
            gassServer = createGassServer(user);
            gassServers.put(userId, gassServer);
        }
        return gassServer;
    }

    public String getGassServerUrl(User user)
            throws CredentialException, IOException {
        return getGassServer(user).getURL();
    }

    public void removeGassServer(User user) {
        String userId = user.getID();
        GassServer gassServer = (GassServer)this.gassServers.get(userId);
        if (gassServer != null) {
            _logger.debug("GridSphereGassServerManager shutting down gass server at " + gassServer.getURL());
            gassServer.shutdown();
            _logger.debug("GridSphereGassServerManager gass server has been shutdown");
            gassServers.remove(userId);
        }
    }

    public boolean isGassRequired(FileHandle file) {
        String protocol = file.getFileProtocol();
        String host = file.getFileHost();
        return (// If protocol is "file"
                (protocol.equals(FileHandle.DEFAULT_PROTOCOL))
             && // And host is set to localhost
                (host.equals(Local.getLocalHost()) ||
                 host.equals("localhost") ||
                 host.equals("127.0.0.1"))
               );
    }

    public FileHandle createGassFileHandle(User user, String url)
            throws CredentialException, IOException, MalformedURLException {
        FileHandle file = new FileHandle(url);
        url = getInstance().getGassServerUrl(user) + file.getFilePath();
        file.setFileUrl(url);
        return file;
    }

    public FileHandle createGassStdoutHandle(User user)
            throws CredentialException, IOException {
        Date date = new Date();
        System.out.println("GridSphereGassServerManager: Getting gass server for stdout");
        GassServer gassServer = getInstance().getGassServer(user);
        long time = date.getTime();
        String path = "/tmp/stdout-" + time;
        File file = new File(path);
        file.createNewFile();
        String stdoutUrl = gassServer.getURL() + path;
        System.out.println("GridSphereGassServerManager: Stdout url = " + stdoutUrl);
        FileHandle fileHandle = new FileHandle();
        //fileHandle.setFileOwner(user);
        fileHandle.setFileUrl(stdoutUrl);
        return fileHandle;
    }

    public FileHandle createGassStderrHandle(User user)
            throws CredentialException, IOException {
        Date date = new Date();
        GassServer gassServer = getInstance().getGassServer(user);
        long time = date.getTime();
        System.out.println("GridSphereGassServerManager: Getting gass server for stderr");
        String path = "/tmp/stder-" + time;
        File file = new File(path);
        file.createNewFile();
        String stderrUrl = gassServer.getURL() + path;
        System.out.println("GridSphereGassServerManager: Stderr url = " + stderrUrl);
        FileHandle fileHandle = new FileHandle();
        //fileHandle.setFileOwner(user);
        fileHandle.setFileUrl(stderrUrl);
        return fileHandle;
    }

    private GassServer createGassServer(User user)
            throws CredentialException, IOException {
        GSSCredential globusProxy = null;
        try {
            System.out.println("GassServer.createGassServer(user)");
            globusProxy = getUserDefaultGSSProxy(user);
        } catch (Exception e) {
            _logger.error("createGassServer", e);
            System.err.println(e);
            throw new CredentialException(e.getMessage());
        }
        GassServer gassServer = null;
        try {
            _logger.debug("GridSphereGassServerManager starting up gass server for user " + user.getUserName());
            gassServer = new GassServer(globusProxy, 0);
            gassServer.setOptions( GassServer.STDOUT_ENABLE |
                                   GassServer.STDERR_ENABLE |
                                   GassServer.READ_ENABLE |
                                   GassServer.WRITE_ENABLE );
             _logger.debug("GassFileHandle GASS server started at " + gassServer.getURL());
        } catch (IOException e) {
            _logger.error("Unable to create gass server!", e);
            throw e;
        }
        return gassServer;
    }

    private GSSCredential getUserDefaultGSSProxy(User user) {
        if (credentialManager.hasActiveCredentials(user)) {
            List credentials = credentialManager.getActiveCredentials(user);
            GlobusCredential credential = (GlobusCredential)credentials.get(0);
            return credential.getGSSProxy();
        }
        return null;
    }

}
