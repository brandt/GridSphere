/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.impl.descriptor;

import org.exolab.castor.types.AnyNode;
import org.gridlab.gridsphere.portlet.Portlet;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.ArrayList;
import java.util.List;

/**
 * The <code>SupportsModes</code> class is used by the portlet descriptor
 * to specify which portlet modes are supported for the specified portlet
 * If no modes are specified, then it is assumed that all modes are
 * supported.
 */
public class SupportsModes {

    private List portletModes = new ArrayList();
    private PortletLog log = SportletLog.getInstance(SupportsModes.class);

    /**
     * Constructs an instance of SportletModes
     */
    public SupportsModes() {}

    /**
     * For use by Castor. Clients should consider using #getPortletModes
     * or #getPortletModesAsStrings
     * <p>
     * Returns the modes as defined in the application portlet definition
     *
     * @return a <code>List</code> containing <code>AnyNode</code> elements
     */
    public List getModes() {
        return portletModes;
    }

    /**
     * For use by Castor. Clients should consider using #getPortletModes
     * or #setPortletModesAsStrings
     * <p>
     * Sets the modes as defined in the application portlet definition
     *
     * @param modes an <code>ArrayList</code> containing
     * <code>AnyNode</code> elements
     */
    public void setModes(ArrayList modes) {
        if (modes != null) this.portletModes = modes;
    }

    /**
     * Returns the modes as defined in the application portlet definition
     * as a <code>List</code> containing <code>String</code> elements
     *
     * @return a <code>List</code> containing <code>String</code> elements
     */
    public List getPortletModesAsStrings() {
        List modes = new ArrayList();
        if (portletModes.isEmpty()) {
            modes.add(Portlet.Mode.CONFIGURE.toString());
            modes.add(Portlet.Mode.EDIT.toString());
            modes.add(Portlet.Mode.HELP.toString());
            modes.add(Portlet.Mode.VIEW.toString());
        }
        for (int i = 0; i < portletModes.size(); i++) {
            AnyNode a = (AnyNode) portletModes.get(i);
            Portlet.Mode mode = null;
            try {
                mode = Portlet.Mode.toMode(a.getLocalName());
                modes.add(mode.toString());
            } catch (IllegalArgumentException e) {
                log.error("unable to parse mode: " + mode);
            }
        }
        return modes;
    }

     /**
     * Returns the modes as defined in the application portlet definition
     * as a <code>List</code> containing <code>Portlet.Mode</code> elements
     *
     * @return a <code>List</code> containing <code>Portlet.Mode</code> elements
     */
    public List getPortletModes() {
        List modes = new ArrayList();
        if (portletModes.isEmpty()) {
            modes.add(Portlet.Mode.CONFIGURE);
            modes.add(Portlet.Mode.EDIT);
            modes.add(Portlet.Mode.HELP);
            modes.add(Portlet.Mode.VIEW);
        }
        for (int i = 0; i < portletModes.size(); i++) {
            AnyNode a = (AnyNode) portletModes.get(i);
            Portlet.Mode mode = null;
            try {
                mode = Portlet.Mode.toMode(a.getLocalName());
                modes.add(mode);
            } catch (Exception e) {
              log.error("unable to parse mode: " + mode);
            }
        }
        return modes;
    }

}

