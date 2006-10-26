/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @author <a href="mailto:wehren@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: PortletContainer.java 4986 2006-08-04 09:54:38Z novotny $
 */
package org.gridsphere.layout;

import org.gridsphere.layout.view.Render;
import org.gridsphere.portletcontainer.GridSphereEvent;

import javax.portlet.PortletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * The <code>PortletContainer</code> acts a container for a list of one or more portlet components
 */
public class PortletContainer extends BasePortletComponent implements
        Serializable, Cloneable {

    protected List<PortletComponent> components = new ArrayList<PortletComponent>();
    protected String style = null;
    public final static String STYLE_HEADER = "STYLE_HEADER";
    public final static String STYLE_FOOTER = "STYLE_FOOTER";
    private transient Render containerView = null;

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

        containerView = (Render) getRenderClass(req, "Container");

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
            // invoke init on each component
            list = p.init(req, list);
            p.setParentComponent(this);
        }
        return list;
    }

    /**
     * Destroys this portlet component
     */
    public void destroy() {
        components = null;
    }

    /**
     * Performs an action on this portlet component
     *
     * @param event a gridsphere event
     */
    public void actionPerformed(GridSphereEvent event) {
        super.actionPerformed(event);
    }

    /**
     * Renders the portlet components in the frame layout
     *
     * @param event a gridsphere event
     */

    public void doRender(GridSphereEvent event) {
        super.doRender(event);
        StringBuffer container = new StringBuffer();
        container.append(containerView.doStartBorder(event, this));
        PortletRequest req = event.getRenderRequest();

        Iterator it = components.iterator();
        PortletComponent comp;
        while (it.hasNext()) {
            comp = (PortletComponent) it.next();
            comp.doRender(event);
            container.append(comp.getBufferedOutput(req));
        }

        container.append(containerView.doEndBorder(event, this));

        setBufferedOutput(req, container);
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
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
    public void setPortletComponents(ArrayList<PortletComponent> components) {
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
        PortletContainer f = (PortletContainer) super.clone();
        f.components = new ArrayList<PortletComponent>(components.size());
        for (int i = 0; i < components.size(); i++) {
            PortletComponent comp = (PortletComponent) components.get(i);
            f.components.add((PortletComponent)comp.clone());
        }
        return f;
    }
}


