/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.web;

import org.gridlab.gridsphere.portlet.PortletURI;
import org.gridlab.gridsphere.portlet.DefaultPortletAction;
import org.gridlab.gridsphere.portlet.PortletResponse;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Collection;
import java.util.Iterator;

public class ListBoxTag extends TagSupport {

    private Collection col;
    private boolean allowMultiple = false;

    public void setCollection(Collection col) {
        this.col = col;
    }

    public Collection getCollection() {
        return col;
    }

    public void setMultiple(boolean allowMultiple) {
        this.allowMultiple = allowMultiple;
    }

    public boolean getMultiple() {
        return allowMultiple;
    }

    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.print("<select ");
            out.print(" name=" + getId());
            if (allowMultiple) {
                out.print(" multiple");
            }
            out.print(">");
            Iterator it = col.iterator();
            while (it.hasNext()) {
                out.print("<option>");
                out.print((String)it.next());
                out.print("</option>");
            }
            out.print("</select>");
        } catch (Exception e) {
            throw new JspTagException(e.getMessage());
        }
        return EVAL_BODY_INCLUDE;
    }

}
