/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.provider.portletui.tags;

import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.provider.portletui.beans.ActionSubmitBean;
import org.gridsphere.provider.portletui.beans.ParamBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * An <code>ActionSubmitTag</code> provides a button element that includes a <code>DefaultPortletAction</code> and may
 * also include nested <code>ParamTag</code>s
 */
public class ActionSubmitTag extends ActionTag {

    protected ActionSubmitBean actionSubmitBean = null;
    protected String imageSrc = null;

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

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
        if (imageSrc != null) actionSubmitBean.setImageSrc(imageSrc);

        if (key != null) {
            value = getLocalizedText(key);
            actionSubmitBean.setKey(key);
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
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {

        for (ParamBean paramBean : paramBeans) {
            ParamBean pbean = (ParamBean) paramBean;
            portletPhase.addParameter(pbean.getName(), pbean.getValue());
        }

        String actionURI = createActionURI().toString();
        actionSubmitBean.setName(actionURI);

        if (portletPhase != null) actionSubmitBean.setAction(portletPhase.toString());

        if (pageContext.getRequest().getAttribute(SportletProperties.USE_AJAX) != null) {
            String paction = ((!action.equals("")) ? "&" + portletPhase.toString() : "");
            String portlet = (String) pageContext.getRequest().getAttribute(SportletProperties.PORTLET_NAME);
            String compname = (String) pageContext.getRequest().getAttribute(SportletProperties.COMPONENT_NAME);
            actionSubmitBean.setUseAjax(true);
            actionSubmitBean.setOnClick("GridSphereAjaxHandler2.startRequest('" + portlet + "', '" + compname + "', '" + paction + "');");
        }

        if (useAjax) {
            String cid = (String) pageContext.getRequest().getAttribute(SportletProperties.COMPONENT_ID);
            String paction = ((!action.equals("")) ? "&" + portletPhase.toString() : "");
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
