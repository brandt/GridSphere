/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: ListBoxTag.java 4666 2006-03-27 17:47:56Z novotny $
 */
package org.gridsphere.provider.portletui.tags;

import org.gridsphere.provider.portletui.beans.ListBoxBean;
import org.gridsphere.provider.portletui.beans.ListBoxItemBean;
import org.gridsphere.provider.portletui.beans.TagBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.util.ArrayList;

/**
 * A <code>ListBoxTag</code> represents a list box element
 */
public class ListBoxTag extends ContainerTag {

    protected ListBoxBean listbox = null;
    protected boolean isMultiple = false;
    protected int size = 1;
    protected String onChange = null;
    protected String onBlur = null;
    protected String onFocus = null;
    protected boolean submitOnChange = false;

    /**
     * Returns the (html) size of the field.
     *
     * @return size of the field
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the (html) size of the field
     *
     * @param size size of the field
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Returns true if this listbox should invoke a form submission if the selection is changed
     *
     * @return true if this listbox should invoke a form submission if the selection is changed
     */
    public boolean getSubmitOnChange() {
        return submitOnChange;
    }

    /**
     * Sets whether this listbox should invoke a form submission if the selection is changed
     *
     * @param submitOnChange if true a form submission is invoked if selection changes
     */
    public void setSubmitOnChange(boolean submitOnChange) {
        this.submitOnChange = submitOnChange;
    }

    /**
     * Returns the onChange JavaScript function
     *
     * @return onChange JavaScript function
     */
    public String getOnChange() {
        return onChange;
    }

    /**
     * Sets the onChange JavaScript function
     *
     * @param onChange the onChange JavaScript function
     */
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

    /**
     * Sets multiple selection
     *
     * @param isMultiple is true if listbox provides multiple selections, false otherwise
     */
    public void setMultiple(boolean isMultiple) {
        this.isMultiple = isMultiple;
    }

    public void setListBoxBean(ListBoxBean listbox) {
        this.listbox = listbox;
    }

    public ListBoxBean getListBoxBean() {
        return listbox;
    }

    /**
     * Indicates if multiple selection is provided
     *
     * @return true if this listbox supports multiple selection, false otherwise
     */
    public boolean getMultiple() {
        return isMultiple;
    }

    public int doStartTag() throws JspException {
        list = new ArrayList<TagBean>();
        if (submitOnChange) onChange = "GridSphere_SelectSubmit( this.form )";
        if (!beanId.equals("")) {
            listbox = (ListBoxBean) getTagBean();
            if (listbox == null) {
                listbox = new ListBoxBean();
                listbox.setSize(size);
                listbox.setMultipleSelection(isMultiple);
                this.setBaseComponentBean(listbox);
            } else {
                if (size != 1) listbox.setSize(size);
                this.updateBaseComponentBean(listbox);
            }
        } else {
            listbox = new ListBoxBean();
            listbox.setSize(size);
            listbox.setMultipleSelection(isMultiple);
            this.setBaseComponentBean(listbox);
        }

        if (onChange != null) listbox.setOnChange(onChange);
        if (onBlur != null) listbox.setOnBlur(onBlur);
        if (onFocus != null) listbox.setOnFocus(onFocus);

        try {
            JspWriter out = pageContext.getOut();
            out.print(listbox.toStartString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        for (TagBean aList : list) {
            ListBoxItemBean itembean = (ListBoxItemBean) aList;
            listbox.addBean(itembean);
        }
        try {
            JspWriter out = pageContext.getOut();
            out.print(listbox.toEndString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return EVAL_PAGE;
    }

}
