package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;

import java.util.List;
import java.util.ArrayList;


/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

public class MessageBoxBean extends BaseComponentBean implements TagBean {

    public static final String NAME = "mb";

    private List text = new ArrayList();
    private List keys = new ArrayList();
    private String messageType = TextBean.MSG_INFO;

    public MessageBoxBean() {
        super(NAME);
    }

    public MessageBoxBean(String beanId) {
        super(NAME);
        this.beanId = beanId;
    }

    public MessageBoxBean(PortletRequest req, String beanId) {
        super(NAME);
        this.beanId = beanId;
        this.request = req;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    /**
     * Appends text to the messagebox.
     * @param text text to append
     */
    public void appendText(String text) {
        this.text.add(text);
    }

    /**
     * Prepends text to the current text.
     * @param text text to prepend
     */
    public void prependText(String text) {
        this.text.add(0, text);
    }

    /**
     * Appends a localisation key to the current list of keys.
     * @param key key to append
     */
    public void appendKey(String key) {
        this.keys.add(key);
    }

    /**
     * Prepends a localisation key to the current list of keys.
     * @param key key to prepend
     */
    public void prependKey(String key) {
        this.keys.add(0, key);
    }

    /**
     * Removes a key from the list.
     * @param key key to remove
     */
    public void removeKey(String key) {
        this.keys.remove(key);
    }

    /**
     * Removes the text from the list.
     * @param text text to be removed
     */
    public void removeText(String text) {
        this.text.remove(text);
    }

    /**
     * Clears the message. This includes the text and the keys for localization.
     */
    public void clearMessage() {
       this.text = new ArrayList();
       this.keys = new ArrayList();
    }


    /**
     * Returns the message text. If localized keys were specified the translated (and only those) are returned.
     * @return
     */
    private String getMessage() {
        String result = null;

        List list = text;
        if (keys.size()!=0) {
            list = keys;
        }
        if (list.size()!=0) {
            StringBuffer sb = new StringBuffer();
            for (int i=0;i<list.size();i++) {

                String message = (String)list.get(i);
                if (keys.size()!=0) {
                    message = getLocalizedText(message);
                }
                sb.append("<div class=\""+messageType+"\">"+message+"</div>");
            }
            result = sb.toString();
        }
        return result;

    }

    public String toStartString() {
        return "";
    }

    public String toEndString() {
        this.cssClass="ui-messagebox";
        StringBuffer sb = new StringBuffer();
        String message = getMessage();
        // only return something if we have a message
        if (message!=null) {
            sb.append("<div"+getFormattedCss()+">");
            sb.append(message);
            sb.append("</div>");
        }

        return sb.toString();
    }


}
