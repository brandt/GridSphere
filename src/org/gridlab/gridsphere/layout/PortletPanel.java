/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;
import org.gridlab.gridsphere.layout.event.PortletComponentEvent;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Iterator;

/**
 * A <code>PortletPanel</code> is used to compose a collection of portlet components
 * in a specified layout.
 */
public class PortletPanel extends BasePortletComponent implements Serializable, Cloneable {

    private PortletLayout layout = null;

    /**
     * Constructs an instance of PortletPanel
     */
    public PortletPanel() {
    }

    /**
     * Constructs an instance of PortletPanel with the supplied layout
     *
     * @param layout the <code>PortletLayout</code>
     * @see PortletLayout
     */
    public PortletPanel(PortletLayout layout) {
        this.layout = layout;
    }

    /**
     * Initializes the portlet panel component. Since the components are isolated
     * after Castor unmarshalls from XML, the ordering is determined by a
     * passed in List containing the previous portlet components in the tree.
     *
     * @param list a list of component identifiers
     * @return a list of updated component identifiers
     * @see ComponentIdentifier
     */
    public List init(List list) {
        if (layout == null) {
            return super.init(list);
        } else {
            layout.addComponentListener(this);
            return layout.init(list);
        }
    }

    /**
     * Sets the portlet layout to use for arranging the components in the panel
     *
     * @param layout a PortletLayout concrete instance
     */
    public void setLayout(PortletLayout layout) {
        this.layout = layout;
    }

    /**
     * Returns the portlet layout to use for arranging the components in the panel
     *
     * @return the concrete portlet layout instance
     */
    public PortletLayout getLayout() {
        return layout;
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
        Iterator it = listeners.iterator();
        PortletComponent comp;
        while (it.hasNext()) {
            comp = (PortletComponent) it.next();
            event.addNewRenderEvent(compEvt);
            comp.actionPerformed(event);
        }
    }
    /**
     * Renders the portlet panel component
     *
     * @param event a gridsphere event
     * @throws PortletLayoutException if a layout error occurs during rendering
     * @throws IOException if an I/O error occurs during rendering
     */
    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        if (layout != null) layout.doRender(event);
    }

    public Object clone() throws CloneNotSupportedException {
        PortletPanel p = (PortletPanel)super.clone();
        p.layout = (this.layout == null) ? null : (PortletLayout)this.layout.clone();
        return p;
    }
}
