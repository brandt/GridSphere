/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.provider.portletui.beans.TextFieldBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

/**
 * A <code>TextFieldTag</code> represents a text field element
 */
public class TextFieldTag extends BaseComponentTag {

    protected TextFieldBean textFieldBean = null;
    protected int size = 0;
    protected int maxlength = 0;

    /**
     * Returns the (html) size of the field
     *
     * @return size of the field
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the (html) size of the field
     *
     * @param size size of the field
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Returns the (html) max length of the field
     *
     * @return the max length of the field
     */
    public int getMaxlength() {
        return maxlength;
    }

    /**
     * Sets the (html) max length of the field
     *
     * @param maxlength the max length of the field
     */
    public void setMaxlength(int maxlength) {
        this.maxlength = maxlength;
    }

    public int doStartTag() throws JspException {
        if (!beanId.equals("")) {
            textFieldBean = (TextFieldBean)pageContext.getAttribute(getBeanKey(), PageContext.REQUEST_SCOPE);
            if (textFieldBean == null) {
                textFieldBean = new TextFieldBean();
                if (maxlength != 0) textFieldBean.setMaxLength(maxlength);
                if (size != 0) textFieldBean.setSize(size);
                this.setBaseComponentBean(textFieldBean);
            } else {
                if (maxlength != 0) textFieldBean.setMaxLength(maxlength);
                if (size != 0) textFieldBean.setSize(size);
                this.updateBaseComponentBean(textFieldBean);
            }
        } else {
            textFieldBean = new TextFieldBean();
            if (maxlength != 0) textFieldBean.setMaxLength(maxlength);
            if (size != 0) textFieldBean.setSize(size);
            this.setBaseComponentBean(textFieldBean);
        }

        //debug();
        try {
            JspWriter out = pageContext.getOut();
            out.print(textFieldBean.toStartString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return SKIP_BODY;
    }

}
