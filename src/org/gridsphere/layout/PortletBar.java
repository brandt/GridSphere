package org.gridsphere.layout;


import org.gridsphere.layout.view.Render;
import org.gridsphere.portletcontainer.GridSphereEvent;
import org.gridsphere.portlet.impl.SportletProperties;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import java.io.Serializable;
import java.util.List;

/**
 * A <code>PortletTab</code> represents the visual tab graphical interface and is contained
 * by a {@link org.gridsphere.layout.PortletTabbedPane}. A tab contains a title and any additional
 * portlet component, such as another tabbed pane if a double level
 * tabbed pane is desired.
 */
public class PortletBar extends BasePortletComponent implements Serializable, Cloneable {

    private transient Render barView = null;
    protected PortletComponent component = null;

    //protected StringBuffer tab = new StringBuffer();
    /**
     * Constructs an instance of PortletTab
     */
    public PortletBar() {
    }

    public void setPortletComponent(PortletComponent component) {
        this.component = component;
    }

    public PortletComponent getPortletComponent() {
        return component;
    }

    /**
     * Initializes the portlet tab. Since the components are isolated
     * after Castor unmarshalls from XML, the ordering is determined by a
     * passed in List containing the previous portlet components in the tree.
     *
     * @param list a list of component identifiers
     * @return a list of updated component identifiers
     * @see org.gridsphere.layout.ComponentIdentifier
     */
    public List<ComponentIdentifier> init(PortletRequest req, List<ComponentIdentifier> list) {
        barView = (Render)getRenderClass(req, "Bar");
        list = super.init(req, list);

        ComponentIdentifier compId = new ComponentIdentifier();
        compId.setPortletComponent(this);
        compId.setComponentID(list.size());
        compId.setComponentLabel(label);
        compId.setClassName(this.getClass().getName());
        list.add(compId);

        list = component.init(req, list);
        component.addComponentListener(this);
        component.setParentComponent(this);

        return list;
    }

    /**
     * Renders the portlet tab component
     *
     * @param event a gridsphere event
     */
    public void doRender(GridSphereEvent event) {
        super.doRender(event);
        PortletRequest req = event.getRenderRequest();
        RenderResponse res = event.getRenderResponse();
        StringBuffer bar = new StringBuffer();


        bar.append(barView.doStart(event, this));

        if (req.getAttribute(SportletProperties.LAYOUT_EDIT_MODE) != null) {
            PortletURL url = res.createActionURL();
            //url.setParameter("eatme", "yeah");
            bar.append("<a style=\"text-decoration: underline;\" href=\"" + url.toString() + "\">Edit column layout</a>");
        }
        bar.append(barView.doEndBorder(event, this));


        component.doRender(event);
        bar.append(component.getBufferedOutput(req));

        bar.append(barView.doEnd(event, this));

        setBufferedOutput(req, bar);
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }


}
