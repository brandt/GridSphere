/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.impl;

public class PortletProperties {

    public static final String PORTLET_CONFIG = "portletConfig";
    public static final String PORTLET_APPLICATION = "portletApp";
    public static final String PORTLET_REQUEST = "portletRequest";
    public static final String PORTLET_RESPONSE = "portletResponse";
    public static final String PORTLET_SESSION = "portletSession";
    public static final String PORTLET_SETTINGS = "portletSettings";
    public static final String PORTLET_ACTION = "portletAction";

    public static final String ACTION_EVENT = "actionEvent";
    public static final String MESSAGE_EVENT = "messageEvent";
    public static final String WINDOW_EVENT = "windowEvent";

    /* PORTLET_LIFECYCLE_METHOD determines which lifecycle method to invoke */
    public static final String PORTLET_LIFECYCLE_METHOD = "portletLifecycleMethod";

    public static final String INIT = "init";
    public static final String DESTROY = "destroy";
    public static final String INIT_CONCRETE = "initConcrete";
    public static final String DESTROY_CONCRETE = "destroyConcrete";
    public static final String LOGIN = "init";
    public static final String LOGOUT = "logout";
    public static final String SERVICE = "service";


    /* PORTLET_ACTION_METHOD determines which action method to invoke */
    public static final String PORTLET_ACTION_METHOD = "portletActionMethod";

    public static final String ACTION_PERFORMED = "actionPerformed";
    public static final String MESSAGE_RECEIVED = "messageReceived";
    public static final String WINDOW_DETACHED  = "windowDetached";
    public static final String WINDOW_MINIMIZED = "windowMinimized";
    public static final String WINDOW_MAXIMIZED = "windowMaximized";
    public static final String WINDOW_CLOSING   = "windowClosing";
    public static final String WINDOW_CLOSED    = "windowClosed";
    public static final String WINDOW_RESTORED  = "windowRestored";
    public static final String DO_TITLE         = "doTitle";

}
