<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<jsp:useBean id="logintime" class="java.lang.String" scope="request"/>
<jsp:useBean id="username" class="java.lang.String" scope="request"/>

<ui:form>
<ui:panel>

<ui:text key="PROFILE_EDIT"/>  <b><%= username %></b>

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

<ui:frame beanId="groupsFrame"/>

<ui:frame>
<ui:actionsubmit action="doSaveUser" key="PROFILE_SAVE"/>
</ui:frame>
</ui:panel>

</ui:form>

