<%@ page import="java.util.Iterator, java.util.List,
                 org.gridlab.gridsphere.portlet.PortletRequest"%>
<%@ page import="org.gridlab.gridsphere.services.core.security.group.impl.UserGroup"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>

<% PortletRequest pReq = (PortletRequest)pageContext.getAttribute("portletRequest"); %>

<% List groupEntryList = (List)request.getAttribute("groupEntryList"); %>

<ui:messagebox beanId="msg"/>

<h3><ui:text key="GROUP_MANAGE_MSG" style="nostyle"/>&nbsp; <ui:text beanId="groupName" style="nostyle"/></h3>

<% if (groupEntryList.size() > 0) { %>
<h3><ui:text key="GROUP_MODIFY_USERS" style="nostyle"/></h3>
<ui:form>
<ui:group>

<ui:hiddenfield beanId="groupID"/>

<ui:frame>

        <ui:tablerow header="true">
            <ui:tablecell>
                <ui:text key="DELETE"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text key="USERNAME"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text key="FULLNAME"/>
            </ui:tablecell>

        </ui:tablerow>

<%  Iterator groupIterator = groupEntryList.iterator();
    while (groupIterator.hasNext()) {
        UserGroup groupEntry = (UserGroup)groupIterator.next();
%>
                <ui:tablerow>
                        <ui:tablecell>
                            <ui:checkbox beanId="groupEntryIDCB" name="<%= groupEntry.getID() %>" value="<%= groupEntry.getID() %>"/>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text value="<%= groupEntry.getUser().getUserName() %>"/>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text value="<%= groupEntry.getUser().getFullName() %>"/>
                        </ui:tablecell>
                </ui:tablerow>

     <%              } %>

      </ui:frame>

    <ui:frame>
                <ui:tablerow>
                    <ui:tablecell>
                        <ui:actionsubmit action="doViewViewGroup" key="DELETE"/>
                    </ui:tablecell>
                </ui:tablerow>
            </ui:frame>
            </ui:group>
            </ui:form>

     <%              } %>


    <h3><ui:text key="GROUP_ADD_USERS" style="nostyle"/></h3>
    
    <ui:form>
    <ui:group>
    <p>
    <ui:hiddenfield beanId="groupID"/>
    </p>
    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text key="GROUP_ADMIN_SELECT_USER"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>

    <ui:frame>
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
    <ui:frame>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:actionsubmit action="doViewViewGroup" key="OK"/>
                </ui:tablecell>
            </ui:tablerow>
        </ui:frame>
    </ui:group>
    </ui:form>
     

<ui:form>
    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionsubmit action="doViewListGroup" key="GROUP_LIST_GROUPS"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>
</ui:form>
