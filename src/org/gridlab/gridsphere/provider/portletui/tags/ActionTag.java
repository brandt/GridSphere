/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.portlet.DefaultPortletAction;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.PortletURI;
import org.gridlab.gridsphere.provider.portletui.beans.ActionParamBean;

import java.util.Iterator;
import java.util.List;

/**
 * The abstract <code>ActionTag</code> is used by other Action tags to contain <code>DefaultPortletAction</code>s
 * and possibly <code>ActionParamTag</code>s
 */
public abstract class ActionTag extends BaseComponentTag {

    protected String action = null;
    protected PortletURI actionURI = null;
    protected DefaultPortletAction portletAction = null;
    protected List paramBeans = null;
    protected String label = null;

    /**
     * Sets the label identified with the portlet component to link to
     *
     * @param label the action link key
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Returns the label identified with the portlet component to link to
     *
     * @return the label
     */
    public String getLabel() {
        return label;
    }

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
        if (label != null) {
            actionURI = res.createURI(label);
        } else {
            actionURI = res.createURI();
        }
        if (action != null) {
            portletAction = new DefaultPortletAction(action);
            Iterator it = paramBeans.iterator();
            while (it.hasNext()) {
                ActionParamBean pbean = (ActionParamBean)it.next();
                portletAction.addParameter(pbean.getName(), pbean.getValue());
            }
            actionURI.addAction(portletAction);
        }
        return actionURI.toString();
    }

}
