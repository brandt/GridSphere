package org.gridlab.gridsphere.provider.portletui.tags;

/**
 * Created by IntelliJ IDEA.
 * User: novotny
 * Date: Mar 20, 2004
 * Time: 6:07:53 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TabTag {
    void setActive(boolean isActive);

    boolean getActive();

    String getTitle();

    void setTitle(String title);

    void setPage(String page);

    String getPage();
}
