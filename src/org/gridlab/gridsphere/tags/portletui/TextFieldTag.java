/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextFieldBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.util.Locale;
import java.util.ResourceBundle;

public class TextFieldTag extends BaseComponentTag {

    protected TextFieldBean textFieldBean = null;
    public static final String TEXTFIELD_STYLE = "portlet-frame-text";

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
            textFieldBean = (TextFieldBean)pageContext.getSession().getAttribute(getBeanKey());
            if (textFieldBean == null) textFieldBean = new TextFieldBean();
        } else {
            textFieldBean = new TextFieldBean();
        }

        textFieldBean.setCssStyle(TEXTFIELD_STYLE);
        if (maxlength != 0) textFieldBean.setMaxLength(maxlength);
        if (size != 0) textFieldBean.setSize(size);
        this.setBaseComponentBean(textFieldBean);

        if (!beanId.equals("")) {
            //System.err.println("storing bean in the session");
            store(getBeanKey(), textFieldBean);
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
