/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: PortletContent.java 4993 2006-08-04 10:10:43Z novotny $
 */
package org.gridsphere.layout;

import org.gridsphere.portlet.impl.PortletContextImpl;
import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.portlet.impl.StoredPortletResponseImpl;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.portletcontainer.GridSphereEvent;
import org.gridsphere.services.core.jcr.JCRNode;
import org.gridsphere.services.core.jcr.JCRService;
import org.gridsphere.services.core.portal.PortalConfigService;
import org.radeox.api.engine.RenderEngine;
import org.radeox.api.engine.context.RenderContext;
import org.radeox.engine.BaseRenderEngine;
import org.radeox.engine.context.BaseRenderContext;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.Workspace;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            PortletURL portletURI = res.createRenderURL();
            String link = portletURI.toString();
            content.append("<br><fieldset><a href=\"" + link + "\">" + textFileName + "</a></fieldset>");
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
            try {
                // put a URL in an iframe
                if (textFile.startsWith("http://")) {
                    writer.write("<iframe border=\"0\" width=\"100%\" height=\"100%\" src=\"" + textFile + "\"></iframe>");
                } else if (textFile.startsWith("jcr://")) {
                    // handle content management
                    JCRService jcrService = (JCRService) PortletServiceFactory.createPortletService(JCRService.class, true);
                    Session session = jcrService.getSession();
                    Workspace ws = session.getWorkspace();
                    QueryManager qm = ws.getQueryManager();
                    String nodename = textFile.substring(6, textFile.length()); // remove 'jcr://'
                    String query = "select * from nt:base where " + JCRNode.GSID + "='" + nodename + "'";
                    Query q = qm.createQuery(query, Query.SQL);
                    QueryResult result = q.execute();
                    NodeIterator it = result.getNodes();
                    while (it.hasNext()) {
                        Node n = it.nextNode();
                        String output = n.getProperty(JCRNode.CONTENT).getString();
                        String kit = n.getProperty(JCRNode.RENDERKIT).getString();
                        if (kit.equals(JCRNode.RENDERKIT_RADEOX)) {
                            RenderContext context = new BaseRenderContext();
                            RenderEngine engine = new BaseRenderEngine();
                            output = engine.render(output, context);
                        }
                        if (kit.equals(JCRNode.RENDERKIT_TEXT)) {
                            output = "<pre>" + output + "</pre>";
                        }
                        if (kit.equals(JCRNode.RENDERKIT_HTML)) {
                            // do some wiki markup link replacement for links to other tabs/pages within the portal
                            // [[This|myRef]] will be <a href=".../myRef">This</a>
                            PortalConfigService portalConfigService = (PortalConfigService) PortletServiceFactory.createPortletService(PortalConfigService.class, true);
                            String localPortalURLdeploy = portalConfigService.getProperty("gridsphere.deploy");
                            String localPortalURLcontext = portalConfigService.getProperty("gridsphere.context");
                            String patternFindLinks = "\\[{2}[A-Za-z0-9\\s]++\\|{1}[A-Za-z0-9/\\s]++\\|{1}[A-Za-z0-9/\\s]++\\]{2}";
                            for (Matcher m = Pattern.compile(patternFindLinks).matcher(output); m.find();) {
                                String match = m.toMatchResult().group().toString();
                                String match2 = match.substring(2, match.length() - 2); // subtract [[ and ]]
                                String name = match2.substring(0, match2.indexOf("|")); // get the name
                                String temp = match2.substring(match2.indexOf("|") + 1, match2.length());
                                String layout = temp.substring(0, temp.indexOf("|"));  // layout name
                                String id = temp.substring(temp.indexOf("|") + 1, temp.length()); // fragment id
                                String link = "";
                                try {
                                    link = URLEncoder.encode(id, "UTF-8");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                String replaceString = "<a href=\"/" + localPortalURLdeploy + "/" + localPortalURLcontext + "/"
                                        + layout + "/" + link + "\">" + name + "</a>";
                                output = output.replace(match, replaceString);
                            }
                            output = "<div class=\"gridsphere-content\">" + output + "</div>";
                        }
                        writer.write(output);
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
