/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: HasActionTag.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.provider.portletui.tags;

import org.gridsphere.portlet.PortletRequest;
import org.gridsphere.portlet.impl.SportletProperties;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * The <code>HasRoleTag</code> can be used to selectively display presentation based upon a user's role
 */
public class HasActionTag extends TagSupport {


    protected String action = null;
    protected boolean isDefault = false;

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public boolean getDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public int doStartTag() throws JspException {
        PortletRequest req = (PortletRequest) pageContext.getAttribute("portletRequest");

        String paction = req.getParameter(SportletProperties.DEFAULT_PORTLET_ACTION);
        if ((paction == null) && (isDefault)) return EVAL_BODY_INCLUDE;

        if (paction != null) {
            if (action.equals(paction)) return EVAL_BODY_INCLUDE;
        }

        return SKIP_BODY;
    }

}
