/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.model;

public class CheckBoxItem extends ListSelectItem {

    private boolean enabled = true;

    public CheckBoxItem(String label, String value, boolean selected) {
        setLabel(label);
        setValue(value);
        setSelected(selected);
    }

    public CheckBoxItem(String label, String value) {
        setLabel(label);
        setValue(value);
        setSelected(false);
    }

    /**
     * Enables the checkbox
     */
    public void enable() {
        this.enabled = true;
    }

    /**
     * Disables the checkbox
     */
    public void disable() {
        this.enabled = false;
    }

    /**
     * Returns if the checkbox is enables/disabled
     * @return if the checkbox is enabled
     */
    public boolean isDisabled() {
        return (enabled==false);
    }

    public boolean isEnabled() {
        return (enabled==true);
    }



}
