
<jsp:useBean id="reload" class="java.lang.String" scope="request"/>
<jsp:useBean id="upload" class="java.lang.String" scope="request"/>

<form method="POST" action="<%= reload %>">
    <table cellspacing=2 cellpadding=2 border=0>
    <tr>
        <td>
            <input type="submit" name="option" value="Reload Portlets" ></input>
        </td>
    </tr>
    </table>
</form>

<form  method="POST" action="<%= upload %>" enctype="multipart/form-data">
    <table cellspacing=2 cellpadding=2 border=0>
    <tr>
        <td align="right">Portlet WAR: </td>
        <td align="left"><input type="file" name="filename" size="8" maxlength="20"></input></td>
    </tr>
    <tr>
        <td colspan=5 align="center">
            <input type="submit" name="option" value="Upload" ></input>
        </td>
    </tr>
    </table>
</form>

