/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.ui.beans;

import org.gridlab.gridsphere.provider.ui.model.DefaultList;
import org.gridlab.gridsphere.portlet.PortletRequest;

import java.util.Iterator;

public class TableBean extends NameBean implements Updateable {

    protected String tablestring = "table";

    protected DefaultList list = new DefaultList();

    public void add(TagBean bean) {
        list.addBean(bean);
    }

    public Iterator iterator() {
        return list.iterator();
    }

    public void update(String[] values) {
        System.out.println("VALUES :"+values);
    }

    protected String getCSS() {
        String css = new String();
        if (!cssStyle.equals("")) {
            css = css + " class='" + cssStyle + "' ";
        }
        css = css + " Style='";
        if (!color.equals("")) {
            css = css + "color:" + color + ";";
        }
        if (!font.equals("")) {
            css = css + "font:" + font + ";";
        }
        if (!backgroundcolor.equals("")) {
            css = css + "background:" + backgroundcolor + ";";
        }

        css = css + "'";
        return css;
    }

    public String toString() {
        String result = "<"+tablestring+" "+getCSS()+">";
        Iterator it = list.iterator();
        while (it.hasNext()) {
            result = result + it.next().toString();
        }
        result = result + "</"+tablestring+">";
        return result;
    }

}
