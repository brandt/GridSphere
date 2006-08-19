<portlet-tabbed-pane>
    <portlet-tab label="@PROJECT_TITLE@">
	<title lang="en">@PROJECT_TITLE@</title>
        <portlet-tabbed-pane style="sub-menu">
            <portlet-tab label="hellotab">
		<title lang="en">Hello World!</title>
		<title lang="de">Hallo Welt!</title>
                <table-layout>
                    <row-layout>
                        <column-layout>
                            <portlet-frame label="hello">
                                <portlet-class>org.gridsphere.portlets.examples.HelloWorld.1</portlet-class>
                            </portlet-frame>
                        </column-layout>
                        <column-layout>
                            <portlet-frame>
                                <portlet-class>org.gridsphere.portlets.examples.HalloWelt.1</portlet-class>
                            </portlet-frame>
                        </column-layout>
                    </row-layout>
                </table-layout>
            </portlet-tab>
        </portlet-tabbed-pane>
    </portlet-tab>
</portlet-tabbed-pane>
