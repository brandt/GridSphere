/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer;

/**
 * The <code>GridSphereProperties</code> maintains all the hidden variable names that are passed in the
 * <code>PortletRequest</code> to set the mode, window state, component id and portlet objects.
 */
public class GridSphereProperties {

    public static final String COMPONENT_ID = "cid";
    public static final String ACTION = "action";
    public static final String MESSAGE = "message";
    public static final String PORTLETID = "PortletID";
    public static final String ERROR = "error";

    // Portlet API objects
    public static final String CLIENT = "Client";
    public static final String PORTLETMODE = "PortletMode";
    public static final String PREVIOUSMODE = "PreviousMode";
    public static final String MODEMODIFIER = "ModeModifier";
    public static final String PORTLETWINDOW = "PortletWindow";
    public static final String PORTLETSETTINGS = "PortletSettings";
    public static final String PORTLETDATA = "PortletData";
    public static final String PORTLETERROR = "PortletError";
    public static final String PREFIX = "up";
    public static final String USER = "User";
    public static final String PORTLETGROUP = "PortletGroup";
    public static final String PORTLETGROUPS = "PortletGroups";
    public static final String PORTLETROLE = "PortletRole";
    public static final String GROUPROLES = "GroupRoles";
}
