/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletRole;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * The <code>HasRoleTag</code> can be used to selectively display presentation based upon a user's role
 */
public class HasRoleTag extends TagSupport {

    protected PortletRole role = PortletRole.GUEST;
    protected boolean exclusive = false;

    /**
     * Sets the user's role
     *
     * @param role the user's role
     */
    public void setRole(String role) {
        try {
            this.role = PortletRole.toPortletRole(role);
        } catch (IllegalArgumentException e) {
            // do nothing
            this.role = PortletRole.GUEST;
        }
    }

    /**
     * Returns the user's role
     *
     * @return the user's role
     */
    public String getRole() {
        return role.toString();
    }

    /**
     * If exclusive is set to true then presentation will be displayed ONLY if the user has this exact role
     *
     * @param exclusive is true if presentation will be displayed ONLY if the user has this exact role
     */
    public void setExclusive(boolean exclusive) {
        this.exclusive = exclusive;
    }

    /**
     * If exclusive is set to true then presentation will be displayed ONLY if the user has this exact role
     *
     * @return true if presentation will be displayed ONLY if the user has this exact role
     */
    public boolean getExclusive() {
        return exclusive;
    }

    public int doStartTag() throws JspException {
        PortletRequest req = (PortletRequest) pageContext.getAttribute("portletRequest");
        PortletRole userRole = req.getRole();

        if (userRole != null) {
            if (exclusive) {
                if ((userRole.getName()) == role.getName()) {
                    return EVAL_BODY_INCLUDE;
                }
            } else {
                if ((userRole.getID()) >= role.getID()) {
                    return EVAL_BODY_INCLUDE;
                }
            }
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

}
