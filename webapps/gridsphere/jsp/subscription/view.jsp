<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<ui:form>

<ui:panel>
<ui:frame>
    <ui:tablerow>
        <ui:tablecell>
            <ui:text key="SUBSCRIPTION_START"/>
            <ui:actionlink label="layout" key="SUBSCRIPTION_MIDDLE"/>
            <ui:text key="SUBSCRIPTION_END"/>
        </ui:tablecell>
    </ui:tablerow>
</ui:frame>

</ui:panel>

<ui:panel beanId="panel"/>

<ui:panel>
    <ui:actionsubmit action="applyChanges" key="SUBSCRIPTION_APPLY"/>
</ui:panel>
</ui:form>