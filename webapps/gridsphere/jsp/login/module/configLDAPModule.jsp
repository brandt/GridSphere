<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<ui:form>
    <ui:panel>

        <ui:frame>
            <ui:tablerow>
                <ui:tablecell width="100">
                    <ui:text value="Enter LDAP Host: "/>
                </ui:tablecell>
                <ui:tablecell width="60">
                    <ui:textfield beanId="ldapHost" size="20" maxlength="20"/>
                </ui:tablecell>
                <ui:tablecell/>
            </ui:tablerow>
        </ui:frame>

        <ui:frame>
            <ui:tablerow>
                <ui:tablecell width="100">
                    <ui:text value="Enter LDAP Base DN: "/>
                </ui:tablecell>
                <ui:tablecell width="60">
                    <ui:textfield beanId="baseDN" size="20" maxlength="20"/>
                </ui:tablecell>
                <ui:tablecell/>
            </ui:tablerow>
        </ui:frame>

        <ui:frame>
            <ui:tablerow>
                <ui:tablecell width="100">
                    <ui:actionsubmit action="saveLdapModule" value="Apply"/>
                </ui:tablecell>
                <ui:tablecell/>
            </ui:tablerow>
        </ui:frame>
    </ui:panel>
</ui:form>
