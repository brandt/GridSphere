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

    public static final String ACTION_STYLE = "portlet-frame-label";

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

    public void setActionLinkBean(ActionLinkBean actionlink) {
        this.actionlink = actionlink;
    }

    public ActionLinkBean getActionLinkBean() {
        return actionlink;
    }

    public int doStartTag() throws JspException {
        this.cssStyle = ACTION_STYLE;
        paramBeans = new ArrayList();
        createActionURI();
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspTagException {
        if (!beanId.equals("")) {
            actionlink = (ActionLinkBean)pageContext.getSession().getAttribute(getBeanKey());
            if (actionlink == null) {
                actionlink = new ActionLinkBean();
            }
        } else {
            actionlink = new ActionLinkBean();
        }

        Iterator it = paramBeans.iterator();
        while (it.hasNext()) {
            ActionParamBean pbean = (ActionParamBean)it.next();
            portletAction.addParameter(pbean.getName(), pbean.getValue());
        }

        actionlink.setAction(createActionURI());
        //actionlink.setValue(value);

        this.setBaseComponentBean(actionlink);

        if (getKey() != null) {
            actionlink.setKey(key);
            Locale locale = pageContext.getRequest().getLocale();
            ResourceBundle bundle = ResourceBundle.getBundle("Portlet", locale);
            actionlink.setValue(bundle.getString(actionlink.getKey()));
        }
        //actionlink.setAction(action);


        if (!beanId.equals("")) {
            store(getBeanKey(), actionlink);
        }

        Object parentTag = getParent();
        if (parentTag instanceof ContainerTag) {
            ContainerTag containerTag = (ContainerTag)parentTag;
            containerTag.addTagBean(actionlink);
            //System.err.println("inTextTag: adding " + textBean.toString());
        } else {

            try {
                JspWriter out = pageContext.getOut();
                out.println(actionlink);
            } catch (Exception e) {
                throw new JspTagException(e.getMessage());
            }

        }
        return EVAL_PAGE;
    }
}
