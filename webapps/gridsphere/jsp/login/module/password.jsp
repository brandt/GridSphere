<ui:frame>
    <ui:tablerow>
        <ui:tablecell width="5%">
            <ui:checkbox beanId="passCheck" name="auth_module" value="Hello" selected="<%= active %>"/>
        </ui:tablecell>
        <ui:tablecell width="45%">
            <ui:text key="LOGIN_PASS_MODULE"/>
        </ui:tablecell>
        <ui:tablecell width="50%"/>
    </ui:tablerow>
</ui:frame>