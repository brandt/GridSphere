<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<ui:messagebox beanId="msg"/>

<h3><ui:text key="SETUP_MSG" style="nostyle"/></h3>

<ui:text key="SETUP_MSG2"/>

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
                <ui:actionsubmit action="doSavePortalAdmin" key="SAVE"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>

</ui:form>