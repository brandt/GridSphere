/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */

package org.gridsphere.provider.portletui.beans;

import java.util.Locale;

/**
 * The <code>TagBean</code> interface describes the base methods provided by all visual ui beans
 */
public interface TagBean {

    public static final String CALENDAR_NAME = "ca";

    public static final String CHECKBOX_NAME = "cb";

    public static final String FILEINPUT_NAME = "fi";

    public static final String HIDDENFIELD_NAME = "hf";

    public static final String LISTBOXITEM_NAME = "li";

    public static final String TEXTFIELD_NAME = "tf";

    public static final String TEXTAREA_NAME = "ta";

    public static final String TEXTEDITOR_NAME = "te";

    public static final String PASSWORD_NAME = "pb";

    public static final String RADIOBUTTON_NAME = "rb";

    public static final String RICHTEXTEDITOR_NAME = "rt";

    /**
     * Returns the bean identifier
     *
     * @return the bean identifier
     */
    public String getBeanId();

    public void addParam(String name, String value);

    public void removeParam(String name);

    /**
     * Sets the bean identifier
     *
     * @param beanId the bean identifier
     */
    public void setBeanId(String beanId);

    public void setLocale(Locale locale);

    /**
     * Returns the HTML representation of the bean
     *
     * @return html string representing the object
     */
    public String toStartString();

    /**
     * Returns the HTML representation of the bean
     *
     * @return html string representing the object
     */
    public String toEndString();


}
