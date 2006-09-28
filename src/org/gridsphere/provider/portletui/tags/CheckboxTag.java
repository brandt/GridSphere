/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: CheckboxTag.java 4666 2006-03-27 17:47:56Z novotny $
 */
package org.gridsphere.provider.portletui.tags;

import org.gridsphere.provider.portletui.beans.CheckBoxBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;

/**
 * A <code>CheckBoxTag</code> provides a checkbox element
 */
public class CheckboxTag extends BaseComponentTag {

    protected CheckBoxBean checkbox = null;
    protected boolean selected = false;
    protected boolean selectSet = false;
    protected String onClick = null;

    /**
     * Sets the selected status of the bean
     *
     * @param flag status of the bean
     */
    public void setSelected(boolean flag) {
        this.selected = flag;
        this.selectSet = true;
    }

    /**
     * Returns the selected status of the bean
     *
     * @return selected status
     */
    public boolean isSelected() {
        return selected;
    }

    public String getOnClick() {
        return onClick;
    }

    public void setOnClick(String onClick) {
        this.onClick = onClick;
    }

    public int doStartTag() throws JspException {
        if (!beanId.equals("")) {
            checkbox = (CheckBoxBean) getTagBean();
            if (checkbox == null) {
                checkbox = new CheckBoxBean();
                checkbox.setSelected(selected);
                this.setBaseComponentBean(checkbox);
            } else {
                this.setBaseComponentBean(checkbox);
            }
        } else {
            checkbox = new CheckBoxBean();
            this.setBaseComponentBean(checkbox);
        }
        if (selectSet) checkbox.setSelected(selected);
        if (onClick != null) checkbox.setOnClick(onClick);
       
        Tag parent = getParent();
        if (parent instanceof DataGridColumnTag) {
            DataGridColumnTag dataGridColumnTag = (DataGridColumnTag) parent;
            dataGridColumnTag.addTagBean(this.checkbox);
        } else {

            try {
                JspWriter out = pageContext.getOut();
                out.print(checkbox.toStartString());
            } catch (Exception e) {
                throw new JspException(e.getMessage());
            }
        }
        return SKIP_BODY;
    }

}
