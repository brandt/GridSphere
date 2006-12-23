<jsp:useBean id="error" class="java.lang.Throwable" scope="request"/>


<%@ include file="/WEB-INF/CustomPortal/content/pageheader.html" %>

<h2>GridSphere portal failed to initialize!</h2>

<p>
    <br/>
    <%= error.getMessage() %>
    <b>Stack Trace:</b><br/>
    <% error.printStackTrace(new java.io.PrintWriter(out)); %>
</p>
