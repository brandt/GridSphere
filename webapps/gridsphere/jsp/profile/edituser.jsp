<%@ page import="java.util.Locale"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<jsp:useBean id="logintime" class="java.lang.String" scope="request"/>
<jsp:useBean id="username" class="java.lang.String" scope="request"/>

<% Locale locale = (Locale)request.getAttribute("locale"); %>

<% String flag = "/gridsphere/html/flags/"+locale.getLanguage() +".gif"; %>

<ui:messagebox beanId="msg"/>

<table>
<tr>
<td>

<ui:form>


<h3><ui:text key="PROFILE_EDIT" style="nostyle"/>&nbsp;&nbsp;<%= username %></h3>
<ui:group>
<ui:text key="PROFILE_LASTLOGIN"/>&nbsp;&nbsp;<b><%= logintime %></b>

<ui:frame beanId="errorFrame"/>

<ui:panel cols="50%, 50%">
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
</ui:frame>
<ui:frame>
     <ui:tablerow>
        <ui:tablecell><ui:text key="LOCALE"/></ui:tablecell>
        <ui:tablecell><ui:image src="<%= flag %>" alt="<%= locale.getDisplayLanguage() %>" title="<%= locale.getDisplayLanguage() %>"/><ui:listbox beanId="userlocale"/></ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell><ui:text key="TIMEZONE"/></ui:tablecell>
        <ui:tablecell><ui:listbox beanId="timezones"/></ui:tablecell>
    </ui:tablerow>
</ui:frame>
</ui:panel>
<p>
<ui:actionsubmit action="doSaveUser" key="SAVE"/>
</ui:group>
</ui:form>

<ui:form>

<h3><ui:text value="Configure messaging service" style="nostyle"/></h3>
<ui:group>
<ui:frame beanId="messagingFrame"/>
</ui:group>
</ui:form>


</td>
<td>

<ui:form>

<h3><ui:text value="Configure group membership" style="nostyle"/></h3>
<ui:group>
<ui:frame beanId="groupsFrame"/>
<p>
<ui:actionsubmit action="doSaveGroups" key="SAVE"/>
</ui:group>
</ui:form>

<ui:form>

<h3><ui:text value="Update password" style="nostyle"/></h3>
<ui:group>
<ui:frame width="50%">
    <ui:tablerow>
        <ui:tablecell><ui:text key="ORIG_PASSWORD"/></ui:tablecell>
        <ui:tablecell><ui:password beanId="origPassword"/></ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell><ui:text key="PASSWORD"/></ui:tablecell>
        <ui:tablecell><ui:password beanId="password"/></ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell><ui:text key="CONFIRM_PASS"/></ui:tablecell>
        <ui:tablecell><ui:password beanId="confirmPassword"/></ui:tablecell>
    </ui:tablerow>
</ui:frame>
<p>
<ui:actionsubmit action="doSavePass" key="SAVE"/>
</ui:group>
</ui:form>

</td>
</tr>
</table>