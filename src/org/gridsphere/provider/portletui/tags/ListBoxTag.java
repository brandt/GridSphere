/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: ListBoxTag.java 4666 2006-03-27 17:47:56Z novotny $
 */
package org.gridsphere.provider.portletui.tags;

import org.gridsphere.provider.portletui.beans.ListBoxBean;
import org.gridsphere.provider.portletui.beans.ListBoxItemBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.util.Iterator;
import java.util.Vector;

/**
 * A <code>ListBoxTag</code> represents a list box element
 */
public class ListBoxTag extends ContainerTag {

    protected ListBoxBean listbox = null;
    protected boolean isMultiple = false;
    protected int size = 1;
    protected String onChange = null;
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
     * Sets m  ultiple selection
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
        list = new Vector();

        if (submitOnChange) onChange="GridSphere_SelectSubmit( this.form )";
        if (!beanId.equals("")) {
            listbox = (ListBoxBean) getTagBean();
            if (listbox == null) {
                listbox = new ListBoxBean();
                listbox.setSize(size);
                listbox.setMultipleSelection(isMultiple);
                listbox.setOnChange(onChange);
                this.setBaseComponentBean(listbox);
            } else {
                if (size != 1) listbox.setSize(size);
                this.updateBaseComponentBean(listbox);
                if (onChange != null) listbox.setOnChange(onChange);
            }
        } else {
            listbox = new ListBoxBean();
            listbox.setSize(size);
            listbox.setOnChange(onChange);
            listbox.setMultipleSelection(isMultiple);
            this.setBaseComponentBean(listbox);
        }

        try {
            JspWriter out = pageContext.getOut();
            out.print(listbox.toStartString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {

        Iterator it = list.iterator();
        while (it.hasNext()) {
            ListBoxItemBean itembean = (ListBoxItemBean) it.next();
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
