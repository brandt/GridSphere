/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:wehren@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.layout.event.PortletComponentEvent;
import org.gridlab.gridsphere.layout.event.PortletFrameEvent;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Collections;


/**
 * The abstract <code>PortletFrameLayout</code> acts a container for the layout of portlet frame
 * components and handles PortletFrame events.
 * <p>
 * The <code>PortletTableLayout</code> is a concrete implementation of the <code>PortletFrameLayout</code>
 * that organizes portlets into a grid with a provided number of rows and columns.
 *
 * @see PortletFrame
 * @see PortletFrameEvent
 */
public abstract class PortletFrameLayout extends BasePortletComponent implements
        Serializable, PortletLayout, Cloneable {

    protected List components = new ArrayList();

    /**
     * Initializes the portlet component. Since the components are isolated
     * after Castor unmarshalls from XML, the ordering is determined by a
     * passed in List containing the previous portlet components in the tree.
     *
     * @param list a list of component identifiers
     * @return a list of updated component identifiers
     * @see ComponentIdentifier
     */
    public List init(PortletRequest req, List list) {
        list = super.init(req, list);
        List scomponents = Collections.synchronizedList(components);
        synchronized (scomponents) {
            Iterator it = scomponents.iterator();
            PortletRole userRole = req.getRole();
            PortletComponent p = null;
            List groups = (List)req.getAttribute(SportletProperties.PORTLETGROUPS);
            while (it.hasNext()) {
                p = (PortletComponent) it.next();
                PortletRole reqRole = PortletRole.toPortletRole(p.getRequiredRoleAsString());
                String group = p.getRequiredGroupAsString();
                Iterator git = groups.iterator();
                boolean found = false;
                while (git.hasNext()) {
                    PortletGroup g = (PortletGroup)git.next();
                    if (g.getName().equalsIgnoreCase(group)) {
                        found = true;
                    }
                }
                if (!found) {
                    it.remove();
                }
                if (userRole.compare(userRole, reqRole) < 0) {
                    it.remove();
                } else {
                // all the components have the same theme
                p.setTheme(theme);
                // invoke init on each component
                list = p.init(req, list);

                p.addComponentListener(this);

                p.setParentComponent(this);
                }
            }
        }
        return list;
    }

    /**
     * Destroys this portlet component
     */
    public void destroy() {
    }

    /**
     * Performs an action on this portlet component
     *
     * @param event a gridsphere event
     * @throws PortletLayoutException if a layout error occurs during rendering
     * @throws IOException if an I/O error occurs during rendering
     */
    public void actionPerformed(GridSphereEvent event) throws
            PortletLayoutException, IOException {

        PortletComponentEvent compEvt = event.getLastRenderEvent();
        if ((compEvt != null) && (compEvt instanceof PortletFrameEvent)) {
            PortletFrameEvent frameEvent = (PortletFrameEvent)compEvt;
            handleFrameEvent(frameEvent);
        }

        List slisteners = Collections.synchronizedList(listeners);
        synchronized (slisteners) {
            Iterator it = slisteners.iterator();
            PortletComponent comp;
            while (it.hasNext()) {
                comp = (PortletComponent) it.next();
                event.addNewRenderEvent(compEvt);
                comp.actionPerformed(event);
            }
        }
    }

    /**
     * Performed when a frame maximized event has been received
     *
     * @param event a portlet frame event
     */
    public void handleFrameMaximized(PortletFrameEvent event) {
        List scomponents = Collections.synchronizedList(components);
        synchronized (scomponents) {
        Iterator it = scomponents.iterator();
        PortletComponent p = null;
        int id = event.getID();
        while (it.hasNext()) {
            p = (PortletComponent) it.next();
            // check for the frame that has been maximized
            if (p.getComponentID() == id) {
                p.setWidth("100%");
            } else {
                // If this is not the right frame, make it invisible
                p.setVisible(false);
            }
        }
        }
    }

    /**
     * Performed when a frame minimized event has been received
     *
     * @param event a portlet frame event
     */
    public void handleFrameMinimized(PortletFrameEvent event) {
        List scomponents = Collections.synchronizedList(components);
        synchronized (scomponents) {
        Iterator it = scomponents.iterator();
        PortletComponent p = null;
        int id = event.getID();

        while (it.hasNext()) {
            p = (PortletComponent) it.next();
            if (p.getComponentID() == id) {
                p.setWidth("");
            }
            p.setWidth(p.getDefaultWidth());
            p.setVisible(true);
        }
        }
    }

    /**
     * Performed when a frame restore event has been received
     *
     * @param event a portlet frame event
     */
    public void handleFrameRestore(PortletFrameEvent event) {
        List scomponents = Collections.synchronizedList(components);
        synchronized (scomponents) {
        Iterator it = scomponents.iterator();
        PortletComponent p = null;
        int id = event.getID();
        while (it.hasNext()) {
            p = (PortletComponent) it.next();
            if (p.getComponentID() == id) {
                if (p instanceof PortletFrame) {
                    PortletFrame f = (PortletFrame)p;
                    f.setWidth(event.getOriginalWidth());
                }
                p.setWidth(p.getDefaultWidth());
                //p.setWidth("");
            } else {
                p.setVisible(true);
            }
        }
        }
    }

    /**
     * Performed when a frame event has been received
     *
     * @param event a portlet frame event
     */
    public void handleFrameEvent(PortletFrameEvent event)
            throws PortletLayoutException {
        if (event.getAction() == PortletFrameEvent.FrameAction.FRAME_MAXIMIZED) {
            handleFrameMaximized(event);
        } else if (event.getAction() == PortletFrameEvent.FrameAction.FRAME_MINIMIZED) {
            handleFrameMinimized(event);
        } else if (event.getAction() == PortletFrameEvent.FrameAction.FRAME_RESTORED) {
            handleFrameRestore(event);
        }
    }

    /**
     * Adds a new portlet component to the layout
     *
     * @param component a portlet component
     */
    public void addPortletComponent(PortletComponent component) {
        components.add(component);
    }

    /**
     * Removes a new portlet component to the layout
     *
     * @param component a portlet component
     */
    public void removePortletComponent(PortletComponent component) {
        components.remove(component);
    }

    /**
     * Sets the list of new portlet component to the layout
     *
     * @param components an ArrayList of portlet components
     */
    public void setPortletComponents(ArrayList components) {
        this.components = components;
    }

    /**
     * Returns a list containing the portlet components in this layout
     *
     * @return a list of portlet components
     */
    public List getPortletComponents() {
        return components;
    }

    public Object clone() throws CloneNotSupportedException {
        PortletFrameLayout f = (PortletFrameLayout)super.clone();
        List scomponents = Collections.synchronizedList(components);
        synchronized (scomponents) {
        f.components = new ArrayList(scomponents.size());
        for (int i = 0; i < scomponents.size(); i++) {
            PortletComponent comp = (PortletComponent)scomponents.get(i);
            f.components.add(comp.clone());
        }
        }
        return f;
    }

}


