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

<ui:messagebox beanId="msg"/>

<ui:panel>

<ui:hasrole role="super">


    <ui:frame>
        <ui:tablerow>
        <% if (request.getAttribute("create") != null) { %>
            <ui:tablecell>
                <ui:actionsubmit action="doCreateNewGroup" value="Create new group"/>
            </ui:tablecell>
            <% } %>
            <ui:tablecell>
                <ui:actionsubmit action="doEditDefaultGroups" value="Edit default groups"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>

</ui:hasrole>

    <ui:frame>
        <ui:tablerow header="true">
            <ui:tablecell>
                <ui:text key="GROUP_NAME"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text key="GROUP_MANAGE_USERS"/>
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
                while ((groupIterator.hasNext() && descsIterator.hasNext())) {
                    // Get next user
                    PortletGroup group = (PortletGroup)groupIterator.next();
                    String desc = (String)descsIterator.next();
%>
                <ui:tablerow>
                        <ui:tablecell>
                            <ui:actionlink action="doCreateNewGroup" value="<%=group.getName() %>">
                                <ui:actionparam name="groupID" value="<%= group.getID() %>"/>
                            </ui:actionlink>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:actionlink action="doViewViewGroup" value="Edit Users">
                                <ui:actionparam name="groupID" value="<%= group.getID() %>"/>
                            </ui:actionlink>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text value="<%= desc %>"/>
                        </ui:tablecell>
                        <ui:tablecell>
                        <% if (!group.getName().equals(SportletGroup.CORE.getName())) { %>
                            <ui:actionsubmit action="deleteGroup" value="Delete">
                                <ui:actionparam name="groupID" value="<%= group.getID() %>"/>
                            </ui:actionsubmit>
                        <% } %>
                        </ui:tablecell>
                </ui:tablerow>

<%              } %>
    </ui:frame>

</ui:panel>
</ui:form>
