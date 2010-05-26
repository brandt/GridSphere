<%@ page import="org.gridsphere.services.core.jcr.ContentDocument" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>

<% List<ContentDocument> contentDocs = (List<ContentDocument>) request.getAttribute("contentDocs"); %>

<ui:messagebox beanId="msg"/>

<ui:form>

    <ui:hiddenfield beanId="uuid"/>

    <p/>

    <ui:table>
        <ui:tablerow>
            <ui:tablecell valign="top">
                <%--  <ui:group key="CM_DOCUMENT">
                  <ui:text key="CM_DOCUMENT_TITLE"/>
                  <ui:textfield beanId="title" maxlength="40"/>
              </ui:group>  --%>
                <ui:group key="CM_AVAILDOCUMENTS">

                    <ui:table>
                        <ui:tablerow header="true">
                            <ui:tablecell>
                                <ui:text key="DELETE"/>
                            </ui:tablecell>
                            <ui:tablecell>
                                <ui:text key="EDIT"/>
                            </ui:tablecell>
                        </ui:tablerow>

                        <% for (ContentDocument content : contentDocs) { %>
                        <ui:tablerow>
                            <ui:tablecell>
                                <ui:checkbox name="nodeCB" value="<%= content.getUuid() %>"/>
                            </ui:tablecell>
                            <ui:tablecell>
                                <ui:renderlink render="showNode" value="<%= content.getTitle() %>">
                                    <ui:param name="nodeId" value="<%= content.getUuid() %>"/>
                                </ui:renderlink>
                            </ui:tablecell>
                        </ui:tablerow>
                        <% } %>

                    </ui:table>

                    <%-- <ui:listbox beanId="nodelist" size="20"/>
                    <br/>
                    <ui:actionsubmit action="showNode" key="CM_SHOWDOCUMENT"/>   --%>
                    <br/>
                    <ui:actionsubmit action="removeNode" key="DELETE"/>
                    <ui:renderlink cssStyle="font-weight: bold; text-decoration: underline;" render="showNode"
                                   value="New Document"/>
                    <%--   <br/>
                   <ui:actionsubmit action="clearEditor" key="CM_CLEAREDITOR"/>
                   <br/> --%>
                </ui:group>
            </ui:tablecell>
            <% if (request.getAttribute("showContent") != null) { %>
            <ui:tablecell valign="top">
                <ui:group key="CM_DOCUMENT">
                    <ui:text key="CM_DOCUMENT_TITLE"/>
                    <ui:textfield beanId="title" maxlength="40"/>
                    <ui:actionsubmit action="saveDocument" key="CM_CREATEUPDATEDOCUMENT"/>
                    <ui:richtexteditor beanId="content" cols="80" rows="30"/>
                </ui:group>
            </ui:tablecell>
            <% } %>
        </ui:tablerow>
    </ui:table>
</ui:form>

<ui:renderlink cssStyle="font-weight: bold; text-decoration: underline;" portletMode="EDIT" value="Content Settings"/>
