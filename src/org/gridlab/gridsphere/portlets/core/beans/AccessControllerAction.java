/*
 * Created by IntelliJ IDEA.
 * User: russell
 * Date: Feb 5, 2003
 * Time: 2:28:41 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.portlets.core.beans;

import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.PortletURI;
import org.gridlab.gridsphere.portlet.DefaultPortletAction;
import org.gridlab.gridsphere.portlet.PortletRequest;

public class AccessControllerAction extends PortletActionConstant {

    public static final AccessControllerAction ACTION_GROUP_LIST = new AccessControllerAction("groupList");
    public static final AccessControllerAction ACTION_GROUP_VIEW = new AccessControllerAction("groupView");
    public static final AccessControllerAction ACTION_GROUP_EDIT = new AccessControllerAction("groupEdit");
    public static final AccessControllerAction ACTION_GROUP_EDIT_CONFIRM = new AccessControllerAction("groupEditConfirm");
    public static final AccessControllerAction ACTION_GROUP_EDIT_CANCEL = new AccessControllerAction("groupEditCancel");
    public static final AccessControllerAction ACTION_GROUP_DELETE = new AccessControllerAction("groupDelete");
    public static final AccessControllerAction ACTION_GROUP_DELETE_CONFIRM = new AccessControllerAction("groupDeleteConfirm");
    public static final AccessControllerAction ACTION_GROUP_DELETE_CANCEL = new AccessControllerAction("groupDeleteCancel");

    public static final AccessControllerAction ACTION_GROUP_ENTRY_VIEW = new AccessControllerAction("groupEntryView");
    public static final AccessControllerAction ACTION_GROUP_ENTRY_EDIT = new AccessControllerAction("groupEntryEdit");
    public static final AccessControllerAction ACTION_GROUP_ENTRY_EDIT_CONFIRM = new AccessControllerAction("groupEntryConfirm");
    public static final AccessControllerAction ACTION_GROUP_ENTRY_EDIT_CANCEL = new AccessControllerAction("groupEntryCancel");
    public static final AccessControllerAction ACTION_GROUP_ENTRY_ADD = new AccessControllerAction("groupEntryAdd");
    public static final AccessControllerAction ACTION_GROUP_ENTRY_ADD_CONFIRM = new AccessControllerAction("groupEntryAddConfirm");
    public static final AccessControllerAction ACTION_GROUP_ENTRY_ADD_CANCEL = new AccessControllerAction("groupEntryAddCancel");
    public static final AccessControllerAction ACTION_GROUP_ENTRY_REMOVE = new AccessControllerAction("groupEntryRemove");
    public static final AccessControllerAction ACTION_GROUP_ENTRY_REMOVE_CONFIRM = new AccessControllerAction("groupEntryRemoveConfirm");
    public static final AccessControllerAction ACTION_GROUP_ENTRY_REMOVE_CANCEL = new AccessControllerAction("groupEntryRemoveCancel");

    protected AccessControllerAction(String name) {
        super(name);
    }
}
