/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: PortletContent.java 4993 2006-08-04 10:10:43Z novotny $
 */
package org.gridsphere.layout;

import org.gridsphere.portlet.impl.PortletContextImpl;
import org.gridsphere.portlet.impl.PortletURLImpl;
import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.portlet.impl.StoredPortletResponseImpl;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.portletcontainer.GridSphereEvent;
import org.gridsphere.services.core.jcr.JCRService;

import javax.jcr.Session;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private boolean border = true;

    public static final String DELETE_CONTENT = "deleteContent";

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
    public List<ComponentIdentifier> init(PortletRequest req, List<ComponentIdentifier> list) {
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
     * Returns if the included content is in the border and should have no gridsphere-content div around it
     *
     * @return if content is in the border
     */
    public boolean isBorder() {
        return border;
    }

    /**
     * Set if the included content is in the border and should have no gridsphere-content div around it
     *
     * @param border if border or not
     */
    public void setBorder(boolean border) {
        this.border = border;
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
        return textFile.substring(textFile.lastIndexOf("/") + 1);
    }

    /**
     * Renders the portlet text component
     *
     * @param event a gridsphere event
     */
    public void doRender(GridSphereEvent event) {
        super.doRender(event);
        PortletContextImpl ctext = (PortletContextImpl) event.getPortletContext();
        ServletContext ctx = ctext.getServletContext();
        PortletRequest req = event.getRenderRequest();
        RenderResponse res = event.getRenderResponse();
        StringWriter writer = new StringWriter();
        StoredPortletResponseImpl sres = new StoredPortletResponseImpl((HttpServletRequest) req, (HttpServletResponse) res, writer);
        StringBuffer content = new StringBuffer();
        String textFileName = textFile.substring(textFile.lastIndexOf("/") + 1);
        if (req.getAttribute(SportletProperties.LAYOUT_EDIT_MODE) != null) {
            PortletURLImpl portletURI = (PortletURLImpl) res.createRenderURL();
            String editLink = portletURI.toString();
            portletURI.setAction(DELETE_CONTENT);
            String deleteLink = portletURI.toString();                                                                                               /*  getLocalizedText(req, "EDIT") */
            content.append("<br><fieldset>");
            content.append(textFileName);
            content.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"");
            content.append(editLink);
            content.append("\">");
            content.append("<img src=\"");
            content.append(req.getContextPath());
            content.append("/images/edit.gif\" alt=\"" + getLocalizedText(req, "EDIT") + "\"/>");
            content.append("</a>&nbsp;&nbsp;&nbsp;<a href=\"");
            content.append(deleteLink);
            content.append("\">");
            content.append("<img src=\"");
            content.append(req.getContextPath());
            content.append("/images/delete.gif\" alt=\"" + getLocalizedText(req, "DELETE") + "\"/>");
            content.append("</a></fieldset>");
            setBufferedOutput(req, content);
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
            Session session = null;
            try {
                // put a URL in an iframe
                if (textFile.startsWith("http://")) {
                    writer.write("<iframe border=\"0\" width=\"100%\" height=\"100%\" src=\"" + textFile + "\"></iframe>");
                } else if (textFile.startsWith("jcr://")) {
                    JCRService jcrService = (JCRService) PortletServiceFactory.createPortletService(JCRService.class, true);
                    if (!border) {
                        writer.write(jcrService.getContent(textFile.substring(6, textFile.length())));
                    } else {
                        writer.write("<div class=\"gridsphere-content\">" + jcrService.getContent(textFile.substring(6, textFile.length())) + "</div>");
                    }
                } else {
                    // do a normal dispatch
                    rd = ctx.getRequestDispatcher(textFile);
                    if (rd != null) {
                        rd.include(event.getHttpServletRequest(), sres);
                    } else {
                        throw new PortletException("Unable to include resource: RequestDispatcher is null");
                    }
                }
                content = writer.getBuffer();
            } catch (Exception e) {
                log.error("Unable to include : " + textFile, e);
                content.append("Unable to include : ").append(textFile);
            } finally {
                if (session != null) session.logout();
            }
            setBufferedOutput(req, content);
        }
    }

    public Object clone() throws CloneNotSupportedException {
        PortletContent t = (PortletContent) super.clone();
        t.textFile = this.textFile;
        t.context = this.context;
        return t;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.toString());
        sb.append("\ntext file=").append(textFile);
        return sb.toString();
    }
}


