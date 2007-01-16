<%@ page import="com.sun.syndication.feed.synd.SyndContent" %>
<%@ page import="com.sun.syndication.feed.synd.SyndEntry" %>
<%@ page import="com.sun.syndication.feed.synd.SyndFeed" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Iterator" %>

<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<br>

<ui:messagebox beanId="msg"/>

<% SyndFeed feed = (SyndFeed) request.getAttribute("rssfeed");

    if (feed != null) {
%>

<ui:form action="selectFeed">
    <ui:listbox submitOnChange="true" beanId="feedsLB"/>
</ui:form>

<br>

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
<ui:group label="<%=entryTitle%>">
    <ui:text cssStyle="font-size: x-small;  font-weight: italic"><%=entryDate%><br/></ui:text>
    <p/>
    <%=value%>
    <p/>
    <ui:text cssStyle="font-size: x-small;"><a href="<%=entryLink%>">
        <ui:text key="RSS_READ_FULL_STORY"/>
    </a></ui:text>
</ui:group>

<%


    }
%>

<%

    }

%>

