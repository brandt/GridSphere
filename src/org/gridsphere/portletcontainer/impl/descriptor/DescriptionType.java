/*
 * This class was automatically generated with
 * <a href="http://www.castor.org">Castor 0.9.5.2</a>, using an XML
 * Schema.
 * $Id$
 */

package org.gridsphere.portletcontainer.impl.descriptor;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * The description element is used to provide text describing the
 * parent element. The description element should include any
 * information that the portlet application war file producer
 * wants
 * to provide to the consumer of the portlet application war file
 * (i.e., to the Deployer). Typically, the tools used by the
 * portlet application war file consumer will display the
 * description when processing the parent element that contains
 * the
 * description. It has an optional attribute xml:lang to indicate
 * which language is used in the description according to
 * RFC 1766 (http://www.ietf.org/rfc/rfc1766.txt). The default
 * value of this attribute is English (en).
 * Used in: init-param, portlet, portlet-app, security-role
 *
 * @version $Revision: 4299 $ $Date: 2005-10-15 12:24:07 -0700 (Sat, 15 Oct 2005) $
 */
public class DescriptionType implements java.io.Serializable {


    //--------------------------/
    //- Class/Member Variables -/
    //--------------------------/

    /**
     * internal content storage
     */
    private java.lang.String _content = "";

    /**
     * In due course, we should install the relevant ISO 2- and
     * 3-letter
     * codes as the enumerated possible values . . .
     */
    private java.lang.String _lang;


    //----------------/
    //- Constructors -/
    //----------------/

    public DescriptionType() {
        super();
        setContent("");
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.DescriptionType()


    //-----------/
    //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'content'. The field 'content'
     * has the following description: internal content storage
     *
     * @return the value of field 'content'.
     */
    public java.lang.String getContent() {
        return this._content;
    } //-- java.lang.String getContent()

    /**
     * Returns the value of field 'lang'. The field 'lang' has the
     * following description: In due course, we should install the
     * relevant ISO 2- and 3-letter
     * codes as the enumerated possible values . . .
     *
     * @return the value of field 'lang'.
     */
    public java.lang.String getLang() {
        return this._lang;
    } //-- java.lang.String getLang()

    /**
     * Method isValid
     */
    public boolean isValid() {
        try {
            validate();
        } catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    } //-- boolean isValid()

    /**
     * Method marshal
     *
     * @param out
     */
    public void marshal(java.io.Writer out)
            throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {

        Marshaller.marshal(this, out);
    } //-- void marshal(java.io.Writer)

    /**
     * Method marshal
     *
     * @param handler
     */
    public void marshal(org.xml.sax.ContentHandler handler)
            throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {

        Marshaller.marshal(this, handler);
    } //-- void marshal(org.xml.sax.ContentHandler)

    /**
     * Sets the value of field 'content'. The field 'content' has
     * the following description: internal content storage
     *
     * @param content the value of field 'content'.
     */
    public void setContent(java.lang.String content) {
        this._content = content;
    } //-- void setContent(java.lang.String)

    /**
     * Sets the value of field 'lang'. The field 'lang' has the
     * following description: In due course, we should install the
     * relevant ISO 2- and 3-letter
     * codes as the enumerated possible values . . .
     *
     * @param lang the value of field 'lang'.
     */
    public void setLang(java.lang.String lang) {
        this._lang = lang;
    } //-- void setLang(java.lang.String)

    /**
     * Method unmarshal
     *
     * @param reader
     */
    public static java.lang.Object unmarshal(java.io.Reader reader)
            throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.gridsphere.portletcontainer.impl.descriptor.DescriptionType) Unmarshaller.unmarshal(org.gridsphere.portletcontainer.impl.descriptor.DescriptionType.class, reader);
    } //-- java.lang.Object unmarshal(java.io.Reader)

    /**
     * Method validate
     */
    public void validate()
            throws org.exolab.castor.xml.ValidationException {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate()

}
