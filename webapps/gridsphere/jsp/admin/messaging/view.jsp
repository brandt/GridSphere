<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>
<jsp:useBean id="services" class="java.lang.String" scope="request"/>

<ui:messagebox beanId="msg"/>

<%
    if (new Integer(services).intValue() > 0) {
%>
<p>
    <ui:text key="MESSAGING_SERVICE_CONFIGTEXT"/>
</p>
<ui:form>

    <ui:frame beanId="serviceframe"/>
    <p>
        <ui:actionsubmit action="doSaveValues" key="MESSAGING_SERVICE_SAVE"/>
    </p>
</ui:form>

<%
} else {
%>
<p>
    <ui:text key="MESSAGING_NO_SERVICE_CONFIGURED"/>
</p>
<%
    }
%>