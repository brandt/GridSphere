/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import java.io.IOException;
import java.util.List;

/**
 * A <code>PortletPanel</code> is used to compose a collection of portlet components
 * in a specified layout.
 */
public class PortletPanel extends BasePortletComponent {

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
     * @returns PortletLayout concrete instance
     */
    public PortletLayout getLayout() {
        return layout;
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

}
