/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

public class RadioButtonListBean extends CheckBoxListBean {

    public RadioButtonListBean(String name) {
        super(name);
    }


    public void add(String value, String label, boolean selected) {
        RadioButtonBean cbb = new RadioButtonBean(this.name, value, selected, false);
        cbb.setLabel(label);
        list.addElement(cbb);
    }

}
