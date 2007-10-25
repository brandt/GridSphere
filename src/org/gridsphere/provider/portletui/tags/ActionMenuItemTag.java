package org.gridsphere.provider.portletui.tags;

import org.gridsphere.provider.portletui.beans.ActionMenuItemBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

public class ActionMenuItemTag extends ContainerTag {

    protected boolean isSelected = false;
    protected boolean seperator = false;
    protected ActionMenuItemBean actionMenuItemBean = null;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSeperator() {
        return seperator;
    }

    public void setSeperator(boolean seperator) {
        this.seperator = seperator;
    }

    public int doStartTag() throws JspException {

        if (!beanId.equals("")) {
            actionMenuItemBean = (ActionMenuItemBean) getTagBean();
            if (actionMenuItemBean == null) {
                actionMenuItemBean = new ActionMenuItemBean();
            }
        } else {
            actionMenuItemBean = new ActionMenuItemBean();
        }

        // set info if not already set and we have something to set
        //if (info!=null && actionMenuItemBean.getInfo()==null) actionMenuItemBean.setInfo(info);

        Tag parent = getParent();
        if (parent instanceof ActionMenuTag) {
            ActionMenuTag actionMenuTag = (ActionMenuTag) parent;
            if (actionMenuTag.getLayout() != null) actionMenuItemBean.setAlign(actionMenuTag.getLayout());
            if (actionMenuTag.getMenutype() != null) actionMenuItemBean.setMenutype(actionMenuTag.getMenutype());
            actionMenuItemBean.setSelected(isSelected);
        }
        String beanString = actionMenuItemBean.toStartString();
        // Print the string
        try {
            pageContext.getOut().print(beanString);
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {

        try {
            JspWriter out = pageContext.getOut();
            out.print(actionMenuItemBean.toEndString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return EVAL_PAGE;
    }
}
