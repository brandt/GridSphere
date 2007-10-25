/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.portletcontainer;

/**
 * The <code>Client</code> interface represents the client device that the user connects to the portal with.
 * It defines methods to obtain information about clients, e.g. browsers running on PCs, WAP phones, PDAs etc.
 */
public interface Client {

    /**
     * Returns the name of the manufacturer of this client, or null if the name is not available.
     *
     * @return the manufacturer
     */
    public String getManufacturer();

    /**
     * Returns the name of the model of this client, or null if the name is not available.
     *
     * @return the model
     */
    public String getModel();

    /**
     * Returns the version of the model of this client, or null if the version is not available.
     *
     * @return the version
     */
    public String getVersion();

    /**
     * Returns the exact user agent that this client uses to identify itself to the portal.
     * If the client does not send a user agent, this method returns null.
     *
     * @return the user agent
     */
    public String getUserAgent();

    /**
     * Returns the preferred mime-type that this client device supports.
     *
     * @return the mime-type
     */
    public String getMimeType();

    /**
     * Returns the preferred markup name that this client device supports.
     *
     * @return the name of the markup
     */
    public String getMarkupName();

}
