/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: PortletTab.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.layout;

import org.gridsphere.layout.event.PortletTabEvent;
import org.gridsphere.layout.event.impl.PortletTabEventImpl;
import org.gridsphere.portlet.jsrimpl.SportletProperties;
import org.gridsphere.portlet.service.spi.impl.descriptor.Description;
import org.gridsphere.portletcontainer.GridSphereEvent;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.Serializable;
import java.util.*;

/**
 * A <code>PortletTab</code> represents the visual tab graphical interface and is contained
 * by a {@link PortletTabbedPane}. A tab contains a title and any additional
 * portlet component, such as another tabbed pane if a double level
 * tabbed pane is desired.
 */
public class PortletTab extends BasePortletComponent implements Serializable, Cloneable, Comparator {

    public static final int DEFAULT_USERTAB_ORDER = 20;
    private List titles = new ArrayList();
    private transient boolean selected = false;
    private String url = null;
    private PortletComponent portletComponent = null;
    private int tabOrder = 50;
    private String align = "left";
    private boolean outline = true;
    private String padding = null;
    private String include = null;
    private boolean display = true;

    //protected StringBuffer tab = new StringBuffer();
    /**
     * Constructs an instance of PortletTab
     */
    public PortletTab() {
    }

    /**
     * Constructs an instance of PortletTab with the supplied title and
     * portlet component.
     *
     * @param titles           the titles of the portlet tab
     * @param portletComponent any portlet component to represent beneath the tab
     */
    public PortletTab(List titles, PortletComponent portletComponent) {
        this.titles = titles;
        this.portletComponent = portletComponent;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public boolean getDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public String getInclude() {
        return include;
    }

    public void setInclude(String include) {
        this.include = include;
    }

    public boolean getOutline() {
        return outline;
    }

    public void setOutline(boolean outline) {
        this.outline = outline;
    }

    public String getPadding() {
        return padding;
    }

    public void setPadding(String padding) {
        this.padding = padding;
    }

    public int getTabOrder() {
        return tabOrder;
    }

    public void setTabOrder(int tabOrder) {
        this.tabOrder = tabOrder;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    /**
     * Returns the portlet tab title
     *
     * @return the portlet tab title
     */
    public List getTitles() {
        return titles;
    }

    /**
     * Sets the portlet tab title
     *
     * @param titles the portlet tab title
     */
    public void setTitles(List titles) {
        this.titles = titles;
    }

    public String getTitle(String lang) {
        if (lang == null) throw new IllegalArgumentException("lang is NULL");
        Iterator it = titles.iterator();
        String defTitle = null;
        while (it.hasNext()) {
            Description t = (Description) it.next();
            if (t.getLang() == null) t.setLang(Locale.ENGLISH.getLanguage());
            if (lang.equals(t.getLang())) return t.getText();
            if (t.getLang().regionMatches(0, lang, 0, 2)) return t.getText();
            if (t.getLang().equals(Locale.ENGLISH.getLanguage())) defTitle = t.getText();
        }
        return defTitle;
    }

    public void setTitle(String lang, String title) {
        Iterator it = titles.iterator();
        boolean found = false;
        if (lang == null) throw new IllegalArgumentException("lang is NULL");
        if (title == null) throw new IllegalArgumentException("title is NULL");

        while (it.hasNext()) {
            Description t = (Description) it.next();
            if (lang.equalsIgnoreCase(t.getLang())) {
                found = true;
                t.setText(title);
            }
        }
        if (!found) {
            Description t = new Description();
            t.setLang(lang);
            t.setText(title);
            titles.add(t);
        }
    }

    /**
     * Creates the portlet tab title links that are rendered by the
     * {@link PortletTabbedPane}
     *
     * @param event the gridsphere event
     */
    public String createTabTitleLink(GridSphereEvent event) {
        super.doRender(event);
        if (url != null) return url;
        RenderResponse res = event.getRenderResponse();
        RenderRequest req = event.getRenderRequest();
        PortletURL portletURL = res.createActionURL();


        //portletURL.setParameter(this.getComponentIDVar(req), componentIDStr);

        String extraQuery = (String)req.getAttribute(SportletProperties.EXTRA_QUERY_INFO);
        if (extraQuery != null) {
            return portletURL.toString() + extraQuery;
        }
        return portletURL.toString();
    }

    /**
     * Sets the selected flag used in determining if this tab is selected and
     * should be rendered
     *
     * @param selected the selected flag is true if this tag is currently selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Returns the selected flag used in determining if this tab is selected and
     * hence rendered
     *
     * @return true if the tab is selected, false otherwise
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets the concrete portlet component contained by the portlet tab
     *
     * @param portletComponent a concrete portlet component instance
     */
    public void setPortletComponent(PortletComponent portletComponent) {
        this.portletComponent = portletComponent;
    }

    /**
     * Returns the concrete portlet component contained by the portlet tab
     *
     * @return the concrete portlet component instance conatined by this tab
     */
    public PortletComponent getPortletComponent() {
        return portletComponent;
    }

    public void removePortletComponent() {
        this.portletComponent = null;
    }

    /**
     * Initializes the portlet tab. Since the components are isolated
     * after Castor unmarshalls from XML, the ordering is determined by a
     * passed in List containing the previous portlet components in the tree.
     *
     * @param list a list of component identifiers
     * @return a list of updated component identifiers
     * @see ComponentIdentifier
     */
    public List init(PortletRequest req, List list) {
        list = super.init(req, list);
        ComponentIdentifier compId = new ComponentIdentifier();
        compId.setPortletComponent(this);
        compId.setComponentID(list.size());
        compId.setComponentLabel(label);
        compId.setClassName(this.getClass().getName());
        list.add(compId);
        if (portletComponent != null) {
            list = portletComponent.init(req, list);
            portletComponent.addComponentListener(this);
            portletComponent.setParentComponent(this);
        }
        return list;
    }

    public void remove(PortletComponent pc, PortletRequest req) {
        portletComponent = null;
        parent.remove(this, req);
    }

    /**
     * Performs an action on this portlet tab component
     *
     * @param event a gridsphere event
     */
    public void actionPerformed(GridSphereEvent event) {

        super.actionPerformed(event);

        // pop last event from stack
        event.getLastRenderEvent();
        PortletTabEvent tabEvent = new PortletTabEventImpl(this, event.getActionRequest(), PortletTabEvent.TabAction.TAB_SELECTED, COMPONENT_ID);

        Iterator it = listeners.iterator();
        PortletComponent comp;
        while (it.hasNext()) {
            comp = (PortletComponent) it.next();
            event.addNewRenderEvent(tabEvent);
            comp.actionPerformed(event);
        }

    }

    /**
     * Renders the portlet tab component
     *
     * @param event a gridsphere event
     */
    public void doRender(GridSphereEvent event) {
        super.doRender(event);
        PortletRequest req = event.getRenderRequest();
        if (portletComponent == null) return;
        StringBuffer tab = new StringBuffer();
        if ((requiredRoleName.equals("")) || (req.isUserInRole(requiredRoleName))) {
            portletComponent.doRender(event);
            tab.append(portletComponent.getBufferedOutput(req));
        }
        setBufferedOutput(req, tab);
    }



    public int compare(Object left, Object right) {
        int leftID = ((PortletTab) left).getTabOrder();
        int rightID = ((PortletTab) right).getTabOrder();
        int result;
        if (leftID < rightID) {
            result = -1;
        } else if (leftID > rightID) {
            result = 1;
        } else {
            result = 0;
        }
        return result;
    }


    public Object clone() throws CloneNotSupportedException {
        PortletTab t = (PortletTab) super.clone();
        t.tabOrder = this.tabOrder;
        t.url = this.url;
        t.portletComponent = (this.portletComponent == null) ? null : (PortletComponent) this.portletComponent.clone();
        t.selected = this.selected;
        t.titles = new ArrayList(titles.size());
        for (int i = 0; i < titles.size(); i++) {
            Description title = (Description) titles.get(i);
            t.titles.add(title.clone());
        }
        return t;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("\ntab order=").append(tabOrder);
        sb.append("\ntab selected=").append(selected);
        Iterator it = titles.iterator();
        while (it.hasNext()) {
            Description desc = (Description)it.next();
            sb.append("\nlang=").append(desc.getLang());
            sb.append("\ntitle=").append(desc.getText());
        }
        return sb.toString();
    }
}
