/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletURI;
import org.gridlab.gridsphere.portlet.DefaultPortletAction;
import org.gridlab.gridsphere.provider.ui.beans.TextBean;
import org.gridlab.gridsphere.provider.ui.beans.Link;
import org.gridlab.gridsphere.provider.ui.beans.ParamBean;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class ActionLinkBean extends BaseComponentBean implements TagBean {

    protected String action = "no action specified";
    protected PortletURI portletURI = null;
    protected List paramBeanList = new ArrayList();

    public ActionLinkBean() {}

    /**
     * Sets the uri for the link.
     */
    public void setPortletURI(PortletURI portletURI) {
        this.portletURI = portletURI;
    }

    /**
     * Gets the uri for the link.
     * @return returns the action
     */
    public PortletURI getPortletURI() {
        return portletURI;
    }

    /**
     * Sets the action for the link.
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Gets the action for the link.
     * @return returns the action
     */
    public String getAction() {
        return action;
    }

    public void setParamBeanList(List paramBeanList) {
        this.paramBeanList = paramBeanList;
    }

    public List getParamBeanList() {
        return paramBeanList;
    }

    public void addParamBean(ParamBean paramBean) {
        paramBeanList.add(paramBean);
    }

    public void addParamBean(String paramName, String paramValue) {
        ParamBean paramBean = new ParamBean(paramName, paramValue);
        paramBeanList.add(paramBean);
    }

    public void removeParamBean(ParamBean paramBean) {
        paramBeanList.remove(paramBean);
    }

    private void createLink() {
        DefaultPortletAction portletAction = new DefaultPortletAction(action);
        Iterator it = paramBeanList.iterator();
        ParamBean paramBean = null;
        while (it.hasNext()) {
            paramBean = (ParamBean)it.next();
            portletAction.addParameter(paramBean.getName(), paramBean.getValue());
        }
        portletURI.addAction(portletAction);
        action = portletURI.toString();
    }

    public String toString() {
        if (value == null) createLink();
        return "<a href=\"" + action + "\"/>" + value + "</a>";
    }

}
