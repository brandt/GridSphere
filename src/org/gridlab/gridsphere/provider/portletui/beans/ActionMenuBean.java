package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletURI;

import java.util.Iterator;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

public class ActionMenuBean extends BeanContainer implements TagBean {

    public final static String TYPE_ACTIONBAR = "actionbar";
    // public final static String TYPE_LINKTREE = "linktree";
    // public final static String TYPE_DROPDOWN = "dropdown";



    protected String align = BaseComponentBean.MENU_HORIZONTAL;
    protected String title = null;
    private boolean hasParentMenu = false;
    protected String menuType = TYPE_ACTIONBAR;
    protected boolean collapsible = false;
    protected boolean collapsed = false;
    protected PortletURI portletURI = null;

    public ActionMenuBean() {
        super();
    }

    public ActionMenuBean(String beanId) {
        super();
        this.beanId = beanId;
    }

    public ActionMenuBean(PortletRequest req, String beanId) {
        super();
        this.request = req;
        this.beanId = beanId;
    }

    public PortletURI getPortletURI() {
        return portletURI;
    }

    public void setPortletURI(PortletURI portletURI) {
        this.portletURI = portletURI;
    }

    public boolean isCollapsible() {
        return collapsible;
    }

    public void setCollapsible(boolean collapsible) {
        this.collapsible = collapsible;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    /**
     * Adds a BaseComponentBean. This can be a bean like a ActionLink, an ActionMenuItemBean wrapper will
     * be created around it.
     * @param bean to add
     */
    public void addMenuEntry(BaseComponentBean bean) {
        ActionMenuItemBean amiBean = new ActionMenuItemBean();
        amiBean.addBean(bean);
        this.addBean(amiBean);

    }

    /**
     * Gets the title of the menu.
     * @return title of the menu
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the menu.
     * @param title title of the menu
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the alignment of the menu.
     * @return alignment
     */
    public String getAlign() {
        return align;
    }

    /**
     * Sets the alignment of the menu.
     * @param align alignmnet of the menu
     */
    public void setAlign(String align) {
        this.align = align;
    }

    /**
     * Returns true when the parent is another actionmenu.
     * @return true/false if parent is actionmenu.
     */
    public boolean isHasParentMenu() {
        return hasParentMenu;
    }

    /**
     * Defines if parent is another Actionmenu. Is used for different coloring of the labels. Set automatically.
     * @param hasParentMenu true/false
     */
    public void setHasParentMenu(boolean hasParentMenu) {
        this.hasParentMenu = hasParentMenu;
    }

    public String toStartString() {
        StringBuffer sb = new StringBuffer();
        if (!hasParentMenu) {
            // need a table for limiting the div just to the size of the longest text
            sb.append("<table class=\"portlet-menu\" ");
            sb.append("><tr><td>");
            //this.cssClass = "portlet-menu";
        } else {
            this.cssStyle = "margin-top: 5px;";
        }

        sb.append("<div " + getFormattedCss() + ">");

//        sb.append("<div ");
//        this.cssClass = "portlet-menu";
        //if (title!=null)  {
        //     this.addCssStyle("padding: 0px;");
        //  }
//        sb.append(getFormattedCss());
//        sb.append(">");
        // try to render title if there is one
        if (title != null) {
            sb.append("<div class=\"portlet-menu-caption\">");

            sb.append(title);

            sb.append("</div>");
        }

        return sb.toString();
    }

    public String toEndString() {

        StringBuffer sb = new StringBuffer();

        if (!this.isCollapsed()) {


            Iterator it = container.iterator();
            while (it.hasNext()) {
                BaseComponentBean bean = (BaseComponentBean) it.next();
                if (bean instanceof ActionMenuItemBean) {
                    ActionMenuItemBean itemBean = (ActionMenuItemBean) bean;
                    // if child is actionitem set these values on them, needed for correct rendering
                    itemBean.setAlign(this.align);
                    itemBean.setMenuType(this.menuType);
                }
                sb.append(bean.toStartString());
                sb.append(bean.toEndString());
            }
        }

        sb.append("</div>");
        if (!hasParentMenu) {
            sb.append("</td></tr></table>");
        }
        return sb.toString();
    }
}