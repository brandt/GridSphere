/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.tags.gs;

import org.gridlab.gridsphere.provider.portletui.beans.SpecialBean;
import org.gridlab.gridsphere.provider.portletui.tags.gs.BaseComponentTagImpl;
import org.gridlab.gridsphere.provider.portletui.tags.gs.BaseComponentTagImpl;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

/**
 * A <code>SpecialTag</code> serves an example of developing a tag that can dynamically create presentation using
 * JavScript if it is available or plain HTML if not.
 */
public class SpecialTagImpl extends BaseComponentTagImpl {

    protected SpecialBean specialBean = null;

    public int doEndTag() throws JspException {
        if (!beanId.equals("")) {
            specialBean = (SpecialBean) pageContext.getAttribute(getBeanKey(), PageContext.REQUEST_SCOPE);
            if (specialBean == null) {
                specialBean = new SpecialBean();
                specialBean.setBeanId(beanId);
                this.setBaseComponentBean(specialBean);
            } else {
                this.updateBaseComponentBean(specialBean);
            }
        } else {
            specialBean = new SpecialBean();
            this.setBaseComponentBean(specialBean);
        }

        //debug();

        try {
            JspWriter out = pageContext.getOut();
            out.print(specialBean.toStartString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return EVAL_BODY_INCLUDE;
    }

}
