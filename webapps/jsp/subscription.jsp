
<jsp:useBean id="reload" class="java.lang.String" scope="request"/>

<form method="POST" action="<%= reload %>">
<table cellspacing=2 cellpadding=2 border=0>
<tr>
    <td>
        <input type="submit" name="option" value="Reload Portlets" ></input>
    </td>
</tr>
</table>
</form>