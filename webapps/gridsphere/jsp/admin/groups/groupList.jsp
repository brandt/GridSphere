<%@ page import="java.util.Iterator,
                 org.gridlab.gridsphere.services.core.security.group.PortletGroup,
                 java.util.List" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>

<% PortletGroup coreGroup = (PortletGroup) request.getAttribute("coreGroup"); %>
<% List groupList = (List) request.getAttribute("groupList"); %>

<ui:form>

    <ui:messagebox beanId="msg"/>

    <ui:panel>

        <ui:frame>
            <ui:tablerow header="true">
                <ui:tablecell>
                    <ui:text key="GROUP_NAME"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:text key="GROUP_MANAGE_PORTLETS"/>
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
                    PortletGroup group = (PortletGroup) groupIterator.next();
            %>

            <ui:tablerow>
                <ui:tablecell>
                    <ui:text value="<%=group.getName() %>"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:actionlink action="doCreateNewGroup" key="GROUP_EDIT_PORTLETS">
                        <ui:actionparam name="groupName" value="<%= group.getName() %>"/>
                    </ui:actionlink>
                </ui:tablecell>
                <ui:tablecell>
                    <% if (group.getName().equals(coreGroup.getName())) { %>
                    <ui:hasrole role="super">
                        <ui:actionlink action="doViewViewGroup" key="GROUP_EDIT_USERS">
                            <ui:actionparam name="groupName" value="<%= group.getName() %>"/>
                        </ui:actionlink>
                    </ui:hasrole>
                    <% } else { %>
                    <ui:actionlink action="doViewViewGroup" key="GROUP_EDIT_USERS">
                        <ui:actionparam name="groupName" value="<%= group.getName() %>"/>
                    </ui:actionlink>
                    <% } %>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:text value="<%= group.getDescription() %>"/>
                </ui:tablecell>
                <ui:tablecell>
                    <% if (!group.getName().equals(coreGroup.getName())) { %>
                    <ui:actionsubmit action="deleteGroup" key="DELETE">
                        <ui:actionparam name="groupName" value="<%= group.getName() %>"/>
                    </ui:actionsubmit>
                    <% } %>
                </ui:tablecell>
            </ui:tablerow>


            <%              } %>
        </ui:frame>

        <ui:hasrole role="super">


            <ui:frame>
                <ui:tablerow>
                    <ui:tablecell>
                        <% if (request.getAttribute("create") != null) { %>

                        <ui:actionsubmit action="doCreateNewGroup" key="GROUP_CREATE_NEW"/>

                        <% } %>

                        <ui:actionsubmit action="doEditDefaultGroups" key="GROUP_EDIT_DEFS"/>
                    </ui:tablecell>
                </ui:tablerow>
            </ui:frame>

        </ui:hasrole>

    </ui:panel>
</ui:form>
