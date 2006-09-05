<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>

<% Boolean sendMail = (Boolean)request.getAttribute("sendMail"); %>
<ui:form>
    <ui:group key="LOGIN_CONFIG_ERROR_MSG">
        <p>
            <ui:radiobutton beanId="errorRB" value="NOMAIL" selected="<%= !sendMail.booleanValue() %>"/>
            <ui:text key="LOGIN_CONFIG_ERROR_STACKTRACE_MSG"/>
        </p>
        <p>
            <ui:radiobutton beanId="errorRB" value="MAIL" selected="<%= sendMail.booleanValue() %>"/>
            <ui:text key="LOGIN_CONFIG_ERROR_MAIL_MSG"/>
        </p>
        <ui:frame>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:actionsubmit action="configErrorSettings" key="APPLY"/>
                </ui:tablecell>
            </ui:tablerow>
        </ui:frame>
    </ui:group>
</ui:form>
