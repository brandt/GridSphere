<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>
<ui:form>
<ui:hiddenfield beanId="groupID"/>
<ui:hiddenfield beanId="groupEntryID"/>
<ui:panel>

    <ui:frame>
         <ui:tablerow>
                <ui:tablecell width="200">
                    <ui:text key="GROUP_NAME"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:text beanId="groupName"/>
                </ui:tablecell>
          </ui:tablerow>

            <ui:tablerow>
                <ui:tablecell>
                    <ui:text key="GROUP_LABEL"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:text beanId="groupLabel"/>
                </ui:tablecell>
            </ui:tablerow>
    </ui:frame>

    <ui:frame>
            <ui:tablerow header="true">
                <ui:tablecell>
                    <ui:text key="USERNAME"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:text key="FULLNAME"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:text key="GROUP_ROLEIN_GROUP"/>
                </ui:tablecell>
            </ui:tablerow>

            <ui:tablerow>
                <ui:tablecell>
                    <ui:text beanId="groupEntryUserName"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:text beanId="groupEntryUserFullName"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:text beanId="groupEntryRole"/>
                </ui:tablecell>
            </ui:tablerow>

        </ui:frame>

    <ui:frame>
            <ui:tablerow>
                <ui:tablecell>
                        <ui:actionsubmit action="doViewViewGroup" key="GROUP_GO_BACK"/>
                        <ui:actionsubmit action="doViewEditGroupEntry" key="GROUP_ROLE_CHANGE"/>
                </ui:tablecell>
            </ui:tablerow>
        </ui:frame>
        
</ui:panel>
</ui:form>
