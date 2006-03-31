/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.provider.portletui.beans.ActionParamBean;
import org.gridlab.gridsphere.provider.portletui.beans.ActionSubmitBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * An <code>ActionSubmitTag</code> provides a button element that includes a <code>DefaultPortletAction</code> and may
 * also include nested <code>ActionParamTag</code>s
 */
public class ActionSubmitTag extends ActionTag {

    protected String key = "";

    protected ActionSubmitBean actionSubmitBean = null;

    /**
     * Returns the action link key used to locate localized text
     *
     * @return the action link key
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the action link key used to locate localized text
     *
     * @param key the action link key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Returns the onClick JavaScript function
     *
     * @return onClick JavaScript function
     */
    public String getOnClick() {
        return onClick;
    }

    /**
     * Sets the onClick JavaScript function
     *
     * @param onClick the onClick JavaScript function
     */
    public void setOnClick(String onClick) {
        this.onClick = onClick;
    }

    public int doStartTag() throws JspException {
        if (!beanId.equals("")) {
            actionSubmitBean = (ActionSubmitBean) getTagBean();
            if (actionSubmitBean == null) {
                actionSubmitBean = new ActionSubmitBean(beanId);
                paramBeans = new ArrayList();
            } else {

                if (actionSubmitBean.getAction() != null) {
                    action = actionSubmitBean.getAction();
                }
                if (actionSubmitBean.getValue() != null) {
                    value = actionSubmitBean.getValue();
                }
                if (actionSubmitBean.getKey() != null) {
                    key = actionSubmitBean.getKey();
                }
                if (actionSubmitBean.getParamBeanList() != null) {
                    paramBeans = actionSubmitBean.getParamBeanList();
                }

            }
        } else {
            actionSubmitBean = new ActionSubmitBean();
            paramBeans = new ArrayList();
        }

        actionSubmitBean.setName(createActionURI());

        if (anchor != null) actionSubmitBean.setAnchor(anchor);

        if (!key.equals("")) {
            actionSubmitBean.setKey(key);
            value = getLocalizedText(key);
        }

        if (onClick != null) {
            actionSubmitBean.setOnClick(onClick);
        }
        
        if (!beanId.equals("")) {
            this.updateBaseComponentBean(actionSubmitBean);
        } else {
            this.setBaseComponentBean(actionSubmitBean);
        }

        if (action != null) actionSubmitBean.setAction(action);
        if (trackMe != null) actionSubmitBean.setTrackme(trackMe);
        
        if (cssStyle != null) {
            actionSubmitBean.setCssStyle(cssStyle);
        }
        if (cssClass != null) {
            actionSubmitBean.setCssClass(cssClass);
        }

        Object parentTag = getParent();
        if (parentTag instanceof ContainerTag) {
            ContainerTag containerTag = (ContainerTag) parentTag;
            containerTag.addTagBean(actionSubmitBean);

        }
        try {
            JspWriter out = pageContext.getOut();
            out.print(actionSubmitBean.toStartString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {

        Iterator it = paramBeans.iterator();
        while (it.hasNext()) {
            ActionParamBean pbean = (ActionParamBean) it.next();
            portletAction.addParameter(pbean.getName(), pbean.getValue());
        }

        String actionURI = createActionURI().toString();

        actionSubmitBean.setName(actionURI);

        if (portletAction != null) actionSubmitBean.setAction(portletAction.toString());

        if ((bodyContent != null) && (value == null)) {
            actionSubmitBean.setValue(bodyContent.getString());
        }

        try {
            JspWriter out = pageContext.getOut();
            out.print(actionSubmitBean.toEndString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return EVAL_PAGE;
    }

}
