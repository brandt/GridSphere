package org.gridlab.gridsphere.provider.portletui.tags;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public interface ComponentTag extends BeanTag {

    void setName(String name);

    String getName();

    void setValue(String value);

    String getValue();

    boolean isDisabled();

    void setDisabled(boolean flag);

    void setReadonly(boolean flag);

    boolean isReadonly();

    String getCssStyle();

    void setCssStyle(String style);

    String getCssClass();

    void setCssClass(String cssClass);
}
