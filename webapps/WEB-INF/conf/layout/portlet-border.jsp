
  <% String title = (String)request.getAttribute("title"); %>
  <% String titlecolor = (String)request.getAttribute("titlecolor"); %>
  <% String font = (String)request.getAttribute("font"); %>
  <% String linecolor = (String)request.getAttribute("linecolor"); %>
  <% String thickness = (String)request.getAttribute("thickness"); %>

  <table width="100%" border="0" cellspacing="2" cellpadding="0" bgcolor="#999999">
    <tr>
      <td width="100%">
        <table width="100%" border="0" cellspacing="1" cellpadding="1" bgcolor="<%= linecolor %>">
          <tr>
            <td><img src="/gridsphere/images/help.gif" align=left></td>
            <td height="20" align="left" valign="middle">
              <font color="#FFFFFF" face="<%= font %>">
              &nbsp;<%= title %>
              </font></td>
             <td><img src="/gridsphere/images/window.gif" align=right></td>
          </tr>
        </table>
      </td>
    </tr>



