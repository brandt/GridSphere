<%@ page import="java.util.List,
                 org.gridlab.gridsphere.tmf.config.TmfService,
                 java.util.ArrayList"%>
<%@ page import="org.gridlab.gridsphere.tmf.config.User"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>


<portletAPI:init/>


<ui:form>

<ui:text value="Choose Service to edit:"/>
<ui:listbox beanId="services"/>

<ui:actionsubmit action="editChoosenService" value="edit settings"/>


</ui:form>
