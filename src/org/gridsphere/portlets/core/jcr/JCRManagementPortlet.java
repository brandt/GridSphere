package org.gridsphere.portlets.core.jcr;

import org.gridsphere.provider.event.jsr.ActionFormEvent;
import org.gridsphere.provider.event.jsr.FormEvent;
import org.gridsphere.provider.event.jsr.RenderFormEvent;
import org.gridsphere.provider.portlet.jsr.ActionPortlet;
import org.gridsphere.provider.portletui.beans.ListBoxBean;
import org.gridsphere.provider.portletui.beans.ListBoxItemBean;
import org.gridsphere.provider.portletui.beans.TextAreaBean;
import org.gridsphere.provider.portletui.beans.TextFieldBean;
import org.gridsphere.services.core.jcr.JCRNode;
import org.gridsphere.services.core.jcr.JCRService;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.naming.NamingException;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import java.util.ArrayList;
import java.util.List;

/*
 * @author <a href="mailto:wehrens@gridsphere.org">Oliver Wehrens</a>
 * @version $Id$
 */

// todo add admin interface to backup all nodes as files/as one .xml file
// todo help mode

public class JCRManagementPortlet extends ActionPortlet {

    private JCRService jcrService = null;
    private final static String viewJSP = "jcr/view.jsp";
    private List renderKits = new ArrayList();


    public void init(PortletConfig config) throws PortletException {
        super.init(config);
        jcrService = (JCRService) createPortletService(JCRService.class);
        DEFAULT_VIEW_PAGE = "doView";
        DEFAULT_HELP_PAGE = "jcr/help.jsp";
        renderKits.add(JCRNode.RENDERKIT_HTML);
        renderKits.add(JCRNode.RENDERKIT_RADEOX);
        renderKits.add(JCRNode.RENDERKIT_TEXT);

    }

    public void listNodes(FormEvent event, Session session) throws RepositoryException, NamingException {
        ListBoxBean nodelist = event.getListBoxBean("nodelist");
        nodelist.clear();
        String query = "select * from nt:base where " + JCRNode.GSID + " IS NOT NULL";
        NodeIterator it = jcrService.query(query, session);
        while (it.hasNext()) {
            Node n = it.nextNode();
            ListBoxItemBean item = new ListBoxItemBean();
            item.setValue(n.getName());
            nodelist.addBean(item);
        }
    }

    protected void clearInputs(FormEvent event) {
        TextFieldBean nodeid = event.getTextFieldBean("nodeid");
        TextAreaBean nodecontent = event.getTextAreaBean("nodecontent");
        nodeid.setValue("");
        nodecontent.setValue("");
        setRenderKitValue(event, JCRNode.RENDERKIT_DEFAULT);
    }

    protected void setRenderKitValue(FormEvent event, String kit) {
        ListBoxBean renderkit = event.getListBoxBean("renderkit");
        renderkit.clear();
        for (int i = 0; i < renderKits.size(); i++) {
            ListBoxItemBean item = new ListBoxItemBean();
            String akit = (String) renderKits.get(i);
            String aname[] = akit.split("/");
            item.setName(akit);
            item.setValue(aname[1]);
            if (kit.equals(akit)) {
                item.setSelected(true);
            }
            renderkit.addBean(item);
        }
    }

    public void doView(RenderFormEvent event) throws PortletException {
        Session session = null;
        try {
            session = jcrService.getSession();
            listNodes(event, session);
        } catch (RepositoryException e) {
            e.printStackTrace();
            log.error("Could not retrieve Nodelist.");
            createErrorMessage(event, "Could not create Nodelist.");
        } catch (NamingException e) {
            e.printStackTrace();
            log.error("Could not retrieve Nodelist.");
            createErrorMessage(event, "Could not create Nodelist.");
        } finally {
            if (session != null) session.logout();
        }
        clearInputs(event);
        setNextState(event.getRenderRequest(), viewJSP);
    }

    public void createNode(ActionFormEvent event) throws PortletException {
        String nodeid = event.getTextFieldBean("nodeid").getValue();
        String nodecontent = event.getTextAreaBean("nodecontent").getValue();
        String renderkit = event.getListBoxBean("renderkit").getSelectedName();
        Session session = null;
        try {
            String action = "";
            Node node = null;
            session = jcrService.getSession();

            if (jcrService.exists(nodeid)) {
                // get existing node
                String query = "select * from nt:base where " + JCRNode.GSID + "='" + nodeid + "'";
                NodeIterator it = jcrService.query(query, session);
                if (it.hasNext()) node = it.nextNode();
                action = "Updated";
            } else {
                // create a new node
                Node rootnode = session.getRootNode();
                node = rootnode.addNode(nodeid);
                action = "Created";
            }
            if (node != null) {
                node.setProperty(JCRNode.CONTENT, nodecontent);
                node.setProperty(JCRNode.GSID, nodeid);
                node.setProperty(JCRNode.RENDERKIT, renderkit);
                session.save();
                createSuccessMessage(event, action + " node " + nodeid + ".");
            } else {
                createErrorMessage(event, "Could not save node " + nodeid + ".");
            }
            listNodes(event, session);
        } catch (RepositoryException e) {
            e.printStackTrace();
            log.error("Could not retrieve Nodelist.");
            createErrorMessage(event, "Could not create Nodelist.");
        } catch (NamingException e) {
            e.printStackTrace();
            log.error("Could not retrieve Nodelist.");
            createErrorMessage(event, "Could not create Nodelist.");
        } finally {
            if (session != null) session.logout();
        }
        clearInputs(event);
        setNextState(event.getActionRequest(), viewJSP);
    }

    public void showNode(ActionFormEvent event) throws PortletException {
        ListBoxBean nodelist = event.getListBoxBean("nodelist");
        TextAreaBean nodecontent = event.getTextAreaBean("nodecontent");
        TextFieldBean nodeid = event.getTextFieldBean("nodeid");
        String renderkit = JCRNode.RENDERKIT_DEFAULT;
        String nodename = nodelist.getSelectedName();

        Session session = null;
        try {
            session = jcrService.getSession();
            String query = "select * from nt:base where " + JCRNode.GSID + "='" + nodename + "'";
            NodeIterator it = jcrService.query(query, session);
            Node node = it.nextNode();
            nodeid.setValue(node.getProperty(JCRNode.GSID).getString());
            nodecontent.setValue(node.getProperty(JCRNode.CONTENT).getString());
            renderkit = node.getProperty(JCRNode.RENDERKIT).getString();
            listNodes(event, jcrService.getSession());
        } catch (RepositoryException e) {
            e.printStackTrace();
            createErrorMessage(event, "Could not load node " + nodename);
            log.error("Could not load node (RepositoryException): " + nodename);
        } catch (NamingException e) {
            e.printStackTrace();
            createErrorMessage(event, "Could not load node " + nodename);
            log.error("Could not load node (NamingException): " + nodename);
        } finally {
            if (session != null) session.logout();
        }
        setRenderKitValue(event, renderkit);
        setNextState(event.getActionRequest(), viewJSP);
    }

    public void removeNode(ActionFormEvent event) throws PortletException {
        ListBoxBean nodelist = event.getListBoxBean("nodelist");
        String nodename = nodelist.getSelectedName();
        Session session = null;
        try {
            session = jcrService.getSession();
            String query = "select * from nt:base where " + JCRNode.GSID + "='" + nodename + "'";
            NodeIterator it = jcrService.query(query, session);
            Node node = it.nextNode();
            node.remove();
            session.save();
            createSuccessMessage(event, "Removed node " + nodename + ".");
            listNodes(event, jcrService.getSession());
        } catch (RepositoryException e) {
            e.printStackTrace();
            createErrorMessage(event, "Could not load node " + nodename);
            log.error("Could not load node (RepositoryException): " + nodename);
        } catch (NamingException e) {
            e.printStackTrace();
            createErrorMessage(event, "Could not load node " + nodename);
            log.error("Could not load node (NamingException): " + nodename);
        } finally {
            if (session != null) session.logout();
        }
        clearInputs(event);
        setNextState(event.getActionRequest(), viewJSP);
    }
}
