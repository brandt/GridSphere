/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import java.util.List;
import java.util.ArrayList;

public class TagBeanContainer extends BaseBeanImpl {

    public List container = new ArrayList();

    public TagBeanContainer() {

    }

    public void addTagBean(TagBean bean) {
        container.add(bean);
    }

    public List getTagBeans() {
        return container;
    }

}
