package org.gridlab.gridsphere.provider.portletui.tags;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public interface ActionMenuItemTag extends ContainerTag {
    boolean isSeperator();

    void setSeperator(boolean seperator);
}
