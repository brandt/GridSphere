package org.gridlab.gridsphere.provider.portletui.tags;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public interface FrameTag extends TableTag {
    void setKey(String key);

    String getKey();

    void setStyle(String style);

    String getStyle();
}
