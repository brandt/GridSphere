/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: Client.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.portlet;

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
     * Returns whether this client has the given capability. If the portal does not know enough about the client,
     * it has to be on the safe side and return false.
     *
     * @param capability the capability
     * @return <code>true</code> if the client has the given capability, <code>false</code> otherwise
     */
    public boolean isCapableOf(Capability capability);

    /**
     * Returns whether this client has the given capabilities.
     * The array of capability is tested in its entirety, ie. only if the client is
     * capable of every single capability this methods returns true.
     *
     * @param capabilities an array of capabilities
     * @return <code>true</code> if the client has the given capability, <code>false</code> otherwise
     */
    public boolean isCapableOf(Capability[] capabilities);

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
