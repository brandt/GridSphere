<%@ page import="java.util.Iterator,
                 org.gridlab.gridsphere.portlet.PortletGroup"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>

<jsp:useBean id="groupList" class="java.util.List" scope="request"/>
<jsp:useBean id="groupDescs" class="java.util.List" scope="request"/>

<ui:form>
<ui:panel>

    <ui:frame>
        <ui:tablerow header="true">
            <ui:tablecell>
                <ui:text value="Group Name"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:text value="Group Description"/>
            </ui:tablecell>
        </ui:tablerow>

<%
                Iterator groupIterator = groupList.iterator();
                Iterator descsIterator = groupDescs.iterator();
                while ((groupIterator.hasNext() && descsIterator.hasNext())) {
                    // Get next user
                    PortletGroup group = (PortletGroup)groupIterator.next();
                    String desc = (String)descsIterator.next();
%>
                <ui:tablerow>
                        <ui:tablecell>
                            <ui:actionlink action="doViewViewGroup" value="<%=group.getName() %>">
                                <ui:actionparam name="groupID" value="<%= group.getID() %>"/>
                            </ui:actionlink>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text value="<%= desc %>"/>
                        </ui:tablecell>
                </ui:tablerow>

<%              } %>
    </ui:frame>

</ui:panel>
</ui:form>
