/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextFieldBean;
import org.gridlab.gridsphere.provider.portletui.beans.PasswordBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.util.Locale;
import java.util.ResourceBundle;

public class PasswordTag extends BaseComponentTag {

    protected PasswordBean passwordBean = null;
    public static final String PASSWORD_STYLE = "portlet-frame-text";

    protected int size = 10;
    protected int maxlength = 15;

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
            passwordBean = (PasswordBean)pageContext.getSession().getAttribute(getBeanKey());
            if (passwordBean == null) {
                passwordBean = new PasswordBean();
            }
        } else {
                passwordBean = new PasswordBean();
        }
        passwordBean.setCssStyle(PASSWORD_STYLE);
        passwordBean.setMaxLength(maxlength);
        passwordBean.setSize(size);
        this.setBaseComponentBean(passwordBean);
        if (!beanId.equals("")) {
            //System.err.println("storing bean in the session");
            store(getBeanKey(), passwordBean);
        }
        //debug();

        Object parentTag = getParent();
        if (parentTag instanceof ContainerTag) {
            ContainerTag containerTag = (ContainerTag)parentTag;
            containerTag.addTagBean(passwordBean);
        } else {
            try {
                JspWriter out = pageContext.getOut();
                out.print(passwordBean.toString());
            } catch (Exception e) {
                throw new JspException(e.getMessage());
            }
        }
        return EVAL_BODY_INCLUDE;
    }

}
