/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.provider.portletui.beans.ActionLinkBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The <code>ActionLinkTag</code> provides a hyperlink element that includes a <code>DefaultPortletAction</code>
 * and can contain nested <code>ActionParamTag</code>s
 */
public class ActionLinkTag extends ActionTag {

    protected ActionLinkBean actionlink = null;
    protected String key = null;

    /**
     * Sets the action link key used to locate localized text
     *
     * @param key the action link key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Returns the action link key used to locate localized text
     *
     * @return the action link key
     */
    public String getKey() {
        return key;
    }

    public int doStartTag() throws JspException {
        if (!beanId.equals("")) {
            actionlink = (ActionLinkBean)pageContext.getAttribute(getBeanKey(), PageContext.REQUEST_SCOPE);
            if (actionlink == null) {
                actionlink = new ActionLinkBean();
                this.setBaseComponentBean(actionlink);
            }
        } else {
            actionlink = new ActionLinkBean();
            this.setBaseComponentBean(actionlink);
        }

        paramBeans = new ArrayList();

        if (key != null) {
            actionlink.setKey(key);
            Locale locale = pageContext.getRequest().getLocale();
            ResourceBundle bundle = ResourceBundle.getBundle("Portlet", locale);
            actionlink.setValue(bundle.getString(actionlink.getKey()));
        }

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {

        actionlink.setAction(createActionURI());

        if ((bodyContent != null) && (value == null)) {
            actionlink.setValue(bodyContent.getString());
        }

        try {
            JspWriter out = pageContext.getOut();
            out.print(actionlink.toEndString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return EVAL_PAGE;
    }
}
