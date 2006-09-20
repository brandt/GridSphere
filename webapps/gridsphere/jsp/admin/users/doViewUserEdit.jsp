<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>
<ui:messagebox beanId="msg"/>


<h3>
    <% if (request.getAttribute("newuser") != null) { %>
    <ui:text key="USER_EDIT_USER_MSG" style="nostyle"/>
<% } else { %>
    <ui:text key="USER_NEW_USER_MSG" style="nostyle"/>
    <% } %>

</h3>

<p>
    <% if ((request.getAttribute("savePass") != null) && (request.getAttribute("newuser") == null)) { %>
    <ui:text style="alert" key="USER_PASS_BLANK_MSG"/>
    <% } %>
</p>
<ui:form>
<p>
    <ui:hiddenfield beanId="userID"/>
    <ui:hiddenfield beanId="newuser"/>
</p>
<ui:frame>

    <ui:tablerow>
        <ui:tablecell width="200">
            <ui:text key="USERNAME"/>
        </ui:tablecell>
        <ui:tablecell>
            <ui:textfield beanId="userName"/>
        </ui:tablecell>
    </ui:tablerow>

    <ui:tablerow>
        <ui:tablecell width="200">
            <ui:text key="GIVENNAME"/>
        </ui:tablecell>
        <ui:tablecell>
            <ui:textfield beanId="firstName"/>
        </ui:tablecell>
    </ui:tablerow>

    <ui:tablerow>
        <ui:tablecell width="200">
            <ui:text key="FAMILYNAME"/>
        </ui:tablecell>
        <ui:tablecell>
            <ui:textfield beanId="lastName"/>
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
            <ui:text key="USER_ACCOUNT_STATUS"/>
        </ui:tablecell>
        <ui:tablecell>
            <ui:checkbox beanId="accountCB"/>
        </ui:tablecell>
    </ui:tablerow>

</ui:frame>

<ui:frame beanId="roleFrame"/>

<% if (request.getAttribute("certSupport") != null) { %>
<ui:tablerow>
            <ui:tablecell width="200">
                <ui:text key="CERTIFICATE"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:textfield beanId="certificate"/>
            </ui:tablecell>
</ui:tablerow>
<% } %>

<% if (request.getAttribute("savePass") != null) { %>
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
<% } %>

<% if (request.getAttribute("newuser") != null) { %>
<ui:checkbox beanId="emailUserCB"/><ui:text key="USER_EMAIL_USER"/>
<% } %>
<p/>
<ui:frame>
    <ui:tablerow>
        <ui:tablecell>
            <ui:actionsubmit action="doConfirmEditUser" key="USER_SAVE"/>
            <ui:actionsubmit action="doReturn" key="CANCEL"/>
        </ui:tablecell>
    </ui:tablerow>
</ui:frame>

</ui:form>