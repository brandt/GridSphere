
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<ui:form>
    <ui:group key="LOGIN_CONFIG_SMTP">
        <ui:frame beanId="serviceframe"/>

        <ui:frame>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:actionsubmit action="doSaveValues" key="APPLY"/>
                </ui:tablecell>
            </ui:tablerow>
        </ui:frame>

    </ui:group>
</ui:form>



