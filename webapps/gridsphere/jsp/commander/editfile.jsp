<%@ page import="java.io.File,
                 java.io.FileReader"%>
<jsp:useBean id="formURIs" class="java.util.HashMap" scope="request"/>
<jsp:useBean id="userData" class="org.gridlab.gridsphere.portlets.core.file.UserData" scope="request" />
<table border="0" cellpadding="2" cellspacing="0" width="100%">
<form action="<%=formURIs.get("commit")%>" method="post">
    <%  File file=userData.getEditFile();
        if(file==null || !file.exists() || file.isDirectory()){ %>
  <tr>
    <td class="portlet-msg-info" style="text-align: center" colspan="2">
      Unable to load file !!!
    </td>
  </tr>
  <tr>
    <td class="portlet-msg-info" style="text-align: center">
      <input type="submit" value="cancel"/>
    </td>
  </tr>
    <% }else{  %>
  <tr>
    <td class="portlet-section-header" style="text-align: center" colspan="2">
      Edit <%= userData.getPath(userData.getEditSide())+file.getName() %>
    </td>
  </tr>
  <tr>
    <td class="portlet-msg-info" style="text-align: center" colspan="2">
      <textarea cols="160" rows="20" name="fileData"><%
        FileReader fileReader=new FileReader(file);
        int numRead;
        char[] buf = new char[4096];
        while (!((numRead=fileReader.read(buf)) < 0)) {
            out.write(buf,0,numRead);
        }
      %></textarea>
    </td>
  </tr>
  <tr>
    <td class="portlet-msg-info" style="text-align: center">
      <input type="submit" name="formAction" value="save"/>
    </td>
    <td class="portlet-msg-info" style="text-align: center">
      <input type="submit" name="formAction" value="cancel"/>
    </td>
  </tr>
    <% } %>
</form>
</table>