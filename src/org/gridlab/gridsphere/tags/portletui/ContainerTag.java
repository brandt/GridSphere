/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.provider.portletui.beans.TagBean;

import javax.servlet.jsp.JspException;
import java.util.List;
import java.util.Vector;
import java.util.ArrayList;

public abstract class ContainerTag extends BaseBeanTag {

    protected List list = null;

    public void addTagBean(TagBean tagBean) {
        list.add(tagBean);
    }

    public void removeTagBean(TagBean tagBean) {
        list.remove(tagBean);
    }

    public List getTagBeans() {
        return list;
    }

    public int doStartTag() throws JspException {
        System.err.println("in ContainerTag:doStartTag");
        list = new ArrayList();
        return EVAL_PAGE;
    }

    public abstract int doEndTag() throws JspException;

}
