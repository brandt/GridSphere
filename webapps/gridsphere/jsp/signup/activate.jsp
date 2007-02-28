<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<ui:form>

    <ui:messagebox value="Account has been activated"/>


    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:rendersubmit render="doNewUser" key="OK"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>

</ui:form>

