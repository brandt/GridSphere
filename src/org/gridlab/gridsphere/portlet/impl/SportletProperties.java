/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.impl;

/**
 * <code>SportletProperties</code> conatins all the "hidden" variable names
 * that get transmitted between the portlet container and the portlets to
 * request a particular portlet lifecycle action.
 * <p>
 * SportletProperties comtains three kinds of attributes
 * <ul>
 * <li>Lifecycle atttributes specify the lifecycle method to invoke on a
 * portlet</li>
 * <li>Portlet object attributes contain the actual portlet object e.g.
 * PortletConfig, PortletSettings which must be transferred in the
 * servlet request.</li>
 * <li>Portlet event objects</li>
 * </ul>
 */
public interface SportletProperties {

    /**
     *  Determines which lifecycle command to invoke
     */
    public static final String PORTLET_LIFECYCLE_METHOD = "portletLifecycleMethod";

    // Portlet Lifecyle methods
    /**
     * Command to perform the init method on a portlet
     */
    public static final String INIT = "init";

    /**
     * Command to perform the destroy method on a portlet
     */
    public static final String DESTROY = "destroy";

    /**
     * Command to perform the init concrete method on a portlet
     */
    public static final String INIT_CONCRETE = "initConcrete";

    /**
     * Command to perform the destroy concrete method on a portlet
     */
    public static final String DESTROY_CONCRETE = "destroyConcrete";

    /**
     * Command to perform the login method on a portlet
     */
    public static final String LOGIN = "login";

    /**
     * Command to perform the logout method on a portlet
     */
    public static final String LOGOUT = "logout";

    /**
     * Command to perform the service method on a portlet
     */
    public static final String SERVICE = "service";


    // Portlet obects
    /**
     * The variable name of the PortletConfig object
     */
    public static final String PORTLET_CONFIG = "portletConfig";

    /**
     * The variable name of the PortletApplication object
     */
    public static final String PORTLET_APPLICATION = "portletApp";

    /**
     *  The variable name of the PortletSettings object
     */
    public static final String PORTLET_SETTINGS = "portletSettings";

    // Portlet events
    /**
     *  The variable name of the ActionEvent object
     */
    public static final String ACTION_EVENT = "actionEvent";

    /**
     *  The variable name of the MessageEvent object
     */
    public static final String MESSAGE_EVENT = "messageEvent";

    /**
     *  The variable name of the WindowEvent object
     */
    public static final String WINDOW_EVENT = "windowEvent";

    /**
     *  Determines which event listener to notify
     */
    public static final String PORTLET_ACTION_METHOD = "portletActionMethod";

    /**
     * Command to perform the actionPerformed method on a portlet
     */
    public static final String ACTION_PERFORMED = "actionPerformed";

    /**
     * Command to perform the messageReceived method on a portlet
     */
    public static final String MESSAGE_RECEIVED = "messageReceived";

    /**
     * Command to perform the windowDetached method on a portlet
     */
    public static final String WINDOW_DETACHED = "windowDetached";

    /**
     * Command to perform the windowMinimized method on a portlet
     */
    public static final String WINDOW_MINIMIZED = "windowMinimized";

    /**
     * Command to perform the windowMaximized method on a portlet
     */
    public static final String WINDOW_MAXIMIZED = "windowMaximized";

    /**
     * Command to perform the windowClosing method on a portlet
     *
     * *NOT IMPLEMENTED*
     */
    public static final String WINDOW_CLOSING = "windowClosing";

    /**
     * Command to perform the windowClosed method on a portlet
     *
     * *NOT IMPLEMENTED*
     */
    public static final String WINDOW_CLOSED = "windowClosed";

    /**
     * Command to perform the windowRestored method on a portlet
     */
    public static final String WINDOW_RESTORED = "windowRestored";

    /**
     * Command to perform the doTitle method on a portlet
     */
    public static final String DO_TITLE = "doTitle";

}
