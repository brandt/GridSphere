<%@ page import="java.util.List, java.util.Iterator,
                 org.gridlab.gridsphere.services.core.security.acl.GroupEntry"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>

<% List groupEntryList = (List)request.getAttribute("groupEntryList"); %>

<ui:form>
<ui:hiddenfield beanId="groupID"/>
<ui:panel>

    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionsubmit action="doViewViewGroup" key="GROUP_GO_BACK"/>
                <ui:actionsubmit action="doViewListGroup" key="GROUP_LIST_GROUPS"/>
            </ui:tablecell>
        </ui:tablerow>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text key="GROUP_USERS_ADDED"/>&nbsp;
                <ui:text beanId="groupLabel"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>
    <ui:frame>
        <ui:tablerow header="true">
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
<%
    Iterator groupIterator = groupEntryList.iterator();
    while (groupIterator.hasNext()) {
        GroupEntry groupEntry = (GroupEntry)groupIterator.next();
%>
                <ui:tablerow>
                        <ui:tablecell>
                            <ui:actionlink action="doViewViewGroupEntry" value="<%= groupEntry.getUser().getUserName() %>">
                                <ui:actionparam name="groupEntryID" value="<%= groupEntry.getID() %>"/>
                            </ui:actionlink>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text value="<%= groupEntry.getUser().getFullName() %>"/>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text value="<%= groupEntry.getRole().toString() %>"/>
                        </ui:tablecell>
                </ui:tablerow>

<%              } %>

    </ui:frame>

</ui:panel>
</ui:form>
