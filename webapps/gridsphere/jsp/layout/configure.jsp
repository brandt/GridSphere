<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<ui:form>
<ui:panel>

<ui:frame beanId="msgFrame"/>

Configure supported themes by specifying the css directory name in the comma separated list:
<ui:frame>
    <ui:tablerow>
        <ui:tablecell><ui:text value="Supported themes: "/><ui:textfield beanId="themesTF"/></ui:tablecell>
    </ui:tablerow>
</ui:frame>

<ui:frame>
    <ui:tablerow>
        <ui:tablecell>
            <ui:actionsubmit action="doSaveThemes" value="Save Changes"/>
        </ui:tablecell>
    </ui:tablerow>
</ui:frame>

</ui:panel>
</ui:form>