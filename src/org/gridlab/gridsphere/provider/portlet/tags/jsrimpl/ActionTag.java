/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portlet.tags.jsrimpl;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.provider.portletui.beans.ActionParamBean;

import javax.portlet.RenderResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.PortletMode;
import javax.portlet.WindowStateException;
import javax.portlet.PortletModeException;
import javax.servlet.jsp.PageContext;
import java.util.Iterator;
import java.util.List;

/**
 * The abstract <code>ActionTag</code> is used by other Action tags to contain <code>DefaultPortletAction</code>s
 * and possibly <code>ActionParamTag</code>s
 */
public abstract class ActionTag extends BaseComponentTag {

    protected String action = null;
    protected PortletURL actionURL = null;
    protected String windowState = null;
    protected String portletMode = null;
    protected DefaultPortletAction portletAction = null;
    protected List paramBeans = null;
    protected String label = null;
    protected boolean isSecure = false;

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

    public void setPortletMode(String portletMode) {
        this.portletMode = portletMode;
    }

    public String getPortletMode() {
        return portletMode;
    }

    public void setWindowState(String windowState) {
        this.windowState = windowState;
    }

    public boolean getSecure(boolean isSecure) {
        return isSecure;
    }

    public void setSecure(boolean isSecure) {
        this.isSecure = isSecure;
    }

    public String getWindowState() {
        return windowState;
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
        RenderResponse res = (RenderResponse) pageContext.getAttribute(SportletProperties.RENDER_RESPONSE, PageContext.REQUEST_SCOPE);
        // action is a required attribute except for FormTag
        if (label != null) {
            actionURL = res.createRenderURL();
            res.setProperty("label", label);
        } else if (windowState != null) {
            WindowState state = new WindowState(windowState);
            try {
                actionURL = res.createRenderURL();
                actionURL.setWindowState(state);
            } catch (WindowStateException e) {
                System.err.println("Unknown window state in renderURL tag: " + windowState);
            }
        } else if (portletMode != null) {
            PortletMode mode = new PortletMode(portletMode);
            try {
                actionURL = res.createRenderURL();
                actionURL.setPortletMode(mode);
            } catch (PortletModeException e) {
                System.err.println("Unknown portlet mode in renderURL tag: " + mode);
            }
        } else {
            actionURL = res.createRenderURL();
        }

        if (!paramBeans.isEmpty()) {
            if (action != null) actionURL.setParameter(SportletProperties.DEFAULT_PORTLET_ACTION, action);
            Iterator it = paramBeans.iterator();
            while (it.hasNext()) {
                ActionParamBean pbean = (ActionParamBean)it.next();
                actionURL.setParameter(pbean.getName(), pbean.getValue());
            }
        }
        return actionURL.toString();
    }

}
