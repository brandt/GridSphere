package org.gridsphere.provider.portletui.beans;

import java.util.Iterator;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

public class ActionMenuItemBean extends BeanContainer {

    protected boolean seperator = false;
    protected String align = ActionMenuBean.MENU_HORIZONTAL;
    protected String menutype = ActionMenuBean.TYPE_ACTIONBAR;
    protected boolean isSelected = false;
    //protected String info = null;

    public ActionMenuItemBean() {
        super();
    }

    public ActionMenuItemBean(String beanId) {
        super();
        this.beanId = beanId;
    }

    public String getMenutype() {
        return menutype;
    }

    public void setMenutype(String menutype) {
        this.menutype = menutype;
    }

    public String getAlign() {
        return align;
    }

    /**
     * Sets the alignment of the MenuItem. If it is added to a @see ActionMenuBean the alignment from that one
     * has will be used.
     *
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


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String toStartString() {
        StringBuffer sb = new StringBuffer();
        if (menutype.equals(ActionMenuBean.TYPE_ACTIONTAB)) {
            if (isSelected()) {
                sb.append("<div id=\"actiontabselected\">");
            }
            sb.append("<li id=\"actiontabmenu\">");
        } else if (menutype.equals(ActionMenuBean.TYPE_ACTIONBAR)) {
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

        if (menutype.equals(ActionMenuBean.TYPE_ACTIONTAB)) {
            sb.append("</li>");
            if (isSelected()) {
                sb.append("</div>");
            }
        } else if (menutype.equals(ActionMenuBean.TYPE_ACTIONBAR)) {
            if (align.equals(ActionMenuBean.MENU_VERTICAL)) {
                sb.append("</div>");
            }
        }

        return sb.toString();
    }
}
