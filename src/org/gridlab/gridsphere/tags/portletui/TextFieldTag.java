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

public class TextFieldTag extends BaseBeanTag {

    protected TextFieldBean textFieldBean = new TextFieldBean();
    public static final String TEXTFIELD_STYLE = "portlet-frame-text";

    protected int size;
    protected String inputType = "text";
    protected int maxlength;



    public int doStartTag() throws JspException {
        textFieldBean.setCssStyle(TEXTFIELD_STYLE);
        TextFieldBean tb = (TextFieldBean)pageContext.getSession().getAttribute(getBeanKey());
        if (tb != null) {
            System.err.println("found a text bean in the session");
            textFieldBean = tb;
        }
        if (!beanId.equals("")) {
            System.err.println("storing bean in the session");
            store(getBeanKey(), textFieldBean);
        }
        //debug();



        Object parentTag = getParent();
        if (parentTag instanceof ContainerTag) {
            ContainerTag containerTag = (ContainerTag)parentTag;
            containerTag.addTagBean(textFieldBean);
        } else {
            try {
                System.err.println("writing text");
                JspWriter out = pageContext.getOut();
                out.print(textFieldBean.toString());
            } catch (Exception e) {
                throw new JspException(e.getMessage());
            }
        }
        return EVAL_BODY_INCLUDE;
    }

}
