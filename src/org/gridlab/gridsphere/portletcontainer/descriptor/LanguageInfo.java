/*
 * @author <a href="mailto:wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.gridsphere.portletcontainer.descriptor;

import java.util.Vector;
import java.util.List;

public class LanguageInfo {

    private String locale = new String();
    private String keywords = new String();
    private String description = new String();
    private String title = new String();
    private String titleShort = new String();

    /**
     * Returns the description of the portlet
     *
     * @returns description of the portlet
     */
    public String getDescription() {
        return description;
    }

    /**
     * sets the name of the portlet
     *
     * @param description the description of the portlet
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the keywords of the portlet
     *
     * @returns keywords of the portlet
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     * sets the keywords of the portlet
     *
     * @param keywords the keywords of the portlet
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    /**
     * Returns the locale of the portlet
     *
     * @returns locale of the portlet
     */
    public String getLocale() {
        return locale;
    }

    /**
     * sets the locale of the portlet
     *
     * @param locale the locale of the portlet
     */
    public void setLocale(String locale) {
        this.locale = locale;
    }

    /**
     * gets the title
     *
     * @returns the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * sets the title
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * gets the title
     *
     * @returns the title
     */
    public String getTitleShort() {
        return titleShort;
    }

    /**
     * sets the title
     *
     * @param title the title
     */
    public void setTitleShort(String titleShort) {
        this.titleShort = titleShort;
    }

}

