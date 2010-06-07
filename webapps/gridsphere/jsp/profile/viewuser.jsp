<%@ page import="org.gridsphere.services.core.security.role.PortletRole" %>
<%@ page import="javax.portlet.RenderRequest" %>
<%@ page import="java.util.Locale" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<jsp:useBean id="logintime" class="java.lang.String" scope="request"/>

<% Locale locale = (Locale) request.getAttribute("locale"); %>
<% RenderRequest req = (RenderRequest) pageContext.findAttribute("renderRequest"); %>
<% String flag = req.getContextPath() + "/images/flags/" + locale.getLanguage() + ".gif"; %>


<div style="margin: 0px 0px 0px 15%;">

<ui:form>
<ui:messagebox beanId="msg"/>


<h3>
    <!--  Modified by Alan start -->
<%
	String shib_flag = (String)request.getSession(true).getAttribute("shibboleth.login");
	
	if((shib_flag == null) || (!shib_flag.equals("true")))
	{
%>
		<ui:actionlink action="doEditPassword" value="Change password"/></h3>
<%
	}
%>
<!--  Modified by Alan endt -->

<ui:frame>
<ui:tablerow>
<ui:tablecell>
    <ui:text key="PROFILE_LASTLOGIN"/>
</ui:tablecell>
<ui:tablecell>
<b><%= logintime %>
</b>
</ui:tablecell>
</ui:tablerow>

<ui:tablerow>
<ui:tablecell>
    <ui:text key="USERNAME"/>
</ui:tablecell>
<ui:tablecell>
    <% if (req.isUserInRole(PortletRole.ADMIN.getName())) { %>
<ui:textfield beanId="userNameTF">
    <ui:validator type="checkNotEmpty" key="USER_NAME_BLANK"/>
</ui:textfield>
    <% } else { %>
    <ui:text beanId="userName"/>
    <% } %>
</ui:tablecell>
</ui:tablerow>
<ui:tablerow>
<ui:tablecell>
    <ui:text key="GIVENNAME"/>
</ui:tablecell>
<ui:tablecell>
<ui:textfield beanId="firstName">
    <ui:validator type="checkNotEmpty" key="USER_GIVENNAME_BLANK"/>
</ui:textfield>
</ui:tablecell>
</ui:tablerow>
<ui:tablerow>
<ui:tablecell>
    <ui:text key="FAMILYNAME"/>
</ui:tablecell>
<ui:tablecell>
<ui:textfield beanId="lastName">
    <ui:validator type="checkNotEmpty" key="USER_FAMILYNAME_BLANK"/>
</ui:textfield>
</ui:tablecell>
</ui:tablerow>
<ui:tablerow>
<ui:tablecell>
    <ui:text key="ORGANIZATION"/>
</ui:tablecell>
<ui:tablecell>
    <ui:textfield beanId="organization"/>
</ui:tablecell>
</ui:tablerow>
<ui:tablerow>
<ui:tablecell>
    <ui:text key="USER_ROLES"/>
</ui:tablecell>
<ui:tablecell>
    <ui:text beanId="userRoles"/>
</ui:tablecell>
</ui:tablerow>

<ui:tablerow>
<ui:tablecell>
    <ui:text key="EMAILADDRESS"/>
</ui:tablecell>
<ui:tablecell>
    <ui:textfield size="30" beanId="emailTF"/>
</ui:tablecell>
</ui:tablerow>
<ui:tablerow>
<ui:tablecell>
    <ui:text key="LOCALE"/>
</ui:tablecell>
<ui:tablecell>
    <ui:image src="<%= flag %>" alt="<%= locale.getDisplayLanguage() %>"
              title="<%= locale.getDisplayLanguage() %>"/>
    <ui:listbox beanId="userlocale"/>
</ui:tablecell>
</ui:tablerow>
<ui:tablerow>
<ui:tablecell valign="top">
    <ui:text key="TIMEZONE"/>
</ui:tablecell>
<ui:tablecell>
    <ui:listbox beanId="timezones"/>
</ui:tablecell>
</ui:tablerow>
<ui:tablerow>
<ui:tablecell>
    <ui:text key="LAYOUT_SELECT_THEME"/>
</ui:tablecell>
<ui:tablecell>
    <ui:listbox beanId="themeLB"/>
</ui:tablecell>
</ui:tablerow>


</ui:frame>

<ui:frame>
<ui:tablerow>
<ui:tablecell align="center">
    <ui:actionsubmit action="doSaveAll" key="SAVE"/>
</ui:tablecell>
</ui:tablerow>
</ui:frame>

</ui:form>

</div>