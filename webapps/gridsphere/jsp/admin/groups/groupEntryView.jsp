<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<ui:form>
<ui:hiddenfield bean="groupID"/>
<ui:hiddenfield bean="groupEntryID"/>
<ui:panel>

    <ui:frame>
        <ui:tablerow>
            <ui:tablecell cssStyle="portlet-frame-actions">
                    <ui:actionsubmit action="doViewViewGroup" value="Back To Group"/>
                    <ui:actionsubmit action="doViewEditGroupEntry" value="Change User Role"/>
                    <ui:actionsubmit action="doViewDeleteGroupEntry" value="Remove User From Group"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>

    <ui:frame>
         <ui:tablerow>
                <ui:tablecell cssStyle="portlet-frame-label" width="200">
                Group Name:
                </ui:tablecell>
                <ui:tablecell>
                    <ui:text beanId="groupName"/>
                </ui:tablecell>
          </ui:tablerow>

            <ui:tablerow>
                <ui:tablecell>
                Group Label:
                </ui:tablecell>
                <ui:tablecell>
                    <ui:text beanId="groupLabel"/>
                </ui:tablecell>
            </ui:tablerow>
    </ui:frame>

    <ui:frame>
            <ui:tablerow header="true">
                <ui:tablecell>
                User Name:
                </ui:tablecell>
                <ui:tablecell>
                Full Name:
                </ui:tablecell>
                <ui:tablecell>
                Role In Group:
                </ui:tablecell>
            </ui:tablerow>

            <ui:tablerow>
                <ui:tablecell>
                    <gs:text beanId="groupEntryUserName"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:text beanId="groupEntryUserFullName"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:text beanId="groupEntryRole"/>
                </ui:tablecell>
            </ui:tablerow>

        </ui:frame>

</ui:panel>
</ui:form>
