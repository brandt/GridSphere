<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

    <ui:panel>
        <ui:form>
        <ui:frame beanId="alert"/>

        <ui:frame>
            <ui:tablerow>
                <ui:tablecell width="100">
                    <ui:text value="File to display: "/>
                </ui:tablecell>
                <ui:tablecell width="300">
                    <ui:listbox beanId="filelist"/>
                </ui:tablecell>
                <ui:tablecell/>
            </ui:tablerow>
        </ui:frame>

        <ui:frame>
            <ui:tablerow>
                <ui:tablecell width="100">
                    <ui:text value="Title to display: "/>
                </ui:tablecell>
                <ui:tablecell width="300">
                    <ui:textfield beanId="displayTitle"/>
                </ui:tablecell>
                <ui:tablecell/>
            </ui:tablerow>
        </ui:frame>

        <ui:frame>
            <ui:tablerow>
                <ui:tablecell/>
                <ui:tablecell>
                    <ui:actionsubmit action="setEditDisplayFile" value="Ok"/>
                    <ui:actionsubmit action="cancelEditFile" value="Cancel"/>
                </ui:tablecell>
                <ui:tablecell/>
            </ui:tablerow>
        </ui:frame>
        </ui:form>
    </ui:panel>
