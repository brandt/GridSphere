<jsp:useBean id="userData" class="org.gridlab.gridsphere.portlets.core.file.UserData" scope="request" />
<jsp:useBean id="formURIs" class="java.util.HashMap" scope="request"/>
<jsp:useBean id="leftEditURIs" class="java.util.List" scope="request"/>
<jsp:useBean id="rightEditURIs" class="java.util.List" scope="request"/>
<%@page import="org.gridlab.gridsphere.services.core.secdir.ResourceInfo,
                java.util.Date"%>
<table border="1" cellpadding="2" cellspacing="0" width="100%">
<% if(!userData.getCorrect().booleanValue()){ %>
  <tr>
    <td class="portlet-msg-info" style="text-align: center">
      Secure directory service unavailable !!!
    </td>
  </tr>
<% }else{ %>
    <tr>
      <td class="portlet-section-header" width="50%">
        <b><%=userData.getPath("left")%></b>
      </td>
      <td class="portlet-section-header" width="50%">
        <b><%=userData.getPath("right")%></b>
      </td>
    </tr>
  <tr>
    <td style="vertical-align: top; width:50%">
     <form action="<%=formURIs.get("explorer_left")%>" method="post" enctype="multipart/form-data">
     <table border="0" cellpadding="2" cellspacing="0" width="100%">
      <tr>
        <td class="portlet-msg-info" colspan="2" style="text-align: center" width="66%">
          File: &nbsp;<input type="file" name="file"/>
        </td>
        <td class="portlet-msg-info" style="text-align: center" width="34%">
          <input type="submit" name="formAction" value="upload"/>
        </td>
      </tr>
      <tr>
        <td class="portlet-msg-info" style="text-align: center">
          <input type="submit" name="formAction" value="copy"/>
        </td>
        <td class="portlet-msg-info" style="text-align: center">
          <input type="submit" name="formAction" value="move"/>
        </td>
        <td class="portlet-msg-info" style="text-align: center">
          <input type="submit" name="formAction" value="delete"/>
        </td>
      </tr>
      <tr>
        <td class="portlet-msg-info" style="text-align: center">
          <input type="edit" name="resourceName"/>
        </td>
        <td class="portlet-msg-info" style="text-align: center">
          <input type="submit" name="formAction" value="mkdir"/>
        </td>
        <td class="portlet-msg-info" style="text-align: center">
          <input type="submit" name="formAction" value="touch"/>
        </td>
      </tr>
     </table>
     <br/><table border="0" cellpadding="2" cellspacing="0" width="100%">
     <tr>
       <td class="portlet-section-header" width="20">&nbsp;</td>
       <td class="portlet-section-header" style="text-align: left">resource</td>
       <td class="portlet-section-header" width="50" style="text-align: right">size</td>
       <td class="portlet-section-header" width="160" style="text-align: center">last modified</td>
       <td class="portlet-section-header" width="30">&nbsp;</td>
     </tr>
  <%
      String[] URIs=userData.getLeftURIs();
      ResourceInfo[] resources=userData.getLeftResourceList();
      for(int i=0;i<URIs.length;++i){
          if(resources[i].isDirectory()){
          %>
  <tr>
    <td class="portlet-msg-info" style="text-align: center" width="20">
      <% if(i>0){ %>
        <input type="checkbox" name="left_<%= i %>">
      <% }else{ %>
        &nbsp;
      <% } %>
    </td>
    <td class="portlet-msg-info">
      <a href="<%= URIs[i] %>"><b><%= resources[i].getResource() %></b></a>
    </td>
    <td class="portlet-msg-info">
      &nbsp;
    </td>
    <td class="portlet-msg-info" style="text-align: left">
      <%= new Date(resources[i].getLastModified()).toString() %>
    </td>
    <td class="portlet-msg-info">
      &nbsp;
    </td>
   </tr>
              <%
          }
      }
      for(int i=0;i<URIs.length;++i){
          if(!resources[i].isDirectory()){
          %>
  <tr>
    <td class="portlet-msg-info" style="text-align: center" width="20">
      <input type="checkbox" name="left_<%= i %>"/>
    </td>
    <td class="portlet-msg-info">
      <a href="<%= URIs[i] %>"><%= resources[i].getResource() %></a>
    </td>
    <td class="portlet-msg-info" style="text-align: right;margin-right:15 px">
      <%= resources[i].getLength() %>
    </td>
    <td class="portlet-msg-info" style="text-align: left">
      <%= new Date(resources[i].getLastModified()).toString() %>
    </td>
    <td class="portlet-msg-info" style="text-align: right;margin-right:10 px" width="40">
      <a href="<%= leftEditURIs.get(i) %>">edit</a>
    </td>
   </tr>
              <%
          }
      }
    %>
    </form>
    </table>
    </td>
    <td style="vertical-align: top; width:50%">
     <form action="<%=formURIs.get("explorer_right")%>" method="post" enctype="multipart/form-data">
     <table border="0" cellpadding="2" cellspacing="0" width="100%">
      <tr>
        <td class="portlet-msg-info" colspan="2" style="text-align: center" width="66%">
          File: &nbsp;<input type="file" name="file"/>
        </td>
        <td class="portlet-msg-info" style="text-align: center"  width="34%">
          <input type="submit" name="formAction" value="upload"/>
        </td>
      </tr>
      <tr>
        <td class="portlet-msg-info" style="text-align: center">
          <input type="submit" name="formAction" value="copy"/>
        </td>
        <td class="portlet-msg-info" style="text-align: center">
          <input type="submit" name="formAction" value="move"/>
        </td>
        <td class="portlet-msg-info" style="text-align: center">
          <input type="submit" name="formAction" value="delete"/>
        </td>
      </tr>
      <tr>
        <td class="portlet-msg-info" style="text-align: center">
          <input type="edit" name="resourceName"/>
        </td>
        <td class="portlet-msg-info" style="text-align: center">
          <input type="submit" name="formAction" value="mkdir"/>
        </td>
        <td class="portlet-msg-info" style="text-align: center">
          <input type="submit" name="formAction" value="touch"/>
        </td>
      </tr>
     </table>
<br/><table border="0" cellpadding="2" cellspacing="0" width="100%">
     <tr>
       <td class="portlet-section-header" width="20">&nbsp;</td>
       <td class="portlet-section-header" style="text-align: left">resource</td>
       <td class="portlet-section-header" width="50" style="text-align: right">size</td>
       <td class="portlet-section-header" width="160" style="text-align: center">last modified</td>
       <td class="portlet-section-header" width="30">&nbsp;</td>
     </tr>
  <%
      URIs=userData.getRightURIs();
      resources=userData.getRightResourceList();
      for(int i=0;i<URIs.length;++i){
          if(resources[i].isDirectory()){
          %>
  <tr>
    <td class="portlet-msg-info" style="text-align: center" width="20">
      <% if(i>0){ %>
        <input type="checkbox" name="right_<%= i %>"/>
      <% }else{ %>
        &nbsp;
      <% } %>
    </td>
    <td class="portlet-msg-info">
      <a href="<%= URIs[i] %>"><b><%= resources[i].getResource() %></b></a>
    </td>
    <td class="portlet-msg-info">
      &nbsp;
    </td>
    <td class="portlet-msg-info" style="text-align: left">
      <%= new Date(resources[i].getLastModified()).toString() %>
    </td>
    <td class="portlet-msg-info">
      &nbsp;
    </td>
   </tr>
              <%
          }
      }
      for(int i=0;i<URIs.length;++i){
          if(!resources[i].isDirectory()){
          %>
  <tr>
    <td class="portlet-msg-info" style="text-align: center" width="20">
      <input type="checkbox" name="right_<%= i %>">
    </td>
    <td class="portlet-msg-info">
      <a href="<%= URIs[i] %>"><%= resources[i].getResource() %></a>
    </td>
    <td class="portlet-msg-info" style="text-align: right;margin-right:15 px">
      <%= resources[i].getLength() %>
    </td>
    <td class="portlet-msg-info" style="text-align: left">
      <%= new Date(resources[i].getLastModified()).toString() %>
    </td>
    <td class="portlet-msg-info" style="text-align: right;margin-right:10 px" width="40">
      <a href="<%= rightEditURIs.get(i) %>">edit</a>
    </td>
   </tr>
              <%
          }
      }
    %>
    </form>
    </table>
    </td>
    </tr>
    <%
  } %>
</table>