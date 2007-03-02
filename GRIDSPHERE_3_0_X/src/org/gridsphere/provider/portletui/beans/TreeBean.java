package org.gridsphere.provider.portletui.beans;

import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.services.core.portal.PortalConfigService;

import javax.portlet.PortletURL;
import java.util.ArrayList;
import java.util.List;

/*
* @author <a href="mailto:wehrens@gridsphere.org">Oliver Wehrens</a>
* @version $Id$
*/
public class TreeBean extends BaseComponentBean implements TagBean {

    private List<TreeNodeBean> nodeList = new ArrayList<TreeNodeBean>();

    public static final String NAME = "tr";

    protected PortletURL portletURL = null;

    private String action = null;

    public TreeBean() {
        super(TreeBean.NAME);
    }

    /**
     * Constructs a text area bean from a supplied portlet request and bean identifier
     *
     * @param beanId the bean identifier
     */
    public TreeBean(String beanId) {
        super(TreeBean.NAME);
        this.beanId = beanId;
    }

    public void addNode(TreeNodeBean node) {
        nodeList.add(node);
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }


    public PortletURL getPortletURL() {
        return portletURL;
    }

    public void setPortletURL(PortletURL portletURL) {
        this.portletURL = portletURL;
    }

    public List<TreeNodeBean> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<TreeNodeBean> nodes) {
        this.nodeList = nodes;
    }


    protected String createUniquePrefix(int numChars) {
        StringBuffer s = new StringBuffer();
        for (int i = 0; i <= numChars; i++) {
            int nextChar = (int) (Math.random() * 62);
            if (nextChar < 10) //0-9
                s.append(nextChar);
            else if (nextChar < 36) //a-z
                s.append((char) (nextChar - 10 + 'a'));
            else
                s.append((char) (nextChar - 36 + 'A'));
        }
        return s.toString();
    }


    public String toStartString() {
        PortalConfigService configService = (PortalConfigService) PortletServiceFactory.createPortletService(PortalConfigService.class, true);
        // deal with ROOT context case
        String contextPath = configService.getProperty("gridsphere.deploy");
        if (!contextPath.equals("")) contextPath = "/" + contextPath;
        renderResponse.addProperty("JAVASCRIPT_SRC", contextPath + "/javascript/tree/tree.js");
        renderResponse.addProperty("CSS_HREF", contextPath + "/javascript/tree/tree.css");

        String uniquePrefix = createUniquePrefix(2);
        StringBuffer buffer = new StringBuffer();

        buffer.append("\n<ul class=\"tree\"> <!-- tree start -->\n");


        for (int i = 0; i < nodeList.size(); i++) {
            TreeNodeBean n = nodeList.get(i);
            if (n.getAction() == null) n.setAction(action);
            n.setPortletURL(portletURL);
            n.setUniquePrefix(uniquePrefix);
            buffer.append(n.toStartString());
        }

        buffer.append("\n</ul> <!-- tree end -->\n");
        return buffer.toString();
    }

    public String toEndString() {
        return "";
    }
}
