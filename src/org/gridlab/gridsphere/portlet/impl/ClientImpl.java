/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.impl;

import org.gridlab.gridsphere.portlet.Capability;
import org.gridlab.gridsphere.portlet.Client;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * The ClientImpl class represents the client device that the user connects to the portal with.
 * It defines methods to obtain information about clients, e.g. browsers running on PCs, WAP phones, PDAs etc.
 */
public class ClientImpl implements Client {

    private String manufacturer = null;
    private String model = null;
    private String version = null;
    private String userAgent = null;
    private String mimeType = null;
    private String markupName = null;

    public ClientImpl(HttpServletRequest req) {
        Enumeration enum = req.getHeaders("user-agent");
        while (enum.hasMoreElements()) {
            System.err.println((String)enum.nextElement());
        }
    }


    /**
     * Returns the name of the manufacturer of this client, or null if the name is not available.
     *
     * @return the manufacturer
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * Returns the name of the model of this client, or null if the name is not available.
     *
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * Returns the version of the model of this client, or null if the version is not available.
     *
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Returns the exact user agent that this client uses to identify itself to the portal.
     * If the client does not send a user agent, this method returns null.
     *
     * @return the user agent
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * Returns whether this client has the given capability. If the portal does not know enough about the client,
     * it has to be on the safe side and return false.
     *
     * @param capability the capability
     * @return true if the client has the given capability, false otherwise
     */
    public boolean isCapableOf(Capability capability) {
        // XXX: FILL ME IN
        return false;
    }

    /**
     * Returns whether this client has the given capabilities.
     * The array of capability is tested in its entirety, ie. only if the client is
     * capable of every single capability this methods returns true.
     *
     * @param capabilities an array of capabilities
     * @return true if the client has the given capability, false otherwise
     */
    public boolean isCapableOf(Capability[] capabilities) {
        // XXX: FILL ME IN
        return false;
    }

    /**
     * Returns the preferred mime-type that this client device supports.
     *
     * @return the mime-type
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * Returns the preferred markup name that this client device supports.
     *
     * @return the name of the markup
     */
    public String getMarkupName() {
        return markupName;
    }

}
