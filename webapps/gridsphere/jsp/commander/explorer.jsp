<%@ page import="java.util.Locale,
                 org.gridlab.gridsphere.services.core.secdir.ResourceInfo,
                 java.util.Date"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<jsp:useBean id="userData" class="org.gridlab.gridsphere.portlets.core.file.UserData" scope="request" />
<jsp:useBean id="leftEditURIs" class="java.util.List" scope="request"/>
<jsp:useBean id="rightEditURIs" class="java.util.List" scope="request"/>

<% if(!userData.getCorrect().booleanValue()){ %>
<ui:panel width="100%">
    <ui:frame beanId="errorFrame"/>
</ui:panel>
<% }else{ %>
<ui:table width="100%">
<ui:tablerow>
    <ui:tablecell width="50%" valign="top">
    <ui:table width="100%">
        <ui:tablerow header="true">
            <ui:tablecell >
                 <%=userData.getPath("left")%>
            </ui:tablecell>
        </ui:tablerow>
    <ui:fileform action="uploadFileLeft">
        <ui:tablerow>
            <ui:tablecell>
            <ui:table width="100%">
            <ui:tablerow>
                <ui:tablecell width="100">
                    <ui:text key="COMMANDER_FILE_NAME"/>
                </ui:tablecell>
                <ui:tablecell width="60">
                    <ui:fileinput beanId="userfileLeft" size="20" maxlength="20"/>
                </ui:tablecell>
                <ui:tablecell width="100">
                    <ui:actionsubmit action="uploadFileLeft" key="COMMANDER_FILE_UPLOAD"/>
                </ui:tablecell>
            </ui:tablerow>
            </ui:table>
            </ui:tablecell>
        </ui:tablerow>
    </ui:fileform>
    <ui:form>
    <ui:hiddenfield name="side" value="left"/>
        <ui:tablerow>
            <ui:tablecell>
            <ui:table width="100%">
            <ui:tablerow>
                <ui:tablecell width="14%">
                    <ui:text key="COMMANDER_DIR_FILE_NAME"/>
                </ui:tablecell>
                <ui:tablecell width="20%">
                    <ui:textfield beanId="resourceNameleft"/>
                </ui:tablecell>
                <ui:tablecell width="33%">
                    <ui:actionsubmit action="newDirectory" key="COMMANDER_MKDIR"/>
                </ui:tablecell>
               <ui:tablecell width="33%">
                    <ui:actionsubmit action="newFile" key="COMMANDER_TOUCH"/>
                </ui:tablecell>
            </ui:tablerow>
            </ui:table>
            </ui:tablecell>
        </ui:tablerow>
    </ui:form>
    <ui:form>
    <ui:hiddenfield name="side" value="left"/>
        <ui:tablerow>
            <ui:tablecell>
            <ui:table width="100%">
            <%
                String[] URIs=userData.getLeftURIs();
                ResourceInfo[] resources=userData.getLeftResourceList();

                if(URIs!=null && resources!=null){
            %>
                    <ui:tablerow header="true">
                        <ui:tablecell width="20"/>
                        <ui:tablecell>
                            <ui:text key="COMMANDER_RESOURCE"/>
                        </ui:tablecell>
                        <ui:tablecell width="50">
                            <ui:text key="COMMANDER_SIZE"/>
                        </ui:tablecell>
                        <ui:tablecell width="160">
                            <ui:text key="COMMANDER_LAST_MODIFIED"/>
                        </ui:tablecell>
                        <ui:tablecell width="30"/>
                    </ui:tablerow>
             <%
                for(int i=0;i<URIs.length;++i){
                    if(resources[i].isDirectory()){
             %>
                    <ui:tablerow>
                        <ui:tablecell width="20">
                            <% if(i>0){ %>
                                <input type="checkbox" name="left_<%= i %>"/>
                            <% } %>
                        </ui:tablecell>
                        <ui:tablecell>
                            <a href="<%= URIs[i] %>"><b><%= resources[i].getResource() %></b></a>
                        </ui:tablecell>
                        <ui:tablecell width="50"/>
                        <ui:tablecell width="160">
                                <%= new Date(resources[i].getLastModified()).toString()%>
                        </ui:tablecell>
                        <ui:tablecell width="30"/>
                    </ui:tablerow>
            <%
                    }
                }
                for(int i=0;i<URIs.length;++i){
                    if(!resources[i].isDirectory()){
             %>
                    <ui:tablerow>
                        <ui:tablecell width="20">
                            <% if(i>0){ %>
                                <input type="checkbox" name="left_<%= i %>"/>
                            <% } %>
                        </ui:tablecell>
                        <ui:tablecell>
                            <a href="<%= URIs[i] %>"><%= resources[i].getResource() %></a>
                        </ui:tablecell>
                        <ui:tablecell width="50">
                                <%= resources[i].getLength() %>
                        </ui:tablecell>
                        <ui:tablecell width="190">
                                <%= new Date(resources[i].getLastModified()).toString()%>
                        </ui:tablecell>
                        <ui:tablecell width="30">
                            <a href="<%= leftEditURIs.get(i) %>"><ui:text key="COMMANDER_EDIT"/></a>
                        </ui:tablecell>
                    </ui:tablerow>
            <%
                    }
                }
            }else{ %>
                <ui:tablerow>
                    <ui:tablecell width="100%">
                        <ui:text style="error" key="COMMANDER_ERROR_DIR_READ"/>
                        <ui:actionlink action="gotoRootDirLeft" key="COMMANDER_ERROR_DIR_BACK"/>
                    </ui:tablecell>
                </ui:tablerow>
            <% } %>
            </ui:table>
            </ui:tablecell>
        </ui:tablerow>
        <ui:tablerow>
            <ui:tablecell>
            <ui:table width="100%">
            <ui:tablerow>
                <ui:tablecell width="33%">
                    <ui:actionsubmit action="copy" key="COMMANDER_COPY"/>
                </ui:tablecell>
                <ui:tablecell width="34%">
                    <ui:actionsubmit action="move" key="COMMANDER_MOVE"/>
                </ui:tablecell>
                <ui:tablecell width="33%">
                    <ui:actionsubmit action="delete" key="COMMANDER_DELETE"/>
                </ui:tablecell>
            </ui:tablerow>
            </ui:table>
            </ui:tablecell>
        </ui:tablerow>
        </ui:form>
    </ui:table>
    </ui:tablecell>
    <ui:tablecell width="50%" valign="top">
    <ui:table width="100%">
        <ui:tablerow header="true">
            <ui:tablecell >
                 <%=userData.getPath("right")%>
            </ui:tablecell>
        </ui:tablerow>
    <ui:fileform action="uploadFileRight">
        <ui:tablerow>
            <ui:tablecell>
            <ui:table width="100%">
            <ui:tablerow>
                <ui:tablecell width="100">
                    <ui:text key="COMMANDER_FILE_NAME"/>
                </ui:tablecell>
                <ui:tablecell width="60">
                    <ui:fileinput beanId="userfileRight" size="20" maxlength="20"/>
                </ui:tablecell>
                <ui:tablecell width="100">
                    <ui:actionsubmit action="uploadFileRight" key="COMMANDER_FILE_UPLOAD"/>
                </ui:tablecell>
            </ui:tablerow>
            </ui:table>
            </ui:tablecell>
        </ui:tablerow>
    </ui:fileform>
    <ui:form>
    <ui:hiddenfield name="side" value="right"/>
        <ui:tablerow>
            <ui:tablecell>
            <ui:table width="100%">
            <ui:tablerow>
                <ui:tablecell width="14%">
                    <ui:text key="COMMANDER_DIR_FILE_NAME"/>
                </ui:tablecell>
                <ui:tablecell width="20%">
                    <ui:textfield beanId="resourceNameright"/>
                </ui:tablecell>
                <ui:tablecell width="33%">
                    <ui:actionsubmit action="newDirectory" key="COMMANDER_MKDIR"/>
                </ui:tablecell>
               <ui:tablecell width="33%">
                    <ui:actionsubmit action="newFile" key="COMMANDER_TOUCH"/>
                </ui:tablecell>
            </ui:tablerow>
            </ui:table>
            </ui:tablecell>
        </ui:tablerow>
    </ui:form>
    <ui:form>
    <ui:hiddenfield name="side" value="right"/>
        <ui:tablerow>
            <ui:tablecell>
            <ui:table width="100%">
            <%
                String[] URIs=userData.getRightURIs();
                ResourceInfo[] resources=userData.getRightResourceList();

                if(URIs!=null && resources!=null){
            %>
                    <ui:tablerow header="true">
                        <ui:tablecell width="20"/>
                        <ui:tablecell>
                            <ui:text key="COMMANDER_RESOURCE"/>
                        </ui:tablecell>
                        <ui:tablecell width="50">
                            <ui:text key="COMMANDER_SIZE"/>
                        </ui:tablecell>
                        <ui:tablecell width="190">
                            <ui:text key="COMMANDER_LAST_MODIFIED"/>
                        </ui:tablecell>
                        <ui:tablecell width="30"/>
                    </ui:tablerow>
             <%
                for(int i=0;i<URIs.length;++i){
                    if(resources[i].isDirectory()){
             %>
                    <ui:tablerow>
                        <ui:tablecell width="20">
                            <% if(i>0){ %>
                                <input type="checkbox" name="right_<%= i %>"/>
                            <% } %>
                        </ui:tablecell>
                        <ui:tablecell>
                            <a href="<%= URIs[i] %>"><b><%= resources[i].getResource() %></b></a>
                        </ui:tablecell>
                        <ui:tablecell width="50"/>
                        <ui:tablecell width="160">
                                <%= new Date(resources[i].getLastModified()).toString()%>
                        </ui:tablecell>
                        <ui:tablecell width="30"/>
                    </ui:tablerow>
            <%
                    }
                }
                for(int i=0;i<URIs.length;++i){
                    if(!resources[i].isDirectory()){
             %>
                    <ui:tablerow>
                        <ui:tablecell width="20">
                            <% if(i>0){ %>
                                <input type="checkbox" name="right_<%= i %>"/>
                            <% } %>
                        </ui:tablecell>
                        <ui:tablecell>
                            <a href="<%= URIs[i] %>"><%= resources[i].getResource() %></a>
                        </ui:tablecell>
                        <ui:tablecell width="50">
                                <%= resources[i].getLength() %>
                        </ui:tablecell>
                        <ui:tablecell width="160">
                                <%= new Date(resources[i].getLastModified()).toString()%>
                        </ui:tablecell>
                        <ui:tablecell width="30">
                            <a href="<%= rightEditURIs.get(i) %>"><ui:text key="COMMANDER_EDIT"/></a>
                        </ui:tablecell>
                    </ui:tablerow>
            <%
                    }
                }
            }else{ %>
                <ui:tablerow>
                    <ui:tablecell width="100%">
                        <ui:text style="error" key="COMMANDER_ERROR_DIR_READ"/>
                        <ui:actionlink action="gotoRootDirRight" key="COMMANDER_ERROR_DIR_BACK"/>
                    </ui:tablecell>
                </ui:tablerow>
            <% } %>
            </ui:table>
            </ui:tablecell>
        </ui:tablerow>
        <ui:tablerow>
            <ui:tablecell>
            <ui:table width="100%">
            <ui:tablerow>
                <ui:tablecell width="33%">
                    <ui:actionsubmit action="copy" key="COMMANDER_COPY"/>
                </ui:tablecell>
                <ui:tablecell width="34%">
                    <ui:actionsubmit action="move" key="COMMANDER_MOVE"/>
                </ui:tablecell>
                <ui:tablecell width="33%">
                    <ui:actionsubmit action="delete" key="COMMANDER_DELETE"/>
                </ui:tablecell>
            </ui:tablerow>
            </ui:table>
            </ui:tablecell>
        </ui:tablerow>
        </ui:form>
    </ui:table>
    </ui:tablecell>
</ui:tablerow>
</ui:table>
<% } %>