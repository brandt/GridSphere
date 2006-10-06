<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>

<ui:messagebox beanId="msg"/>

<ui:form>

    <ui:text value="Select node:"/>
    <ui:listbox beanId="nodelist"/>

    <ui:actionsubmit action="showNode" value="Show Node"/>
    <ui:actionsubmit action="removeNode" value="Remove Node"/>

    <p/>

    <ui:table>
        <ui:tablerow>
            <ui:tablecell valign="top">
                <ui:text value="ID"/>
                <br/>
                <ui:textfield beanId="nodeid"/>
                <br/>
                <ui:text value="RenderKit"/>
                <br/>
                <ui:listbox beanId="renderkit"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="Content"/>
                <br/>
                <ui:textarea beanId="nodecontent" rows="30" cols="80"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:table>

    <ui:actionsubmit action="createNode" value="Create/Update Node"/>


</ui:form>

