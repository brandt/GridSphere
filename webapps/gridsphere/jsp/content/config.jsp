<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>

<ui:messagebox beanId="msg"/>


<ui:form>


    <ui:group key="CM_BACKUP">
        <ui:text key="CM_BACKUP_HELP"/>
        <br/>
        <ui:actionsubmit action="backupContent" key="CM_BACKUPCONTENT"/>
    </ui:group>

    <ui:group key="CM_IMPORT">
        <ui:text key="CM_IMPORT_HELP"/>
        <br/>
        <ui:listbox beanId="filelist" size="20"/>
        <br/>
        <ui:actionsubmit action="importContent" key="CM_IMPORTCONTENT"/>
    </ui:group>

</ui:form>