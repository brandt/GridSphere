/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.core.layout;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.provider.event.FormEvent;
import org.gridlab.gridsphere.provider.portlet.ActionPortlet;
import org.gridlab.gridsphere.provider.portletui.beans.*;
import org.gridlab.gridsphere.provider.portletui.model.DefaultTableModel;
import org.gridlab.gridsphere.services.core.security.acl.AccessControlManagerService;
import org.gridlab.gridsphere.services.core.security.acl.GroupRequest;
import org.gridlab.gridsphere.services.core.security.acl.InvalidGroupRequestException;
import org.gridlab.gridsphere.services.core.security.acl.GroupEntry;
import org.gridlab.gridsphere.services.core.security.password.PasswordManagerService;
import org.gridlab.gridsphere.services.core.security.password.Password;
import org.gridlab.gridsphere.services.core.security.auth.LoginAuthModule;
import org.gridlab.gridsphere.services.core.user.UserManagerService;
import org.gridlab.gridsphere.services.core.user.AccountRequest;
import org.gridlab.gridsphere.services.core.user.InvalidAccountRequestException;
import org.gridlab.gridsphere.services.core.user.LoginService;
import org.gridlab.gridsphere.services.core.layout.LayoutManagerService;

import javax.servlet.UnavailableException;
import java.util.*;
import java.text.DateFormat;
import java.io.IOException;

public class LayoutManagerPortlet extends ActionPortlet {

    // JSP pages used by this portlet
    public static final String VIEW_JSP = "layout/view.jsp";
    public static final String EDIT_JSP = "layout/edit.jsp";
    public static final String CONFIGURE_JSP = "layout/configure.jsp";

    // Portlet services
    private LayoutManagerService layoutMgr = null;


    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        this.log.debug("Entering initServices()");
        try {
            this.layoutMgr = (LayoutManagerService)config.getContext().getService(LayoutManagerService.class);
        } catch (PortletServiceException e) {
            log.error("Unable to initialize services!", e);
        }
        this.log.debug("Exiting initServices()");

        DEFAULT_VIEW_PAGE = "doViewLayout";
        DEFAULT_EDIT_PAGE = "doEditLayout";
        DEFAULT_CONFIGURE_PAGE = "doConfigureLayout";
    }

    public void initConcrete(PortletSettings settings) throws UnavailableException {
        super.initConcrete(settings);
    }

    public void doViewLayout(FormEvent event) {
        PortletRequest req = event.getPortletRequest();

        TableRowBean tr = event.getTableRowBean("tabsRow");
        String[] tabNames = layoutMgr.getTabNames(req);
        for (int i = 0; i < tabNames.length; i++) {
            TableCellBean tc = new TableCellBean();
            TextBean tb = new TextBean();
            tb.setStyle(TextBean.MSG_STATUS);
            tb.setValue(tabNames[i]);
            tc.addBean(tb);
            tr.addBean(tc);
        }

        setNextState(req, VIEW_JSP);
    }

    public void doEditLayout(FormEvent event) {
        PortletRequest req = event.getPortletRequest();

        TableRowBean tr = event.getTableRowBean("tabsRow");
        String[] tabNames = layoutMgr.getTabNames(req);
        for (int i = 0; i < tabNames.length; i++) {
            TableCellBean tc = new TableCellBean();
            TextFieldBean tf = event.getTextFieldBean("tab" + i);
            tf.setValue(tabNames[i]);
            tc.addBean(tf);
            tr.addBean(tc);
        }

        setNextState(req, EDIT_JSP);
    }

    public void saveTabs(FormEvent event) {
        PortletRequest req = event.getPortletRequest();
        String[] tabNames = layoutMgr.getTabNames(req);
        for (int i = 0; i < tabNames.length; i++) {
            TextFieldBean tf = event.getTextFieldBean("tab" + i);
            String newtitle = tf.getValue();
            if (newtitle == null) newtitle = "Untitled " + i + 1;
            tabNames[i] = newtitle;
            System.err.println("settng " + tabNames[i]);
        }
        layoutMgr.setTabNames(req, tabNames);
    }

    public void doConfigureLayout(FormEvent event) throws PortletException {
        PortletRequest req = event.getPortletRequest();


        setNextState(req, CONFIGURE_JSP);
    }

}
