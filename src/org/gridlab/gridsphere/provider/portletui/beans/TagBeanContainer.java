/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import java.util.List;
import java.util.ArrayList;

public class TagBeanContainer extends BaseBean {

    public List container = new ArrayList();

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

}
