<%@ page import="org.gridsphere.services.core.user.User" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<%
    String numSessions = (String) request.getAttribute("num_sessions");
    List<User> names = (List) request.getAttribute("uids");

%>

<ui:text key="SESSION_ACTIVE_USERS"/> : <%=numSessions%>.

<ui:table zebra="true">
    <ui:tablerow header="true">
        <ui:tablecell><ui:text key="SESSION_FULLNAME"/></ui:tablecell>
        <ui:tablecell><ui:text key="SESSION_ORGANIZATION"/></ui:tablecell>
        <ui:tablecell><ui:text key="SESSION_EMAIL"/></ui:tablecell>
        <ui:tablecell><ui:text key="SESSION_LASTLOGIN"/></ui:tablecell>
    </ui:tablerow>

    <%
        if (names != null) {
            for (User user : names) {
    %>
    <ui:tablerow>
        <ui:tablecell><%=user.getFullName()%>
        </ui:tablecell>
        <ui:tablecell><%=user.getOrganization()%>
        </ui:tablecell>
        <ui:tablecell><%=user.getEmailAddress()%>
        </ui:tablecell>
        <ui:tablecell><%=new Date(user.getLastLoginTime())%>
        </ui:tablecell>
    </ui:tablerow>
    <%
            }
        }


    %>

</ui:table>