package org.gridlab.gridsphere.portlets.core;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.event.impl.ActionEventImpl;
import org.gridlab.gridsphere.provider.gpdk.core.PageCommand;
import org.gridlab.gridsphere.provider.gpdk.core.CommandException;
import org.gridlab.gridsphere.provider.gpdk.core.PageException;
import org.gridlab.gridsphere.provider.gpdk.core.DefaultActionPage;
import org.gridlab.gridsphere.provider.gpdk.core.descriptor.PortletPagesDescriptor;
import org.gridlab.gridsphere.provider.gpdk.core.descriptor.PortletPageCollection;
import org.gridlab.gridsphere.provider.gpdk.core.descriptor.PortletPageDefinition;
import org.gridlab.gridsphere.provider.gpdk.core.descriptor.PortletPagesList;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfigProperties;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;

import javax.servlet.UnavailableException;
import java.io.*;
import java.util.*;
import java.lang.reflect.Constructor;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public class GPDKPortlet extends AbstractPortlet {

    private static HashMap pages = new HashMap();
    private static String ERROR_PAGE = "error.jsp";
    private static String DEFAULT_VIEW_JSP;
    private static String DEFAULT_EDIT_JSP;
    private static String DEFAULT_HELP_JSP;
    private static String DEFAULT_CONFIGURE_JSP;

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        // load pages config file here
        try {
            loadPages(config);
        } catch (PortletException e) {
            log.error("GPDKPortlet: Encountered casting exception: ", e);
        }
        Iterator portletsIt = pages.values().iterator();
        while (portletsIt.hasNext()) {
            Map pagecmds = (Map)portletsIt.next();
            Iterator cmdsIt = pagecmds.values().iterator();
            while (cmdsIt.hasNext()) {
                PageCommand cmd = (PageCommand)cmdsIt.next();
                cmd.initPage(config);
            }
        }
    }

    public void initConcrete(PortletSettings settings) throws UnavailableException {
        super.initConcrete(settings);
        Iterator portletsIt = pages.values().iterator();
        while (portletsIt.hasNext()) {
            Map pagecmds = (Map)portletsIt.next();
            Iterator cmdsIt = pagecmds.values().iterator();
            while (cmdsIt.hasNext()) {
                PageCommand cmd = (PageCommand)cmdsIt.next();
                cmd.initConcretePage(settings);
            }
        }
    }

    protected void setNextPage(PortletRequest request, String method) {
        String id = request.getPortletSettings().getConcretePortletID();
        request.setAttribute(id + ".page", method);
    }

    protected String getNextPage(PortletRequest request) {
        String id = request.getPortletSettings().getConcretePortletID();
        String state = (String)request.getAttribute(id+".page");
        return state;
    }

    public void actionPerformed(ActionEvent event) {
        String action = event.getAction().getName();
        PortletRequest req = event.getPortletRequest();
        if (action == null) {
            action = req.getMode().toString().toUpperCase();
        }
        String portletId = req.getPortletSettings().getConcretePortletID();
        try {
            PageCommand cmd = lookupPage(portletId, action);
            log.debug("Performing actionPerformed on: " + cmd.getClass().getName());
            String gotoPage = cmd.actionPerformed(event);
            setNextPage(req, gotoPage);
        } catch (CommandException ce) {
            log.error("Command exception occurred" + ce);
            req.setAttribute("javax.servlet.jsp.jspException", ce);
            setNextPage(req, ERROR_PAGE);
        } catch (PortletException pe) {
            req.setAttribute("javax.servlet.jsp.jspException", pe);
            log.error("portlet exception occurred" + pe);
            setNextPage(req, ERROR_PAGE);
        }
    }

    public void doView(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        String gotoPage = getNextPage(request);
        if (gotoPage == null) {
            gotoPage = DEFAULT_VIEW_JSP;
        }
        log.debug("Forward to JSP page:" + gotoPage);
        getPortletConfig().getContext().include("/jsp/" + gotoPage, request, response);
    }

    public void doEdit(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        String gotoPage = getNextPage(request);
        if (gotoPage == null) {
            gotoPage = DEFAULT_EDIT_JSP;
        }
        log.debug("Forward to JSP page:" + gotoPage);
        getPortletConfig().getContext().include("/jsp/" + gotoPage, request, response);
    }

    public void doConfigure(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        String gotoPage = getNextPage(request);
        if (gotoPage == null) {
            gotoPage = DEFAULT_CONFIGURE_JSP;
        }
        log.debug("Forward to JSP page:" + gotoPage);
        getPortletConfig().getContext().include("/jsp/" + gotoPage, request, response);
    }

    public void doHelp(PortletRequest request, PortletResponse response) throws PortletException, IOException {
        String gotoPage = getNextPage(request);
        if (gotoPage == null) {
            gotoPage = DEFAULT_HELP_JSP;
        }
        log.debug("Forward to JSP page:" + gotoPage);
        getPortletConfig().getContext().include("/jsp/" + gotoPage, request, response);
    }

    private PageCommand lookupPage(String concretePortletId, String action) throws CommandException {
        if (pages.containsKey(concretePortletId)) {
            Map pagecmds = (Map)pages.get(concretePortletId);
            if (pagecmds != null) {
                PageCommand cmd = (PageCommand)pagecmds.get(action);
                if (cmd == null) throw new CommandException("Unable to find page from " + concretePortletId + " action= " + action);
                log.debug("returning page " + cmd.getClass().getName() + " from " + concretePortletId + " " + action);
                return cmd;
            }
            throw new CommandException("Unable to find page for concretePortletId" + concretePortletId);
        } else {
            throw new CommandException("Invalid action parameter: action=" + action);
        }
    }

    /**
     * Creates the mappings between actions and
     * project defined Pages based on the pages.config file.
     */
    private void loadPages(PortletConfig config) throws PortletException {
        pages = new HashMap();

        String pagesConfig = config.getContext().getRealPath("") + "/WEB-INF/pages.xml";

        PortletPagesDescriptor descriptor = null;
        String mappingPath = GridSphereConfig.getProperty(GridSphereConfigProperties.GPDK_MAPPING);

        try {
            descriptor = new PortletPagesDescriptor(pagesConfig, mappingPath);
        } catch (IOException e) {
            log.error("IO error unmarshalling " + pagesConfig + " using " + mappingPath + " : " + e.getMessage());
            return;
        } catch (PersistenceManagerException e) {
            log.error("Unable to unmarshall " + pagesConfig + " using " + mappingPath + " : " + e.getMessage());
            return;
        }

        PortletPageCollection pagesCollection = descriptor.getPortletPageCollection();

        List pagelist = pagesCollection.getPortletPagesList();
        Iterator it = pagelist.iterator();
        while (it.hasNext()) {
            PortletPagesList pagesList = (PortletPagesList) it.next();
            String concPortlet = pagesList.getConcretePortlet();
            Map pageCommandObjects = createPortletActionPages(pagesList);
            pages.put(concPortlet, pageCommandObjects);
        }
    }

    public Map createPortletActionPages(PortletPagesList pageList) throws PortletException {

        List portletPages = pageList.getPortletPages();
        String baseDir = pageList.getBasePageClassDir();
        Iterator it = portletPages.iterator();
        Map actionPages = new HashMap();
        while (it.hasNext()) {
            PortletPageDefinition pageDef = (PortletPageDefinition)it.next();
            String action = pageDef.getAction();
            String jsppage = pageDef.getPresentation();
            String pageimpl = pageDef.getPageImplementation();
            String pageclass = baseDir + "." + pageimpl;

            if (pageimpl.equals("DefaultActionPage")) pageclass = DefaultActionPage.class.getName();

            if (action.toUpperCase().equals("VIEW")) {
                DEFAULT_VIEW_JSP = jsppage;
            } else if (action.toUpperCase().equals("EDIT")) {
                DEFAULT_EDIT_JSP = jsppage;
            } else if (action.toUpperCase().equals("CONFIGURE")) {
                DEFAULT_CONFIGURE_JSP = jsppage;
            } else if (action.toUpperCase().equals("HELP")) {
                DEFAULT_HELP_JSP = jsppage;
            }

             try {
                Class c = Class.forName(pageclass.trim());
                Class[] parameterTypes = new Class[] {String.class};
                Object[] obj = new Object[] {jsppage};
                Constructor con = c.getConstructor(parameterTypes);
                PageCommand page = (PageCommand)con.newInstance(obj);
                actionPages.put(action, page);
            } catch (ClassNotFoundException cne) {
                log.error("GPDKPortlet: Unable to locate class: " + pageclass);
                throw new PortletException("GPDKPortlet: Unable to locate class: " + pageclass);
            } catch(Exception e) {
                log.error("GPDKPortlet: Unable to create new " + pageclass + "(" + jsppage + ") for " + action, e);
                throw new PortletException("GPDKPortlet: Unable to create new " + pageclass + "(" + jsppage + ") for " + action);
            }
        }
        return actionPages;
    }
}

