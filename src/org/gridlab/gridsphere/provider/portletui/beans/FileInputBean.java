/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

public class FileInputBean extends InputBean implements TagBean {
    public static String NAME = "fi";

    public FileInputBean() {
        super(NAME);
        this.inputtype = "file";
    }

}
