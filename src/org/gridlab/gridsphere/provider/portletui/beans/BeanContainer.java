/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

public class BeanContainer extends BaseComponentBean {

    public List container  = new Vector();

    public BeanContainer() {}

    public BeanContainer(String name) {
        super(name);
    }

    public void addBean(BaseComponentBean bean) {
        container.add(bean);
    }

    public void removeBean(BaseComponentBean bean) {
        container.remove(bean);
    }

    public void clear() {
        container.clear();
    }

    public List getBeans() {
        return container;
    }

    public String toString() {
        Iterator it = container.iterator();
        StringBuffer sb = new StringBuffer();
        while (it.hasNext()) {
            BaseComponentBean tagBean = (BaseComponentBean)it.next();
            if (tagBean.toString() != null) sb.append(tagBean.toString());
            //System.err.println("its the tagbean out: " + tagBean.toString());
        }
        return sb.toString();
    }

}
