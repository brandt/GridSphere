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
                <ui:textfield size="15" beanId="userName" maxlength="40"/>
            </ui:tablecell>
        </ui:tablerow>


        <ui:tablerow>
            <ui:tablecell width="200">
                <ui:text key="GIVENNAME"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:textfield size="15" beanId="firstName" maxlength="40"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell width="200">
                <ui:text key="FAMILYNAME"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:textfield size="15" beanId="lastName" maxlength="40"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell width="200">
                <ui:text key="EMAILADDRESS"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:textfield size="15" beanId="emailAddress" maxlength="60"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell width="200">
                <ui:text key="ORGANIZATION"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:textfield size="15" beanId="organization" maxlength="40"/>
            </ui:tablecell>
        </ui:tablerow>

        <% if (request.getAttribute("savePass") != null) { %>

        <ui:tablerow>
            <ui:tablecell width="200">
                <ui:text key="PASSWORD"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:password size="15" beanId="passwordx" maxlength="40"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell width="200">
                <ui:text key="CONFIRM_PASS"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:password size="15" beanId="confirmPassword" maxlength="40"/>
            </ui:tablecell>
        </ui:tablerow>
        <% } %>

    </ui:frame>

    <ui:image src="<%= request.getContextPath() + \"/Captcha.jpg\" %>"/>
    <ui:renderlink key="LOGIN_CANT_READ" render="doNewUser"/>
    <p/>

    <ui:text key="CAPTCHA_TEXT"/>
    <p/>
    <ui:textfield size="10" beanId="captchaTF" maxlength="40"/>
    <p/>
    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionsubmit action="doConfirmEditUser" key="SAVE"/>
                <ui:actionsubmit action="doViewUser" key="CANCEL"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>

</ui:form>

