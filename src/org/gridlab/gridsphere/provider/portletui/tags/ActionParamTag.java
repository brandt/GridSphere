package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.provider.portletui.beans.ActionParamBean;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public interface ActionParamTag {
    void setName(String name);

    String getName();

    void setValue(String value);

    String getValue();

    void setParamBean(ActionParamBean paramBean);

    ActionParamBean getParamBean();
}
