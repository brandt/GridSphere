package org.gridlab.gridsphere.provider.portletui.tags.old;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import javax.swing.table.TableModel;
import java.io.IOException;
import java.util.Collection;

public class DataGridTag extends TagSupport {

    private Collection col;
    private TableModel tableModel;

    public void setTableModel(TableModel tableModel) {
        this.tableModel = tableModel;
    }

    public TableModel getTableModel() {
        return tableModel;
    }

    private void writeTable(JspWriter out) throws IOException {
        int rows = tableModel.getRowCount();
        int cols = tableModel.getColumnCount();
        out.print("<table>");
        for (int i = 0; i < rows; i++) {
            out.print("<tr>");
            for (int j = 0; j < cols; j++) {
                out.print("<td>");
                out.print(tableModel.getValueAt(i, j));
                out.print("</td>");
            }
            out.print("</tr>");
        }
        out.print("</table>");
    }

    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.print("<ul>");
            writeTable(out);
            out.print("</ul>");
        } catch (Exception e) {
            throw new JspTagException(e.getMessage());
        }
        return EVAL_BODY_INCLUDE;
    }

}
