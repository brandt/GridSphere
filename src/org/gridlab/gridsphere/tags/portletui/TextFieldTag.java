/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.provider.portletui.beans.TextFieldBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

public class TextFieldTag extends BaseComponentTag {

    protected TextFieldBean textFieldBean = null;
    protected int size = 0;
    protected int maxlength = 0;

    /**
     * Returns the (html) size of the field.
     * @return size of the field
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the (html) size of the field
     * @param size size of the field
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Returns the (html) maxlength of the field
     * @return maxlength of the field
     */
    public int getMaxlength() {
        return maxlength;
    }

    /**
     * Sets the (html) maxlnegth of the field
     * @param maxlength maxlength of the field
     */
    public void setMaxlength(int maxlength) {
        this.maxlength = maxlength;
    }

    public int doEndTag() throws JspException {
        if (!beanId.equals("")) {
            textFieldBean = (TextFieldBean)pageContext.getAttribute(getBeanKey(), PageContext.REQUEST_SCOPE);
            if (textFieldBean == null) {
                textFieldBean = new TextFieldBean();
                textFieldBean.setBeanId(beanId);
                this.setBaseComponentBean(textFieldBean);
            } else {
                this.updateBaseComponentBean(textFieldBean);
            }
        } else {
            textFieldBean = new TextFieldBean();
            if (maxlength != 0) textFieldBean.setMaxLength(maxlength);
            if (size != 0) textFieldBean.setSize(size);
            this.setBaseComponentBean(textFieldBean);
        }

        //debug();

        Object parentTag = getParent();
        if (parentTag instanceof ContainerTag) {
            ContainerTag containerTag = (ContainerTag)parentTag;
            containerTag.addTagBean(textFieldBean);
        } else {
            try {
                JspWriter out = pageContext.getOut();
                out.print(textFieldBean.toString());
            } catch (Exception e) {
                throw new JspException(e.getMessage());
            }
        }
        return EVAL_BODY_INCLUDE;
    }

}
