<%@ page import="org.gridlab.gridsphere.tmf.config.User"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>


<portletAPI:init/>


<ui:form>

    <ui:hiddenfield beanId="Hservice"/>
    <ui:hiddenfield beanId="Huserid"/>

    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text value="Username "/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:textfield beanId="username"/>
            </ui:tablecell>
        </ui:tablerow>

    </ui:frame>

<ui:actionsubmit action="saveServiceSettings" value="Save settings"/>
<ui:actionsubmit action="deleteServiceSettings" value="Delete settings"/>
<ui:actionsubmit action="cancelEdit" value="Cancel"/>


</ui:form>
