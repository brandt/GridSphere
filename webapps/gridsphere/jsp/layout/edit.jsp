<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>
<ui:form>

<ui:hiddenfield beanId="tabHF" value=""/>
<ui:hiddenfield beanId="subtabHF" value=""/>

<ui:panel>
Theme configuration
<ui:frame>
Select a theme: <ui:listbox beanId="themeLB"/> <ui:actionsubmit action="saveTheme" value="Save theme"/>
</ui:frame>
</ui:panel>

<ui:panel>
<ui:text value="Tab configuration"/>
<ui:frame>

<ui:tablerow>
<ui:text value="Edit tab title: "/>
    <ui:tablecell beanId="tabsTC"/>
 <ui:tablecell>
<ui:actionsubmit action="saveTabs" value="Apply changes"/>
</ui:tablecell>
</ui:tablerow>

</ui:frame>

<ui:frame>
<ui:tablerow>
<ui:tablecell>
<ui:text value="Delete a tab"/><ui:text style="alert" value="Warning! this will remove all portlets from selected tab"/>
</ui:tablecell>
</ui:tablerow>
<ui:tablerow>
<ui:tablecell>
<ui:listbox beanId="deltabsLB"/>

<ui:actionsubmit action="deleteTab" value="Delete tab"/>
</ui:tablecell>
</ui:tablerow>
</ui:frame>

<ui:frame>
<ui:tablerow>
<ui:tablecell>
<ui:text value="Create a new tab"/>
</ui:tablecell>
</ui:tablerow>
<ui:tablerow>
<ui:tablecell>
<ui:text value="Enter tab name:"/>
<ui:textfield beanId="newTab"/>
<ui:actionsubmit action="createTab" value="Create tab"/>
</ui:tablecell>
</ui:tablerow>
</ui:frame>


</ui:panel>

<ui:panel>
<ui:frame>
<ui:tablerow>
<ui:tablecell>
<ui:text value="Subtab configuration"/>
</ui:tablecell>
<ui:tablecell>
<ui:listbox beanId="seltabsLB"/>
<ui:actionsubmit action="selectTab" value="Select a tab"/>
</ui:tablecell>
</ui:tablerow>
</ui:frame>

<ui:frame>
<ui:tablerow>
<ui:text value="Edit subtab title: "/>
    <ui:tablecell beanId="subtabsTC"/>
 <ui:tablecell>
<ui:actionsubmit action="saveSubTabs" value="Apply changes"/>
</ui:tablecell>
</ui:tablerow>

</ui:frame>

<ui:frame>
<ui:tablerow>
<ui:tablecell>
<ui:text value="Delete a subtab"/><ui:text style="alert" value="Warning! this will remove all portlets from selected tab"/>
</ui:tablecell>
</ui:tablerow>
<ui:tablerow>
<ui:tablecell>
<ui:listbox beanId="delsubtabsLB"/>

<ui:actionsubmit action="deleteTab" value="Delete subtab"/>
</ui:tablecell>
</ui:tablerow>
</ui:frame>


<ui:frame>
<ui:tablerow>
<ui:tablecell>
<ui:text value="Create a new subtab"/>
</ui:tablecell>
</ui:tablerow>
<ui:tablerow>
<ui:tablecell>
<ui:text value="Enter subtab name:"/>
<ui:textfield beanId="newSubTab"/>
<ui:actionsubmit action="createSubTab" value="Create subtab"/>
</ui:tablecell>
</ui:tablerow>
</ui:frame>
</ui:panel>

<ui:panel>
Portlet content configuration
<ui:frame>
<ui:tablerow>
<ui:tablecell>
<ui:text value="Select a subtab"/>
</ui:tablecell>
</ui:tablerow>
<ui:tablerow>
<ui:tablecell>
<ui:listbox beanId="selsubtabsLB"/>
<ui:actionsubmit action="selectSubTab" value="Select a subtab"/>
</ui:tablecell>
</ui:tablerow>
</ui:frame>

<ui:frame>
<ui:tablerow>
<ui:tablecell>
<ui:text value="Table layout"/>
</ui:tablecell>
</ui:tablerow>
<ui:tablerow>
<ui:tablecell>
<ui:actionsubmit action="addTableRow" value="Add new table row"/>
</ui:tablecell>
</ui:tablerow>
</ui:frame>

<ui:frame>
<ui:tablerow>
<ui:tablecell>
<ui:text value="Portlet layout"/>
</ui:tablecell>
</ui:tablerow>
<ui:tablerow>

</ui:tablerow>
</ui:frame>

<ui:frame beanId="portletLayout"/>

<ui:frame>
<ui:tablerow>
<ui:tablecell>
<ui:actionsubmit action="savePortletFrames" value="Save portlet changes"/>
</ui:tablecell>
</ui:tablerow>
</ui:frame>

</ui:panel>

</ui:form>