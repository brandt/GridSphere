<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<ui:form>
<ui:panel>

<ui:frame beanId="msgFrame"/>

<ui:text key="LAYOUT_CONFIG_THEMES"/>
<ui:frame>
    <ui:tablerow>
        <ui:tablecell><ui:text key="LAYOUT_SUPPORTED_THEMES"/>&nbsp;<ui:textfield beanId="themesTF"/></ui:tablecell>
    </ui:tablerow>
</ui:frame>

<ui:frame>
    <ui:tablerow>
        <ui:tablecell>
            <ui:actionsubmit action="doSaveThemes" key="LAYOUT_APPLY"/>
        </ui:tablecell>
    </ui:tablerow>
</ui:frame>

</ui:panel>
</ui:form>