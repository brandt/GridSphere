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
        //System.err.println("in the fucking container: adding " + tagBean.toString());
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
