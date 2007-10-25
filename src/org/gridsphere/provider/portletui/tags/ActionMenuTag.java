package org.gridsphere.provider.portletui.tags;

import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.provider.portletui.beans.ActionMenuBean;

import javax.portlet.*;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

public class ActionMenuTag extends ContainerTag {

    protected ActionMenuBean actionMenuBean = null;
    protected String layout = null;
    protected String title = null;
    protected String menutype = null;
    protected boolean collapsible = false;
    protected boolean collapsed = false;
    protected String key = null;

    public ActionMenuBean getActionMenuBean() {
        return actionMenuBean;
    }

    public String getMenutype() {
        return menutype;
    }

    public void setMenutype(String menutype) {
        this.menutype = menutype;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public boolean isCollapsible() {
        return collapsible;
    }

    public void setCollapsible(boolean collapsible) {
        this.collapsible = collapsible;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    /**
     * Returns the key used to identify localized text
     *
     * @return the key used to identify localized text
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key used to identify localized text
     *
     * @param key the key used to identify localized text
     */
    public void setKey(String key) {
        this.key = key;
    }

    public int doStartTag() throws JspException {

        // get the bean and the values
        if (!beanId.equals("")) {
            actionMenuBean = (ActionMenuBean) getTagBean();
        }
        if (actionMenuBean == null) {
            actionMenuBean = new ActionMenuBean();
        }

        Tag parent = getParent();
        if (parent instanceof ActionMenuTag) {
            actionMenuBean.setHasParentMenu(true);
            if (menutype == null) {
                ActionMenuTag actionMenu = (ActionMenuTag)parent;
                menutype = actionMenu.getMenutype();
            }
        }

        if (layout == null) {
            layout = actionMenuBean.getAlign();
        } else {
            actionMenuBean.setAlign(layout);
        }
        if (title == null) {
            title = actionMenuBean.getTitle();
        } else {
            actionMenuBean.setTitle(title);
        }
        if (menutype == null) {
            menutype = actionMenuBean.getMenutype();
        } else {
            actionMenuBean.setMenutype(menutype);
        }


        // TODO not working so far
        actionMenuBean.setCollapsible(this.collapsible);
        actionMenuBean.setCollapsed(this.collapsed);
        // if using JSR then create render link
        RenderResponse res = (RenderResponse) pageContext.getAttribute(SportletProperties.RENDER_RESPONSE, PageContext.REQUEST_SCOPE);
        PortletURL uri = null;

            uri = res.createActionURL();

        actionMenuBean.setPortletURL(uri);
//        PortletResponse res = (PortletResponse) pageContext.getAttribute("portletResponse");
//        PortletURI uri = res.createURI();
        // locale stuff
        if (key != null) {
            actionMenuBean.setTitle(getLocalizedText(key, "ActionMenu"));
        }

        try {
            JspWriter out = pageContext.getOut();
            out.print(actionMenuBean.toStartString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }
        return EVAL_BODY_INCLUDE;

    }

    protected String createJSRActionURI(RenderResponse res) throws JspException {
        // action is a required attribute except for FormTag
        RenderRequest req = (RenderRequest) pageContext.getAttribute(SportletProperties.RENDER_REQUEST, PageContext.REQUEST_SCOPE);
        String windowState = req.getWindowState().toString();
        String portletMode = req.getPortletMode().toString();
        PortletURL actionURL = res.createRenderURL();
        if (windowState != null) {
            WindowState state = new WindowState(windowState);
            try {
                System.err.println("set state to:" + state);
                actionURL.setWindowState(state);
            } catch (WindowStateException e) {
                throw new JspException("Unknown window state in renderURL tag: " + windowState);
            }
        }
        if (portletMode != null) {
            PortletMode mode = new PortletMode(portletMode);
            try {
                actionURL.setPortletMode(mode);
                System.err.println("set mode to:" + mode);
            } catch (PortletModeException e) {
                throw new JspException("Unknown portlet mode in renderURL tag: " + portletMode);
            }
        }

        System.err.println("printing action  URL = " + actionURL.toString());

        return actionURL.toString();
    }

    public int doEndTag() throws JspException {

        try {
            JspWriter out = pageContext.getOut();
            out.print(actionMenuBean.toEndString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return EVAL_PAGE;
    }
}