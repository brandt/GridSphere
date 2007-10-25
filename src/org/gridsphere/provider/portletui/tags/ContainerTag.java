/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.provider.portletui.tags;

import org.gridsphere.provider.portletui.beans.BaseComponentBean;
import org.gridsphere.provider.portletui.beans.BeanContainer;
import org.gridsphere.provider.portletui.beans.TagBean;

import javax.servlet.jsp.JspException;
import java.util.ArrayList;
import java.util.List;

/**
 * The abstract <code>ContainerTag</code> provides a bean container used by other tags that are themselves containers
 * for nested tags.
 */
public abstract class ContainerTag extends BaseComponentTag {

    // make sure it is initalized, otherwise adding a tag throws NPE
    protected List<TagBean> list = new ArrayList<TagBean>();

    /**
     * Adds a tag bean to the container
     *
     * @param tagBean a tag bean
     */
    public void addTagBean(TagBean tagBean) {
        list.add(tagBean);
    }

    /**
     * Removes a tag bean from the container
     *
     * @param tagBean a tag bean
     */
    public void removeTagBean(TagBean tagBean) {
        list.remove(tagBean);
    }

    /**
     * Returns a list of tag beans
     *
     * @return a list of tag beans
     */
    public List<TagBean> getTagBeans() {
        return list;
    }

    public abstract int doStartTag() throws JspException;

    public abstract int doEndTag() throws JspException;

    public void doEndTag(BeanContainer beanContainer) throws JspException {
        for (TagBean tagBean : list) {
            BaseComponentBean bc = (BaseComponentBean)tagBean;
            beanContainer.addBean(bc);
        }
    }

}
