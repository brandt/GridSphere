package org.gridsphere.provider.portletui.beans;

import java.util.HashMap;
import java.util.Map;


/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: DataGridAttributes.java 4496 2006-02-08 20:27:04Z wehrens $
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

    public void setSelect(String name, boolean flag) {
        Attribute attribute = this.getAttribute(name);
        attribute.setSelected(flag);
        items.put(name, attribute);
    }

    public void setReadonly(String name, boolean flag) {
        Attribute attribute = this.getAttribute(name);
        attribute.setReadonly(flag);
        items.put(name, attribute);
    }

    public void setDisabled(String name, boolean flag) {
        Attribute attribute = this.getAttribute(name);
        attribute.setDisabled(flag);
        items.put(name, attribute);
    }


    public boolean isDisabled(String name) {
        Attribute attribute = this.getAttribute(name);
        return attribute.isDisabled();
    }

    public boolean isReadOnly(String name) {
        Attribute attribute = this.getAttribute(name);
        return attribute.isReadonly();
    }

    public boolean isSelected(String name) {
        Attribute attribute = this.getAttribute(name);
        return attribute.isSelected();
    }

    private Attribute getAttribute(String name) {
        if (items.containsKey(name)) {
            return (Attribute) items.get(name);
        } else {
            return new Attribute();
        }
    }
}

