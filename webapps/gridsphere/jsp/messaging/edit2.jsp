<%@ page import="org.gridlab.gridsphere.tmf.config.TmfUser"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>


<portletAPI:init/>


<ui:form>

    <ui:hiddenfield beanId="Hservice"/>
    <ui:hiddenfield beanId="Huserid"/>

    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text key="MESSAGING_USERNAME"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:textfield beanId="username"/>
            </ui:tablecell>
        </ui:tablerow>

    </ui:frame>

<ui:actionsubmit action="saveServiceSettings" key="MESSAGING_SAVESETTINGS"/>
<ui:actionsubmit action="deleteServiceSettings" key="MESSAGING_DELETESETTINGS"/>
<ui:actionsubmit action="cancelEdit" key="MESSAGING_CANCEL"/>


</ui:form>
