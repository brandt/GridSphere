<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<ui:form>
    <p>
        <ui:hiddenfield beanId="fileName"/>
    </p>
    <ui:panel>

        <ui:frame beanId="editError"/>

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
                    <ui:actionsubmit action="deleteFile" key="FILE_DELETE"/>
                    <ui:actionsubmit action="saveFile" key="FILE_SAVE"/>
                    <ui:actionsubmit action="cancelEdit" key="CANCEL"/>
                </ui:tablecell>
                <ui:tablecell/>
            </ui:tablerow>
        </ui:frame>

    </ui:panel>
</ui:form>
