<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

    <ui:panel>
        <ui:form>
        <ui:frame beanId="alert"/>
        <ui:frame>
            <ui:tablerow>
                <ui:tablecell width="100">
                    <ui:text key="BANNER_TITLE"/>&nbsp;
                </ui:tablecell>
                <ui:tablecell width="300">
                    <ui:textfield beanId="displayTitle"/>
                </ui:tablecell>
                <ui:tablecell/>
            </ui:tablerow>
        </ui:frame>

        <ui:frame>
            <ui:tablerow>
                <ui:tablecell width="100">
                    <ui:text key="BANNER_FILE"/>
                </ui:tablecell>
                <ui:tablecell width="300">
                    <ui:textfield beanId="displayFile"/>
                </ui:tablecell>
                <ui:tablecell/>
            </ui:tablerow>
        </ui:frame>

        <ui:frame>
            <ui:tablerow>
                <ui:tablecell/>
                <ui:tablecell>
                    <ui:actionsubmit action="setConfigureDisplayFile" key="OK"/>
                    <ui:actionsubmit action="cancelEditFile" key="CANCEL"/>
                </ui:tablecell>
                <ui:tablecell/>
            </ui:tablerow>
        </ui:frame>
        </ui:form>


    </ui:panel>
