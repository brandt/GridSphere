/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.layout.event.PortletComponentEvent;
import org.gridlab.gridsphere.layout.event.PortletTabEvent;
import org.gridlab.gridsphere.layout.event.impl.PortletTabEventImpl;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.portlet.PortletURI;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * A <code>PortletTab</code> represents the visual tab graphical interface and is contained
 * by a {@link PortletTabbedPane}. A tab contains a title and any additional
 * portlet component, such as another tabbed pane if a double level
 * tabbed pane is desired.
 */
public class PortletTab extends BasePortletComponent implements Serializable, Cloneable {

    private String title = "?";
    private List titles = new ArrayList();
    private transient boolean selected = false;
    private PortletComponent portletComponent = null;

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

    /**
     * Returns the portlet tab title
     *
     * @return the portlet tab title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the portlet tab title
     *
     * @param title the portlet tab title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle(String lang) {
        if (lang == null) throw new IllegalArgumentException("lang is NULL");
        Iterator it = titles.iterator();
        String defTitle = title;
        while (it.hasNext()) {
            PortletTitle t = (PortletTitle) it.next();
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
            PortletTitle t = (PortletTitle) it.next();
            if (lang.equalsIgnoreCase(t.getLang())) {
                found = true;
                t.setText(title);
            }
        }
        if (!found) {
            PortletTitle t = new PortletTitle();
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
        //PortletRequest req = event.getPortletRequest();
        PortletResponse res = event.getPortletResponse();
        //req.setAttribute(SportletProperties.COMPONENT_ID, componentIDStr);
        PortletURI portletURI = res.createURI();
        portletURI.addParameter(SportletProperties.COMPONENT_ID, componentIDStr);
        return portletURI.toString();
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
        portletComponent.setTheme(theme);
        list = portletComponent.init(req, list);
        portletComponent.addComponentListener(this);
        portletComponent.setParentComponent(this);
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
     * @throws PortletLayoutException if a layout error occurs during rendering
     * @throws IOException            if an I/O error occurs during rendering
     */
    public void actionPerformed(GridSphereEvent event) throws PortletLayoutException, IOException {

        super.actionPerformed(event);

        PortletComponentEvent compEvt = event.getLastRenderEvent();
        PortletTabEvent tabEvent = new PortletTabEventImpl(this, event.getPortletRequest(), PortletTabEvent.TabAction.TAB_SELECTED, COMPONENT_ID);
        List l = Collections.synchronizedList(listeners);

        synchronized (l) {
            Iterator it = l.iterator();
            PortletComponent comp;
            while (it.hasNext()) {
                comp = (PortletComponent) it.next();
                event.addNewRenderEvent(tabEvent);
                comp.actionPerformed(event);
            }
        }
    }

    /**
     * Renders the portlet tab component
     *
     * @param event a gridsphere event
     * @throws PortletLayoutException if a layout error occurs during rendering
     * @throws IOException            if an I/O error occurs during rendering
     */
    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        PortletRole userRole = event.getPortletRequest().getRole();
        if (userRole.compare(userRole, requiredRole) >= 0) {
            portletComponent.doRender(event);
        }
    }

    public Object clone() throws CloneNotSupportedException {
        PortletTab t = (PortletTab) super.clone();
        t.portletComponent = (this.portletComponent == null) ? null : (PortletComponent) this.portletComponent.clone();
        t.selected = this.selected;
        List stitles = Collections.synchronizedList(titles);
        synchronized (stitles) {
            t.titles = new ArrayList(stitles.size());
            for (int i = 0; i < stitles.size(); i++) {
                PortletTitle title = (PortletTitle) stitles.get(i);
                t.titles.add(title.clone());
            }
        }
        return t;
    }
}
