<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<ui:form>
    <p>
        <ui:hiddenfield beanId="groupName"/>
        <ui:hiddenfield beanId="userName"/>
    </p>
    <ui:panel>

        <ui:frame>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:text key="USERNAME"/>
                    <ui:text beanId="userName"/>
                </ui:tablecell>
            </ui:tablerow>
        </ui:frame>

        <ui:frame>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:text key="GROUP_PORTLET_GROUP"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:text beanId="groupLabel"/>
                </ui:tablecell>
            </ui:tablerow>
        </ui:frame>

        <ui:frame>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:actionsubmit action="doViewConfirmChangeRole" key="SAVE"/>
                    <ui:actionsubmit action="doViewViewGroup" key="CANCEL"/>
                </ui:tablecell>
            </ui:tablerow>
        </ui:frame>


    </ui:panel>
</ui:form>


