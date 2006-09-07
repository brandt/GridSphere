<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>


<ui:messagebox beanId="msg"/>

<h3><ui:text key="ROLE_EDIT_MSG" style="nostyle"/></h3>

<ui:form>
    <ui:hiddenfield beanId="roleHF"/>
    <ui:hiddenfield beanId="isNewRoleHF"/>
    <ui:frame>

        <ui:tablerow>
            <ui:tablecell width="200">
                <ui:text key="ROLENAME"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:textfield beanId="roleNameTF"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell width="200">
                <ui:text key="ROLEDESC"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:textfield beanId="roleDescTF"/>
            </ui:tablecell>
        </ui:tablerow>

    </ui:frame>

    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionsubmit action="doSaveRole" key="ROLE_SAVE"/>
                <ui:actionsubmit action="doListRoles" key="CANCEL"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>

</ui:form>