/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.provider.portletui.beans.ListBoxBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import java.util.Vector;

public class ListBoxTag extends ContainerTag {

    protected ListBoxBean listbox = null;

    public int doStartTag() throws JspException {

        //System.err.println("in TableCellTag:doStartTag");
        list = new Vector();

        //System.err.println("creating new list");

        if (!beanId.equals("")) {
            listbox = (ListBoxBean)pageContext.getAttribute(getBeanKey(), PageContext.REQUEST_SCOPE);
            if (listbox == null) listbox = new ListBoxBean();
        } else {
            listbox = new ListBoxBean();
        }

        ContainerTag rowTag = (ContainerTag)getParent();
        if (rowTag == null) return SKIP_BODY;
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {

        //debug();

        Object parentTag = getParent();
        if (parentTag instanceof ContainerTag) {
            ContainerTag containerTag = (ContainerTag)parentTag;
            containerTag.addTagBean(listbox);
        } else {
            try {
                JspWriter out = pageContext.getOut();
                out.print(listbox.toString());
            } catch (Exception e) {
                throw new JspException(e.getMessage());
            }
        }
        return EVAL_BODY_INCLUDE;
    }

}
