package org.gridlab.gridsphere.provider.portletui.tags;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public interface TextTag extends ComponentTag {
    void setStyle(String style);

    String getStyle();

    void setFormat(String format);

    String getFormat();

    String getKey();

    void setKey(String key);
}
