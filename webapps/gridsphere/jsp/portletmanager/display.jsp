
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

The Portlet Application Manager allows you to view and manage portlet
web applications.

<ui:actionlink action="list" value="view portlet web applications"/>

<ui:fileform>
<ui:panel>

    <ui:frame beanId="errorFrame"/>

    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text value="File: "/>
            </ui:tablecell>
        </ui:tablerow>
        <ui:tablerow>
            <ui:tablecell>
                <ui:fileinput name="filename" size="8" maxlength="20"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:actionsubmit action="option" value="Upload Portlet WAR"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:frame>

    </ui:panel>
 </ui:fileform>

