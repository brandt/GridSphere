<%@ page import="org.gridlab.gridsphere.portlet.User,
                 java.util.Calendar,
                 java.util.Date,
                 java.text.DateFormat"%>

<table border=0 cellpadding=0 cellspacing=0 >
<tr>
<td width=1 bgcolor=cccccc>
<spacer type=block width=1></td>
<td>

    <table cellspacing=2 cellpadding=2 border=0>
    <tr>
    <td align="right"><b>Username: </b></td>
    <td align="left"><%= user.getUserName() %></td>
    </tr>
    <tr>
    <td align="right"><b>Full Name: </b></td>
    <td align="left"><%= user.getFullName() %></td>
    </tr>
    <tr>
    <td align="right"><b>Last Login Time: </b></td>
    <td align="left"><%= DateFormat.getDateTimeInstance().format(new Date(user.getLastLoginTime())) %></td>
    </tr>

</td>
</tr>
</table>

</td>
<td width=1 bgcolor=cccccc>
<spacer type=block width=1>
</td>
</tr>
<tr>
<td height=1 colspan=3 bgcolor=cccccc>
<spacer type=block height=1></td></tr>
</table>

<table border=0 cellpadding=0 cellspacing=0>
<tr><td></td></tr>
</table>




