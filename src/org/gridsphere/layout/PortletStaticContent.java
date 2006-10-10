/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: PortletStaticContent.java 4986 2006-08-04 09:54:38Z novotny $
 */
package org.gridsphere.layout;

import org.gridsphere.portlet.jsrimpl.StoredPortletResponseImpl;
import org.gridsphere.portletcontainer.GridSphereEvent;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * <code>PortletContent</code> is used to display the contents of an included
 * text file located in the ui application.
 */
public class PortletStaticContent extends BasePortletComponent implements Serializable, Cloneable {

    private String textFile = null;
    private String encoding = null;
    protected StringBuffer content = null;

    /**
     * Constructs an instance of PortletContent
     */
    public PortletStaticContent() {
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

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getEncoding() {
        return encoding;
    }

    /**
     * Renders the portlet text component
     *
     * @param event a gridsphere event
     */
    public void doRender(GridSphereEvent event) {
        super.doRender(event);

        PortletRequest req = event.getRenderRequest();
        PortletResponse res = event.getRenderResponse();
        if (textFile != null) {

            // Try the localized version first
            StringWriter writer = new StringWriter();
            RenderResponse sres = new StoredPortletResponseImpl((HttpServletRequest)req, (HttpServletResponse)res, writer);
            InputStream resourceStream = req.getPortletSession(true).getPortletContext().getResourceAsStream(textFile);
            if (resourceStream != null) {
                try {
                    Reader reader;
                    if (encoding != null) {
                        reader = new BufferedReader(new InputStreamReader(resourceStream, encoding));
                    } else {
                        reader = new BufferedReader(new InputStreamReader(resourceStream));
                    }
                    writeData(reader, sres.getWriter());
                    content = writer.getBuffer();
                } catch (IOException e) {
                    log.error("Unable to render static content from file: " + textFile, e);
                    content.append("Unable to render static content from file: " + textFile);
                }
            }

        }
    }

    public StringBuffer getBufferedOutput() {
        return content;
    }

    private void writeData(Reader reader, Writer writer) {
        try {
            int ch;
            while ((ch = reader.read()) != -1) {
                writer.write(ch);
            }
        } catch (IOException ioex) {
            log.error("Unable to write data:", ioex);
        }
    }

    public Object clone() throws CloneNotSupportedException {
        PortletStaticContent t = (PortletStaticContent) super.clone();
        t.textFile = this.textFile;
        return t;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.toString());
        sb.append("\ntext file=").append(textFile);
        return sb.toString();
    }
}
