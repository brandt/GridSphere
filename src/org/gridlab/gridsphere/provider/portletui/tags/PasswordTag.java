package org.gridlab.gridsphere.provider.portletui.tags;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public interface PasswordTag extends ComponentTag {
    int getSize();

    void setSize(int size);

    int getMaxlength();

    void setMaxlength(int maxlength);
}
