package org.gridlab.gridsphere.tags.web;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public class DataListTag extends TagSupport {

    private Collection col;

    public void setCollection(Collection col) {
        this.col = col;
    }

    public Collection getCollection() {
        return col;
    }

    private void writeList(JspWriter out, Collection c) throws IOException {
        Iterator it = c.iterator();
        while (it.hasNext()) {
            Object obj = it.next();
            if (obj instanceof Collection) {
                writeList(out, (Collection) obj);
            }
            out.print("<li>");
            out.print(obj.toString());
            out.print("</li>");
        }
    }

    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.print("<ul>");
            writeList(out, col);
            out.print("</ul>");
        } catch (Exception e) {
            throw new JspTagException(e.getMessage());
        }
        return EVAL_BODY_INCLUDE;
    }

}
