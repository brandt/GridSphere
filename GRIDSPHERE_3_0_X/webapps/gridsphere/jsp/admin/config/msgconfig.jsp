<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>


<ui:messagebox beanId="msg"/>
<ui:form>
    <ui:group key="LOGIN_CONFIG_FORGET">
        <ui:frame>
            <ui:tablerow>
                <ui:tablecell>Enter e-mail subject:</ui:tablecell>
                <ui:tablecell>
                    <ui:textfield size="50" beanId="forgotHeaderTF"/>
                </ui:tablecell>
            </ui:tablerow>
            <ui:tablerow>
                <ui:tablecell>Enter e-mail body:<br> <span style="font-size: smaller; color: red">(links will be placed in the body automatically)</span></ui:tablecell>
                <ui:tablecell>
                    <ui:textarea cols="50" beanId="forgotBodyTA"/>
                </ui:tablecell>
            </ui:tablerow>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:actionsubmit action="doSaveMailMessage" key="SAVE">
                        <ui:actionparam name="type" value="forgot"/>
                    </ui:actionsubmit>
                </ui:tablecell>
            </ui:tablerow>
        </ui:frame>
    </ui:group>
</ui:form>

<ui:form>
    <ui:group key="LOGIN_CONFIG_ACTIVATE">
        <ui:frame>
            <ui:tablerow>
                <ui:tablecell>Enter e-mail subject:</ui:tablecell>
                <ui:tablecell>
                    <ui:textfield size="50" beanId="activateHeaderTF"/>
                </ui:tablecell>
            </ui:tablerow>
            <ui:tablerow>
                <ui:tablecell>Enter e-mail body:</ui:tablecell>
                <ui:tablecell>
                    <ui:textarea cols="50" beanId="activateBodyTA"/>
                </ui:tablecell>
            </ui:tablerow>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:actionsubmit action="doSaveMailMessage" key="SAVE">
                        <ui:actionparam name="type" value="activate"/>
                    </ui:actionsubmit>
                </ui:tablecell>
                <ui:tablecell/>
            </ui:tablerow>
        </ui:frame>
    </ui:group>
</ui:form>

<ui:form>
    <ui:group key="LOGIN_CONFIG_APPROVED">
        <ui:frame>
            <ui:tablerow>
                <ui:tablecell>Enter e-mail subject:</ui:tablecell>
                <ui:tablecell>
                    <ui:textfield size="50" beanId="approvedHeaderTF"/>
                </ui:tablecell>
            </ui:tablerow>
            <ui:tablerow>
                <ui:tablecell>Enter e-mail body:</ui:tablecell>
                <ui:tablecell>
                    <ui:textarea cols="50" beanId="approvedBodyTA"/>
                </ui:tablecell>
            </ui:tablerow>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:actionsubmit action="doSaveMailMessage" key="SAVE">
                        <ui:actionparam name="type" value="approved"/>
                    </ui:actionsubmit>
                </ui:tablecell>
                <ui:tablecell/>
            </ui:tablerow>
        </ui:frame>
    </ui:group>
</ui:form>

<ui:form>
    <ui:group key="LOGIN_CONFIG_DENIED">
        <ui:frame>
            <ui:tablerow>
                <ui:tablecell>Enter e-mail subject:</ui:tablecell>
                <ui:tablecell>
                    <ui:textfield size="50" beanId="deniedHeaderTF"/>
                </ui:tablecell>
            </ui:tablerow>
            <ui:tablerow>
                <ui:tablecell>Enter e-mail body:</ui:tablecell>
                <ui:tablecell>
                    <ui:textarea cols="50" beanId="deniedBodyTA"/>
                </ui:tablecell>
            </ui:tablerow>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:actionsubmit action="doSaveMailMessage" key="SAVE">
                        <ui:actionparam name="type" value="denied"/>
                    </ui:actionsubmit>
                </ui:tablecell>
                <ui:tablecell/>
            </ui:tablerow>
        </ui:frame>
    </ui:group>
</ui:form>