<%@ page import="java.util.Set" %>
<%@ page import="java.util.Iterator" %>
<table>
    <%
        Set preferences = (Set) request.getAttribute("preferences");
        Iterator preferencesIterator = preferences.iterator();
        while (preferencesIterator.hasNext()) {
            String value = (String) preferencesIterator.next();
    %>
    <tr>
        <td align="right">
            <label for="feed_<%= value %>"><%= value %>
            </label>
        </td>
        <td align="left">
            <input type="checkbox" name="feed_<%= value %>" id="feed_<%= value %>" value="<%= value %>"/>
        </td>
    </tr>
    <%
        }
    %>
    <tr>
        <td colspan="2" align="center">
            <input type="submit" name="operation=remove" value="Usu&#x0144;"/>
        </td>
    </tr>
    <tr>
        <td colspan="2" align="center">
            <hr/>
        </td>
    </tr>
    <tr>
        <td align="right">
            <label for="newFeed">Feed:</label>
        </td>
        <td align="left">
            <input type="text" name="newFeed" id="newFeed"/>
        </td>
    </tr>
    <tr>
        <td colspan="2" align="center">
            <input type="submit" name="operation=add" value="Dodaj"/>
        </td>
    </tr>
</table>
