<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<ui:form>

    <ui:messagebox beanId="msg"/>

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

        <% if (request.getAttribute("savePass") != null) { %>

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
        <% } %>

        <ui:tablerow>

            <ui:tablecell width="200">
                <ui:image src="<%= request.getContextPath() + "/jcaptcha" %>"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell width="200">
                <ui:text key="CAPTCHA_TEXT"/>
            </ui:tablecell>
            <ui:tablecell width="200">
                <ui:textfield beanId="captchaTF"/>
            </ui:tablecell>
        </ui:tablerow>

    </ui:frame>

    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionsubmit action="doConfirmEditUser" key="SAVE"/>
                <ui:actionsubmit action="doViewUser" key="CANCEL"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>

</ui:form>

