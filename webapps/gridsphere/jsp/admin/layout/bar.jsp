<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<jsp:useBean id="controlUI" class="java.lang.String" scope="request"/>


<ui:group key="LAYOUT_EDIT_BAR">
    <ui:table>
        <ui:tablerow>
            <ui:tablecell>
                Select column layout:
                <ui:listbox beanId="colsLB"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:table>
    <ui:table>
        <ui:tablerow>
            <ui:tablecell>
                <ui:actionsubmit action="doSaveBar" value="Save"/>
                <ui:actionsubmit action="doCancel" value="Cancel"/>
            </ui:tablecell>
        </ui:tablerow>
    </ui:table>

</ui:group>