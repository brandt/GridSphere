/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.ui.beans;

import java.util.Iterator;

public class CheckBoxListBean extends DropDownListBean {

    public CheckBoxListBean(String name) {
        super(name);
    }

    /**
     * Adds a checkbox to the list.
     * @param value vaule of the checkbox
     * @param label label of the checkbox
     */
    public void add(String value, String label) {
        add(value, label, false);
    }

    /**
     * Adds a checkbox to the list.
     * @param value of the checkbox
     * @param label label of the checkbox
     * @param selected selected status of the checkbox
     */
    public void add(String value, String label, boolean selected) {
        CheckBoxBean cbb = new CheckBoxBean(this.name, value, selected, false);
        cbb.setLabel(label);
        list.addElement(cbb);
    }

    /**
     * Adds a checkbox to the list.
     * @param value of the checkbox
     * @param label label of the checkbox
     * @param selected selected status of the checkbox
     */
    public void add(String value, String label, boolean selected, boolean disabled) {
        CheckBoxBean cbb = new CheckBoxBean(this.name, value, selected, disabled);
        cbb.setLabel(label);
        list.addElement(cbb);
    }

    public String toString() {
        String result = new String();
        if (this.label!=null) {
            result = result + "<fieldset><legend>"+this.label.getValue()+"</legend>";
        }

        Iterator it = list.iterator();
        while (it.hasNext()) {
            SelectElementBean seb =  ((SelectElementBean)it.next());
            seb.setCID(this.cid);
            result = result + seb.toString();
            if (seb.getLabel()!=null) {
                result = result + seb.getLabel().getValue();
            }
            result = result +"<br/>";
        }

        if (this.label!=null) {
            result = result +"</fieldset>";
        }

        return result;

    }

}
