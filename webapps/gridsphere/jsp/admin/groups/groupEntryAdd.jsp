<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<ui:form>
<ui:hiddenfield beanId="groupID"/>
<ui:panel>

    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionsubmit action="doViewConfirmAddGroupEntry" value="Add User"/>
                <ui:actionsubmit action="doViewCancelAddGroupEntry" value="Cancel Add"/>
            </ui:tablecell>
        </ui:tablerow>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text value="Select the user you would like to add and their role in: "/>
                <ui:text beanId="groupLabel"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>

    <ui:frame beanId="errorMessage"/>

    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text value="Portlet Group:"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text beanId="groupLabel"/>
            </ui:tablecell>
        </ui:tablerow>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text value="User To Add:"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:listbox beanId="usersNotInGroupList"/>
            </ui:tablecell>
        </ui:tablerow>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text value="Role In Group:"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:listbox beanId="groupEntryRole"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>

    </ui:panel>
</ui:form>


