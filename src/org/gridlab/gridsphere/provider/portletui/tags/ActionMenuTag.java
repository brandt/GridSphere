package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.provider.portletui.beans.ActionMenuBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

public class ActionMenuTag extends ContainerTag {

    protected ActionMenuBean actionMenuBean = null;
    protected String align = null;
    protected String title = null;
    protected String menuType = null;


    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }


    public int doStartTag() throws JspException {

        // get the bean and the values
        if (!beanId.equals("")) {
            actionMenuBean = (ActionMenuBean) pageContext.getAttribute(getBeanKey(), PageContext.REQUEST_SCOPE);
        }
        if (actionMenuBean == null) {
            actionMenuBean = new ActionMenuBean();
            if (this.align != null) actionMenuBean.setAlign(align);
            if (this.title != null) actionMenuBean.setTitle(title);
            if (this.menuType != null) actionMenuBean.setMenuType(this.menuType);
        }

        Tag parent = getParent();
        if (parent instanceof ActionMenuTag) {
            actionMenuBean.setHasParentMenu(true);
        }

        try {
            JspWriter out = pageContext.getOut();
            out.print(actionMenuBean.toStartString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }
        return EVAL_BODY_INCLUDE;

    }

    public int doEndTag() throws JspException {

        try {
            JspWriter out = pageContext.getOut();
            out.print(actionMenuBean.toEndString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return EVAL_PAGE;
    }
}