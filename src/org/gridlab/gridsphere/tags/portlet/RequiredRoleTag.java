package org.gridlab.gridsphere.tags.portlet;

import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public class RequiredRoleTag extends TagSupport {

    protected PortletRole role = PortletRole.GUEST;

    public void setRole(String role) {
        try {
            this.role = PortletRole.toPortletRole(role);
        } catch (IllegalArgumentException e) {
            // do nothing
            this.role = PortletRole.GUEST;
        }
    }

    public String getRole() {
        return role.toString();
    }

    public int doStartTag() throws JspException {
        PortletRequest req = (PortletRequest)pageContext.getAttribute("portletRequest");
        PortletRole userRole = req.getRole();
        if (userRole != null) {
            //System.err.println(userRole);
            if ((userRole.getID()) >= role.getID()) {
                return EVAL_BODY_INCLUDE;
            }
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

}
