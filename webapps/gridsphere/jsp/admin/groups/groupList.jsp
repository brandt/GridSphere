<%@ page import="java.util.Iterator,
                 org.gridlab.gridsphere.portlet.PortletGroup,
                 org.gridlab.gridsphere.portlet.impl.SportletGroup,
                 java.util.List"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>

<% List groupList = (List)request.getAttribute("groupList"); %>

<ui:form>

<ui:messagebox beanId="msg"/>

<ui:panel>

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
                while (groupIterator.hasNext()) {
                    // Get next user
                    PortletGroup group = (PortletGroup)groupIterator.next();
%>
                <ui:tablerow>
                        <ui:tablecell>
                            <ui:actionlink action="doCreateNewGroup" value="<%=group.getName() %>">
                                <ui:actionparam name="groupID" value="<%= group.getID() %>"/>
                            </ui:actionlink>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:actionlink action="doViewViewGroup" key="GROUP_EDIT_USERS">
                                <ui:actionparam name="groupID" value="<%= group.getID() %>"/>
                            </ui:actionlink>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text value="<%= group.getDescription() %>"/>
                        </ui:tablecell>
                        <ui:tablecell>
                        <% if (!group.getName().equals(SportletGroup.CORE.getName())) { %>
                            <ui:actionsubmit action="deleteGroup" key="DELETE">
                                <ui:actionparam name="groupID" value="<%= group.getID() %>"/>
                            </ui:actionsubmit>
                        <% } %>
                        </ui:tablecell>
                </ui:tablerow>

<%              } %>
    </ui:frame>

    <ui:hasrole role="super">


    <ui:frame>
        <ui:tablerow>
        <% if (request.getAttribute("create") != null) { %>
            <ui:tablecell>
                <ui:actionsubmit action="doCreateNewGroup" key="GROUP_CREATE_NEW"/>
            </ui:tablecell>
            <% } %>
            <ui:tablecell>
                <ui:actionsubmit action="doEditDefaultGroups" key="GROUP_EDIT_DEFS"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>

    </ui:hasrole>

</ui:panel>
</ui:form>
