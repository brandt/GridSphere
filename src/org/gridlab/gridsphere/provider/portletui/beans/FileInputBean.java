/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

public class FileInputBean extends InputBean implements TagBean {

    public FileInputBean() {
        this.inputtype = "file";
    }

    public FileInputBean(String name, String value, boolean disabled, boolean readonly, int size, int maxlength) {
        //  super(name, value, disabled, readonly);
        super();
        this.inputtype = "file";
        this.name = name;
        this.value = value;
        this.disabled = disabled;
        this.readonly = readonly;
        this.size = size;
        this.maxlength = maxlength;
    }

}
