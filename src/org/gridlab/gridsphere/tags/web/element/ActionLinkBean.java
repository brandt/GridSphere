/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 *
 * The Class provides
 */
package org.gridlab.gridsphere.tags.web.element;

import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.DefaultPortletAction;
import org.gridlab.gridsphere.portlet.PortletURI;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class ActionLinkBean extends BaseElementBean {

    protected String action = "";
    protected String label = "";
    protected List paramBeanList = new ArrayList();
    protected PortletURI portletURI = null;

    public ActionLinkBean(String action, String label, List actionParamBeanList, PortletResponse res) {
        this.action = action;
        this.label = label;
        this.paramBeanList = actionParamBeanList;
        this.portletURI = res.createURI();
    }

    public void setActionParamBeanList(List paramBeanList) {
        this.paramBeanList = paramBeanList;
    }

    public List getActionParamBeanList() {
        return paramBeanList;
    }

    public void addActionParamBean(ActionParamBean paramBean) {
        paramBeanList.add(paramBean);
    }

    public void removeActionParamBean(ActionParamBean paramBean) {
        paramBeanList.remove(paramBean);
    }

    protected void createURI() {
        DefaultPortletAction portletAction = new DefaultPortletAction(action);
        Iterator it = paramBeanList.iterator();
        ActionParamBean paramBean = null;
        while (it.hasNext()) {
            paramBean = (ActionParamBean)it.next();
            portletAction.addParameter(paramBean.getName(), paramBean.getValue());
        }
        portletURI.addAction(portletAction);
    }

    public String toString() {
        createURI();
        return "<a href='" + portletURI.toString() + "'/>" + label + "</a>";
    }
}
