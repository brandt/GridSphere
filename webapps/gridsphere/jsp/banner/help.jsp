
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<p>
<b>Using the banner portlet</b>
<p>
The banner portlet is used for displaying content to portal users. The following modes are supported:
<p>
<ul>
<li><b>Configure</b> Allows administrators to configure the default file that is displayed to guests
<li><b>Edit</b> Allows you to edit the currently displayed file that you see after logging in. Use the
<ui:actionlink label="filemanager" value="File Manager Portlet"/>, to upload files locally, edit them and save.</li>
<li><b>View</b> Displays the selected file.</li>
<li><b>Help</b> Displays help information.</li>
</ul>

<p>
<b>Tips :-)</b>
<p>
For portlet developers, it is useful to use the file manager portlet to upload and edit JSP files containing
UI tags and configure this portlet to display them. This make interactive portlet presentation development
much easier!