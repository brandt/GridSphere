<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

    <ui:panel>
        <ui:errorframe beanId="errorFrame"/>

        <ui:fileform action="uploadFile">
        <ui:frame>
            <ui:tablerow>
                <ui:tablecell width="100">
                    <ui:text value="File: "/>
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
                    <ui:actionsubmit action="uploadFile" value="Upload file"/>
                </ui:tablecell>
                <ui:tablecell/>
            </ui:tablerow>
        </ui:frame>
        </ui:fileform>

        <ui:form>
        <ui:frame>
            <ui:tablerow>
                <ui:tablecell width="300">
                    <ui:text value="List current files:"/>
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
                    <ui:actionsubmit action="deleteFile" value="Delete file"/>
                    <ui:actionsubmit action="editFile" value="Edit file"/>
                    <ui:actionsubmit action="downloadFile" value="Download file"/>
                </ui:tablecell>
                <ui:tablecell/>
            </ui:tablerow>
        </ui:frame>
        </ui:form>
    </ui:panel>
