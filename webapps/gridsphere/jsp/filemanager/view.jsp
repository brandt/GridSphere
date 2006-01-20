<%@ page import="java.util.Locale" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<ui:panel>
    <ui:frame beanId="errorFrame"/>

    <ui:fileform action="uploadFile">
        <ui:frame>
            <ui:tablerow>
                <ui:tablecell width="100">
                    <ui:text key="FILE_NAME"/>
                </ui:tablecell>
                <ui:tablecell width="60">
                    <ui:fileinput beanId="userfile" size="20" maxlength="20"/>
                </ui:tablecell>
                <ui:tablecell/>
            </ui:tablerow>
        </ui:frame>

        <ui:frame>
            <ui:tablerow>
                <ui:tablecell width="100">
                    <ui:actionsubmit action="uploadFile" key="FILE_UPLOAD"/>
                </ui:tablecell>
                <ui:tablecell/>
            </ui:tablerow>
        </ui:frame>
    </ui:fileform>

    <ui:form>
        <ui:frame>
            <ui:tablerow>
                <ui:tablecell width="300">
                    <ui:text key="FILE_LIST"/>
                </ui:tablecell>
            </ui:tablerow>
            <ui:tablerow>
                <ui:tablecell width="300">
                    <ui:listbox beanId="filelist"/>
                </ui:tablecell>
            </ui:tablerow>
        </ui:frame>

        <ui:frame>
            <ui:tablerow>
                <ui:tablecell/>
                <ui:tablecell>
                    <ui:actionsubmit action="deleteFile" key="FILE_DELETE"/>
                    <ui:actionsubmit action="editFile" key="FILE_EDIT"/>
                    <ui:actionsubmit action="downloadFile" key="FILE_DOWNLOAD"/>
                </ui:tablecell>
                <ui:tablecell/>
            </ui:tablerow>
        </ui:frame>
    </ui:form>
</ui:panel>
