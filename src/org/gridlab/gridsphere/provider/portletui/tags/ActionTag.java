package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.portlet.DefaultPortletAction;
import org.gridlab.gridsphere.provider.portletui.beans.ActionParamBean;

import java.util.List;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public interface ActionTag extends ComponentTag {
    void setAnchor(String anchor);

    String getAnchor();

    void setLabel(String label);

    String getLabel();

    void setSecure(boolean isSecure);

    boolean getSecure();

    void setPortletMode(String portletMode);

    String getPortletMode();

    void setWindowState(String windowState);

    String getWindowState();

    void setAction(String action);

    String getAction();

    void setPortletAction(DefaultPortletAction portletAction);

    DefaultPortletAction getPortletAction();

    void addParamBean(ActionParamBean paramBean);

    void removeParamBean(ActionParamBean paramBean);

    List getParamBeans();
}
