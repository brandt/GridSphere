/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.portletui;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Map;
import java.util.HashMap;

public class ParamTag extends TagSupport {

    private String name = "noname";
    private String value = "novalue";

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public int doStartTag() throws JspException {

        Map paramMap = (Map)pageContext.getAttribute("_params");
        if (paramMap == null) paramMap = new HashMap();
        paramMap.put(name, value);
        pageContext.setAttribute("_params", paramMap);

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspTagException {
        return EVAL_PAGE;
    }

}
