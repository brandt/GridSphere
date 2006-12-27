package org.gridsphere.provider.portletui.tags;

import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.provider.portletui.beans.ImageBean;
import org.gridsphere.provider.portletui.beans.MessageStyle;
import org.gridsphere.provider.portletui.beans.RenderLinkBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

/**
 * The <code>ActionLinkTag</code> provides a hyperlink element that includes a <code>DefaultPortletAction</code>
 * and can contain nested <code>ActionParamTag</code>s
 */
public class RenderLinkTag extends ActionTag {

    protected RenderLinkBean renderlink = null;

    protected String style = MessageStyle.MSG_INFO;
    protected ImageBean imageBean = null;

    /**
     * Sets the style of the text: Available styles are
     * <ul>
     * <li>nostyle</li>
     * <li>error</li>
     * <li>info</li>
     * <li>status</li>
     * <li>alert</li>
     * <li>success</li>
     *
     * @param style the text style
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * Returns the style of the text: Available styles are
     * <ul>
     * <li>nostyle</li>
     * <li>error</li>
     * <li>info</li>
     * <li>status</li>
     * <li>alert</li>
     * <li>success</li>
     *
     * @return the text style
     */
    public String getStyle() {
        return style;
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
     * Returns the action link key used to locate localized text
     *
     * @return the action link key
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the image bean
     *
     * @param imageBean the image bean
     */
    public void setImageBean(ImageBean imageBean) {
        this.imageBean = imageBean;
    }

    /**
     * Returns the image bean
     *
     * @return the image bean
     */
    public ImageBean getImageBean() {
        return imageBean;
    }

    public int doStartTag() throws JspException {
        if (!beanId.equals("")) {
            renderlink = (RenderLinkBean) getTagBean();
            if (renderlink == null) {
                renderlink = new RenderLinkBean(beanId);
                renderlink.setStyle(style);
                this.setBaseComponentBean(renderlink);
            } else {
                if (renderlink.getParamBeanList() != null) {
                    paramBeans = renderlink.getParamBeanList();
                }
                if (renderlink.getAction() != null) {
                    action = renderlink.getAction();
                }
                if (renderlink.getValue() != null) {
                    value = renderlink.getValue();
                }
                if (renderlink.getKey() != null) {
                    key = renderlink.getKey();
                }
                if (renderlink.getOnClick() != null) {
                    onClick = renderlink.getOnClick();
                }
            }
        } else {
            renderlink = new RenderLinkBean();
            this.setBaseComponentBean(renderlink);
            renderlink.setStyle(style);
        }

        renderlink.setUseAjax(useAjax);
        if (name != null) renderlink.setName(name);
        if (anchor != null) renderlink.setAnchor(anchor);
        if (action != null) renderlink.setAction(action);
        if (value != null) renderlink.setValue(value);
        if (onClick != null) renderlink.setOnClick(onClick);
        if (style != null) renderlink.setStyle(style);
        if (cssStyle != null) renderlink.setCssStyle(cssStyle);
        if (cssClass != null) renderlink.setCssClass(cssClass);
        if (layout != null) renderlink.setLayout(label);
        if (onMouseOut != null) renderlink.setOnMouseOut(onMouseOut);
        if (onMouseOver != null) renderlink.setOnMouseOver(onMouseOver);

        Tag parent = getParent();
        if (parent instanceof ActionMenuTag) {
            ActionMenuTag actionMenuTag = (ActionMenuTag) parent;
            if (!actionMenuTag.getLayout().equals("horizontal")) {
                renderlink.setCssStyle("display: block");
            }
        }

        if (key != null) {
            renderlink.setKey(key);
            renderlink.setValue(getLocalizedText(key));
            value = renderlink.getValue();
        }

        return EVAL_BODY_BUFFERED;
    }

    public int doEndTag() throws JspException {
        if (!beanId.equals("")) {
            paramBeans = renderlink.getParamBeanList();
            label = renderlink.getLabel();
            action = renderlink.getAction();
        }

        renderlink.setPortletURI(createRenderURI());

        if ((bodyContent != null) && (value == null)) {
            renderlink.setValue(bodyContent.getString());
        }

        if (pageContext.getRequest().getAttribute(SportletProperties.USE_AJAX) != null) {
            String paction = ((!action.equals("")) ? "&" + portletPhase.toString() : "");
            String portlet = (String) pageContext.getRequest().getAttribute(SportletProperties.PORTLET_NAME);
            String compname = (String) pageContext.getRequest().getAttribute(SportletProperties.COMPONENT_NAME);
            renderlink.setUseAjax(true);
            renderlink.setOnClick("GridSphereAjaxHandler2.startRequest('" + portlet + "', '" + compname + "', '" + paction + "');");
        }

        if (useAjax) {
            String cid = (String) pageContext.getRequest().getAttribute(SportletProperties.COMPONENT_ID);
            String paction = ((!action.equals("")) ? "&" + portletPhase.toString() : "");
            renderlink.setOnClick("GridSphereAjaxHandler.startRequest(" + cid + ", '" + paction + "');");
        }

        if (imageBean != null) {
            String val = renderlink.getValue();
            if (val == null) val = "";
            renderlink.setValue(imageBean.toStartString() + val);
        }

        if (var == null) {
            try {
                JspWriter out = pageContext.getOut();
                out.print(renderlink.toEndString());
            } catch (Exception e) {
                throw new JspException(e.getMessage());
            }
        } else {
            pageContext.setAttribute(var, renderlink.toEndString(), PageContext.PAGE_SCOPE);
        }
        release();
        return EVAL_PAGE;
    }
}
