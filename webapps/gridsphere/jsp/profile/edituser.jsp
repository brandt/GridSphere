<%@ page import="java.util.Locale"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<jsp:useBean id="logintime" class="java.lang.String" scope="request"/>
<jsp:useBean id="username" class="java.lang.String" scope="request"/>

<% Locale locale = (Locale)request.getAttribute("locale"); %>

<% String flag = "/gridsphere/html/flags/"+locale.getLanguage() +".gif"; %>

<ui:form>
<ui:panel>

<ui:text key="PROFILE_EDIT"/>&nbsp;&nbsp;<b><%= username %></b>

<br>
<ui:text key="PROFILE_LASTLOGIN"/>&nbsp;&nbsp;<b><%= logintime %></b>

<ui:frame beanId="errorFrame"/>

<ui:frame>
    <ui:tablerow>
        <ui:tablecell><ui:text key="USERNAME"/></ui:tablecell>
        <ui:tablecell><ui:textfield beanId="userName"/></ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell><ui:text key="FULLNAME"/></ui:tablecell>
        <ui:tablecell><ui:textfield beanId="fullName"/></ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell><ui:text key="EMAILADDRESS"/></ui:tablecell>
        <ui:tablecell><ui:textfield beanId="emailAddress"/></ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell><ui:text key="ORGANIZATION"/></ui:tablecell>
        <ui:tablecell><ui:textfield beanId="organization"/></ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell><ui:text key="PASSWORD"/></ui:tablecell>
        <ui:tablecell><ui:password beanId="password"/></ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell><ui:text key="CONFIRM_PASS"/></ui:tablecell>
        <ui:tablecell><ui:password beanId="confirmPassword"/></ui:tablecell>
    </ui:tablerow>
     <ui:tablerow>
        <ui:tablecell><ui:text key="LOCALE"/></ui:tablecell>
        <ui:tablecell><ui:image src="<%= flag %>" alt="<%= locale.getDisplayLanguage() %>" title="<%= locale.getDisplayLanguage() %>"/><ui:listbox beanId="userlocale"/></ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell><ui:text key="TIMEZONE"/></ui:tablecell>
        <ui:tablecell><ui:listbox beanId="timezones"/></ui:tablecell>
    </ui:tablerow>
</ui:frame>

<ui:frame beanId="messagingFrame"/>

<ui:frame beanId="groupsFrame"/>

<ui:frame>
<ui:tablerow>
<ui:tablecell>
<ui:actionsubmit action="doSaveUser" key="PROFILE_SAVE"/>
</ui:tablecell>
</ui:tablerow>
</ui:frame>
</ui:panel>

</ui:form>

