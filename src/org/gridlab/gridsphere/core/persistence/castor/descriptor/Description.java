/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.core.persistence.castor.descriptor;

import java.io.Serializable;
import java.util.Locale;

/**
 * <code>Description</code> is used to store a locale dependent titloe or description
 */
public class Description implements Serializable, Cloneable {

    private String lang = Locale.ENGLISH.getLanguage();
    private String text = "";

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

    public Object clone() throws CloneNotSupportedException {
        Description t = (Description) super.clone();
        t.lang = this.lang;
        t.text = this.text;
        return t;
    }

}
