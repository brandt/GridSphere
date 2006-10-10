/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: ActionSubmitTag.java 4915 2006-07-10 20:51:49Z novotny $
 */
package org.gridsphere.provider.portletui.tags;

import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.provider.portletui.beans.ActionParamBean;
import org.gridsphere.provider.portletui.beans.ActionSubmitBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.util.Iterator;

/**
 * An <code>ActionSubmitTag</code> provides a button element that includes a <code>DefaultPortletAction</code> and may
 * also include nested <code>ActionParamTag</code>s
 */
public class ActionSubmitTag extends ActionTag {

    protected ActionSubmitBean actionSubmitBean = null;

    public int doStartTag() throws JspException {
        if (!beanId.equals("")) {
            actionSubmitBean = (ActionSubmitBean) getTagBean();
            if (actionSubmitBean == null) {
                actionSubmitBean = new ActionSubmitBean(beanId);
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
                if (actionSubmitBean.getOnClick() != null) {
                    onClick = actionSubmitBean.getOnClick();
                }
            }
        } else {
            actionSubmitBean = new ActionSubmitBean();
        }

        if (onClick != null) actionSubmitBean.setOnClick(onClick);

        actionSubmitBean.setName(createActionURI());
        actionSubmitBean.setUseAjax(useAjax);

        if (anchor != null) actionSubmitBean.setAnchor(anchor);

        if (key != null) {
            actionSubmitBean.setKey(key);
            value = getLocalizedText(key);
        }

        if (!beanId.equals("")) {
            this.updateBaseComponentBean(actionSubmitBean);
        } else {
            this.setBaseComponentBean(actionSubmitBean);
        }

        if (action != null) actionSubmitBean.setAction(action);

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

        if (useAjax) {
            String cid = (String)pageContext.getRequest().getAttribute(SportletProperties.COMPONENT_ID);
            String paction = ((!action.equals("")) ? "&" + portletAction.toString() : "");
            actionSubmitBean.setOnClick("GridSphereAjaxHandler.startRequest('" + cid + "', '" + paction + "');");
        }

        if ((bodyContent != null) && (value == null)) {
            actionSubmitBean.setValue(bodyContent.getString());
        }

        try {
            JspWriter out = pageContext.getOut();
            out.print(actionSubmitBean.toEndString());
        } catch (Exception e) {
            throw new JspException(e);
        }

        return EVAL_PAGE;
    }

    public void release() {
        super.release();
        actionSubmitBean = null;
    }

}
