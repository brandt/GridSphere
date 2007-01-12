<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>


<ui:messagebox beanId="msg"/>

<ui:form>

    <ui:hiddenfield beanId="uuid"/>

    <p/>

    <ui:table>
        <ui:tablerow>
            <ui:tablecell valign="top">
                <ui:group key="CM_DOCUMENT">
                    <ui:text key="CM_DOCUMENT_TITLE"/>
                    <ui:textfield beanId="title"/>
                </ui:group>
                <ui:group key="CM_AVAILDOCUMENTS">
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
                    <ui:actionsubmit action="saveDocument" key="CM_CREATEUPDATEDOCUMENT"/>
                </ui:group>
            </ui:tablecell>
            <ui:tablecell valign="top">
                <ui:group label="Content">
                    <ui:richtexteditor beanId="content" cols="80" rows="30"/>
                </ui:group>
            </ui:tablecell>
        </ui:tablerow>
    </ui:table>
</ui:form>

