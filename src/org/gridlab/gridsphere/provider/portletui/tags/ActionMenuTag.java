package org.gridlab.gridsphere.provider.portletui.tags;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public interface ActionMenuTag extends ContainerTag {
    String getMenuType();

    void setMenuType(String menuType);

    boolean isCollapsed();

    void setCollapsed(boolean collapsed);

    boolean isCollapsible();

    void setCollapsible(boolean collapsible);

    String getTitle();

    void setTitle(String title);

    String getLayout();

    void setLayout(String layout);

    String getKey();

    void setKey(String key);
}
