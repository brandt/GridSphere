package org.gridlab.gridsphere.provider.portletui.tags;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public interface ListBoxItemTag extends ComponentTag {
    String getKey();

    void setKey(String key);

    void setSelected(boolean selected);

    boolean getSelected();
}
