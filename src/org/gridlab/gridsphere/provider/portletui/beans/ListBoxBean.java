/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletRequest;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;


public class ListBoxBean extends BeanContainer implements TagBean {

    protected String LISTBOX_STYLE = "portlet-frame-input";
    public static final String NAME = "lb";

    protected transient static PortletLog log = SportletLog.getInstance(ListBoxBean.class);

    protected int size = 0;
    protected boolean isMultiple = false;

    public ListBoxBean() {
        super(NAME);
        this.cssStyle = LISTBOX_STYLE;
    }

    public ListBoxBean(String beanId) {
        super(NAME);
        this.beanId = beanId;
        this.cssStyle = LISTBOX_STYLE;
    }

    public ListBoxBean(PortletRequest request, String beanId) {
        super(NAME);
        this.cssStyle = LISTBOX_STYLE;
        this.request = request;
        this.beanId = beanId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setMultipleSelection(boolean isMultiple) {
        this.isMultiple = isMultiple;
    }

    public boolean getMultipleSelection() {
        return isMultiple;
    }

    public String toStartString() {
        StringBuffer sb = new StringBuffer();
        String pname = (name == null) ? "" : name;
        String sname = pname;
        if (!beanId.equals("")) {
            sname = "ui_" + vbName + "_" + beanId + "_" + pname;
        }
        sb.append("<select name='"+sname+"' size='"+size+"'");
        if (isMultiple) {
            sb.append(" multiple='multiple'");
        }
        sb.append(">");

        Iterator it = container.iterator();
        while (it.hasNext()) {
            ListBoxItemBean itemBean = (ListBoxItemBean)it.next();
            sb.append(itemBean.toStartString());
            sb.append(itemBean.toEndString());
        }

        return sb.toString();
    }

    public String toEndString() {
        return "</select>";
    }

    /**
     * Returns the selected values of the list.
     * @return selected values of the list
     */
    public String getSelectedValue() {
        Iterator it = container.iterator();
        while (it.hasNext()) {
            ListBoxItemBean item = (ListBoxItemBean)it.next();
            if (item.isSelected()) {
                return item.getValue();
            }
        }
        return null;
    }

    public boolean hasSelectedValue() {
        Iterator it = container.iterator();
        while (it.hasNext()) {
            ListBoxItemBean item = (ListBoxItemBean)it.next();
            if (item.isSelected()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the selected values of the list.
     * @return selected values of the list
     */
    public List getSelectedValues() {
        List result = new ArrayList();
        Iterator it = container.iterator();
        while (it.hasNext()) {
            ListBoxItemBean item = (ListBoxItemBean)it.next();
            if (item.isSelected()) {
                result.add(item.getValue());
            }
        }
        return result;
    }

    /**
     * Returns the selected items of the list
     * @return  the selected item of the list
     */
    public List getSelectedItems() {
        ArrayList result = new ArrayList();
        Iterator it = container.iterator();
        while (it.hasNext()) {
            ListBoxItemBean item = (ListBoxItemBean)it.next();
            if (item.isSelected()) {
                result.add(item);
            }
        }
        return result;
    }

}
