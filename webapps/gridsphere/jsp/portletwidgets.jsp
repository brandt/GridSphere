<%@ taglib uri="/portletUI" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<html bgcolor="white">

<head>
    <title>Portlet Widget Tag Library</title>
</head>


<body>

<h1>GridSphere Portlet Widget Tag Library</h1>
The GridSphere Portlet Widget Tag Library (GPWTL) is intended to provide portlet developers with an easy to use
set of tags for rapid web GUI development. As HTML is not only lacking, inconsistent and browser dependent,
the goal of the GPWTL is to provide browser and device independent user interfaces by means of a set of XML tags
that are available as a JSP tag library. The tag library is also integrated tightly with the Portlet API to make
portlet development that much easier. Below is a quick rundown of the various tags and their uses.
At this stage, most of the tags are wrappers around existing HTML tags.

<h3>Using the GPWTL</h3>
A recommended best practice for portlet development is to separate logic from presentation in the portlet by
programming all the presentation logic in the appropriate doView, doEdit, etc. method of the portlet and then
including a JSP page responsible for the presentation via the following call: <p>

    <code>
        public void doView(PortletRequest request, PortletResponse response) throws PortletException, IOException {<br/>
        ...<br/>
        getPortletConfig().getContext().include("/jsp/any.jsp", request, response);<br/>
        }<br/>
    </code>
</p>

<p>
    In the "any.jsp" file, the following JSP would be included to load in the tag library:
</p>

<p>
    <code>
        &lt;%@ taglib uri="/portletWidgets" prefix="gs" %&gt;<br/>
        &lt;%@ taglib uri="/portletAPI" prefix="portletAPI" %&gt;<br/>
        <br/>
        &lt;portletAPI:init/&gt;<br/>
    </code>
</p>

<p>
    Lines 1 and 2 indicate that the portletWidgets and portletAPI tag libraries are to be used and line 3
    must be called to make the necessary portlet objects i.e. PortletRequest available to the JSP.
</p>
<br/>
The prefix is used to reference the tag library later on e.g. to use the portletWidgets form tag:
<p>
    <code>&lt;gs:form ... &gt;

        &lt;/gs:form&gt;

    </code>
</p>

<p>
</p>

<h3>Creating Actions</h3>

<h4>Action Links</h4>
The simplest tag is the <b>actionlink</b> tag:
<p>
    <code>&lt;gs:actionlink <b>action</b>="actionlink" <b>label</b>="a link"&gt;&lt;/gs:actionlink&gt;</code>
</p>

<p>
    Parameters:
</p>
<ul>
    <li><b>action</b> -- the action is a name given that can later be referenced as the event.getAction().getName() in
        the <code>actionPerformed(ActionEvent event)</code> portlet method.
    <li><b>label</b> -- the label is the text to be underlined and linked e.g "a link"
</ul>

Example:
<p>
    <gs:actionlink action="actionlink" label="a link"/>
</p>

<h4>Forms</h4>

The <b>form</b> tag is a lightweight replacement for the HTML form tag:
<p>
    <code>
        &lt;gs:form <b>action</b>="myformaction"&gt;
        ...
        &lt;/gs:form&gt;
    </code>
</p>

<p>
    Parameters:
</p>
<ul>
    <li><b>action</b> -- the action is a name given that can later be referenced as the event.getAction().getName() in
        the <code>actionPerformed(ActionEvent event)</code> portlet method.
    <li><b>method</b> -- (optional) the HTTP method to use either GET or POST by default it's POST
</ul>

<h4>File Forms</h4>


<table cellspacing=2 cellpadding=2 border=0>
    <tr>
        <td align="right">File: </td>
        <td align="left"><gs:fileinput name="filename" size="8" maxlength="20"/></td>
    </tr>
    <tr>
        <td colspan=5 align="center"><gs:actionsubmit name="option" value="Login"/></td>
    </tr>
</table>

<h4>Action Parameters</h4>
Parameters can be associated with forms and actionlinks and can be used to perform action logic in
the <code>actionPerformed(ActionEvent event)</code> portlet via the
event.getParameters() method.

One or more <b>actionparam</b> tags can be placed anywhere between a <b>actionlink</b> or <b>form</b> begin and
end tags.

<p>
    <code>
        &lt;gs:actionparam <b>name</b>="aparamname" <b>value</b>="aparamvalue"/&gt;
    </code>
</p>
Parameters:
<ul>
    <li><b>name</b> -- the name of the parameter
    <li><b>value</b> -- the value of the parameter
</ul>

<p>
    Example:
</p>

<p>
</p>
<gs:form action="myform">
    <gs:actionparam name="aparamname" value="aparamvalue"/>
    <table cellspacing=2 cellpadding=2 border=0>
        <tr>
            <td colspan=5 align="center"><gs:actionsubmit value="Yes"></gs:actionsubmit></td>
        </tr>
        <tr>
            <td colspan=5 align="center"><gs:actionsubmit value="No"></gs:actionsubmit></td>
        </tr>
    </table>

</gs:form>

<h4>Additional HTML Tags</h4>

<b>textfield</b><br/>

&lt;gs:textfield name="username" size="8" maxlength="20"/&gt; <p/>

<b>password</b><br/>

&lt;gs:password name="password" size="8" minlength="6" maxlength="20"/&gt; <p/>

<b>input</b><br/>

&lt;gs:input type="submit" name="option" value="Login"/&gt; <p/>

<b>submit</b><br/>
&lt;gs:submit name="cancel" value="Cancel" /&gt; <br/>

This generates an html submit button with the name cancel. If this button is pressed
FormEvent.getPressedSubmitButton() will return this name.


<h4>More Information</h4>

The Tag source code is in the org.gridsphere.tags package and may be used as a reference or to develop
new tags. Tags are defined in Tag Library Descriptors (TLD) that is in the gridsphere webapp in WEB-INF/conf/tlds.
For more specific tag information including required and optional attributes, etc, please refer to the TLD.

</body>

</html>