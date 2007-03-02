package org.gridsphere.provider.portletui.tags;

import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.provider.portletui.beans.TreeBean;

import javax.portlet.RenderResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

/*
* @author <a href="mailto:wehrens@gridsphere.org">Oliver Wehrens</a>
* @version $Id$
*/
public class TreeTag extends ActionTag {

    protected TreeBean treeBean = null;

    public int doStartTag() throws JspException {
        if (!beanId.equals("")) {
            treeBean = (TreeBean) getTagBean();
            if (treeBean == null) {
                treeBean = new TreeBean();
                this.setBaseComponentBean(treeBean);
            } else {
                this.updateBaseComponentBean(treeBean);

            }
        } else {
            treeBean = new TreeBean();
            this.setBaseComponentBean(treeBean);
        }

        treeBean.setAction(action);

        RenderResponse res = (RenderResponse) pageContext.getAttribute(SportletProperties.RENDER_RESPONSE, PageContext.REQUEST_SCOPE);

        treeBean.setPortletURL(res.createActionURL());

        try {
            JspWriter out = pageContext.getOut();
            out.print(treeBean.toStartString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {

        if ((bodyContent != null) && (value == null)) {
            treeBean.setValue(bodyContent.getString());
        }

        try {
            JspWriter out = pageContext.getOut();
            out.print(treeBean.toEndString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return EVAL_PAGE;
    }

}
