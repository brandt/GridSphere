<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<ui:frame>
    <ui:tablerow>
        <ui:tablecell width="5%">
            <ui:checkbox beanId="ldapCheck" name="ldapModule" value="Hello" selected="<%= active %>"/>
        </ui:tablecell>
        <ui:tablecell width="45%">
            <ui:text key="LOGIN_LDAP_MODULE"/>
        </ui:tablecell>
        <ui:tablecell width="50%">
            <ui:actionlink action="configLdapModule" key="LOGIN_MODULE_ACTION"/>
        </ui:tablecell>
    </ui:tablerow>
</ui:frame>