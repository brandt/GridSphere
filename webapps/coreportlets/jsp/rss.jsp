<jsp:useBean id="rss_url" class="java.lang.String" scope="request"/>

    <form method="POST" action="<%= rss_url %>">
    URL:
    <input type="text" name="rss_url" size="30" maxlength="80"></input>
     <input type="submit" name="option" value="Ok"></input>
    </form>

