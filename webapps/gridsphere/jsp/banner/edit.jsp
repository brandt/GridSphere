<%@ page import="org.gridlab.gridsphere.services.core.secdir.ResourceInfo,
                 java.util.Date"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<jsp:useBean id="userData" class="org.gridlab.gridsphere.portlets.core.file.UserData" scope="request" />

<portletAPI:init/>

    <ui:panel>
        <ui:form>
        <ui:frame beanId="alert"/>

        <ui:frame>
            <ui:tablerow header="true">
                <ui:tablecell width="100">
                    <ui:text key="BANNER_FILE"/>&nbsp;(<ui:text beanId="fileName"/>)
                </ui:tablecell>
            </ui:tablerow>
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
                                <ui:text key="COMMANDER_RESOURCE"/>&nbsp;(<%=userData.getPath("left")%>)
                            </ui:tablecell>
                            <ui:tablecell width="80">
                                <ui:text key="COMMANDER_SIZE"/>
                            </ui:tablecell>
                            <ui:tablecell width="200">
                                <ui:text key="COMMANDER_LAST_MODIFIED"/>
                            </ui:tablecell>
                        </ui:tablerow>
             <%
                for(int i=0;i<URIs.length;++i){
                    if(resources[i].isDirectory()){
             %>
                        <ui:tablerow>
                            <ui:tablecell width="20"/>
                            <ui:tablecell>
                                <a href="<%= URIs[i] %>"><b><%= resources[i].getResource() %></b></a>
                            </ui:tablecell>
                            <ui:tablecell width="80"/>
                            <ui:tablecell width="200">
                                <%= new Date(resources[i].getLastModified()).toString()%>
                            </ui:tablecell>
                        </ui:tablerow>
            <%
                    }
                }
                for(int i=0;i<URIs.length;++i){
                    if(!resources[i].isDirectory()){
             %>
                        <ui:tablerow>
                            <ui:tablecell width="20">
                                <input type="radio" name="fileNumber" value="<%= i %>"/>
                            </ui:tablecell>
                            <ui:tablecell>
                                <%= resources[i].getResource() %>
                            </ui:tablecell>
                            <ui:tablecell width="50">
                                <%= resources[i].getLength() %>
                            </ui:tablecell>
                            <ui:tablecell width="190">
                                <%= new Date(resources[i].getLastModified()).toString()%>
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
        </ui:frame>


        <ui:frame>
            <ui:tablerow header="true">
                <ui:tablecell>
                    <ui:text key="BANNER_TITLE"/>&nbsp;(<ui:text beanId="title"/>)
                </ui:tablecell>
            </ui:tablerow>
            <ui:tablerow>
                <ui:tablecell cssStyle="align:center;" >
                    <ui:textfield beanId="displayTitle"/>
                </ui:tablecell>
                <ui:tablecell/>
            </ui:tablerow>
        </ui:frame>

        <ui:frame>
            <ui:tablerow>
                <ui:tablecell/>
                <ui:tablecell>
                    <ui:actionsubmit action="setEditDisplayFile" key="OK"/>
                    <ui:actionsubmit action="cancelEditFile" key="CANCEL"/>
                </ui:tablecell>
                <ui:tablecell/>
            </ui:tablerow>
        </ui:frame>
        </ui:form>
    </ui:panel>
