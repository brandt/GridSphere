/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

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
public class PortletContent extends BasePortletComponent implements Serializable, Cloneable {

    private String textFile = null;

    /**
     *  Constructs an instance of PortletContent
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
                int dotIndex=textFile.lastIndexOf(".");
                String textFilePrefix = textFile.substring(0,dotIndex);
                String textFileSuffix = textFile.substring(dotIndex);
                
                String locale = (String) req.getPortletSession(true).getAttribute(User.LOCALE);
                
                if (locale != null) {       
                        // This locFile hack is needed because ctx.include does not throw an exception if localizedTextFile 
                        // doesn't exists.
                        File locFile = null;
                        
                        String localizedTextFile = textFilePrefix + "-" + locale + textFileSuffix;
                        
                        locFile = new File(ctx.getRealPath(localizedTextFile));
                        
                        if (!locFile.isFile()) {
                                // try without the _XY part of the xy_XY type locales.
                                int index = locale.indexOf("_");
                                if (index >= 0) {
                                        locale = locale.substring(0,index);
                                }
                               
                                localizedTextFile = textFilePrefix + "-" + locale + textFileSuffix;
                                
                                locFile = new File(ctx.getRealPath(localizedTextFile));
                                if (locFile.isFile()) {
                                        ctx.include(localizedTextFile, req, res);    
                                        return ;                    
                                }                                
                        } else {
                                ctx.include(localizedTextFile, req, res);    
                                return ;                    
                        }
                }                                     
            } catch (PortletException e) {
                    // Ignore, because localization is optional.
            }

            try {                    
                ctx.include(textFile, req, res);
            } catch (PortletException e) {
                throw new PortletLayoutException("Unable to include text: " + textFile, e);
            }
        }
    }

    public Object clone() throws CloneNotSupportedException {
        PortletContent t = (PortletContent)super.clone();
        t.textFile = this.textFile;
        return t;
    }

}
