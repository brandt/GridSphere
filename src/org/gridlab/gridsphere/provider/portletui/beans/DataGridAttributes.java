package org.gridlab.gridsphere.provider.portletui.beans;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;


/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

public class DataGridAttributes {

    public class Attribute {
        private boolean readonly = false;
        private boolean disabled = false;
        private boolean selected = false;

        public boolean isReadonly() {
            return readonly;
        }

        public void setReadonly(boolean readonly) {
            this.readonly = readonly;
        }

        public boolean isDisabled() {
            return disabled;
        }

        public void setDisabled(boolean disabled) {
            this.disabled = disabled;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }


    private Map items = new HashMap();

    public void add(String name, boolean readonly, boolean disabled, boolean selected) {
        Attribute attribute = new Attribute();
        attribute.setDisabled(disabled);
        attribute.setReadonly(readonly);
        attribute.setSelected(selected);
        items.put(name, attribute);
    }

    public void add(String name, boolean selected) {
        Attribute attribute = new Attribute();
        attribute.setSelected(selected);
        items.put(name, attribute);
    }

    public boolean isSelected(String name) {
        boolean result = false;

        if (items.containsKey(name)) {
            Attribute attribute = (Attribute)items.get(name);
            result = attribute.isSelected();
        }

        return result;
    }
}
