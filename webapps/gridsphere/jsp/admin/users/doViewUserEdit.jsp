<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>

<ui:messagebox beanId="msg"/>

<ui:form>
<ui:hiddenfield beanId="userID"/>
<ui:hiddenfield beanId="newuser"/>

    <ui:frame>

        <ui:tablerow>
            <ui:tablecell width="200">
                <ui:text key="USERNAME"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:textfield beanId="userName"/>
            </ui:tablecell>
        </ui:tablerow>
<%--
        <ui:tablerow>
            <ui:tablecell width="200">
                <ui:text key="FAMILYNAME"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:textfield beanId="familyName"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell width="200">
                <ui:text key="GIVENNAME"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:textfield beanId="givenName"/>
            </ui:tablecell>
        </ui:tablerow>
--%>

        <ui:tablerow>
            <ui:tablecell width="200">
                <ui:text key="FULLNAME"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:textfield beanId="fullName"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell width="200">
                <ui:text key="EMAILADDRESS"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:textfield beanId="emailAddress"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell width="200">
                <ui:text key="ORGANIZATION"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:textfield beanId="organization"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell width="200">
                <ui:text key="USER_ROLE"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:listbox beanId="userRole"/>
            </ui:tablecell>
        </ui:tablerow>

        </ui:frame>
        <ui:frame>
        <ui:tablerow>
            <ui:tablecell width="200">
                <ui:text key="PASSWORD"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:password beanId="password"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell width="200">
                <ui:text key="CONFIRM_PASS"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:password beanId="confirmPassword"/>
            </ui:tablecell>
        </ui:tablerow>

        
    </ui:frame>


<ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionsubmit action="doConfirmEditUser" key="USER_SAVE"/>
                <ui:actionsubmit action="doCancelEditUser" key="USER_CANCEL_EDIT"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>

</ui:form>