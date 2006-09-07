<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<ui:messagebox beanId="msg"/>

<ui:form>

    <h3><ui:text value="Update password" style="nostyle"/></h3>

    <p>
        <ui:hiddenfield beanId="reqid"/>
    </p>
    <ui:group>
        <ui:frame width="50%">
            <ui:tablerow>
                <ui:tablecell><ui:text key="PASSWORD"/></ui:tablecell>
                <ui:tablecell><ui:password beanId="password"/></ui:tablecell>
            </ui:tablerow>
            <ui:tablerow>
                <ui:tablecell><ui:text key="CONFIRM_PASS"/></ui:tablecell>
                <ui:tablecell><ui:password beanId="confirmPassword"/></ui:tablecell>
            </ui:tablerow>
        </ui:frame>
        <p>
            <ui:actionsubmit action="doSavePass" key="SAVE"/>
        </p>
    </ui:group>

</ui:form>