/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core.subscription;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.provider.event.FormEvent;
import org.gridlab.gridsphere.provider.portlet.ActionPortlet;
import org.gridlab.gridsphere.provider.portletui.beans.*;
import org.gridlab.gridsphere.provider.portletui.model.DefaultTableModel;
import org.gridlab.gridsphere.services.core.security.acl.AccessControlManagerService;
import org.gridlab.gridsphere.services.core.layout.LayoutManagerService;
import org.gridlab.gridsphere.services.core.registry.impl.PortletManager;
import org.gridlab.gridsphere.portletcontainer.PortletRegistry;
import org.gridlab.gridsphere.portletcontainer.ApplicationPortlet;
import org.gridlab.gridsphere.portletcontainer.ConcretePortlet;
import org.gridlab.gridsphere.layout.*;

import javax.servlet.UnavailableException;
import java.util.*;

public class SubscriptionPortlet extends ActionPortlet {

    // JSP pages used by this portlet
    public static final String VIEW_JSP = "subscription/view.jsp";

    // Portlet services
    private AccessControlManagerService aclService = null;
    private LayoutManagerService layoutMgr = null;
    private PortletManager portletMgr = null;
    private PortletRegistry portletRegistry = null;
    private List gsPortlets = new Vector();

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        this.log.debug("Entering initServices()");
        try {
            this.layoutMgr = (LayoutManagerService)config.getContext().getService(LayoutManagerService.class);
            this.aclService = (AccessControlManagerService)config.getContext().getService(AccessControlManagerService.class);
        } catch (PortletServiceException e) {
            log.error("Unable to initialize services!", e);
        }
        this.log.debug("Exiting initServices()");
        portletRegistry = PortletRegistry.getInstance();
        portletMgr = PortletManager.getInstance();

        DEFAULT_VIEW_PAGE = "doViewSubscription";
    }

    public void initConcrete(PortletSettings settings) throws UnavailableException {
        super.initConcrete(settings);
    }

    public void doViewSubscription(FormEvent event) {
        PortletRequest req = event.getPortletRequest();
        gsPortlets.clear();
        User user = req.getUser();

        List myNames = layoutMgr.getSubscribedPortlets(req);

        List groups = aclService.getGroups(user);
        Iterator it = groups.iterator();

        PanelBean panel = event.getPanelBean("panel");
        PortletRole role = req.getRole();
        while (it.hasNext()) {
            FrameBean frame = new FrameBean();
            DefaultTableModel model = new DefaultTableModel();
            PortletGroup g = (PortletGroup)it.next();
            TableRowBean tr = new TableRowBean();
            tr.setHeader(true);
            TableCellBean tc3 = new TableCellBean();
            TextBean text3 = new TextBean();
            text3.setValue(this.getLocalizedText(req, "SUBSCRIPTION_SUBSCRIBE"));
            tc3.addBean(text3);
            tr.addBean(tc3);
            TableCellBean tc = new TableCellBean();
            TextBean text = new TextBean();
            text.setValue(portletMgr.getPortletWebApplicationDescription(g.getName()));
            tc.addBean(text);
            tr.addBean(tc);
            tc = new TableCellBean();
            text = new TextBean();
            text.setValue(this.getLocalizedText(req, "SUBSCRIPTION_DESC"));
            tc.addBean(text);
            tr.addBean(tc);
            model.addTableRowBean(tr);
            Collection appColl = portletRegistry.getApplicationPortlets(g.getName());
            Iterator appIt = appColl.iterator();
            while (appIt.hasNext()) {
                ApplicationPortlet app = (ApplicationPortlet)appIt.next();
                List concPortlets = app.getConcretePortlets();
                Iterator cit = concPortlets.iterator();
                while (cit.hasNext()) {
                    ConcretePortlet conc = (ConcretePortlet)cit.next();
                    String concID = conc.getConcretePortletID();

                    PortletRole reqrole = conc.getConcretePortletConfig().getRequiredRole();
                    System.err.println(concID + " " + reqrole);
                    if (role.compare(role, reqrole) >= 0) {
                    // build an interface
                    CheckBoxBean cb = new CheckBoxBean(req, "portletsCB");
                    cb.setValue(concID);
                    if (myNames.contains(concID)) {
                            cb.setSelected(true);
                    }
                    // don't allow core portlets to be changed
                    if (g.equals(PortletGroupFactory.GRIDSPHERE_GROUP)) {
                        gsPortlets.add(concID);
                        cb.setDisabled(true);
                        cb.setSelected(true);
                    }

                    TableRowBean newtr = new TableRowBean();
                    TableCellBean newtc = new TableCellBean();
                    newtc.addBean(cb);
                    newtr.addBean(newtc);

                    TableCellBean newtc2 = new TableCellBean();
                    TextBean tb = new TextBean();
                    int li = concID.lastIndexOf(".");
                    concID = concID.substring(0, li);
                    li = concID.lastIndexOf(".");
                    concID = concID.substring(li+1);
                    tb.setValue(concID);
                    newtc2.addBean(tb);
                    newtr.addBean(newtc2);
                    newtc = new TableCellBean();
                    TextBean tb2 = new TextBean();

                    user.getAttribute(User.LOCALE);

                    Locale loc = conc.getPortletSettings().getDefaultLocale();

                        System.err.println("default loc: " + loc.getDisplayLanguage());

                    tb2.setValue(conc.getPortletSettings().getDescription(loc, null));
                    newtc.addBean(tb2);
                    newtr.addBean(newtc);
                    model.addTableRowBean(newtr);
                    }
                }
            }
            frame.setTableModel(model);
            panel.addBean(frame);
        }
        setNextState(req, VIEW_JSP);
    }

    public void applyChanges(FormEvent event) {
        PortletRequest req = event.getPortletRequest();
        CheckBoxBean cb = event.getCheckBoxBean("portletsCB");
        List newlist = cb.getSelectedValues();
        List newportlets = new Vector();
        List removePortlets = new Vector();

        // compare to orig list
        List oldlist = layoutMgr.getSubscribedPortlets(req);


        // check if new list has new portlets to add
        Iterator it = newlist.iterator();
        while (it.hasNext()) {
            String pid = (String)it.next();
            if (!oldlist.contains(pid)) {
                newportlets.add(pid);
                layoutMgr.addSubscribedPortlet(req, pid);
            }
        }

        // check if new list no longer has entries that oldlist has
        it = oldlist.iterator();
        while (it.hasNext()) {
            String pid = (String)it.next();
            // don't allow users to remove core portlets
            if (!newlist.contains(pid) && (!gsPortlets.contains(pid))) {
                removePortlets.add(pid);
                //System.err.println("removing " + pid);
            }
        }

        layoutMgr.removeSubscribedPortlets(req, removePortlets);

        // remove portlets
        if (!removePortlets.isEmpty()) {
            layoutMgr.removePortlets(req, removePortlets);
            layoutMgr.reloadPage(req);
        }


    }


}
