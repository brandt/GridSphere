<ui:form>
    <ui:panel>
        <ui:frame>
            <ui:tablerow>
                <ui:tablecell>
                    <ui:errortext beanId="errorMsg"/>
                </ui:tablecell>
            </ui:tablerow>
        </ui:frame>

        <ui:frame>
            <ui:tablerow>
                <ui:tablecell width="100">
                    <ui:text key="LOGIN_NAME"/>
                </ui:tablecell>
                <ui:tablecell width="60">
                    <ui:textfield name="username" size="20" maxlength="20"/>
                </ui:tablecell>
                <ui:tablecell/>
            </ui:tablerow>
        </ui:frame>

        <ui:frame>
            <ui:tablerow>
                <ui:tablecell width="100">
                    <ui:text key="LOGIN_PASS"/>
                </ui:tablecell>
                <ui:tablecell width="60">
                    <ui:password name="password" size="20" maxlength="20"/>
                </ui:tablecell>
                <ui:tablecell/>
            </ui:tablerow>
        </ui:frame>

        <ui:frame>
            <ui:tablerow>
                <ui:tablecell width="100">
                    <ui:actionsubmit action="gs_login" name="login" key="LOGIN_ACTION"/>
                </ui:tablecell>
                <ui:tablecell/>
            </ui:tablerow>
        </ui:frame>
    </ui:panel>
</ui:form>
