<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<ui:frame beanId="errorFrame"/>

<ui:form>

    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text key="MESSAGING_SEND"/>
            </ui:tablecell>
            <ui:tablecell>
                 <ui:textfield beanId="message" size="50"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell>
                <ui:text key="MESSAGING_TO"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:listbox beanId="userlist"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell>
                <ui:text key="MESSAGING_VIA"/>
            </ui:tablecell>
            <ui:tablecell>
                 <ui:listbox beanId="services"/>
            </ui:tablecell>
        </ui:tablerow>


        <ui:tablerow>
            <ui:tablecell>
                <ui:text value="&nbsp;"/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:actionsubmit action="sendIM" key="MESSAGING_SENDIT"/>
            </ui:tablecell>
        </ui:tablerow>

    </ui:frame>

</ui:form>
