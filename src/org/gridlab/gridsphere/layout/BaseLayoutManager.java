/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.io.IOException;


/**
 * The LayoutManager is responsible for constructing a layout appropriate
 * to the user's layout preferences.
 */
public abstract class BaseLayoutManager implements LayoutManager, PortletFrameListener {

    protected List components = new ArrayList();
    protected PortletInsets insets;

    public List init(List list) {
        Iterator it = components.iterator();
        PortletComponent p = null;
        while (it.hasNext()) {
            p = (PortletComponent)it.next();
            //PortletRender a = (PortletRender)p;
            list.add(p);
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

    public void login() {}

    public void logout() {}

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
            p.setVisible(true);
        }
    }

    public void handleFrameEvent(PortletFrameEvent event) throws PortletLayoutException {
        if (event.getAction() == PortletFrameEvent.Action.FRAME_MAXIMIZED) {
            handleFrameMaximized(event);
        } else if (event.getAction() == PortletFrameEvent.Action.FRAME_MINIMIZED) {
            handleFrameMinimized(event);
        }
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


