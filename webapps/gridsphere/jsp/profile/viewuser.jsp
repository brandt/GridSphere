<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<jsp:useBean id="logintime" class="java.lang.String" scope="request"/>
<jsp:useBean id="username" class="java.lang.String" scope="request"/>
<jsp:useBean id="mylocale" class="java.lang.String" scope="request"/>
<ui:panel>

<ui:text key="PROFILE_VIEW"/>  <b><%= username %></b>

<br>
<ui:text key="PROFILE_LASTLOGIN"/>  <b><%= logintime %></b>
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
        <ui:tablecell><ui:textfield beanId="email"/></ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell><ui:text key="ORGANIZATION"/></ui:tablecell>
        <ui:tablecell><ui:textfield beanId="organization"/></ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell><ui:text key="PROFILE_LOCALE"/></ui:tablecell>
        <ui:tablecell><ui:listbox beanId="userLocale"/></ui:tablecell>
    </ui:tablerow>
</ui:frame>

<ui:frame beanId="messagingFrame"/>

<ui:frame beanId="groupsFrame"/>

</ui:panel>

<ui:actionlink portletMode="edit" value="EDIT"/> <ui:text key="PROFILE_SETTINGS"/>

