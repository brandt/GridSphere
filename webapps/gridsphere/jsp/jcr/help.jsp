<h2>Help for Content Management</h2>

A Node represents a document which can be included in the portlet-content tag in the
layout.xml of GridSphere.

Each node has an id, a content and a renderkit associated with it.

The id is a unique identifier which is used in the portlet-content tag to include it. To show the node
'overview' in the layout use the following code in the layout.xml:

<pre>
    &lt;portlet-content include="jcr://overview"/&gt;
</pre>

The renderkit determins how the content is rendered.

<table>
    <tr>
        <td>text</td>
        <td>Will be rendered as plain text.</td>
    </tr>
    <tr>
        <td>html</td>
        <td>Will be rendered as HTML.</td>
    </tr>
    <tr>
        <td>radeox</td>
        <td>Will be rendered using <a href="http://www.radeox.org">Radeox</a> markup.</td>
    </tr>
</table>

