<%@ page import="java.util.Iterator,
                 org.gridlab.gridsphere.portlet.PortletGroup"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>

<jsp:useBean id="groupList" class="java.util.List" scope="request"/>
<jsp:useBean id="groupDescs" class="java.util.List" scope="request"/>

<ui:form>
<ui:panel>

    <ui:frame>
        <ui:tablerow header="true">
            <ui:tablecell>
                <ui:text value="Group Name"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="Group Access"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="Group Description"/>
            </ui:tablecell>
        </ui:tablerow>

<%
                Iterator groupIterator = groupList.iterator();
                Iterator descsIterator = groupDescs.iterator();
                int i = 0;
                while ((groupIterator.hasNext() && descsIterator.hasNext())) {
                    // Get next user
                    PortletGroup group = (PortletGroup)groupIterator.next();
                    String desc = (String)descsIterator.next();
                    //String groupRB = "groupsRB" + i;
%>
                <ui:tablerow>
                        <ui:tablecell>
                            <ui:actionlink action="doViewViewGroup" value="<%=group.getName() %>">
                                <ui:actionparam name="groupID" value="<%= group.getID() %>"/>
                            </ui:actionlink>
                        </ui:tablecell>
                        <ui:tablecell>
                        <% if (group.isPublic()) { %>
                        <ui:radiobutton selected="true" name="<%= group.getName() %>" value="PUBLIC"/>
                        <ui:text value="PUBLIC"/>
                        <ui:radiobutton  name="<%= group.getName() %>" value="PRIVATE"/>
                        <ui:text value="PRIVATE"/>
                        <% } else { %>
                        <ui:radiobutton name="<%= group.getName() %>" value="PUBLIC"/>
                        <ui:text value="PUBLIC"/>
                        <ui:radiobutton selected="true" name="<%= group.getName() %>" value="PRIVATE"/>
                        <ui:text value="PRIVATE"/>
                        <% } %>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text value="<%= desc %>"/>
                        </ui:tablecell>
                </ui:tablerow>

<%             i++; } %>
    </ui:frame>

    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionsubmit action="saveGroups" value="Apply Changes"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>
</ui:panel>
</ui:form>
