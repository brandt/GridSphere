/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.tags.jsr;

import org.gridlab.gridsphere.provider.portletui.beans.ActionParamBean;
import org.gridlab.gridsphere.provider.portletui.beans.ActionSubmitBean;
import org.gridlab.gridsphere.provider.portletui.tags.ActionSubmitTag;
import org.gridlab.gridsphere.provider.portletui.tags.ContainerTag;
import org.gridlab.gridsphere.provider.portlet.tags.jsr.ActionTagImpl;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.portlet.RenderResponse;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * An <code>ActionSubmitTag</code> provides a button element that includes a <code>DefaultPortletAction</code> and may
 * also include nested <code>ActionParamTag</code>s
 */
public class ActionSubmitTagImpl extends ActionTagImpl implements ActionSubmitTag {

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

    public int doStartTag() throws JspException {
        actionSubmitBean = new ActionSubmitBean();
        if (!beanId.equals("")) {
            actionSubmitBean = (ActionSubmitBean) pageContext.getAttribute(getBeanKey());
        }
        if (actionSubmitBean == null) actionSubmitBean = new ActionSubmitBean();
        paramBeans = new ArrayList();
        RenderResponse res = (RenderResponse) pageContext.getAttribute(SportletProperties.RENDER_RESPONSE, PageContext.REQUEST_SCOPE);
        actionSubmitBean.setName(createActionURI(res.createActionURL()).toString());

        if (anchor != null) actionSubmitBean.setAnchor(anchor);

        if (!key.equals("")) {
            actionSubmitBean.setKey(key);
            value = getLocalizedText(key);
        }

        if (!beanId.equals("")) {
            this.updateBaseComponentBean(actionSubmitBean);
        } else {
            this.setBaseComponentBean(actionSubmitBean);
        }

        actionSubmitBean.setAction(action);
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
        RenderResponse res = (RenderResponse) pageContext.getAttribute(SportletProperties.RENDER_RESPONSE, PageContext.REQUEST_SCOPE);
        String actionURI = createActionURI(res.createActionURL()).toString();

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
