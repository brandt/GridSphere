
<jsp:useBean id="setvalue" class="java.lang.String" scope="request"/>

<form method="POST" action="<%= setvalue %>">
    <table cellspacing=2 cellpadding=2 border=0>
    <tr>
    <td align="right">Enter a value: </td>
    <td align="left"><input type="text" name="foobar" size="8" maxlength="20"></input></td>
    </tr>
    <tr>
    <td colspan=5 align="center"><input type="submit" name="option" value="Change" ></input></td>
    </tr>
    </table>
</form>
