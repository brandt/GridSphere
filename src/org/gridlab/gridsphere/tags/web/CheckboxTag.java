ckage org.gridlab.gridsphere.tags.web;

import org.gridlab.gridsphere.tags.web.element.CheckBox;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

public class CheckboxTag extends InputTag {

    public int doStartTag() throws JspException {
        this.htmlelement = new CheckBox(name, value, isChecked, isDisabled);
        return super.doStartTag();
    }

}
