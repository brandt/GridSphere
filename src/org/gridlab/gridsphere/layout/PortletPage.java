/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;
import org.gridlab.gridsphere.portletcontainer.PortletInvoker;
import org.gridlab.gridsphere.layout.event.PortletComponentEvent;
import org.gridlab.gridsphere.services.core.cache.CacheService;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.*;

/**
 * The <code>PortletPage</code> is the generic container for a collection of
 * concrete portlet components and provides lifecycle methods for traversing
 * the tree of components and handling actions and performing rendering.
 */
public class PortletPage implements Serializable, Cloneable {

    //private PortletLog log = SportletLog.getInstance(PortletPage.class);

    protected int COMPONENT_ID = -1;

    protected transient CacheService cacheService = null;

    protected PortletContainer footerContainer = null;
    protected PortletContainer headerContainer = null;
    protected PortletContainer bodyContainer = null;
    protected PortletTabbedPane tabbedPane = null;

    // The component ID's of each of the layout components
    protected List componentIdentifiers = null;

    // The list of portlets a user has-- generally contained within a PortletFrame/PortletTitleBar combo
    //protected List portlets = new ArrayList();

    protected String keywords = "";
    protected String title = "";
    protected String theme = "default";

    //private String layoutMappingFile = GridSphereConfig.getServletContext().getRealPath("/WEB-INF/mapping/layout-mapping.xml");
    private String layoutDescriptor = null;

    private Hashtable labelsHash = new Hashtable();
    private Hashtable portletHash = new Hashtable();
    /**
     * Constructs an instance of PortletPage
     */
    public PortletPage() {
    }

    public void setLayoutDescriptor(String layoutDescriptor) {
        this.layoutDescriptor = layoutDescriptor;
    }

    public String getLayoutDescriptor() {
        return layoutDescriptor;
    }

    /**
     * Sets the portlet container title
     *
     * @param title the portlet container title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the portlet container title
     *
     * @return the portlet container title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the theme of this portlet component
     *
     * @param theme the theme of this portlet component
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     * Return the theme of this portlet component
     *
     * @return the theme of this portlet component
     */
    public String getTheme() {
        return theme;
    }

    /**
     * Returns the keywords used in rendering output
     *
     * @return keywords
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     * Sets the keywords used in rendering output
     *
     * @param keywords  used in rendering output
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    /**
     * Sets the page header
     *
     * @param headerContainer a portlet container with header components
     */
    public void setPortletHeader(PortletContainer headerContainer) {
        this.headerContainer = headerContainer;
    }

    /**
     * Returns the page header
     *
     * @return a portlet container with header components
     */
    public PortletContainer getPortletHeader() {
        return headerContainer;
    }

    /**
     * Sets the page footer
     *
     * @param footerContainer a portlet container with footer components
     */
    public void setPortletFooter(PortletContainer footerContainer) {
        this.footerContainer = footerContainer;
    }

    /**
     * Returns the page footer
     *
     * @return a portlet container with footer components
     */
    public PortletContainer getPortletFooter() {
        return footerContainer;
    }

    /**
     * Sets the page body
     *
     * @param bodyContainer a portlet container with body components
     */
    public void setPortletBody(PortletContainer bodyContainer) {
        this.bodyContainer = bodyContainer;
    }

    /**
     * Returns the page body
     *
     * @return a portlet container with body components
     */
    public PortletContainer getPortletBody() {
        return bodyContainer;
    }

    public void setPortletTabbedPane(PortletTabbedPane tabbedPane) {
        this.tabbedPane = tabbedPane;
    }


    public PortletTabbedPane getPortletTabbedPane() {
        return tabbedPane;
    }

    /**
     * Returns the list of portlet component identifiers
     *
     * @return the list of portlet component identifiers
     * @see ComponentIdentifier
     */
    public List getComponentIdentifierList() {
        return componentIdentifiers;
    }

    /**
     * Sets the list of portlet component identifiers
     *
     * @param componentIdentifiers a list of portlet component identifiers
     * @see ComponentIdentifier
     */
    public void setComponentIdentifierList(List componentIdentifiers) {
        this.componentIdentifiers = componentIdentifiers;
    }

    /**
     * Returns the associated portlet component id
     *
     * @return the portlet component id
     */
    public int getComponentID() {
        return COMPONENT_ID;
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
    public List init(PortletRequest req, List list) {

        PortletServiceFactory factory = SportletServiceFactory.getInstance();
        try {
            cacheService = (CacheService)factory.createPortletService(CacheService.class, null, true);
        } catch (PortletServiceException e) {
            System.err.println("Unable to init Cache service! " + e.getMessage());
        }

        componentIdentifiers = new Vector();

        if (headerContainer != null) {
            headerContainer.setTheme(theme);
            list = headerContainer.init(req, list);
        }
        if (tabbedPane != null) {
            tabbedPane.setTheme(theme);
            list = tabbedPane.init(req, list);
        }

        if (bodyContainer != null) {
            bodyContainer.setTheme(theme);
            list = bodyContainer.init(req, list);
        }


        if (footerContainer != null) {
            footerContainer.setTheme(theme);
            list = footerContainer.init(req, list);
        }

        componentIdentifiers = list;

        // Now go thru and create a labels hash

        Iterator it = componentIdentifiers.iterator();
        while (it.hasNext()) {
            ComponentIdentifier cid = (ComponentIdentifier)it.next();
            String compLabel = cid.getComponentLabel();
            if (cid.hasPortlet()) {
                String portletClass = cid.getPortletClass();
                portletHash.put(portletClass, new Integer(cid.getComponentID()));
            }
            if (!compLabel.equals("")) {
                // create a labels to integer component id mapping
                labelsHash.put(compLabel, new Integer(cid.getComponentID()));
            }
        }

        return componentIdentifiers;
    }

    /**
     * Performs {@link org.gridlab.gridsphere.portlet.Portlet#login(PortletRequest) login}
     * on all the portlets conatined by this PortletPage
     *
     * @param event a gridsphere event
     * @throws PortletException if an error occurs while invoking login on the portlets
     * @see <a href="org.gridlab.gridsphere.portlet.Portlet#login">Portlet.login(PortletRequest)</a>
     */
    public void loginPortlets(GridSphereEvent event) throws PortletException, IOException {
        Iterator it = componentIdentifiers.iterator();
        ComponentIdentifier cid = null;
        PortletFrame f = null;
        String id = event.getPortletRequest().getPortletSession(true).getId();
        while (it.hasNext()) {
            cid = (ComponentIdentifier) it.next();
            PortletComponent pc = cid.getPortletComponent();
            if (pc instanceof PortletFrame) {
                f = (PortletFrame) pc;
                // remove any cached portlet
                cacheService.removeCached(f.getPortletClass() + id);
                //portlets.add(f.getPortletClass());
                PortletInvoker.login(f.getPortletClass(), event.getPortletRequest(), event.getPortletResponse());
            }
        }
    }

    /**
     * Performs {@link org.gridlab.gridsphere.portlet.Portlet#logout}
     * on all the portlets conatined by this PortletPage
     *
     * @param event a gridsphere event
     * @throws PortletException if an error occurs while invoking login on the portlets
     * @see <a href="org.gridlab.gridsphere.portlet.Portlet#logout">Portlet.logout(PortletSession)</a>
     */
    public void logoutPortlets(GridSphereEvent event) throws IOException, PortletException {
        Iterator it = componentIdentifiers.iterator();
        ComponentIdentifier cid = null;
        PortletFrame f = null;
        PortletRequest req = event.getPortletRequest();
        PortletResponse res = event.getPortletResponse();
        String id = req.getPortletSession(true).getId();
        while (it.hasNext()) {
            cid = (ComponentIdentifier) it.next();
            if (cid.getPortletComponent() instanceof PortletFrame) {
                f = (PortletFrame) cid.getPortletComponent();
                // remove any cached portlet
                cacheService.removeCached(f.getPortletClass() + id);
                PortletInvoker.logout(f.getPortletClass(), req, res);
            }
        }
    }

    /**
     * Destroys this portlet container
     */
    public void destroy() {
        if (headerContainer != null) headerContainer.destroy();
        if (tabbedPane != null) tabbedPane.destroy();
        if (bodyContainer != null) bodyContainer.destroy();
        if (footerContainer != null) footerContainer.destroy();
    }

    /**
     * Performs an action by performing an action on the appropriate portlet component
     * contained by this PortletPage
     *
     * @param event a gridsphere event
     * @throws PortletLayoutException if a layout error occurs during rendering
     * @throws IOException if an I/O error occurs during rendering
     */
    public void actionPerformed(GridSphereEvent event) throws PortletLayoutException, IOException {

        // if there is a layout action do it!
        if (!event.getPortletComponentID().equals("")) {

            // the component id determines where in the list the portlet component is

            // first check the hash
            ComponentIdentifier compId = null;
            String cid = event.getPortletComponentID();

            int compIntId = -1;
            if (labelsHash.containsKey(cid)) {
                Integer cint = (Integer) labelsHash.get(cid);
                compIntId =  cint.intValue();
                compId = (ComponentIdentifier) componentIdentifiers.get(compIntId);
            } else {
                // try converting to integer
                try {
                    compIntId = Integer.parseInt(cid);
                    // number can't exceed available components
                    if (compIntId > componentIdentifiers.size()) {
                        compIntId = -1;
                    } else {
                        compId = (ComponentIdentifier) componentIdentifiers.get(compIntId);
                    }
                } catch (NumberFormatException e) {
                    compIntId = -1;
                }
            }

            if (compId != null) {
                PortletComponent comp = compId.getPortletComponent();
                // perform an action if the component is non null
                if (comp != null) {
                    //System.err.println("Calling action performed on " + comp.getClass().getName() + ":" + comp.getName());
                    comp.actionPerformed(event);
                }
            }
        }

    }

    /**
     * Renders the portlet cotainer by performing doRender on all portlet components
     *
     * @param event a gridsphere event
     * @throws PortletLayoutException if a layout error occurs during rendering
     * @throws IOException if an I/O error occurs during rendering
     */
    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {

        PortletRequest req = event.getPortletRequest();
        Client client = req.getClient();

        // handle any client logic to determin which markup to display

        doRenderHTML(event);
    }

    public void doRenderHTML(GridSphereEvent event) throws PortletLayoutException, IOException {

        PortletResponse res = event.getPortletResponse();
        PrintWriter out = null;

        try {
            out = res.getWriter();
        } catch (IllegalStateException e) {
            // means the writer has already been obtained
            return;
        }
        
        res.setContentType("text/html; charset=utf-8");

        // page header
        //out.println("<?xml version=\"1.0\"?>");
        //out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"" );
        //out.println("\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
        out.println("<html>");
        out.println("<head>");

        out.println("  <title>" + title + "</title>");
        out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>");
        out.println("<meta name=\"keywords\" content=\"" + keywords + "\"/>");
        out.println("  <link type=\"text/css\" href=\"themes/" + theme + "/css" +
                "/default.css\" rel=\"stylesheet\"/>");
        out.println("<script language=\"JavaScript\" src=\"javascript/gridsphere.js\"></script>");
        out.println("</head><body>");

        // A Portal page in 3 lines -- voila!
        //  -------- header ---------
        if (headerContainer != null) headerContainer.doRender(event);
        // ..| tabs | here |....
        if (tabbedPane != null) tabbedPane.doRender(event);
        // The body
        if (bodyContainer != null) bodyContainer.doRender(event);
        //.... the footer ..........
        if (footerContainer != null) footerContainer.doRender(event);

        out.println("</body></html>");
    }

    public Object clone() throws CloneNotSupportedException {
        int i;
        PortletPage c = (PortletPage)super.clone();
        c.theme = theme;
        c.COMPONENT_ID = this.COMPONENT_ID;
        c.theme = this.theme;
        List compList = new Vector(this.componentIdentifiers.size());
        for (i = 0; i < this.componentIdentifiers.size(); i++) {
            ComponentIdentifier cid = (ComponentIdentifier)this.componentIdentifiers.get(i);
            compList.add(new ComponentIdentifier(cid));
        }
        c.componentIdentifiers = compList;
        c.title = title;
        c.headerContainer = (this.headerContainer == null) ? null : (PortletContainer)this.headerContainer.clone();
        c.footerContainer = (this.footerContainer == null ) ? null : (PortletContainer)this.footerContainer.clone();
        c.tabbedPane = (this.tabbedPane == null) ? null : (PortletTabbedPane)this.tabbedPane.clone();
        c.bodyContainer = (this.bodyContainer == null ) ? null : (PortletContainer)this.bodyContainer.clone();
        return c;
    }

    public void save() throws IOException {
        try {
            // save user tab
            PortletTabbedPane myPane = new PortletTabbedPane();
            for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                PortletTab tab = tabbedPane.getPortletTabAt(i);
                if (tab.getCanModify()) {
                    myPane.addTab(tab);
                }
            }
            if (myPane.getTabCount() > 0) {
                String layoutMappingFile = GridSphereConfig.getServletContext().getRealPath("/WEB-INF/mapping/layout-mapping.xml");
                PortletLayoutDescriptor.savePortletTabbedPane(myPane, layoutDescriptor, layoutMappingFile);
            }
        } catch (PersistenceManagerException e) {
            throw new IOException("Unable to save user's tabbed pane: " + e.getMessage());
        }
    }

    /**
     * Processes a message. The message is directed at a concrete portlet with
     * a given concrete portlet ID. If the target ID is "*" the message is delivered
     * to every portlet in the PortletPage.
     * @param concPortletID  The target concrete portlet's ID
     * @param msg  The message to deliver
     * @param event The GridsphereEvent associated with the message delivery
     */
    public void messageEvent(String concPortletID, PortletMessage msg, GridSphereEvent event) throws PortletException {

        // support for broadcast messages
        if (concPortletID.equals("*")) {
            Iterator entryIter = portletHash.entrySet().iterator();
            while (entryIter.hasNext()) {
                Map.Entry entry = (Map.Entry) entryIter.next();
                Integer cint = (Integer) entry.getValue();
                String portletID = (String) entry.getKey();

                int compIntId =  cint.intValue();
                ComponentIdentifier compId = (ComponentIdentifier) componentIdentifiers.get(compIntId);

                if (compId != null) {
                    PortletComponent comp = compId.getPortletComponent();

                    // perform an action if the component is non null
                    if (comp == null) {
                        //log.warn("Event has invalid component id associated with it!");
                    } else {
                        //log.debug("Calling action performed on " + comp.getClass().getName() + ":" + comp.getName());
                        comp.messageEvent(portletID, msg, event);
                    }
                }
            }
            return ;
        }


        // the component id determines where in the list the portlet component is

        // first check the hash

        ComponentIdentifier compId = null;

        int compIntId = -1;
        if (portletHash.containsKey(concPortletID)) {
            Integer cint = (Integer) portletHash.get(concPortletID);
            compIntId =  cint.intValue();
            compId = (ComponentIdentifier) componentIdentifiers.get(compIntId);
        } else {
            throw new PortletException("Delivery of the message "+msg.toString()+" failed: "+concPortletID+" not found");
        }

        if (compId != null) {
            PortletComponent comp = compId.getPortletComponent();
            // perform an action if the component is non null
            if (comp == null) {
                //log.warn("Event has invalid component id associated with it!");
            } else {
                //log.debug("Calling action performed on " + comp.getClass().getName() + ":" + comp.getName());
                comp.messageEvent(concPortletID, msg, event);
            }
        }
    }

}

