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

        // get theme
        String theme = layoutMgr.getTheme(req);
        req.setAttribute("theme", theme);

        // get tabs
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

        ListBoxBean themeLB = event.getListBoxBean("themeLB");
        String themes = getPortletSettings().getAttribute("supported-themes");
        StringTokenizer st = new StringTokenizer(themes, ",");
        while (st.hasMoreTokens()) {
            ListBoxItemBean lb = new ListBoxItemBean();
            String val = (String)st.nextElement();
            lb.setValue(val);
            themeLB.addBean(lb);
        }

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

    public void saveChanges(FormEvent event) {
        PortletRequest req = event.getPortletRequest();

        ListBoxBean themeLB = event.getListBoxBean("themeLB");
        String theme = themeLB.getSelectedValue();
        layoutMgr.setTheme(req, theme);

        String[] tabNames = layoutMgr.getTabNames(req);
        for (int i = 0; i < tabNames.length; i++) {
            TextFieldBean tf = event.getTextFieldBean("tab" + i);
            String newtitle = tf.getValue();
            if (newtitle == null) newtitle = "Untitled " + i + 1;
            tabNames[i] = newtitle;
            System.err.println("settng " + tabNames[i]);
        }
        layoutMgr.initPage(req);

        layoutMgr.setTabNames(req, tabNames);
    }

    public void doConfigureLayout(FormEvent event) throws PortletException {
        PortletRequest req = event.getPortletRequest();

        String themes = getPortletSettings().getAttribute("supported-themes");
        TextFieldBean themesTF = event.getTextFieldBean("themesTF");
        themesTF.setValue(themes);

        setNextState(req, CONFIGURE_JSP);
    }

    public void doSaveThemes(FormEvent event) throws PortletException {
        TextFieldBean tf = event.getTextFieldBean("themesTF");
        String themes = tf.getValue();
        FrameBean msg = event.getFrameBean("msgFrame");
        if (themes != null) {
            getPortletSettings().setAttribute("supported-themes", themes);
            try {
                getPortletSettings().store();
            } catch (IOException e) {
                msg.setValue("Unable to save locale settings!");
                msg.setStyle("error");
            }
            msg.setValue("Saved locale settings");
            msg.setStyle("success");
        } else {
              msg.setValue("Please specify at least one theme (xp)!");
              msg.setStyle("error");
        }
    }

}
