/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.provider.portletui.beans.ActionParamBean;

import java.util.Iterator;
import java.util.List;

/**
 * The abstract <code>ActionTag</code> is used by other Action tags to contain <code>DefaultPortletAction</code>s
 * and possibly <code>ActionParamTag</code>s
 */
public abstract class ActionTag extends BaseComponentTag {

    protected String action = null;
    protected String anchor = null;
    protected boolean isSecure = false;
    protected PortletURI actionURI = null;
    protected String windowState = null;
    protected String portletMode = null;
    protected DefaultPortletAction portletAction = null;
    protected List paramBeans = null;
    protected String label = null;

    /**
     * Sets the text that should be added at the end of generated URL
     *
     * @param anchor the action link key
     */
    public void setAnchor(String anchor) {
        this.anchor = anchor;
    }

    /**
     * Returns the anchor used to identify text that should be added at the end of generated URL
     *
     * @return the anchor
     */
    public String getAnchor() {
        return anchor;
    }

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

    /**
     * If secure is true, then use https, otherwise use http
     *
     * @param isSecure
     */
    public void setSecure(boolean isSecure) {
        this.isSecure = isSecure;
    }

    /**
     * Returns true if this actiontag is secure e.g. https, flase otherwise
     *
     * @return true if this actiontag is secure, false otherwise
     */
    public boolean getSecure() {
        return isSecure;
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

    public PortletURI createActionURI() {
        // Builds a URI containing the actin and associated params
        PortletResponse res = (PortletResponse) pageContext.getAttribute("portletResponse");

        // action is a required attribute except for FormTag
        if (label != null) {
            actionURI = res.createURI(label, isSecure);
        } else if (windowState != null) {
            PortletWindow.State state = PortletWindow.State.toState(windowState);
            actionURI = res.createURI(state);
        } else if (portletMode != null) {
            Portlet.Mode mode = Portlet.Mode.toMode(portletMode);
            actionURI = res.createURI(mode);
        } else {
            actionURI = res.createURI(isSecure);
        }
        if (action != null) {
            portletAction = new DefaultPortletAction(action);
            Iterator it = paramBeans.iterator();

            if (!paramBeans.isEmpty()) {
                String id = createUniquePrefix(2);

                portletAction.addParameter(SportletProperties.PREFIX, id);

                while (it.hasNext()) {
                    ActionParamBean pbean = (ActionParamBean) it.next();
                    portletAction.addParameter(id + "_" + pbean.getName(), pbean.getValue());
                }
            }
            actionURI.addAction(portletAction);
        }
        return actionURI;

    }

    /**
     *  A string utility that produces a string composed of
     * <code>numChars</code> number of characters
     *
     * @param numChars the number of characters in the resulting <code>String</code>
     * @return the <code>String</code>
     */
    private String createUniquePrefix(int numChars) {
        StringBuffer s = new StringBuffer();
        for (int i = 0; i <= numChars; i++) {
            int nextChar = (int) (Math.random() * 62);
            if (nextChar < 10) //0-9
                s.append(nextChar);
            else if (nextChar < 36) //a-z
                s.append((char) (nextChar - 10 + 'a'));
            else
                s.append((char) (nextChar - 36 + 'A'));
        }
        return s.toString();
    }

}
