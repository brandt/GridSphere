/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web;

import org.gridlab.gridsphere.tags.web.model.CheckBoxItem;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;

public class HTMLElementRenderer {

    private static HTMLElementRenderer instance;

    public static HTMLElementRenderer getInstance() {
        if (null == instance) {
            instance = new HTMLElementRenderer();
        }
        return instance;
    }

    /**
     * Renders a checkbox item in html
     * @param out output here
     * @param item checkboxitem to render
     * @param name name of the checkbox
     */
    public void renderCheckBox(JspWriter out, CheckBoxItem item, String name) throws JspException, IOException {
        out.print("<input type=\"checkbox\" name=\"" + name + "\" value=\"" + item.getValue() + "\"");
        if (item.isSelected()) {
            out.print(" checked ");
        }
        if (item.isDisabled()) {
            out.print(" disabled ");
        }
        out.print("/>");
    }

    /**
     * Renders a checkbox with a label in html
     * @param out output here
     * @param item checkboxitem to render
     * @param name name of the checkbox
     */
    public void renderCheckBoxWithLabel(JspWriter out, CheckBoxItem item, String name) throws JspException, IOException {

        renderCheckBox(out, item, name);

        out.println("<span class=\"");
        if (item.isDisabled()) {
            out.print("form-checkbox-label-disabled");
        } else {
            out.print("form-checkbox-label");
        }
        out.print("\">");
        out.println(item.getLabel() + "</span>");
    }


}
