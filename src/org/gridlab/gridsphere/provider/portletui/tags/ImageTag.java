package org.gridlab.gridsphere.provider.portletui.tags;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public interface ImageTag extends ComponentTag {
    String getSrc();

    void setSrc(String src);

    String getBorder();

    void setBorder(String border);

    String getTitle();

    void setTitle(String title);

    String getAlt();

    void setAlt(String alt);

    void setAlign(String align);

    String getAlign();

    void setWidth(String width);

    String getWidth();

    void setHeight(String height);

    String getHeight();
}
