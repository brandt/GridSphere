/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.portlet.DefaultPortletAction;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.PortletURI;
import org.gridlab.gridsphere.provider.portletui.beans.ActionParamBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.util.*;

public abstract class ActionTag extends BaseComponentTag {

    protected String action = null;
    protected PortletURI actionURI = null;
    protected DefaultPortletAction portletAction = null;
    protected List paramBeans = null;

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setPortletAction(DefaultPortletAction portletAction) {
        this.portletAction = portletAction;
    }

    public DefaultPortletAction getPortletAction() {
        return portletAction;
    }

    public void addParamBean(ActionParamBean paramBean) {
        paramBeans.add(paramBean);
    }

    public void removeParamBean(ActionParamBean paramBean) {
        paramBeans.remove(paramBean);
    }

    public List getParamBeans() {
        return paramBeans;
    }

    public String createActionURI() {

        // Builds a URI containing the actin and associated params
        PortletResponse res = (PortletResponse) pageContext.getAttribute("portletResponse");

        // action is a required attribute except for FormTag
        if (action == null) return res.createURI().toString();

        actionURI = res.createURI();
        portletAction = new DefaultPortletAction(action);
        Iterator it = paramBeans.iterator();
        while (it.hasNext()) {
            ActionParamBean pbean = (ActionParamBean)it.next();
            portletAction.addParameter(pbean.getName(), pbean.getValue());
        }
        actionURI.addAction(portletAction);
        return actionURI.toString();
    }

}
