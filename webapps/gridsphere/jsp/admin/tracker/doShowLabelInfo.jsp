<%@ page import="java.util.Iterator"%>
<%@ page import="org.gridlab.gridsphere.services.core.tracker.impl.TrackerInfo"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.DateFormat"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>
<portletAPI:init/>

<jsp:useBean id="label" class="java.lang.String" scope="request"/>
<jsp:useBean id="trackInfoList" class="java.util.ArrayList" scope="request"/>

<h2><ui:text key="TRACKING_STATS" style="nostyle"/>&nbsp;:&nbsp;<%= label %> </h2>

<p>
<ui:text key="TRACKING_NUMACTIONS"/>&nbsp;&nbsp;<%= trackInfoList.size() %>
</p>
<ui:table sortable="true" zebra="true" maxrows="25">
                <ui:tablerow header="true">

                    <ui:tablecell><ui:text key="TRACKING_DATE"/></ui:tablecell>
                    <ui:tablecell><ui:text key="TRACKING_USERAGENT"/></ui:tablecell>
                    <ui:tablecell><ui:text key="USERNAME"/></ui:tablecell>
                </ui:tablerow>
<%
                Iterator it = trackInfoList.iterator();
                while (it.hasNext()) {
                    // Get next user
                    TrackerInfo info = (TrackerInfo)it.next();
%>
                    <ui:tablerow>
                        <ui:tablecell>
                            <ui:text value="<%= DateFormat.getDateTimeInstance().format(new Date(info.getDate())) %>" style="plain"/>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text value="<%= info.getUserAgent() %>" style="plain"/>
                        </ui:tablecell>
                        <ui:tablecell>
                            <ui:text value="<%= info.getUserName() %>" style="plain"/>
                        </ui:tablecell>
                    </ui:tablerow>
<%
                }
%>
    </ui:table>

<ui:form>
<ui:actionsubmit action="doDownload" key="TRACKING_DOWNLOAD">
    <ui:actionparam name="label" value="<%= label %>"/>
</ui:actionsubmit>
</ui:form>