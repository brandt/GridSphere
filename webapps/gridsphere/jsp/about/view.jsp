<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>

<%
    String version = (String) request.getAttribute("version");
    String deployedPath = (String) request.getAttribute("path");
%>

<div id="gsinfo" style="text-align: center">
    <h1>
        <ui:text key="ABOUT_GRIDSPHERE_VERSION"/>
        :
        <%= version %>
    </h1>
</div>
<ui:group label="System Information">
    <ui:table>
        <ui:tablerow>
            <ui:tablecell>Java Version</ui:tablecell>
            <ui:tablecell><%= System.getProperty("java.vendor")%> <%= System.getProperty("java.version")%>
            </ui:tablecell>
        </ui:tablerow>
        <ui:tablerow>
            <ui:tablecell>GridSphere</ui:tablecell>
            <ui:tablecell><%= deployedPath%>
            </ui:tablecell>

        </ui:tablerow>
        <ui:tablerow>
            <ui:tablecell>Java Home</ui:tablecell>
            <ui:tablecell><%= System.getProperty("java.home")%>
            </ui:tablecell>
        </ui:tablerow>
        <ui:tablerow>
            <ui:tablecell valign="top">Java Classpath</ui:tablecell>
            <ui:tablecell>
                <ul><%
                    String path = System.getProperty("java.class.path");
                    String elements[] = path.split(":");
                    for (int i = 0; i < elements.length; i++) {
                %>
                    <li><%= elements[i]%>
                    </li>
                    <%
                        }
                    %>
                </ul>
            </ui:tablecell>
        </ui:tablerow>
        <ui:tablerow>
            <ui:tablecell valign="top">Java Librarypath</ui:tablecell>
            <ui:tablecell>
                <ul><%
                    String path = System.getProperty("java.library.path");
                    String elements[] = path.split(":");
                    for (int i = 0; i < elements.length; i++) {
                %>
                    <li><%= elements[i]%>
                    </li>
                    <%
                        }
                    %>
                </ul>
            </ui:tablecell>
        </ui:tablerow>
        <ui:tablerow>
            <ui:tablecell>OS Name</ui:tablecell>
            <ui:tablecell><%= System.getProperty("os.name")%>
            </ui:tablecell>
        </ui:tablerow>
        <ui:tablerow>
            <ui:tablecell>OS Arch</ui:tablecell>
            <ui:tablecell><%= System.getProperty("os.arch")%>
            </ui:tablecell>
        </ui:tablerow>
        <ui:tablerow>
            <ui:tablecell>OS Version</ui:tablecell>
            <ui:tablecell><%= System.getProperty("os.version")%>
            </ui:tablecell>
        </ui:tablerow>

    </ui:table>

</ui:group>

<div id="gsinfo" style="text-align: center">
    <ui:text key="ABOUT_GRIDSPHERE_WRITTEN_BY"/>
    Jason Novotny, Michael Russell &amp; Oliver Wehrens
</div>
