<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<ui:form>
<ui:hiddenfield beanId="groupID"/>
<ui:panel>

    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionsubmit action="doViewConfirmAddGroupEntry" key="GROUP_ADD_USER"/>
                <ui:actionsubmit action="doViewCancelAddGroupEntry" key="GROUP_CANCEL_ADD"/>
            </ui:tablecell>
        </ui:tablerow>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text key="GROUP_ADMIN_SELECT_USER"/>
                <ui:text beanId="groupLabel"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>

    <ui:frame beanId="errorMessage"/>

    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text key="GROUP_PORTLET_GROUP"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text beanId="groupLabel"/>
            </ui:tablecell>
        </ui:tablerow>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text key="GROUP_ADD_USER"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:listbox beanId="usersNotInGroupList"/>
            </ui:tablecell>
        </ui:tablerow>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text key="GROUP_ROLEIN_GROUP"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:listbox beanId="groupEntryRoleLB"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>

    </ui:panel>
</ui:form>


