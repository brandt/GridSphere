/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.provider.portletui.beans.ListBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * A <code>ListBoxTag</code> represents a list box element
 */
public class ListTag extends BaseComponentTag {

    protected ListBean listBean = null;

    public void setListBean(ListBean listBean) {
        this.listBean = listBean;
    }

    public ListBean getListBean() {
        return listBean;
    }

    public int doStartTag() throws JspException {
        if (!beanId.equals("")) {
            listBean = (ListBean) getTagBean();
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspException {
        if (listBean != null) {
            try {
                JspWriter out = pageContext.getOut();
                out.print(listBean.toEndString());
            } catch (Exception e) {
                throw new JspException(e.getMessage());
            }
        }
        return EVAL_PAGE;
    }

}
