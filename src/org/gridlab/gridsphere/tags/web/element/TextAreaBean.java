/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

public class TextAreaBean extends ReadOnlyBaseBean implements TAElementBean {

    private int cols;
    private int rows;

    public TextAreaBean(String name, String value, boolean disabled, boolean readonly, int rows, int cols) {
        super(name, value, disabled, readonly);
        this.cols = cols;
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String toString() {
        return "<textarea name='"+name+"' cols='"+cols+"' rows='"+rows+"' "+checkDisabled()+" "+checkReadonly()+">"+
            value+"</textarea>";

    }

}
