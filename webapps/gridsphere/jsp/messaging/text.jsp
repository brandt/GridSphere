<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>


<ui:form>

    <ui:frame>
        <ui:tablerow>
            <ui:tablecell>
                <ui:text value="Send"/>
            </ui:tablecell>
            <ui:tablecell>
                 <ui:textfield beanId="message" size="50"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell>
                <ui:text value=" to "/>
            </ui:tablecell>
            <ui:tablecell>
                <ui:listbox beanId="userlist"/>
            </ui:tablecell>
        </ui:tablerow>

        <ui:tablerow>
            <ui:tablecell>
                <ui:text value="via "/>
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
                <ui:actionsubmit action="sendIM" value="Send it"/>
            </ui:tablecell>
        </ui:tablerow>

    </ui:frame>

</ui:form>
