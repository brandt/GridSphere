package org.gridsphere.provider.portletui.beans;

import org.gridsphere.portlet.impl.PortletURLImpl;
import org.gridsphere.portlet.impl.SportletProperties;

import javax.portlet.PortletURL;
import java.util.*;

/*
* @author <a href="mailto:wehrens@gridsphere.org">Oliver Wehrens</a>
* @version $Id$
*/
public class TreeNodeBean extends ActionBean implements TagBean {

    // label to be displayed
    private String label = "";

    // action to be executed
    private String action = null;

    // parameters the action might have
    private Map<String, String> parameters = new HashMap<String, String>();

    private PortletURL portletURL = null;

    private boolean closed = true;

    private String uniquePrefix = null;

    // other nodes (childs) it might have
    private List<TreeNodeBean> nodes = new ArrayList<TreeNodeBean>();


    public String getUniquePrefix() {
        return uniquePrefix;
    }

    public void setUniquePrefix(String uniquePrefix) {
        this.uniquePrefix = uniquePrefix;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public boolean hasChildren() {
        return !nodes.isEmpty();
    }

    public void addNode(TreeNodeBean n) {
        nodes.add(n);
    }

    public PortletURL getPortletURL() {
        return portletURL;
    }

    public void setPortletURL(PortletURL portletURL) {
        this.portletURL = portletURL;
    }

    public List getNodes() {
        return nodes;
    }

    public void setNodes(List nodes) {
        this.nodes = nodes;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setParameter(String key, String value) {
        parameters.put(key, value);
    }

    public Map getParameters() {
        return parameters;
    }

    public void setParameters(Map parameters) {
        this.parameters = parameters;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    public String toStartString() {
        StringBuffer buffer = new StringBuffer();

        Map newParams = new HashMap();
        if (!parameters.isEmpty()) {
            // add params
            Set keys = parameters.keySet();
            // prefix params
            for (Object paramKey : keys) {
                String value = parameters.get(paramKey);
                newParams.put(uniquePrefix + "_" + paramKey, new String[]{value});
            }
        }
        portletURL.setParameters(newParams);
        portletURL.setParameter(SportletProperties.PREFIX, uniquePrefix);
        ((PortletURLImpl) portletURL).setAction(action);

        buffer.append("<li");
        if (closed) buffer.append(" class=\"closed\" ");
        buffer.append("><a href=\"").append(portletURL.toString()).append("\">").append(label).append("</a>");

        // check if has children
        if (hasChildren()) {
            buffer.append("<ul>");
            for (int i = 0; i < nodes.size(); i++) {
                TreeNodeBean n = nodes.get(i);
                if (n.getAction() == null) n.setAction(action);
                n.setPortletURL(portletURL);
                n.setUniquePrefix(uniquePrefix);
                buffer.append(n.toStartString());
            }
            buffer.append("</ul>");
        }
        buffer.append("</li>");
        return buffer.toString();
    }

    public String toEndString() {
        return "";
    }
}
