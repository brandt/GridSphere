package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.provider.portletui.beans.TableRowBean;
import org.gridlab.gridsphere.provider.portletui.beans.TagBeanContainer;
import org.gridlab.gridsphere.provider.portletui.beans.TagBean;
import org.gridlab.gridsphere.provider.portletui.beans.TableCellBean;

import javax.servlet.jsp.JspException;
import java.util.List;
import java.util.Iterator;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public class TableCellTag extends BaseBeanTag {

    public int doStartTag() throws JspException {
        System.err.println("in TableCellTag:doStartTag");
        TagBeanContainer tc = new TagBeanContainer();
        pageContext.setAttribute("_container", tc);
        return super.doStartTag();
    }

    public int doEndTag() throws JspException {
        System.err.println("in TableCellTag:doEndTag");
        TagBeanContainer tc = (TagBeanContainer)pageContext.getAttribute("_container");
        TableRowBean trb = (TableRowBean)pageContext.getAttribute("_tablerow");
        if ((tc != null) && (trb != null)) {

            System.err.println("setting tablecells in tablerow");
            List tagbeans = tc.getTagBeans();
            Iterator it = tagbeans.iterator();
            TableCellBean cellBean = new TableCellBean();
            while (it.hasNext()) {
                TagBean tagBean = (TagBean)it.next();
                System.err.println("adding tagbean " + tagBean.toString());
                cellBean.addTagBean(tagBean);
            }
            trb.addTableCellBean(cellBean);
            pageContext.setAttribute("_tablerow", trb);

        }
        return super.doEndTag();
    }
}
