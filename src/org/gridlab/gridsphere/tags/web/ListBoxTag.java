/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.web;

import org.gridlab.gridsphere.portlet.PortletURI;
import org.gridlab.gridsphere.portlet.DefaultPortletAction;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.tags.web.model.ListBoxModel;
import org.gridlab.gridsphere.tags.web.model.ListSelectItem;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Collection;
import java.util.Iterator;

public class ListBoxTag extends TagSupport {

    private ListBoxModel col;
    private boolean allowMultiple = false;

    public ListBoxModel getCollection() {
        return col;
    }

    public void setCollection(ListBoxModel col) {
        this.col = col;
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
            out.print("<select size=\"");
            out.print(col.getListboxsize()+"\"");
            out.print(" name=\"" + col.getName()+"\"");
            if (col.isMultipleSelection()) {
                out.print(" multiple");
            }
            out.print(">");
            Iterator it = col.iterator();
            while (it.hasNext()) {
                ListSelectItem item = (ListSelectItem)it.next();
                out.print("<option value=\"");
                out.print(item.getValue());
                out.print("\"");
                if (item.isSelected()) {
                    out.print(" selected ");
                }
                out.print(">");
                out.print(item.getLabel());
                out.print("</option>");
            }
            out.print("</select>");
        } catch (Exception e) {
            throw new JspTagException(e.getMessage());
        }
        return EVAL_BODY_INCLUDE;
    }

}
