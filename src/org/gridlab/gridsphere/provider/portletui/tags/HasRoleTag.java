/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletRole;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Iterator;
import java.util.List;

/**
 * The <code>HasRoleTag</code> can be used to selectively display presentation based upon a user's role
 */
public class HasRoleTag extends TagSupport {

    protected String role = "";
    protected String group = "";

    /**
     * Sets the user's role
     *
     * @param role the user's role
     */
    public void setRole(String role) {
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
     * Sets the group name
     *
     * @param group the user's group
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * Returns the user's group
     *
     * @return the user's group
     */
    public String getGroup() {
        return group;
    }

    public int doStartTag() throws JspException {
        PortletRequest req = (PortletRequest) pageContext.getAttribute("portletRequest");
        List userRoles = req.getRoles();

        List groups = (List)req.getGroups();
        if (userRoles != null) {
            if (userRoles.contains(PortletRole.SUPER.getName())) {
                return EVAL_BODY_INCLUDE;
            }
            if (group != null) {
                Iterator it = groups.iterator();
                while (it.hasNext()) {
                    String g = (String)it.next();
                    if (g.equalsIgnoreCase(group)) {
                        //System.err.println("my role is " + userRole + " group is " + group);
                        break;
                    }
                }
            }
            if ((userRoles.contains(role))) {
                return EVAL_BODY_INCLUDE;
            }
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

}
