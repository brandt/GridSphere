<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<jsp:useBean id="logintime" class="java.lang.String" scope="request"/>
<jsp:useBean id="username" class="java.lang.String" scope="request"/>

<ui:form>
<ui:panel>

Edit Settings for <b><%= username %></b>

<br>
<b>Last Login Time: </b><%= logintime %>
<ui:frame>
    <ui:tablerow>
        <ui:tablecell><ui:text value="Username: "/></ui:tablecell>
        <ui:tablecell><ui:textfield beanId="userName"/></ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell><ui:text value="Full Name: "/></ui:tablecell>
        <ui:tablecell><ui:textfield beanId="fullName"/></ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell><ui:text value="Email Address: "/></ui:tablecell>
        <ui:tablecell><ui:textfield beanId="email"/></ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell><ui:text value="Organization: "/></ui:tablecell>
        <ui:tablecell><ui:textfield beanId="organization"/></ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell><ui:text value="Preferred Locale: "/></ui:tablecell>
        <ui:tablecell><ui:listbox beanId="userLocale"/></ui:tablecell>
    </ui:tablerow>
</ui:frame>

<ui:frame beanId="groupsFrame"/>

<ui:frame>
<ui:actionsubmit action="doSaveUser" value="Save Changes"/>
</ui:frame>
</ui:panel>

</ui:form>

