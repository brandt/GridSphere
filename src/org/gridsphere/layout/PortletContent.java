/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletContent.java 4993 2006-08-04 10:10:43Z novotny $
 */
package org.gridsphere.layout;

import org.gridsphere.portlet.PortletException;
import org.gridsphere.portlet.PortletRequest;
import org.gridsphere.portlet.PortletResponse;
import org.gridsphere.portlet.PortletURI;
import org.gridsphere.portlet.impl.StoredPortletResponseImpl;
import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.portletcontainer.GridSphereEvent;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;

/**
 * <code>PortletContent</code> is used to display the contents of an included
 * text file located in the ui application.
 */
public class PortletContent extends BasePortletComponent implements Serializable, Cloneable {

    private String textFile = null;
    private String context = null;

    /**
     * Constructs an instance of PortletContent
     */
    public PortletContent() {
    }

    /**
     * Initializes the portlet component. Since the components are isolated
     * after Castor unmarshalls from XML, the ordering is determined by a
     * passed in List containing the previous portlet components in the tree.
     *
     * @param list a list of component identifiers
     * @return a list of updated component identifiers
     * @see ComponentIdentifier
     */
    public List init(PortletRequest req, List list) {
        list = super.init(req, list);
        ComponentIdentifier compId = new ComponentIdentifier();
        compId.setPortletComponent(this);
        compId.setComponentID(list.size());
        compId.setComponentLabel(label);
        compId.setClassName(this.getClass().getName());
        list.add(compId);
        return list;
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

    public String getFileName() {
        return textFile.substring(textFile.lastIndexOf("/")+1);
    }

    /**
     * Renders the portlet text component
     *
     * @param event a gridsphere event
     */
    public void doRender(GridSphereEvent event) {
        super.doRender(event);
        ServletContext ctx = event.getPortletContext();
        PortletRequest req = event.getPortletRequest();
        PortletResponse res = event.getPortletResponse();
        StringWriter writer = new StringWriter();
        StoredPortletResponseImpl sres = new StoredPortletResponseImpl(res, writer);
        StringBuffer content = new StringBuffer();
        String textFileName = textFile.substring(textFile.lastIndexOf("/")+1);
        if (req.getAttribute(SportletProperties.LAYOUT_EDIT_MODE) != null) {
            String extraQuery = (String)req.getAttribute(SportletProperties.EXTRA_QUERY_INFO);
            if (extraQuery != null) {
                PortletURI portletURI = res.createURI();
                String link = portletURI.toString() + extraQuery;
                content.append("<br><fieldset><a href=\"" + link + "\">" + textFileName + "</a></fieldset>");
                setBufferedOutput(req, content);
            }
            return;
        }
        if (context != null) {
            if (!context.startsWith("/")) {
                context = "/" + context;
            }
            ctx = ctx.getContext(context);
        }
        if (textFile != null) {
            RequestDispatcher rd = null;
            try {
                if (!textFile.startsWith("http://")) {
                    rd = ctx.getRequestDispatcher(textFile);
                    if (rd != null) {
                        rd.include(req, sres);
                    } else {
                        throw new PortletException("Unable to include resource: RequestDispatcher is null");
                    }
                } else {
                    writer.write("<iframe border=\"0\" width=\"100%\" height=\"100%\" src=\"" + textFile + "\"></iframe>");
                }
                content = writer.getBuffer();
            } catch (Exception e) {
                log.error("Unable to include textfile: " + textFile, e);
                content.append("Unable to include textfile: " + textFile);
            }
            setBufferedOutput(req, content);
        }


    }

    public Object clone() throws CloneNotSupportedException {
        PortletContent t = (PortletContent) super.clone();
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
