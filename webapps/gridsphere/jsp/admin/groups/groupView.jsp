<%@ page import="java.util.Iterator, java.util.List,
                 org.gridlab.gridsphere.services.core.security.acl.GroupEntry"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>

<% List groupEntryList = (List)request.getAttribute("groupEntryList"); %>

<ui:messagebox beanId="msg"/>

<h3>Manage users in group: <ui:text beanId="groupName" style="nostyle"/></h3>

<% if (groupEntryList.size() > 0) { %>
<h3><ui:text key="GROUP_MODIFY_USERS" style="nostyle"/></h3>
<ui:group>
<ui:form>
<ui:hiddenfield beanId="groupID"/>

<ui:frame>

        <ui:tablerow header="true">
            <ui:tablecell>
                <ui:text key="DELETE"/>
            </ui:tablecell>
            <ui:tablecell>
            <ui:text key="USERNAME"/>
            </ui:tablecell>
            <ui:tablecell>
            <ui:text key="FULLNAME"/>
            </ui:tablecell>
            <ui:tablecell>
            <ui:text key="GROUP_ROLEIN_GROUP"/>
            </ui:tablecell>

        </ui:tablerow>

<%  Iterator groupIterator = groupEntryList.iterator();
    while (groupIterator.hasNext()) {
        GroupEntry groupEntry = (GroupEntry)groupIterator.next();
%>
                <ui:tablerow>
                        <ui:tablecell>
                            <ui:checkbox beanId="groupEntryIDCB" name="<%= groupEntry.getID() %>" value="<%= groupEntry.getID() %>"/>
                        </ui:tablecell>
                        <ui:tablecell>
                                <ui:text value="<%= groupEntry.getUser().getUserName() %>"/>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text value="<%= groupEntry.getUser().getFullName() %>"/>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:actionlink action="doViewEditGroupEntry" value="<%= groupEntry.getRole().toString() %>">
                                <ui:actionparam name="groupEntryID" value="<%= groupEntry.getID() %>"/>
                            </ui:actionlink>
                        </ui:tablecell>
                </ui:tablerow>

     <%              } %>

      </ui:frame>

    <ui:frame>
                <ui:tablerow>
                    <ui:tablecell>
                        <ui:actionsubmit action="doViewViewGroup" key="DELETE"/>
                    </ui:tablecell>
                </ui:tablerow>
            </ui:frame>
            </ui:form>
            </ui:group>

     <%              } %>


    <h3><ui:text key="GROUP_ADD_USERS" style="nostyle"/></h3>

    <ui:group>
    <ui:form>
    <ui:hiddenfield beanId="groupID"/>

    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text key="GROUP_ADMIN_SELECT_USER"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>

    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text key="GROUP_ADD_USER"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:listbox beanId="usersNotInGroupList"/>
            </ui:tablecell>
        </ui:tablerow>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text key="GROUP_ROLEIN_GROUP"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:listbox beanId="groupEntryRoleLB"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>
    <ui:frame>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:actionsubmit action="doViewViewGroup" key="OK"/>
                </ui:tablecell>
            </ui:tablerow>
        </ui:frame>
        </ui:form>
     </ui:group>

<ui:form>
<ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionsubmit action="doViewListGroup" key="GROUP_LIST_GROUPS"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>

</ui:form>
