/*
 * @author <a href="mailto:wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @team sonicteam
 * @version $Id: LanguageInfo.java 4496 2006-02-08 20:27:04Z wehrens $
 */

package org.gridsphere.portletcontainer.impl.descriptor;


public class LanguageInfo {

    private String locale = "";
    private String keywords = "";
    private String description = "";
    private String title = "";
    private String titleShort = "";

    /**
     * Creates an instance of LanguageInfo
     */
    public LanguageInfo() {
    }

    /**
     * Returns the description of the portlet
     *
     * @return the description of the portlet
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
     * @return the keywords of the portlet
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
     * @return the locale of the portlet
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
     * @return the title
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
     * @return the title
     */
    public String getTitleShort() {
        return titleShort;
    }

    /**
     * sets the title
     *
     * @param titleShort the title
     */
    public void setTitleShort(String titleShort) {
        this.titleShort = titleShort;
    }

}

