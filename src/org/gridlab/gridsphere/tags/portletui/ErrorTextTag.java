/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.provider.portletui.beans.TextBean;
import org.gridlab.gridsphere.provider.portletui.beans.TextBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.util.Locale;
import java.util.ResourceBundle;

public class ErrorTextTag extends TextTag {

    public static final String TEXT_ERROR_STYLE = "portlet-frame-message-alert";

    public int doStartTag() throws JspException {
        this.cssStyle = TEXT_ERROR_STYLE;

        return super.doStartTag();
    }

    public int doEndTag() throws JspException {
       return super.doEndTag();
   }
}
