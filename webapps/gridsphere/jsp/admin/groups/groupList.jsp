<%@ page import="java.util.Iterator,
                 org.gridlab.gridsphere.portlet.PortletGroup,
                 org.gridlab.gridsphere.portlet.impl.SportletGroup,
                 java.util.List"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>

<% List groupList = (List)request.getAttribute("groupList"); %>
<% List groupDescs = (List)request.getAttribute("groupDescs"); %> 

<ui:form>
<ui:panel>

    <% if (request.getAttribute("create") != null) { %>
    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionsubmit action="doCreateNewGroup" value="Create new group"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>
    <% } %>

    <ui:frame>
        <ui:tablerow header="true">
            <ui:tablecell>
                <ui:text key="GROUP_NAME"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text key="GROUP_ACCESS"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text key="GROUP_DESCRIPTION"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text key="GROUP_DELETE"/>
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
                        <ui:tablecell>
                        <% if (!group.getName().equals(SportletGroup.CORE.getName())) { %>
                            <ui:checkbox beanId="<%= group.getName() %>" value="yes"/>
                        <% } %>
                        </ui:tablecell>
                </ui:tablerow>

<%             i++; } %>
    </ui:frame>

    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionsubmit action="saveGroups" key="APPLY"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>
</ui:panel>
</ui:form>
