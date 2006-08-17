/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.layout.view.TableLayoutView;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridlab.gridsphere.portletcontainer.ConcretePortlet;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;
import org.gridlab.gridsphere.services.core.registry.PortletRegistryService;

import java.io.Serializable;
import java.util.*;

/**
 * The <code>PortletTableLayout</code> is a concrete implementation of the <code>PortletFrameLayout</code>
 * that organizes portlets into a grid with a provided number of columns.
 * Portlets are arranged in column-wise order starting from the left most column.
 */
public class PortletTableLayout extends PortletFrameLayout implements Serializable, Cloneable {

    public static final String PORTLET_COL = "gs_col";
    public static final String PORTLET_ADD_ACTION = "gs_addPortlet";
    public static final String PORTLET_NO_ACTION = "gs_none";

    protected transient TableLayoutView tableView = null;

    protected transient PortletRegistryService registryService;
    /**
     * css Style of the table
     */
    protected String style = "";

    /**
     * Constructs an instance of PortletTableLayout
     */
    public PortletTableLayout() {
    }

    /**
     * Returns the CSS style name for the grid-layout.
     *
     * @return css style name
     */
    public String getStyle() {
        return style;
    }

    /**
     * Sets the CSS style name for the grid-layout.
     * This needs to be set if you want to have transparent portlets, if there is
     * no background there can't be a real transparent portlet.
     * Most likely one sets just the background in that one.
     *
     * @param style css style of the that layout
     */
    public void setStyle(String style) {
        this.style = style;
    }

    public List init(PortletRequest req, List list) {
        try {
            registryService = (PortletRegistryService)PortletServiceFactory.createPortletService(PortletRegistryService.class, true);
        } catch (PortletServiceException e) {
            log.error("Unable to create instance of PortletRegistryService!");
        }
        tableView = (TableLayoutView)getRenderClass(req, "TableLayout");
        return super.init(req, list);
    }

    private PortletComponent getMaximizedComponent(List components) {
        PortletComponent p;
        for (int i = 0; i < components.size(); i++) {
            p = (PortletComponent) components.get(i);
            if (p instanceof PortletLayout) {
                PortletComponent layout = this.getMaximizedComponent(((PortletLayout) p).getPortletComponents());
                if (layout != null) {
                    p = layout;
                }
            }
            if (p.getWidth().equals("100%")) {
                return p;
            }
        }
        return null;
    }

    protected void addPortlet(GridSphereEvent event) {

        PortletRequest req = event.getPortletRequest();

        String portletId = req.getParameter(PORTLET_ADD_ACTION);

        if (portletId.equals(PORTLET_NO_ACTION)) return;

        String colStr = req.getParameter(PORTLET_COL);
        int col = Integer.valueOf(colStr).intValue();

        // first loop thru rows to see if one is empty for the requested column
        if (!components.isEmpty()) {
            Object o = components.get(0);
            if (o instanceof PortletFrameLayout) {
                PortletFrameLayout r = (PortletFrameLayout) o;
                List cols = r.getPortletComponents();

                Object c = cols.get(col);

                if (c instanceof PortletFrameLayout) {
                    PortletFrameLayout existingColumn = (PortletFrameLayout) c;

                    if (!portletId.equals("")) {
                        PortletFrame frame = new PortletFrame();
                        frame.setPortletClass(portletId);
                        existingColumn.addPortletComponent(frame);
                    }
                }
            }
        }

        req.setAttribute(SportletProperties.INIT_PAGE, "true");
    }

    public void customActionPerformed(GridSphereEvent event) {
        if (event.hasAction()) {
            if (event.getAction().getName().equals(PORTLET_ADD_ACTION)) {
                addPortlet(event);
            }
        }
    }

    public Map getAllPortletsToAdd(GridSphereEvent event) {
        PortletRequest req = event.getPortletRequest();

        Map allPortlets = new HashMap();
        Collection appColl = registryService.getAllApplicationPortlets();
        Locale locale = req.getLocale();
        Iterator appIt = appColl.iterator();
        while (appIt.hasNext()) {
            ApplicationPortlet appPortlet = (ApplicationPortlet) appIt.next();
            List concPortlets = appPortlet.getConcretePortlets();
            Iterator cit = concPortlets.iterator();
            while (cit.hasNext()) {
                ConcretePortlet conc = (ConcretePortlet) cit.next();
                String concID = conc.getConcretePortletID();
                // we don't want to list PortletServlet loader!
                //if (concID.startsWith(PortletServlet.class.getName())) continue;
                String dispName = conc.getDisplayName(locale);
                allPortlets.put(concID, dispName);
            }
        }
        return allPortlets;
    }

    /*
    public Map getAvailablePortletsToAdd(GridSphereEvent event) {
        PortletRequest req = event.getPortletRequest();
        if (req.getAttribute(SportletProperties.LAYOUT_EDIT_MODE) != null) return getAllPortletsToAdd(event);
        PortletRegistry registry = PortletRegistry.getInstance();
        Locale locale = req.getLocale();
        List groups = (List) req.getGroups();
        Map availPortlets = new HashMap();
        Iterator it = groups.iterator();
        while (it.hasNext()) {
            String group = (String) it.next();
            PortletGroup g = groupService.getGroup(group);
            //System.err.println("group= " + g.getName());

            if ((req.getAttribute(SportletProperties.LAYOUT_EDIT_MODE) == null) && (g.equals(req.getAttribute(SportletProperties.PORTLET_GROUP)))) continue;
            Iterator sit = g.getPortletRoleList().iterator();
            //PortletRole role = (PortletRole) groups.get(g);
            while (sit.hasNext()) {
                SportletRoleInfo info = (SportletRoleInfo) sit.next();
                String appID = PortletRegistry.getApplicationPortletID(info.getPortletClass());
                //System.err.println("info class= " + info.getPortletClass());
                PortletRole reqRole = info.getSportletRole();
                //System.err.println("role= " + info.getSportletRole());
                if (req.getRoles().contains(reqRole.getName()))
                    if (!availPortlets.containsKey(appID)) {
                        ApplicationPortlet appPortlet = registry.getApplicationPortlet(appID);
                        if (appPortlet != null) {
                            //System.err.println("appID= " + appID);
                            ConcretePortlet concPortlet = appPortlet.getConcretePortlet(appID);
                            if (concPortlet != null) {
                                String dispName = concPortlet.getDisplayName(locale);
                                //System.err.println("show portlet = " + dispName);
                                availPortlets.put(appID, dispName);
                            }
                        } else {
                            //System.err.println("app portlet was null for " + appID);
                        }
                    }

            }
        }
        return availPortlets;
    }
    */

    public void doRender(GridSphereEvent event) {
        super.doRender(event);
        PortletRequest req = event.getPortletRequest();
        StringBuffer table = new StringBuffer();
        PortletComponent p;



        // check if one window is maximized

        for (int i = 0; i < components.size(); i++) {
            p = (PortletComponent) components.get(i);
            if (p instanceof PortletLayout) {
                PortletComponent maxi = getMaximizedComponent(components);
                if (maxi != null) {
                    table.append(tableView.doStartMaximizedComponent(event, this));
                    maxi.doRender(event);
                    table.append(maxi.getBufferedOutput(req));
                    table.append(tableView.doEndMaximizedComponent(event, this));
                    if (((canModify) && (!hasFrameMaximized)) || (req.getAttribute(SportletProperties.LAYOUT_EDIT_MODE) != null)) {
                        table.append(tableView.doRenderUserSelects(event, this));
                    }
                    setBufferedOutput(event.getPortletRequest(), table);
                    return;
                }
            }
        }

        table.append(tableView.doStart(event, this));

        for (int i = 0; i < components.size(); i++) {
            p = (PortletComponent)components.get(i);
            table.append(tableView.doStartBorder(event, p));
            if (p.getVisible()) {
                p.doRender(event);
                table.append(p.getBufferedOutput(event.getPortletRequest()));
            }
            table.append(tableView.doEndBorder(event, this));
        }

        // you have to make sure the component id is reset again to current component
        String compVar = (String)req.getAttribute(SportletProperties.COMPONENT_ID_VAR);
        if (compVar == null) compVar = SportletProperties.COMPONENT_ID;
        req.setAttribute(compVar, componentIDStr);

        /** setup bottom add portlet listbox */
        if (((canModify) && (!hasFrameMaximized)) || (req.getAttribute(SportletProperties.LAYOUT_EDIT_MODE) != null)) {
            table.append(tableView.doRenderUserSelects(event, this));
        }

        table.append(tableView.doEnd(event, this));
        setBufferedOutput(event.getPortletRequest(), table);
    }

    public Object clone() throws CloneNotSupportedException {
        PortletTableLayout g = (PortletTableLayout) super.clone();
        g.style = this.style;
        return g;
    }


}
