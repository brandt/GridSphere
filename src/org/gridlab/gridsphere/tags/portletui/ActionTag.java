/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.portlet.DefaultPortletAction;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.PortletURI;
import org.gridlab.gridsphere.provider.portletui.beans.ParamBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.util.*;

public abstract class ActionTag extends BodyTagSupport {

    protected String action = null;
    protected PortletURI actionURI = null;
    protected DefaultPortletAction portletAction = null;
    protected List paramBeans = new ArrayList();

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

    public void addParamBean(ParamBean paramBean) {
        paramBeans.add(paramBean);
    }

    public void removeParamBean(ParamBean paramBean) {
        paramBeans.remove(paramBean);
    }

    public List getParamBeans() {
        return paramBeans;
    }

    public String createActionURI() {

        // action is a required attribute except for FormTag
        if (action == null) return "";

        // Builds a URI containing the actin and associated params
        PortletResponse res = (PortletResponse) pageContext.getAttribute("portletResponse");
        actionURI = res.createURI();
        portletAction = new DefaultPortletAction(action);
        Iterator it = paramBeans.iterator();
        while (it.hasNext()) {
            ParamBean pbean = (ParamBean)it.next();
            portletAction.addParameter(pbean.getName(), pbean.getValue());
        }
        actionURI.addAction(portletAction);
        return actionURI.toString();
    }

    public abstract int doStartTag() throws JspException;

    public abstract int doEndTag() throws JspException;

}
