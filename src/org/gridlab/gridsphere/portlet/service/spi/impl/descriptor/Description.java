/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: Description.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridlab.gridsphere.portlet.service.spi.impl.descriptor;

import java.io.Serializable;
import java.util.Locale;

/**
 * <code>Description</code> is used to store a locale dependent titloe or description
 */
public class Description implements Serializable, Cloneable {

    private String lang = Locale.ENGLISH.getLanguage();
    private String text = "";
    private String key = "";

    /**
     * Constructs an instance of a Description
     */
    public Description() {
    }

    /**
     * Sets the language locale of this text
     *
     * @param lang the relative path to load a text file
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * Returns the language locale of this text
     *
     * @return the language locale of this text
     */
    public String getLang() {
        return lang;
    }

    /**
     * Sets the key of this text
     *
     * @param key the key of this text
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Returns the key of this text
     *
     * @return the key of this text
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the text
     *
     * @param text the text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Returns the text
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    public String toString() {
        return this.text;        
    }

    public Object clone() throws CloneNotSupportedException {
        Description t = (Description) super.clone();
        t.lang = this.lang;
        t.text = this.text;
        t.key = this.key;
        return t;
    }

}
