<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<ui:form>
<ui:hiddenfield beanId="userID"/>
<ui:panel>
    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionsubmit action="doViewConfirmEditUser" value="Save User"/>
                <ui:actionsubmit action="doViewCancelEditUser" value="Cancel Edit"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>

    <ui:frame beanId="errorFrame"/>

    <ui:frame>

        <ui:tablerow>
            <ui:tablecell width="200">
                <ui:text value="User Name:"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:textfield beanId="userName"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell width="200">
                <ui:text value="Family Name:"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:textfield beanId="familyName"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell width="200">
                <ui:text value="Given Name:"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:textfield beanId="givenName"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell width="200">
                <ui:text value="Full Name:"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:textfield beanId="fullName"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell width="200">
                <ui:text value="Email Address:"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:textfield beanId="emailAddress"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell width="200">
                <ui:text value="Organization:"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:textfield beanId="organization"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell width="200">
                <ui:text value="Role In GridSphere:"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:listbox beanId="userRole">
                    <ui:listboxitem value="USER" selected="true"/>
                    <ui:listboxitem value="ADMIN"/>
                    <ui:listboxitem value="SUPER"/>
                </ui:listbox>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell width="200">
                <ui:text value="New Password:"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:password beanId="password"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell width="200">
                <ui:text value="Confirm Password:"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:password beanId="confirmPassword"/>
            </ui:tablecell>
        </ui:tablerow>

    </ui:frame>

</ui:panel>
</ui:form>

