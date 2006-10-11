<%@ page import="java.util.Iterator, java.util.List, org.gridsphere.services.core.user.User" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>

<% List usersInGroup = (List) request.getAttribute("usersInGroup"); %>

<ui:messagebox beanId="msg"/>

<h3><ui:text key="GROUP_MANAGE_MSG" style="nostyle"/>&nbsp; <ui:text beanId="groupNameText" style="nostyle"/></h3>

<% if (usersInGroup.size() > 0) { %>
<h3><ui:text key="GROUP_MODIFY_USERS" style="nostyle"/></h3>
<ui:form>
    <ui:group>

        <ui:hiddenfield beanId="groupName"/>

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

            <% Iterator usersIterator = usersInGroup.iterator();
                while (usersIterator.hasNext()) {
                    User user = (User) usersIterator.next();
            %>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:checkbox beanId="userCB" name="<%= user.getUserName() %>" value="<%= user.getUserName() %>"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:text value="<%= user.getUserName() %>"/>
                </ui:tablecell>
                <ui:tablecell>
                    <ui:text value="<%= user.getFullName() %>"/>
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
            <ui:hiddenfield beanId="groupName"/>
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
