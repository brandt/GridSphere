package org.gridlab.gridsphere.provider.portletui.tags;

import org.gridlab.gridsphere.provider.portletui.beans.TabBean;

/**
 * Created by IntelliJ IDEA.
 * User: novotny
 * Date: Mar 20, 2004
 * Time: 6:08:33 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TabbedPaneTag {
    void setPaneId(String paneId);

    String getPaneId();

    void setWidth(String width);

    String getWidth();

    void setCurrentTab(int currentTab);

    int getCurrentTab();

    void addTab(TabBean tabBean);

    void removeTab(TabBean tabBean);
}
