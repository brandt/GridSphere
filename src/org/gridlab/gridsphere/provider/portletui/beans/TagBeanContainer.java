/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

public class TagBeanContainer extends BaseBean {

    public List container  = new Vector();

    public TagBeanContainer() {
        super();

    }

    public void addTagBean(TagBean bean) {
        container.add(bean);
    }

    public void removeTagBean(TagBean bean) {
        container.remove(bean);
    }

    public List getTagBeans() {
        return container;
    }

    public String toString() {
        Iterator it = container.iterator();
        StringBuffer sb = new StringBuffer();
        while (it.hasNext()) {
            TagBean tagBean = (TagBean)it.next();
            if (tagBean.toString() != null) sb.append(tagBean.toString());
            //System.err.println("its the tagbean out: " + tagBean.toString());
        }
        return sb.toString();
    }

}
