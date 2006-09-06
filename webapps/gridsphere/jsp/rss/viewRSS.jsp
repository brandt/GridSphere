<%@ page import="com.sun.syndication.feed.synd.SyndContent" %>
<%@ page import="com.sun.syndication.feed.synd.SyndEntry" %>
<%@ page import="com.sun.syndication.feed.synd.SyndFeed" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Iterator" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>


<ui:messagebox beanId="msg"/>

<% SyndFeed feed = (SyndFeed) request.getAttribute("rssfeed");

    if (feed != null) {
        String feedTitle = feed.getTitle();

%>
<h2><%=feedTitle%>
</h2>
<ul>
    <%

        Iterator entryIter = feed.getEntries().iterator();
        while (entryIter.hasNext()) {
            SyndEntry entry = (SyndEntry) entryIter.next();
            String entryLink = entry.getLink();
            String entryTitle = entry.getTitle();
            Date entryDate = entry.getPublishedDate();
            SyndContent content = entry.getDescription();
            String value = content.getValue();

    %>
    <li><a href="<%=entryLink%>"><%=entryTitle%>
    </a>, <%=entryDate%><br/>
        <%=value%>
    </li>

    <%


        }
    %>
</ul>
<%

    }

%>

