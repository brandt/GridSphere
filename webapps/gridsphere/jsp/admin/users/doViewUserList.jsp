<%@ page import="java.util.Iterator,
                 org.gridlab.gridsphere.portlet.User"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>

<jsp:useBean id="userList" class="java.util.List" scope="request"/>

<ui:form>
<ui:panel>
    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionsubmit action="doNewUser" key="USER_NEW_USER"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>

    <ui:frame sortable="true" zebra="true">
                <ui:tablerow header="true">
                    <ui:tablecell><ui:text key="USERNAME"/></ui:tablecell>
                    <ui:tablecell><ui:text key="FULLNAME"/></ui:tablecell>
                    <ui:tablecell><ui:text key="EMAILADDRESS"/></ui:tablecell>
                    <ui:tablecell><ui:text key="ORGANIZATION"/></ui:tablecell>
                </ui:tablerow>
<%
                Iterator userIterator = userList.iterator();
                while (userIterator.hasNext()) {
                    // Get next user
                    User user = (User)userIterator.next();
%>
                    <ui:tablerow>
                        <ui:tablecell>
                            <ui:actionlink action="doViewUser" value="<%= user.getUserName() %>">
                                <ui:actionparam name="userID" value="<%= user.getID() %>"/>
                            </ui:actionlink>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text value="<%= user.getFullName() %>" style="plain"/>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text value="<%= user.getEmailAddress() %>" style="plain"/>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text value="<%= user.getOrganization() %>" style="plain"/>
                        </ui:tablecell>
                    </ui:tablerow>
<%
                }
%>
    </ui:frame>

</ui:panel>
</ui:form>
