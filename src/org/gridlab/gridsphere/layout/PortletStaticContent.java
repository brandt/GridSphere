/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.Locale;

import org.gridlab.gridsphere.portlet.Client;
import org.gridlab.gridsphere.portlet.PortletContext;
import org.gridlab.gridsphere.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

/**
 * <code>PortletContent</code> is used to display the contents of an included
 * text file located in the ui application.
 */
public class PortletStaticContent extends BasePortletComponent implements Serializable, Cloneable {

    private String textFile = null;
    private String encoding = null; 

    /**
     *  Constructs an instance of PortletContent
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
     * @throws PortletLayoutException if a layout error occurs during rendering
     * @throws IOException if an I/O error occurs during rendering
     */
    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        super.doRender(event);
        PortletContext ctx = event.getPortletContext();
        PortletRequest req = event.getPortletRequest();
        PortletResponse res = event.getPortletResponse();
        if (textFile != null) {

            // Try the localized version first
            try {
                Client client = req.getClient();
                
                
                Locale locale = req.getLocale();
                InputStream resourceStream = ctx.getResourceAsStream(textFile, client, locale);
                if (resourceStream != null) {
                    Reader reader;
                    if (encoding != null) {
                            reader = new BufferedReader(new InputStreamReader(resourceStream, encoding));                                                
                    } else {
                            reader = new BufferedReader(new InputStreamReader(resourceStream));
                    }
                    writeData(reader, res.getWriter());                                            
                }
            } catch (PortletException e) {
                throw new PortletLayoutException("Unable to include text: " + textFile, e);
            }
        }
    }

    private void writeData(Reader reader, Writer writer) throws PortletException {
        try {
                int ch;
                while ((ch = reader.read()) != -1) {
                        writer.write(ch);
                }
        } catch (IOException ioex) {
                throw new PortletException("Unable to write data:",ioex);
        }
    }

    public Object clone() throws CloneNotSupportedException {
        PortletStaticContent t = (PortletStaticContent)super.clone();
        t.textFile = this.textFile;
        return t;
    }

}
