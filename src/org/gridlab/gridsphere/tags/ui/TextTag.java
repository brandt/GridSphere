/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.ui;

import org.gridlab.gridsphere.tags.ui.BaseTag;
import org.gridlab.gridsphere.provider.ui.beans.TextBean;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.PortletLog;

import javax.servlet.jsp.JspException;

public class TextTag extends BaseTag {

    protected transient static PortletLog log = SportletLog.getInstance(TextTag.class);

    protected String text = new String();

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int doStartTag() throws JspException {
        if (bean.equals("")) {
            log.debug("Bean is empty");
            this.htmlelement = new TextBean(text);
        }
        log.debug("Bean is : "+bean);
        return super.doStartTag();
    }

}
