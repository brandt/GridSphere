/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: SpecialTag.java 4666 2006-03-27 17:47:56Z novotny $
 */

package org.gridsphere.provider.portletui.tags;

import org.gridsphere.provider.portletui.beans.SpecialBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * A <code>SpecialTag</code> serves an example of developing a tag that can dynamically create presentation using
 * JavScript if it is available or plain HTML if not.
 */
public class SpecialTag extends BaseComponentTag {

    protected SpecialBean specialBean = null;

    public int doEndTag() throws JspException {
        if (!beanId.equals("")) {
            specialBean = (SpecialBean) getTagBean();
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
