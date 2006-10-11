<%@ page import="org.gridsphere.services.core.user.User" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<jsp:useBean id="role" class="java.lang.String" scope="request"/>

<% User user = (User) request.getAttribute("user"); %>

<ui:frame>
    <ui:tablerow>
        <ui:tablecell width="100">
            <ui:text key="USERNAME"/>
        </ui:tablecell>
        <ui:tablecell>
            <ui:text value="<%= user.getUserName() %>" style="plain"/>
        </ui:tablecell>
    </ui:tablerow>

    <ui:tablerow>
        <ui:tablecell width="100">
            <ui:text key="GIVENNAME"/>
        </ui:tablecell>
        <ui:tablecell>
            <ui:text value="<%= user.getFirstName() %>" style="plain"/>
        </ui:tablecell>
    </ui:tablerow>

    <ui:tablerow>
        <ui:tablecell width="100">
            <ui:text key="FAMILYNAME"/>
        </ui:tablecell>
        <ui:tablecell>
            <ui:text value="<%= user.getLastName() %>" style="plain"/>
        </ui:tablecell>
    </ui:tablerow>

    <ui:tablerow>
        <ui:tablecell width="100">
            <ui:text key="EMAILADDRESS"/>
        </ui:tablecell>
        <ui:tablecell>
            <ui:text value="<%= user.getEmailAddress() %>" style="plain"/>
        </ui:tablecell>
    </ui:tablerow>

    <ui:tablerow>
        <ui:tablecell width="100">
            <ui:text key="ORGANIZATION"/>
        </ui:tablecell>
        <ui:tablecell>
            <ui:text value="<%= user.getOrganization() %>" style="plain"/>
        </ui:tablecell>
    </ui:tablerow>

    <ui:tablerow>
        <ui:tablecell width="100">
            <ui:text key="USER_ROLES"/>
        </ui:tablecell>
        <ui:tablecell>
            <ui:text value="<%= role %>" style="plain"/>
        </ui:tablecell>
    </ui:tablerow>

    <ui:tablerow>
        <ui:tablecell width="100">
            <ui:text key="USER_ACCOUNT_STATUS"/>
        </ui:tablecell>
        <ui:tablecell>
            <ui:checkbox beanId="accountCB"/>
        </ui:tablecell>
    </ui:tablerow>

<% String certattr = (String) user.getAttribute("user.certificate");
    if (certattr != null) { %>
    <ui:tablerow>
            <ui:tablecell width="100">
                <ui:text key="USER_CERTIFICATE"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%= certattr %>" style="plain"/>
            </ui:tablecell>
    </ui:tablerow>
<% } %>

</ui:frame>