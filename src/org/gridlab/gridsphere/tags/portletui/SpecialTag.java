/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.provider.portletui.beans.TextFieldBean;
import org.gridlab.gridsphere.provider.portletui.beans.SpecialBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

public class SpecialTag extends BaseComponentTag {

    protected SpecialBean specialBean = null;

    public int doEndTag() throws JspException {
        if (!beanId.equals("")) {
            specialBean = (SpecialBean)pageContext.getAttribute(getBeanKey(), PageContext.REQUEST_SCOPE);
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

        Object parentTag = getParent();
        if (parentTag instanceof ContainerTag) {
            ContainerTag containerTag = (ContainerTag)parentTag;
            containerTag.addTagBean(specialBean);
        } else {
            try {
                JspWriter out = pageContext.getOut();
                out.print(specialBean.toString());
            } catch (Exception e) {
                throw new JspException(e.getMessage());
            }
        }
        return EVAL_BODY_INCLUDE;
    }

}
