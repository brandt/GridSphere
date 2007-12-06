package org.gridsphere.provider.portletui.tags;

import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.provider.portletui.beans.ParamBean;
import org.gridsphere.provider.portletui.beans.RenderSubmitBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * An <code>RenderSubmitTag</code> provides a button element that includes a <code>DefaultPortletAction</code> and may
 * also include nested <code>ActionParamTag</code>s
 */
public class RenderSubmitTag extends ActionTag {

    protected RenderSubmitBean renderSubmitBean = null;

    public int doStartTag() throws JspException {
        if (!beanId.equals("")) {
            renderSubmitBean = (RenderSubmitBean) getTagBean();
            if (renderSubmitBean == null) {
                renderSubmitBean = new RenderSubmitBean(beanId);
            } else {
                if (renderSubmitBean.getAction() != null) {
                    action = renderSubmitBean.getAction();
                }
                if (renderSubmitBean.getValue() != null) {
                    value = renderSubmitBean.getValue();
                }
                if (renderSubmitBean.getKey() != null) {
                    key = renderSubmitBean.getKey();
                }
                if (renderSubmitBean.getParamBeanList() != null) {
                    paramBeans = renderSubmitBean.getParamBeanList();
                }
                if (renderSubmitBean.getOnClick() != null) {
                    onClick = renderSubmitBean.getOnClick();
                }
            }
        } else {
            renderSubmitBean = new RenderSubmitBean();
        }

        if (onClick != null){
            renderSubmitBean.setOnClick("this.form.action=this.form.action.replace('/a/','/r/');"+onClick);
        }else{
            renderSubmitBean.setOnClick("this.form.action=this.form.action.replace('/a/','/r/')");
        }

        renderSubmitBean.setName(createActionURI());
        renderSubmitBean.setUseAjax(useAjax);

        if (anchor != null) renderSubmitBean.setAnchor(anchor);

        if (key != null) {
            renderSubmitBean.setKey(key);
            value = getLocalizedText(key);
        }

        if (!beanId.equals("")) {
            this.updateBaseComponentBean(renderSubmitBean);
        } else {
            this.setBaseComponentBean(renderSubmitBean);
        }

        if (action != null) renderSubmitBean.setAction(action);

        if (cssStyle != null) {
            renderSubmitBean.setCssStyle(cssStyle);
        }
        if (cssClass != null) {
            renderSubmitBean.setCssClass(cssClass);
        }

        Object parentTag = getParent();
        if (parentTag instanceof ContainerTag) {
            ContainerTag containerTag = (ContainerTag) parentTag;
            containerTag.addTagBean(renderSubmitBean);
        }
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {

        for (ParamBean paramBean : paramBeans) {
            ParamBean pbean = (ParamBean) paramBean;
            portletPhase.addParameter(pbean.getName(), pbean.getValue());
        }

        String actionURI = createActionURI().toString();
        renderSubmitBean.setName(actionURI);

        if (portletPhase != null) renderSubmitBean.setAction(portletPhase.toString());

        if (pageContext.getRequest().getAttribute(SportletProperties.USE_AJAX) != null) {
            String paction = ((!action.equals("")) ? "&" + portletPhase.toString() : "");
            String portlet = (String) pageContext.getRequest().getAttribute(SportletProperties.PORTLET_NAME);
            String compname = (String) pageContext.getRequest().getAttribute(SportletProperties.COMPONENT_NAME);
            renderSubmitBean.setUseAjax(true);
            renderSubmitBean.setOnClick("GridSphereAjaxHandler2.startRequest('" + portlet + "', '" + compname + "', '" + paction + "');");
        }

        if (useAjax) {
            String cid = (String) pageContext.getRequest().getAttribute(SportletProperties.COMPONENT_ID);
            String paction = ((!action.equals("")) ? "&" + portletPhase.toString() : "");
            renderSubmitBean.setOnClick("GridSphereAjaxHandler.startRequest('" + cid + "', '" + paction + "');");
        }

        if ((bodyContent != null) && (value == null)) {
            renderSubmitBean.setValue(bodyContent.getString());
        }

        try {
            JspWriter out = pageContext.getOut();
            out.print(renderSubmitBean.toEndString());
        } catch (Exception e) {
            throw new JspException(e);
        }

        return EVAL_PAGE;
    }

    public void release() {
        super.release();
        renderSubmitBean = null;
    }

}
