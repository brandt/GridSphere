/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletContext;
import org.gridlab.gridsphere.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import java.io.IOException;
import java.io.Serializable;

/**
 * <code>PortletText</code> is used to display the contents of an included
 * text file located in the ui application.
 */
public class PortletText extends BasePortletComponent implements Serializable, Cloneable {

    private String text;

    /**
     *  Constructs an instance of PortletText
     */
    public PortletText() {
    }

    /**
     * Sets the text file to be included specified as a path relative to the
     * webapp root directory e.g. /html/newtext.txt
     *
     * @param textFilePath the relative path to load a text file
     */
    public void setInclude(String textFilePath) {
        this.text = textFilePath;
    }

    /**
     * Returns the text file path of the included file
     *
     * @return the relative path of the text file
     */
    public String getInclude() {
        return text;
    }

    /**
     * Renders the portlet text component
     *
     * @param event a gridsphere event
     * @throws PortletLayoutException if a layout error occurs during rendering
     * @throws IOException if an I/O error occurs during rendering
     */
    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        super.doRender(event);
        PortletContext ctx = event.getPortletContext();
        PortletRequest req = event.getPortletRequest();
        PortletResponse res = event.getPortletResponse();
        try {
            ctx.include(text, req, res);
        } catch (PortletException e) {
            throw new PortletLayoutException("Unable to include text: " + text, e);
        }
    }

    public Object clone() throws CloneNotSupportedException {
        PortletText t = (PortletText)super.clone();
        t.text = this.text;
        return t;
    }

}
