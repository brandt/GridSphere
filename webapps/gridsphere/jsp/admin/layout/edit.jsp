<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<jsp:useBean id="name" class="java.lang.String" scope="request"/>
<jsp:useBean id="allowImport" class="java.lang.Boolean" scope="request"/>

<portletAPI:init/>

<ui:form>
<h3><ui:text key="LAYOUTMGR_GROUP_EDIT" style="nostyle"/>&nbsp; <%= name %></h3>
<ui:hiddenfield beanId="layoutHF"/>
<ui:hiddenfield beanId="typeHF"/>


<% if (allowImport.booleanValue()) { %>

<ui:text key="LAYOUTMGR_IMPORT"/>

<ui:listbox beanId="appsLB"/>

<ui:actionsubmit action="importLayout" key="APPLY"/>

<p>


<% } %>

<ui:textarea beanId="layoutFile" rows="30" cols="80"/>

<p>

<ui:table>

<ui:tablerow>
<ui:tablecell>
<ui:actionsubmit action="saveLayout" key="SAVE"/>
</ui:tablecell>
<ui:tablecell>
<ui:actionsubmit action="cancelLayout" key="CANCEL"/>
</ui:tablecell>
</ui:tablerow>
</ui:table>

</ui:form>
