<jsp:useBean id="user" class="org.gridlab.gridsphere.portlet.User" scope="request"/>
<jsp:useBean id="role" class="java.lang.String" scope="request"/>

<ui:frame>
        <ui:tablerow>
            <ui:tablecell width="100">
                <ui:text value="User Name:"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%= user.getUserName() %>" style="plain"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell width="100">
                <ui:text value="Family Name:"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%= user.getFamilyName() %>" style="plain"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell width="100">
                <ui:text value="Given Name:" />
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%= user.getGivenName() %>" style="plain"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell width="100">
                <ui:text value="Full Name:"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%= user.getFullName() %>" style="plain"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell width="100">
                <ui:text value="Email Address:"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%= user.getEmailAddress() %>" style="plain"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell width="100">
                <ui:text value="Organization:"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%= user.getOrganization() %>" style="plain"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell width="100">
                <ui:text value="Role:"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="<%= role %>" style="plain"/>
            </ui:tablecell>
        </ui:tablerow>

</ui:frame>