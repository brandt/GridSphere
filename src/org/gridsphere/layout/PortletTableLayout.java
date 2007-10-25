/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridsphere.layout;

import org.gridsphere.layout.view.TableLayoutView;
import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.portlet.service.PortletServiceException;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridsphere.portletcontainer.GridSphereEvent;
import org.gridsphere.portletcontainer.impl.ApplicationPortletImpl;
import org.gridsphere.provider.portlet.jsr.PortletServlet;
import org.gridsphere.services.core.jcr.ContentDocument;
import org.gridsphere.services.core.jcr.ContentException;
import org.gridsphere.services.core.jcr.JCRService;
import org.gridsphere.services.core.registry.PortletRegistryService;

import javax.portlet.PortletRequest;
import java.io.Serializable;
import java.util.*;

/**
 * The <code>PortletTableLayout</code> is a concrete implementation of the <code>PortletFrameLayout</code>
 * that organizes portlets into a grid with a provided number of columns.
 * Portlets are arranged in column-wise order starting from the left most column.
 */
public class PortletTableLayout extends PortletFrameLayout implements Serializable, Cloneable {

    public static final String PORTLET_COL = "gs_col";
    public static final String PORTLET_ADD_PORTLET = "gs_addPortlet";
    public static final String PORTLET_NO_ACTION = "gs_none";
    public static final String PORTLET_ADD_CONTENT = "gs_addContent";
    public static final String PORTLET_ADD_URL = "gs_addUrl";
    public static final String PORTLET_ADD_URL_HEIGHT = "gs_UrlHeight";

    protected transient TableLayoutView tableView = null;

    protected transient PortletRegistryService registryService;
    protected transient JCRService contentService;

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

    public List<ComponentIdentifier> init(PortletRequest req, List<ComponentIdentifier> list) {
        try {
            registryService = (PortletRegistryService) PortletServiceFactory.createPortletService(PortletRegistryService.class, true);
            contentService = (JCRService) PortletServiceFactory.createPortletService(JCRService.class, true);
        } catch (PortletServiceException e) {
            log.error("Unable to create instance of PortletRegistryService!");
        }
        tableView = (TableLayoutView) getRenderClass(req, "TableLayout");
        return super.init(req, list);
    }

    private PortletComponent getMaximizedComponent(List<PortletComponent> components) {
        PortletComponent p;
        for (PortletComponent component : components) {
            p = component;
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

        PortletRequest req = event.getActionRequest();

        String portletId = req.getParameter(PORTLET_ADD_PORTLET);

        if (portletId.equals(PORTLET_NO_ACTION)) return;

        String colStr = req.getParameter(PORTLET_COL);
        int col = Integer.valueOf(colStr);

        // first loop thru rows to see if one is empty for the requested column
        if (!components.isEmpty()) {
            Object o = components.get(0);
            if (o instanceof PortletFrameLayout) {
                PortletFrameLayout r = (PortletFrameLayout) o;
                List<PortletComponent> cols = r.getPortletComponents();

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

    protected void addContent(GridSphereEvent event) {

        PortletRequest req = event.getActionRequest();

        String textFile = req.getParameter(PORTLET_ADD_CONTENT);

        if (textFile.equals(PORTLET_NO_ACTION)) return;

        String colStr = req.getParameter(PORTLET_COL);
        int col = Integer.valueOf(colStr);

        // first loop thru rows to see if one is empty for the requested column
        if (!components.isEmpty()) {
            Object o = components.get(0);
            if (o instanceof PortletFrameLayout) {
                PortletFrameLayout r = (PortletFrameLayout) o;
                List<PortletComponent> cols = r.getPortletComponents();

                Object c = cols.get(col);

                if (c instanceof PortletFrameLayout) {
                    PortletFrameLayout existingColumn = (PortletFrameLayout) c;

                    if (!textFile.equals("")) {
                        PortletContent content = new PortletContent();
                        content.setInclude("jcr://" + textFile);
                        existingColumn.addPortletComponent(content);
                    }
                }
            }
        }
        req.setAttribute(SportletProperties.INIT_PAGE, "true");
    }

    protected void addUrl(GridSphereEvent event) {
        PortletRequest req = event.getActionRequest();
        String url = req.getParameter(PORTLET_ADD_URL);
        String height = req.getParameter(PORTLET_ADD_URL_HEIGHT);
        if (url.equals("")) return;
        String colStr = req.getParameter(PORTLET_COL);
        int col = Integer.valueOf(colStr);

        // first loop thru rows to see if one is empty for the requested column
        if (!components.isEmpty()) {
            Object o = components.get(0);
            if (o instanceof PortletFrameLayout) {
                PortletFrameLayout r = (PortletFrameLayout) o;
                List<PortletComponent> cols = r.getPortletComponents();

                Object c = cols.get(col);

                if (c instanceof PortletFrameLayout) {
                    PortletFrameLayout existingColumn = (PortletFrameLayout) c;

                    if (!url.equals("")) {
                        PortletContent content = new PortletContent();
                        content.setInclude(url);
                        content.setHeight(height);
                        existingColumn.addPortletComponent(content);
                    }
                }
            }
        }
        req.setAttribute(SportletProperties.INIT_PAGE, "true");


    }

    public void customActionPerformed(GridSphereEvent event) {
        if (event.hasAction()) {
            if (event.getAction().getName().equals(PORTLET_ADD_PORTLET)) {
                addPortlet(event);
            }
            if (event.getAction().getName().equals(PORTLET_ADD_CONTENT)) {
                addContent(event);
            }
            if (event.getAction().getName().equals(PORTLET_ADD_URL)) {
                addUrl(event);
            }
        }
    }

    public Set<ApplicationPortlet> getAllPortletsToAdd(GridSphereEvent event) {
        PortletRequest req = event.getRenderRequest();
        SortedSet<ApplicationPortlet> result = new TreeSet<ApplicationPortlet>();
        //Set<ApplicationPortlet> result = new HashSet<ApplicationPortlet>();
        Collection<ApplicationPortlet> appColl = registryService.getAllApplicationPortlets();
        Locale locale = req.getLocale();
        for (ApplicationPortlet appPortlet : appColl) {
            String concID = appPortlet.getConcretePortletID();
            // we don't want to list PortletServlet loader!
            if (concID.startsWith(PortletServlet.class.getName())) continue;
            ApplicationPortletImpl api = (ApplicationPortletImpl) appPortlet;
            api.setCompareLocale(locale);
            result.add(appPortlet);
        }
        return result;
    }

    public Map<String, String> getAllContentToAdd(GridSphereEvent event) {
        Map<String, String> allContent = new HashMap<String, String>();
        try {
            List<ContentDocument> docs = contentService.listChildContentDocuments("");
            for (ContentDocument doc : docs) {
                allContent.put(doc.getTitle(), doc.getTitle());
            }
        } catch (ContentException e) {
            e.printStackTrace();
        }
        return allContent;
    }

    public void doRender(GridSphereEvent event) {
        super.doRender(event);
        PortletRequest req = event.getRenderRequest();
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
                        table.append(tableView.doRenderSelectPortlets(event, this));
                        table.append(tableView.doRenderSelectContent(event, this));
                    }
                    setBufferedOutput(req, table);
                    return;
                }
            }
        }

        table.append(tableView.doStart(event, this));

        for (PortletComponent component : components) {
            p = component;
            table.append(tableView.doStartBorder(event, p));
            if (p.getVisible()) {
                p.doRender(event);
                table.append(p.getBufferedOutput(req));
            }
            table.append(tableView.doEndBorder(event, this));
        }

        req.setAttribute(SportletProperties.COMPONENT_ID, componentIDStr);

        /** setup bottom add portlet and content listboxes */
        if (((canModify) && (!hasFrameMaximized)) || (req.getAttribute(SportletProperties.LAYOUT_EDIT_MODE) != null)) {

            table.append(tableView.doRenderSelectPortlets(event, this));
            table.append(tableView.doRenderSelectContent(event, this));
        }

        table.append(tableView.doEnd(event, this));
        setBufferedOutput(req, table);
    }

    public Object clone() throws CloneNotSupportedException {
        PortletTableLayout g = (PortletTableLayout) super.clone();
        g.style = this.style;
        return g;
    }
}
