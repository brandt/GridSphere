package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.provider.portletui.beans.ListBean;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public interface ListTag extends ComponentTag {
    void setListBean(ListBean listBean);

    ListBean getListBean();
}
