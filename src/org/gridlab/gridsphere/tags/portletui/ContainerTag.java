/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.provider.portletui.beans.TagBean;

import javax.servlet.jsp.JspException;
import java.util.List;
import java.util.ArrayList;

public abstract class ContainerTag extends BaseBeanTag {

    protected List list = new ArrayList();

    public String getBeanId() {
        return beanId;
    }

    public void addTagBean(TagBean tagBean) {
        list.add(tagBean);
    }

    public void removeTagBean(TagBean tagBean) {
        list.remove(tagBean);
    }

    public List getTagBeans() {
        return list;
    }

    public abstract int doStartTag() throws JspException;

    public abstract int doEndTag() throws JspException;

}
