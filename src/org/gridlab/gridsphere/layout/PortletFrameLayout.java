/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:wehren@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;
import org.gridlab.gridsphere.layout.event.PortletFrameEvent;
import org.gridlab.gridsphere.layout.event.PortletFrameListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * The abstract <code>PortletFrameLayout</code> acts a container for the layout of portlet frame
 * components and handles PortletFrame events.
 * <p>
 * The <code>PortletGridLayout</code> is a concrete implementation of the <code>PortletFrameLayout</code>
 * that organizes portlets into a grid with a provided number of rows and columns.
 *
 * @see PortletFrame
 * @see PortletFrameEvent
 */
public abstract class PortletFrameLayout extends BasePortletComponent implements
        PortletLayout, PortletFrameListener {

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
    public List init(List list) {
        list = super.init(list);
        Iterator it = components.iterator();
        PortletComponent p = null;
        while (it.hasNext()) {
            p = (PortletComponent) it.next();
            // all the components have the same theme
            p.setTheme(theme);
            // invoke init on each component
            list = p.init(list);
            // If the component is a frame we want to be notified
            if (p instanceof PortletFrame) {
                PortletFrame f = (PortletFrame) p;
                f.addFrameListener(this);
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
    }

    /**
     * Performed when a frame maximized event has been received
     *
     * @param event a portlet frame event
     */
    public void handleFrameMaximized(PortletFrameEvent event) {
        Iterator it = components.iterator();
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

    /**
     * Performed when a frame minimized event has been received
     *
     * @param event a portlet frame event
     */
    public void handleFrameMinimized(PortletFrameEvent event) {
        Iterator it = components.iterator();
        PortletComponent p = null;
        int id = event.getID();

        while (it.hasNext()) {
            p = (PortletComponent) it.next();
            if (p.getComponentID() == id) {
                p.setWidth("");
            }
            p.setVisible(true);
        }
    }

    /**
     * Performed when a frame restore event has been received
     *
     * @param event a portlet frame event
     */
    public void handleFrameRestore(PortletFrameEvent event) {
        Iterator it = components.iterator();
        PortletComponent p = null;
        int id = event.getID();

        while (it.hasNext()) {
            p = (PortletComponent) it.next();
            if (p.getComponentID() == id) {
                p.setWidth("");
            } else {
                p.setVisible(true);
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
        if (event.getAction() == PortletFrameEvent.Action.FRAME_MAXIMIZED) {
            handleFrameMaximized(event);
        } else if (event.getAction() == PortletFrameEvent.Action.FRAME_MINIMIZED) {
            handleFrameMinimized(event);
        } else if (event.getAction() == PortletFrameEvent.Action.FRAME_RESTORED) {
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

}


