<%@ page import="java.util.Locale,
                 java.io.File,
                 java.io.FileReader"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<jsp:useBean id="formURIs" class="java.util.HashMap" scope="request"/>
<jsp:useBean id="userData" class="org.gridlab.gridsphere.portlets.core.file.UserData" scope="request" />

<ui:form>
<ui:table width="100%">
    <%  File file=userData.getEditFile();
        if(file==null || !file.exists() || file.isDirectory()){ %>
    <ui:tablerow>
        <ui:tablecell width="100%">
            <ui:text style="error" key="COMMANDER_ERROR_LOAD"/>
        </ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell width="100%">
            <ui:actionsubmit action="cancel" key="COMMANDER_CANCEL"/>
        </ui:tablecell>
    </ui:tablerow>
    <% }else{  %>
    <ui:tablerow header="true">
        <ui:tablecell width="100%">
            <ui:text key="COMMANDER_EDIT"/>
            <ui:text>
            <%= userData.getPath(userData.getEditSide())+file.getName() %>
            </ui:text>
        </ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell width="100%">
        <textarea cols="120" rows="20" name="fileData"><%
        FileReader fileReader=new FileReader(file);
        int numRead;
        char[] buf = new char[4096];
        while (!((numRead=fileReader.read(buf)) < 0)) {
            out.write(buf,0,numRead);
        }
        %></textarea>
        </ui:tablecell>
    </ui:tablerow>
    <ui:tablerow>
        <ui:tablecell width="50%">
            <ui:actionsubmit action="save" key="COMMANDER_SAVE"/>
        </ui:tablecell>
        <ui:tablecell width="50%">
            <ui:actionsubmit action="cancel" key="COMMANDER_CANCEL"/>
        </ui:tablecell>
    </ui:tablerow>
    <% } %>
</ui:table>
</ui:form>