/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.provider.portletui.beans.ActionLinkBean;
import org.gridlab.gridsphere.provider.portletui.beans.ActionParamBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

import java.util.*;

public class ActionLinkTag extends ActionTag {

    protected ActionLinkBean actionlink = null;
    protected String key = null;

    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Returns the label of the beans.
     * @return label of the beans
     */
    public String getKey() {
        return key;
    }

    public int doStartTag() throws JspException {
        actionlink = new ActionLinkBean();
        paramBeans = new ArrayList();
        createActionURI();

        actionlink.setValue(value);
        if (getKey() != null) {
            actionlink.setKey(key);
            Locale locale = pageContext.getRequest().getLocale();
            ResourceBundle bundle = ResourceBundle.getBundle("Portlet", locale);
            actionlink.setValue(bundle.getString(actionlink.getKey()));
        }
        this.setBaseComponentBean(actionlink);
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        Iterator it = paramBeans.iterator();
        while (it.hasNext()) {
            ActionParamBean pbean = (ActionParamBean)it.next();
            portletAction.addParameter(pbean.getName(), pbean.getValue());
        }

        actionlink.setAction(createActionURI());

        Object parentTag = getParent();
        if (parentTag instanceof ContainerTag) {
            ContainerTag containerTag = (ContainerTag)parentTag;
            containerTag.addTagBean(actionlink);
        } else {

            try {
                JspWriter out = pageContext.getOut();
                out.println(actionlink.toStartString());
            } catch (Exception e) {
                throw new JspTagException(e.getMessage());
            }

        }
        return EVAL_PAGE;
    }
}
