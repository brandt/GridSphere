<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>
<ui:form>

<ui:hiddenfield beanId="tabHF" value=""/>
<ui:hiddenfield beanId="subtabHF" value=""/>

<ui:panel cellpadding="10">
<ui:frame>
<ui:tablerow>
<ui:tablecell width="15%">
 <ui:text key="LAYOUT_THEME"/>
</ui:tablecell>
<ui:tablecell>
<ui:text key="LAYOUT_SELECT_THEME"/>&nbsp;<ui:listbox beanId="themeLB"/> <ui:actionsubmit action="saveTheme" key="LAYOUT_THEME_SAVE"/>
</ui:tablecell>
</ui:tablerow>
</ui:frame>
</ui:panel>

<ui:panel cellpadding="10">
<ui:frame>
<ui:tablerow>
<ui:tablecell width="15%">
<ui:text key="LAYOUT_TAB_CONFIG"/>
</ui:tablecell>
<ui:tablecell>
<ui:text key="LAYOUT_TAB_EDIT"/>&nbsp;
</ui:tablecell>
<ui:tablecell beanId="tabsTC"/>
<ui:tablecell>
<ui:actionsubmit action="saveTabs" key="LAYOUT_APPLY"/>
</ui:tablecell>
</ui:tablerow>
<ui:tablerow>
<ui:tablecell width="15%">
<ui:text key="LAYOUT_TAB_DEL"/>
</ui:tablecell>
<ui:tablecell>
<ui:listbox beanId="deltabsLB"/>
 </ui:tablecell>
<ui:tablecell>
<ui:text style="alert" key="LAYOUT_WARNING"/>
</ui:tablecell>
<ui:tablecell>
<ui:actionsubmit action="deleteTab" key="LAYOUT_TAB_DEL"/>
</ui:tablecell>

</ui:tablerow>
<ui:tablerow>
<ui:tablecell width="15%">
<ui:text key="LAYOUT_NEW_TAB"/>
</ui:tablecell>
<ui:tablecell>
<ui:text key="LAYOUT_TAB_EDIT2"/>
</ui:tablecell>
<ui:tablecell>
<ui:textfield beanId="newTab"/>
</ui:tablecell>
<ui:tablecell>
<ui:actionsubmit action="createTab" key="LAYOUT_NEW_TAB"/>
</ui:tablecell>
</ui:tablerow>
</ui:frame>
</ui:panel>

<ui:panel cellpadding="10">
<ui:frame>
<ui:tablerow>
<ui:tablecell width="15%">
<ui:text key="LAYOUT_SUBTAB_CONFIG"/>
</ui:tablecell>
<ui:tablecell>
<ui:listbox beanId="seltabsLB"/>
<ui:actionsubmit action="selectTab" key="LAYOUT_TAB_SELECT"/>
</ui:tablecell>
<ui:tablecell/>
</ui:tablerow>
<ui:tablerow>
<ui:tablecell width="15%">
<ui:text key="LAYOUT_SUBTAB_EDIT"/>&nbsp;
</ui:tablecell>
<ui:tablecell beanId="subtabsTC"/>
<ui:tablecell>
<ui:actionsubmit action="saveSubTabs" key="LAYOUT_APPLY"/>
</ui:tablecell>

</ui:tablerow>

<ui:tablerow>
<ui:tablecell width="15%">
<ui:text key="LAYOUT_SUBTAB_DEL"/>
</ui:tablecell>
<ui:tablecell>
<ui:listbox beanId="delsubtabsLB"/>
<ui:text style="alert" key="LAYOUT_WARNING"/>
</ui:tablecell>
<ui:tablecell>
<ui:actionsubmit action="deleteTab" key="LAYOUT_SUBTAB_DEL"/>
</ui:tablecell>
</ui:tablerow>
<ui:tablerow>
<ui:tablecell width="15%">
<ui:text key="LAYOUT_NEW_SUBTAB"/>
</ui:tablecell>
<ui:tablecell>
<ui:text key="LAYOUT_SUBTAB_EDIT2"/>
<ui:textfield beanId="newSubTab"/>
</ui:tablecell>
<ui:tablecell>
<ui:actionsubmit action="createSubTab" key="LAYOUT_NEW_SUBTAB"/>
</ui:tablecell>

</ui:tablerow>
</ui:frame>

<ui:frame>
<ui:tablerow>
<ui:tablecell width="15%"><ui:text key="LAYOUT_PORTLET_CONF"/></ui:tablecell>
<ui:tablecell>
<ui:listbox beanId="selsubtabsLB"/>
<ui:actionsubmit action="selectSubTab" key="LAYOUT_SUBTAB_SEL"/>
</ui:tablecell>
</ui:tablerow>
<ui:tablerow>
<ui:tablecell width="15%">
<ui:text key="LAYOUT_TABLE"/>
</ui:tablecell>
<ui:tablecell>
<ui:actionsubmit action="addTableRow" key="LAYOUT_ADD_ROW"/>
</ui:tablecell>
</ui:tablerow>
</ui:frame>

<ui:frame beanId="portletLayout"/>

<ui:frame>
<ui:tablerow>
<ui:tablecell>
<ui:actionsubmit action="savePortletFrames" key="LAYOUT_SAVE"/>
</ui:tablecell>
</ui:tablerow>
</ui:frame>

</ui:panel>

</ui:form>
