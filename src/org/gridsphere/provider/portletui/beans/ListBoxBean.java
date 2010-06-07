/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */

package org.gridsphere.provider.portletui.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * A <code>ListBoxBean</code> represents a visual list box element
 */
public class ListBoxBean extends BeanContainer implements TagBean {

    protected String LISTBOX_STYLE = "portlet-form-field";
    public static final String NAME = "lb";

    protected int size = 0;
    protected boolean isMultiple = false;
    protected String onChange = null;
    protected String onBlur = null;
    protected String onFocus = null;

    /**
     * Constructs a default list box bean
     */
    public ListBoxBean() {
        super(NAME);
        this.cssClass = LISTBOX_STYLE;
    }

    /**
     * Constructs a list box bean with the supplied bean identifier
     *
     * @param beanId the bean identifier
     */
    public ListBoxBean(String beanId) {
        super(NAME);
        this.beanId = beanId;
        this.cssClass = LISTBOX_STYLE;
    }

    /**
     * Returns the size of the list box
     *
     * @return the size of the list box
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the size of the list box
     *
     * @param size the size of the list box
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Sets multiple selection
     *
     * @param isMultiple is true if listbox provides multiple selections, false otherwise
     */
    public void setMultipleSelection(boolean isMultiple) {
        this.isMultiple = isMultiple;
    }

    /**
     * Indicates if multiple selection is provided
     *
     * @return true if this listbox supports multiple selection, false otherwise
     */
    public boolean getMultipleSelection() {
        return isMultiple;
    }

    public void setOnChange(String onChange) {
        this.onChange = onChange;
    }

    /**
     * Returns the onBlur JavaScript function
     *
     * @return onBlur JavaScript function
     */
    public String getOnBlur() {
        return onBlur;
    }

    /**
     * Sets the onBlur JavaScript function
     *
     * @param onBlur the onBlur JavaScript function
     */
    public void setOnBlur(String onBlur) {
        this.onBlur = onBlur;
    }

    /**
     * Returns the onFocus JavaScript function
     *
     * @return onFocus JavaScript function
     */
    public String getOnFocus() {
        return onChange;
    }

    /**
     * Sets the onFocus JavaScript function
     *
     * @param onFocus the onFocus JavaScript function
     */
    public void setOnFocus(String onFocus) {
        this.onFocus = onFocus;
    }

    public String toStartString() {
        StringBuffer sb = new StringBuffer();

        String sname = createTagName(name);

        sb.append("<select ").append(getFormattedCss()).append(" name='").append(sname).append("' size='").append(size).append("'");
        sb.append(" id='").append(id).append("'");
        if (isMultiple) {
            sb.append(" multiple='multiple'");
        }
        if (disabled) {
            // 'disabled' replaced by 'disabled="disabled"' for XHTML 1.0 Strict compliance
            sb.append(" disabled=\"disabled\" ");
        }
        if (onChange != null) {
            sb.append(" onchange='").append(onChange).append("'");
        }
        if (onBlur != null) {
            sb.append(" onblur='").append(onBlur).append("'");
        }
        if (onFocus != null) {
            sb.append(" onfocus='").append(onFocus).append("'");
        }

        sb.append(">");
        return sb.toString();
    }

    public String toEndString() {
        StringBuffer sb = new StringBuffer();
        for (BaseComponentBean aContainer : container) {
            ListBoxItemBean itemBean = (ListBoxItemBean) aContainer;
            sb.append(itemBean.toStartString());
            sb.append(itemBean.toEndString());
        }
        sb.append("</select>");
        return sb.toString();
    }

    /**
     * Returns the selected value of the list. This is only useful with multiple selection disabled.
     *
     * @return selected value of the list, null if nothing is selected
     */
    public String getSelectedValue() {
        for (BaseComponentBean aContainer : container) {
            ListBoxItemBean item = (ListBoxItemBean) aContainer;
            if (item.isSelected()) {
                return item.getValue();
            }
        }
        return null;
    }

    /**
     * Returns true if the listbox has a selected value, false otherwise
     *
     * @return true if an item is selected, otherwise false
     */
    public boolean hasSelectedValue() {
        for (BaseComponentBean aContainer : container) {
            ListBoxItemBean item = (ListBoxItemBean) aContainer;
            if (item.isSelected()) {
                return true;
            }
        }
        return false;
    }

    private List getSelectedNamesValues(boolean names) {
        List<String> result = new ArrayList<String>();
        for (BaseComponentBean aContainer : container) {
            ListBoxItemBean item = (ListBoxItemBean) aContainer;
            if (item.isSelected()) {
                if (names) {
                    result.add(item.getName());
                } else {
                    result.add(item.getValue());
                }
            }
        }
        return result;

    }

    /**
     * Returns the selected values of the list.
     *
     * @return selected values of the list
     */
    public List getSelectedValues() {
        return getSelectedNamesValues(false);
    }

    /**
     * Returns the selected names of the list
     *
     * @return selected names of the list
     */
    public List getSelectedNames() {
        return getSelectedNamesValues(true);
    }

    /**
     * Returns the selected values of the list.
     *
     * @return selected values of the list
     */
    public String getSelectedName() {
        for (BaseComponentBean aContainer : container) {
            ListBoxItemBean item = (ListBoxItemBean) aContainer;
            if (item.isSelected()) {
                return item.getName();
            }
        }
        return null;
    }

    /**
     * Returns the selected items of the list
     *
     * @return the selected item of the list
     */
    public List getSelectedItems() {
        ArrayList<ListBoxItemBean> result = new ArrayList<ListBoxItemBean>();
        for (BaseComponentBean aContainer : container) {
            ListBoxItemBean item = (ListBoxItemBean) aContainer;
            if (item.isSelected()) {
                result.add(item);
            }
        }
        return result;
    }

}
