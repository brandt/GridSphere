<%@ page import="java.util.List,
                 org.gridlab.gridsphere.tmf.config.TmfService,
                 java.util.ArrayList"%>
<%@ page import="org.gridlab.gridsphere.tmf.config.TmfUser"%>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>


<portletAPI:init/>


<ui:form>

<ui:text key="MESSAGING_CHOOSESERVICETOEDIT"/>
<ui:listbox beanId="services"/>

<ui:actionsubmit action="editChoosenService" key="MESSAGING_EDITSETTINGS"/>


</ui:form>
