/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @author <a href="mailto:wehren@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: PortletFrameLayout.java 4986 2006-08-04 09:54:38Z novotny $
 */
package org.gridsphere.layout;

import org.gridsphere.layout.event.PortletComponentEvent;
import org.gridsphere.layout.event.PortletFrameEvent;
import org.gridsphere.portletcontainer.GridSphereEvent;

import javax.portlet.PortletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


/**
 * The abstract <code>PortletFrameLayout</code> acts a container for the layout of portlet frame
 * components and handles PortletFrame events.
 * <p/>
 * The <code>PortletTableLayout</code> is a concrete implementation of the <code>PortletFrameLayout</code>
 * that organizes portlets into a grid with a provided number of rows and columns.
 *
 * @see PortletFrame
 * @see PortletFrameEvent
 */
public abstract class PortletFrameLayout extends BasePortletComponent implements
        Serializable, PortletLayout, Cloneable {

    protected List<PortletComponent> components = new ArrayList<PortletComponent>();

    protected boolean hasFrameMaximized = false;

    public boolean hasFrameMaximized() {
        return hasFrameMaximized;
    }

    /**
     * Initializes the portlet component. Since the components are isolated
     * after Castor unmarshalls from XML, the ordering is determined by a
     * passed in List containing the previous portlet components in the tree.
     *
     * @param list a list of component identifiers
     * @return a list of updated component identifiers
     * @see ComponentIdentifier
     */
    public List<ComponentIdentifier> init(PortletRequest req, List<ComponentIdentifier> list) {
        list = super.init(req, list);
        ComponentIdentifier compId = new ComponentIdentifier();
        compId.setPortletComponent(this);
        compId.setComponentID(list.size());
        compId.setComponentLabel(label);
        compId.setClassName(this.getClass().getName());
        list.add(compId);
        Iterator it = components.iterator();

        PortletComponent p;
        while (it.hasNext()) {
            p = (PortletComponent) it.next();
            if (!p.getRequiredRole().equals("") && (!req.isUserInRole(p.getRequiredRole()))) {
                it.remove();
            } else {
                p.setCanModify(canModify);
                // invoke init on each component
                list = p.init(req, list);
                p.addComponentListener(this);
                p.setParentComponent(this);
            }
        }
        return list;
    }

    protected void customActionPerformed(GridSphereEvent event) {

    }

    public void doRender(GridSphereEvent event) {
        super.doRender(event);
    }

    public void actionPerformed(GridSphereEvent event) {

        super.actionPerformed(event);

        PortletComponentEvent compEvt = event.getLastRenderEvent();
        if ((compEvt != null) && (compEvt instanceof PortletFrameEvent)) {
            PortletFrameEvent frameEvent = (PortletFrameEvent) compEvt;
            handleFrameEvent(frameEvent);
        }

        customActionPerformed(event);
        Iterator it = listeners.iterator();
        PortletComponent comp;
        while (it.hasNext()) {
            comp = (PortletComponent) it.next();
            event.addNewRenderEvent(compEvt);
            comp.actionPerformed(event);
        }
    }

    public void remove(PortletComponent pc, PortletRequest req) {
        components.remove(pc);
        if (getPortletComponents().isEmpty()) {
            parent.remove(this, req);
        }
    }

    /**
     * Destroys this portlet component
     */
    public void destroy() {
    }

    /**
     * Performed when a frame maximized event has been received
     *
     * @param event a portlet frame event
     */
    public void handleFrameMaximized(PortletFrameEvent event) {
        //System.err.println("in frame layout: frame has been maximized");
        Iterator it = components.iterator();
        PortletComponent p;
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
        //System.err.println("in PortletFrameLayout Minimized");
        List scomponents = Collections.synchronizedList(components);
        synchronized (scomponents) {
            Iterator it = scomponents.iterator();
            PortletComponent p;
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
        //System.err.println("in PortletFrameLayout Resized");
        List scomponents = Collections.synchronizedList(components);
        synchronized (scomponents) {
            Iterator it = scomponents.iterator();
            PortletComponent p;
            int id = event.getID();
            while (it.hasNext()) {
                p = (PortletComponent) it.next();
                if (p.getComponentID() == id) {
                    if (p instanceof PortletFrame) {
                        PortletFrame f = (PortletFrame) p;
                        f.setWidth(event.getOriginalWidth());
                    } else {
                        p.setWidth(p.getDefaultWidth());
                    }
                } else {
                    p.setVisible(true);
                }
            }
        }
    }

    /**
     * Performed when a frame close event has been received
     *
     * @param event a portlet frame event
     */
    public void handleFrameClosed(PortletFrameEvent event) {
        //System.err.println("Portlet FrameLAyout: in frame closed");
        List scomponents = Collections.synchronizedList(components);
        synchronized (scomponents) {
            Iterator it = scomponents.iterator();
            PortletComponent p;
            int id = event.getID();
            while (it.hasNext()) {
                p = (PortletComponent) it.next();
                // check for the frame that has been closed
                if (p.getComponentID() == id) {
                    if (p instanceof PortletFrame) {
                        it.remove();
                    }
                }  else {
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
    public void handleFrameEvent(PortletFrameEvent event) {
        if (event.getAction() == PortletFrameEvent.FrameAction.FRAME_MAXIMIZED) {
            hasFrameMaximized = true;
            handleFrameMaximized(event);
        } else if (event.getAction() == PortletFrameEvent.FrameAction.FRAME_MINIMIZED) {
            hasFrameMaximized = false;
            handleFrameMinimized(event);
        } else if (event.getAction() == PortletFrameEvent.FrameAction.FRAME_RESTORED) {
            hasFrameMaximized = false;
            handleFrameRestore(event);
        } else if (event.getAction() == PortletFrameEvent.FrameAction.FRAME_CLOSED) {
            hasFrameMaximized = false;
            handleFrameClosed(event);
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
    public void setPortletComponents(List<PortletComponent> components) {
        this.components = components;
    }

    /**
     * Returns a list containing the portlet components in this layout
     *
     * @return a list of portlet components
     */
    public List<PortletComponent> getPortletComponents() {
        return components;
    }

    public Object clone() throws CloneNotSupportedException {
        PortletFrameLayout f = (PortletFrameLayout) super.clone();
        List scomponents = Collections.synchronizedList(components);
        synchronized (scomponents) {
            f.components = new ArrayList<PortletComponent>(scomponents.size());
            for (int i = 0; i < scomponents.size(); i++) {
                PortletComponent comp = (PortletComponent) scomponents.get(i);
                f.components.add((PortletComponent)comp.clone());
            }
        }
        return f;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.toString());
        for (int i = 0; i < components.size(); i++) {
            sb.append(components.toString());
        }
        return sb.toString();
    }

}


