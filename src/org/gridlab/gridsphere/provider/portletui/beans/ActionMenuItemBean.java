package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;

import java.util.Iterator;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

public class ActionMenuItemBean extends BeanContainer {

    protected boolean seperator = false;
    protected String align = ActionMenuBean.MENU_HORIZONTAL;
    protected String menuType = ActionMenuBean.TYPE_ACTIONBAR;
    //protected String info = null;

    public ActionMenuItemBean() {
        super();
    }

    public ActionMenuItemBean(String beanId) {
        super();
        this.beanId = beanId;
    }

    public ActionMenuItemBean(PortletRequest req, String beanId) {
        super();
        this.request = req;
        this.beanId = beanId;
    }


    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getAlign() {
        return align;
    }

    /**
     * Sets the alignment of the MenuItem. If it is added to a @see ActionMenuBean the alignment from that one
     * has will be used.
     * @param menualign
     */
    public void setAlign(String menualign) {
        this.align = menualign;
    }

    public boolean isSeperator() {
        return seperator;
    }

    public void setSeperator(boolean seperator) {
        this.seperator = seperator;
    }

    public String toStartString() {
        StringBuffer sb = new StringBuffer();
        if (menuType.equals(ActionMenuBean.TYPE_ACTIONBAR)) {
            if (this.align.equals(ActionMenuBean.MENU_VERTICAL)) {
                sb.append("<div style=\"display: block; margin-top: 5px;\">");
            }
        }

        return sb.toString();
    }

    public String toEndString() {
        StringBuffer sb = new StringBuffer();

        Iterator it = container.iterator();
        while (it.hasNext()) {
            BaseComponentBean itemBean = (BaseComponentBean) it.next();
            sb.append(itemBean.toStartString());
            sb.append(itemBean.toEndString());
        }
        if (align.equals(ActionMenuBean.MENU_VERTICAL)) {
            sb.append("</div>");
        }
        return sb.toString();
    }
}
