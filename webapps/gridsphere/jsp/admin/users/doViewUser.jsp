<%@ page import="org.gridlab.gridsphere.portlet.User"%>
<jsp:useBean id="role" class="java.lang.String" scope="request"/>

<% User user = (User)request.getAttribute("user"); %>

<ui:frame>
        <ui:tablerow>
            <ui:tablecell width="100">
                <ui:text key="USERNAME"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%= user.getUserName() %>" style="plain"/>
            </ui:tablecell>
        </ui:tablerow>
<%--
        <ui:tablerow>
            <ui:tablecell width="100">
                <ui:text key="FAMILYNAME"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%= user.getFamilyName() %>" style="plain"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell width="100">
                <ui:text key="GIVENNAME" />
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%= user.getGivenName() %>" style="plain"/>
            </ui:tablecell>
        </ui:tablerow>

--%>
        <ui:tablerow>
            <ui:tablecell width="100">
                <ui:text key="FULLNAME"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%= user.getFullName() %>" style="plain"/>
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
                <ui:text key="ROLE"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%= role %>" style="plain"/>
            </ui:tablecell>
        </ui:tablerow>

</ui:frame>