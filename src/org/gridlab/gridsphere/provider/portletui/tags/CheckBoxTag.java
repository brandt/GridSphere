package org.gridlab.gridsphere.provider.portletui.tags;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public interface CheckBoxTag extends ComponentTag {
    void setSelected(boolean flag);

    boolean isSelected();
}
