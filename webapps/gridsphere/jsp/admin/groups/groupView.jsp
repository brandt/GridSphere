<%@ page import="java.util.Iterator,
                 org.gridlab.gridsphere.services.core.security.acl.GroupEntry"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>

<jsp:useBean id="groupEntryList" class="java.util.List" scope="request"/>

<ui:form>
<ui:hiddenfield beanId="groupID"/>
<ui:panel>

    <ui:frame>
        <ui:tablerow>
            <ui:tablecell cssClass="portlet-frame-actions">
                <ui:actionsubmit action="doViewListGroup" key="GROUP_LIST_GROUPS"/>
                &nbsp;&nbsp;
                <ui:actionsubmit action="doViewAddGroupEntry" key="GROUP_ADD_USERS"/>
                &nbsp;&nbsp;
                <ui:actionsubmit action="doViewRemoveGroupEntry" key="GROUP_REMOVE_USERS"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>

    <ui:frame>
        <ui:tablerow>
            <ui:tablecell width="200">
                <ui:text key="GROUP_NAME"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text beanId="groupName"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell>
                <ui:text key="GROUP_DESCRIPTION"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text beanId="groupDescription"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>



<% if (groupEntryList.size() > 0) { %>

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

<%  Iterator groupIterator = groupEntryList.iterator();
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

    <%              } %>


  </ui:panel>

</ui:form>
