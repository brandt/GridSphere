<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<ui:form>
    <ui:hiddenfield beanId="fileName"/>
    <ui:panel>

        <ui:errorframe beanId="editError"/>

        <ui:frame>
        <ui:tablerow>
            <ui:tablecell width="50%">
                <ui:text beanId="msg"/>
            </ui:tablecell>
            <ui:tablecell/>
        </ui:tablerow>
        </ui:frame>

        <ui:frame>
            <ui:tablerow>
                <ui:tablecell width="50%">
                    <ui:textarea beanId="fileTextArea" rows="10" cols="100"/>
                </ui:tablecell>
            </ui:tablerow>
        </ui:frame>

        <ui:frame>
            <ui:tablerow>
                <ui:tablecell/>
                <ui:tablecell>
                    <ui:actionsubmit action="deleteFile" value="Delete file"/>
                    <ui:actionsubmit action="saveFile" value="Save file"/>
                    <ui:actionsubmit action="cancelEdit" value="Cancel"/>
                </ui:tablecell>
                <ui:tablecell/>
            </ui:tablerow>
        </ui:frame>

    </ui:panel>
</ui:form>
