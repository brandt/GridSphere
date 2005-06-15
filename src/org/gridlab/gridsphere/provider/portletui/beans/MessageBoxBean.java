package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

public class MessageBoxBean extends BaseComponentBean implements TagBean {

    public static final String NAME = "mb";

    private List text = new ArrayList();
    private List keys = new ArrayList();
    private String messageType = MessageStyle.MSG_INFO;
    private String width = null;
    private String height = null;
    private String imageSource = null;


    public MessageBoxBean() {
        super(NAME);
    }

    public MessageBoxBean(String beanId) {
        super(NAME);
        this.beanId = beanId;
    }

    public MessageBoxBean(HttpServletRequest req, String beanId) {
        super(NAME, req);
        this.beanId = beanId;
    }

    /**
     * Gets the source of the image which should be displayed.
     *
     * @return imagesource
     */
    public String getImageSource() {
        return imageSource;
    }

    /**
     * Sets the source of the image displayed along with the messagebox.
     *
     * @param imageSource image source
     */
    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    /**
     * Enables/disables standard images depending on the style of the messagebox.
     *
     * @param images yes or no  if images should be displayed
     */
    public void setDefaultImage(boolean images){
        String gswebapp = GridSphereConfig.PROJECT_NAME;
        if (images) {
            this.imageSource =  new StringBuffer().append(File.separator).append(gswebapp).
                    append(File.separator).append("images").append(File.separator).append("msgicons").
                    append(File.separator).append(messageType).append(".gif").toString();
        } else {
            this.imageSource = null;
        }
    }

    /**
     * Returns the width of the messagebox
     *
     * @return width of the messagebox
     */
    public String getWidth() {
        return width;
    }

    /**
     * Sets the value of the message to be displayed. All other messages or deleted.
     *
     * @param text text to be displayed
     */
    public void setValue(String text) {
        this.text = new ArrayList();
        this.keys = new ArrayList();
        this.text.add(text);
    }

    /**
     * Returns the text of the messagebox.
     *
     * @return text of the messagebox
     */
    public String getValue() {
        return getMessage(false);
    }

    /**
     * Sets the width of the messgebox
     *
     * @param width witdh of the messagebox
     */
    public void setWidth(String width) {
        this.width = width;
    }

    /**
     * Gets the height of the messagebox
     *
     * @return height of the messagebox
     */
    public String getHeight() {
        return height;
    }

    /**
     * Sets the height of the messageboox
     *
     * @param height height of the messagebox
     */
    public void setHeight(String height) {
        this.height = height;
    }

    /**
     * Returns the type of the message.
     *
     * @return type of the message
     * @see TextBean
     */
    public String getMessageType() {
        return messageType;
    }

    /**
     * Sets the type of the message
     *
     * @param messageType
     * @see TextBean
     */
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    /**
     * Appends text to the messagebox.
     *
     * @param text text to append
     */
    public void appendText(String text) {
        this.text.add(text);
    }

    /**
     * Prepends text to the current text.
     *
     * @param text text to prepend
     */
    public void prependText(String text) {
        this.text.add(0, text);
    }

    /**
     * Appends a localisation key to the current list of keys.
     *
     * @param key key to append
     */
    public void appendKey(String key) {
        this.keys.add(key);
    }

    /**
     * Prepends a localisation key to the current list of keys.
     *
     * @param key key to prepend
     */
    public void prependKey(String key) {
        this.keys.add(0, key);
    }

    /**
     * Removes a key from the list.
     *
     * @param key key to remove
     */
    public void removeKey(String key) {
        this.keys.remove(key);
    }

    /**
     * Removes the text from the list.
     *
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
     *
     * @param format format as html output
     * @return text of the messagebox
     */
    private String getMessage(boolean format) {
        String result = null;

        List list = text;
        if (keys.size() != 0) {
            list = keys;
        }
        if (list.size() != 0) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < list.size(); i++) {

                String message = (String) list.get(i);
                if (keys.size() != 0) {
                    message = getLocalizedText(message);
                }

                if (format) sb.append("<div class=\"" + messageType + "\">" + message + "</div>");
                    else sb.append(message);
            }
            result = sb.toString();
        }
        return result;

    }

    public String toStartString() {
        return "";
    }

    public String toEndString() {
        this.cssClass = "ui-messagebox";
        StringBuffer sb = new StringBuffer();
        String message = getMessage(true);
        if (height != null) this.addCssStyle("height=\"" + height + "\"");
        if (width != null) this.addCssStyle("width=\"" + width + "\"");

        // only return something if we have a message
        if (message != null) {
            sb.append("<div" + getFormattedCss() + ">");
            if (imageSource!=null) {
                sb.append("<table><tr><td valign=\"top\"><img src=\""+imageSource+"\"></td><td>");
                sb.append(message);
                sb.append("</td></tr></table>");
            } else {
                sb.append(message);
            }
            sb.append("</div>");
        }

        return sb.toString();
    }


}
