package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.provider.portletui.beans.*;
import org.gridlab.gridsphere.provider.portletui.model.DefaultTableModel;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public class ErrorFrameTag extends TableTag {

    protected String key = null;
    protected String value = null;

    public int doStartTag() throws JspException {
        return SKIP_BODY;
    }

    public int doEndTag() throws JspException {

        if (!beanId.equals("")) {
            tableBean = (ErrorFrameBean)pageContext.getAttribute(getBeanKey(), PageContext.REQUEST_SCOPE);
            if (tableBean == null) {
                tableBean = new ErrorFrameBean();
            }
        } else {
            tableBean = new ErrorFrameBean();
        }

        if (tableBean.getKey() != null) {
            Locale locale = pageContext.getRequest().getLocale();
            ResourceBundle bundle = ResourceBundle.getBundle("Portlet", locale);
            tableBean.setValue(bundle.getString(tableBean.getKey()));
        }

        return super.doEndTag();
    }


}
