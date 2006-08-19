/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: Markup.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.portletcontainer.impl.descriptor;

import org.exolab.castor.types.AnyNode;
import org.gridsphere.portlet.Mode;
import org.gridsphere.portlet.PortletLog;
import org.gridsphere.portlet.impl.SportletLog;

import java.util.ArrayList;
import java.util.List;

/**
 * The <code>Supports</code> class is used by the portlet descriptor
 * to specify which portlet modes are supported for the specified portlet
 * If no modes are specified, then it is assumed that all modes are
 * supported.
 */
public class Markup {

    private String name = "";
    private List modes = new ArrayList();
    private PortletLog log = SportletLog.getInstance(Markup.class);

    /**
     * Constructs an instance of SportletModes
     */
    public Markup() {
    }

    /**
     * Sets the markup name e.g. html, wml, cml
     *
     * @param name th emarkup name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the markup name e.g. html, wml, cml
     *
     * @return the markup name
     */
    public String getName() {
        return name;
    }

    /**
     * For use by Castor. Clients should consider using #getPortletModes
     * or #getPortletModesAsStrings
     * <p/>
     * Returns the modes as defined in the application portlet definition
     *
     * @return a <code>List</code> containing <code>AnyNode</code> elements
     */
    public List getModes() {
        return modes;
    }

    /**
     * For use by Castor. Clients should consider using #getPortletModes
     * or #setPortletModesAsStrings
     * <p/>
     * Sets the modes as defined in the application portlet definition
     *
     * @param modes an <code>ArrayList</code> containing
     *              <code>AnyNode</code> elements
     */
    public void setModes(ArrayList modes) {
        if (modes != null) this.modes = modes;
    }

    /**
     * Returns the modes as defined in the application portlet definition
     * as a <code>List</code> containing <code>String</code> elements
     *
     * @return a <code>List</code> containing <code>String</code> elements
     */
    public List getPortletModesAsStrings() {
        List modeStrings = new ArrayList();
        if (this.modes.isEmpty()) {
            modeStrings.add(Mode.CONFIGURE.toString());
            modeStrings.add(Mode.EDIT.toString());
            modeStrings.add(Mode.HELP.toString());
            modeStrings.add(Mode.VIEW.toString());
        }
        for (int i = 0; i < this.modes.size(); i++) {
            AnyNode a = (AnyNode) this.modes.get(i);
            Mode mode = null;
            try {
                mode = Mode.toMode(a.getLocalName());
                modeStrings.add(mode.toString());
            } catch (IllegalArgumentException e) {
                log.error("unable to parse mode: " + mode);
            }
        }
        return modeStrings;
    }

    /**
     * Returns the modes as defined in the application portlet definition
     * as a <code>List</code> containing <code>Portlet.Mode</code> elements
     *
     * @return a <code>List</code> containing <code>Portlet.Mode</code> elements
     */
    public List getPortletModes() {
        List portletModes = new ArrayList();
        if (this.modes.isEmpty()) {
            portletModes.add(Mode.CONFIGURE);
            portletModes.add(Mode.EDIT);
            portletModes.add(Mode.HELP);
            portletModes.add(Mode.VIEW);
        }
        for (int i = 0; i < this.modes.size(); i++) {
            AnyNode a = (AnyNode) this.modes.get(i);
            Mode mode = null;
            try {
                mode = Mode.toMode(a.getLocalName());
                portletModes.add(mode);
            } catch (Exception e) {
                log.error("unable to parse mode: " + mode);
            }
        }
        return portletModes;
    }

}

