<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>

<ui:messagebox beanId="msg"/>

<ui:form>

    <p/>

    <ui:table>
        <ui:tablerow>
            <ui:tablecell valign="top">
                <ui:group key="CM_DOCUMENT" cssStyle="border: 1px dashed #c7c7c7;">
                    <ui:text key="CM_DOCUMENT_TITLE"/>
                    <ui:textfield beanId="nodeid"/>
                </ui:group>
                <ui:group key="CM_AVAILDOCUMENTS" cssStyle="text-align:center; border: 1px dashed #c7c7c7;">
                    <ui:listbox beanId="nodelist" size="20"/>
                    <br/>
                    <ui:actionsubmit action="showNode" key="CM_SHOWDOCUMENT"/>
                    <br/>
                    <ui:actionsubmit action="removeNode" key="CM_DELETEDOCUMENT"/>
                    <br/>
                    <ui:actionsubmit action="clearEditor" key="CM_CLEAREDITOR"/>
                    <br/>
                </ui:group>
                <ui:group cssStyle="border: none; text-align:center">
                    <ui:actionsubmit action="createNode" key="CM_CREATEUPDATEDOCUMENT"/>
                </ui:group>
            </ui:tablecell>
            <ui:tablecell>
                <ui:group label="Content" cssStyle="border: 1px dashed #c7c7c7;">
                    <ui:richtexteditor beanId="nodecontent" cols="80" rows="30"/>
                </ui:group>
            </ui:tablecell>
        </ui:tablerow>
    </ui:table>
</ui:form>

