/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:wehren@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * The LayoutManager is responsible for constructing a layout appropriate
 * to the user's layout preferences.
 */
public abstract class BaseLayoutManager extends BasePortletComponent implements LayoutManager, PortletFrameListener {

    protected List components = new ArrayList();
    protected PortletInsets insets;

    public List init(List list) {
        list = super.init(list);
        Iterator it = components.iterator();
        PortletComponent p = null;
        while (it.hasNext()) {
            p = (PortletComponent)it.next();
            p.setTheme(theme);
            // invoke init on each component
            list = p.init(list);
            // If the component is a frame we want to be notified
            if (p instanceof PortletFrame) {
                PortletFrame f = (PortletFrame)p;
                f.addFrameListener(this);
            }
        }
        return list;
    }

    public void destroy() {}

    public void actionPerformed(GridSphereEvent event) throws PortletLayoutException, IOException {}

    public void handleFrameMaximized(PortletFrameEvent event) {
        Iterator it = components.iterator();
        PortletComponent p = null;
        int id = event.getID();

        while (it.hasNext()) {
            p = (PortletComponent)it.next();

            // check for the frame that has been maximized
            if (p.getComponentID() == id) {
                p.setWidth("100%");
            } else {
                // If this is not the right frame, make it invisible
                p.setVisible(false);
            }
        }
    }

    public void handleFrameMinimized(PortletFrameEvent event) {
        Iterator it = components.iterator();
        PortletComponent p = null;
        int id = event.getID();

        while (it.hasNext()) {
            p = (PortletComponent)it.next();
            if (p.getComponentID() == id) {
                p.setWidth("");
            }
            p.setVisible(true);
        }
    }

    public void handleFrameResized(PortletFrameEvent event) {
        Iterator it = components.iterator();
        PortletComponent p = null;
        int id = event.getID();

        while (it.hasNext()) {
            p = (PortletComponent)it.next();
            if (p.getComponentID() == id) {
                p.setWidth("");
            } else {
                p.setVisible(true);
            }
        }
    }

    public void handleFrameEvent(PortletFrameEvent event) throws PortletLayoutException {
        if (event.getAction() == PortletFrameEvent.Action.FRAME_MAXIMIZED) {
            handleFrameMaximized(event);
        } else if (event.getAction() == PortletFrameEvent.Action.FRAME_MINIMIZED) {
            handleFrameMinimized(event);
        } else if (event.getAction() == PortletFrameEvent.Action.FRAME_RESIZED) {
            handleFrameResized(event);
        }
    }

    public void addPortletComponent(PortletComponent component) {
        components.add(component);
    }

    public void removePortletComponent(PortletComponent component) {
        components.remove(component);
    }

    public void setPortletComponents(ArrayList components) {
        this.components = components;
    }

    public List getPortletComponents() {
        return components;
    }

    public PortletInsets getPortletInsets() {
        return insets;
    }

    public void setPortletInsets(PortletInsets insets) {
        this.insets = insets;
    }
}


