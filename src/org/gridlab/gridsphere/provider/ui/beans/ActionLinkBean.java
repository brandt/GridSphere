/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 *
 * The Class provides
 */
package org.gridlab.gridsphere.provider.ui.beans;

import org.gridlab.gridsphere.portlet.PortletURI;
import org.gridlab.gridsphere.portlet.DefaultPortletAction;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class ActionLinkBean extends TextBean implements Link {

    protected String action = null;
    protected String link = null;
    protected PortletURI portletURI = null;
    protected List paramBeanList = new ArrayList();

    /**
     * Default constructor for use ONLY by ActionLinkTag
     */
    public ActionLinkBean() {}

    /**
     * Creates an instance of ActionLinkBean using a portlet URI, an action name and label
     * @param portletURI
     * @param action
     * @param label
     */
    public ActionLinkBean(PortletURI portletURI, String action, String label) {
        this.portletURI = portletURI;
        this.action = action;
        this.label = label;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
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

    public void removeParamBean(ParamBean paramBean) {
        paramBeanList.remove(paramBean);
    }

    /**
     * Sets the action
     * @param link the actionlink
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Gets the actionlink string
     */
    public String getLink() {
        return link;
    }

    private void createLink() {
        DefaultPortletAction portletAction = new DefaultPortletAction(action);
        portletURI.addAction(portletAction);
        Iterator it = paramBeanList.iterator();
        ParamBean paramBean = null;
        while (it.hasNext()) {
            paramBean = (ParamBean)it.next();
            portletAction.addParameter(paramBean.getName(), paramBean.getValue());
        }
        link = portletURI.toString();
        this.setText(label);
    }

    public String toString() {
        if (link == null) createLink();
        return "<a href='" + link + "'/>" + label + "</a>";
    }

}
