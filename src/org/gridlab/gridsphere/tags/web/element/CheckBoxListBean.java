/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

import java.util.Iterator;

public class CheckBoxListBean extends DropDownListBean {

    public CheckBoxListBean(String name) {
        super(name);
    }

    public void add(String value, String label) {
        add(value, label, false);
    }

    public void add(String value, String label, boolean selected) {
        CheckBoxBean cbb = new CheckBoxBean(this.name, value, selected, false);
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
