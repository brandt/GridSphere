/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletContext;
import org.gridlab.gridsphere.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.impl.StoredPortletResponseImpl;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;

/**
 * <code>PortletContent</code> is used to display the contents of an included
 * text file located in the ui application.
 */
public class PortletContent extends BasePortletComponent implements Serializable, Cloneable {

    private String textFile = null;
    private String context = null;
    //private StringBuffer content = null;

    /**
     * Constructs an instance of PortletContent
     */
    public PortletContent() {
    }

    /**
     * Sets the text file to be included specified as a path relative to the
     * webapp root directory e.g. /html/newtext.txt
     *
     * @param textFile the relative path to load a text file
     */
    public void setInclude(String textFile) {
        this.textFile = textFile;
    }

    /**
     * Returns the text file path of the included file
     *
     * @return the relative path of the text file
     */
    public String getInclude() {
        return textFile;
    }

    /**
     * Returns the web application context if not specified assumes included file is located in gridsphere context
     *
     * @return the web application context
     */
    public String getContext() {
        return context;
    }

    /**
     * Sets the web application context if not specified assumes included file is located in gridsphere context
     *
     * @param context the web application context
     */
    public void setContext(String context) {
        this.context = context;
    }

    /**
     * Renders the portlet text component
     *
     * @param event a gridsphere event
     * @throws PortletLayoutException if a layout error occurs during rendering
     * @throws IOException            if an I/O error occurs during rendering
     */
    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        super.doRender(event);
        ServletContext ctx = event.getPortletContext();
        PortletRequest req = event.getPortletRequest();
        PortletResponse res = event.getPortletResponse();
        StringWriter writer = new StringWriter();
        StoredPortletResponseImpl sres = new StoredPortletResponseImpl(res, writer);
        StringBuffer content = new StringBuffer();

        if (context != null) {
            if (!context.startsWith("/")) {
                context = "/" + context;
            }
            ctx = ctx.getContext(context);
        }
        if (textFile != null) {
            RequestDispatcher rd = null;
            rd = ctx.getRequestDispatcher(textFile);
            try {
                if (rd != null) {
                    rd.include(req, sres);
                } else {
                    throw new PortletException("Unable to include resource: RequestDispatcher is null");
                }
                content = writer.getBuffer();
                req.setAttribute(SportletProperties.RENDER_OUTPUT + componentIDStr, content);
            } catch (Exception e) {
                throw new PortletLayoutException("Unable to include textfile: " + textFile, e);
            }
        }
    }

    public Object clone() throws CloneNotSupportedException {
        PortletContent t = (PortletContent) super.clone();
        t.textFile = this.textFile;
        return t;
    }

}
