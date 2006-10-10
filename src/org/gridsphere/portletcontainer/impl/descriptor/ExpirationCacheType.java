/*
 * This class was automatically generated with
 * <a href="http://www.castor.org">Castor 0.9.5.2</a>, using an XML
 * Schema.
 * $Id: ExpirationCacheType.java 4496 2006-02-08 20:27:04Z wehrens $
 */

package org.gridsphere.portletcontainer.impl.descriptor;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Expriation-cache defines expiration-based caching for this
 * portlet. The parameter indicates
 * the time in seconds after which the portlet output expires.
 * -1 indicates that the output never expires.
 * Used in: portlet
 *
 * @version $Revision: 3298 $ $Date: 2004-06-29 07:19:44 -0700 (Tue, 29 Jun 2004) $
 */
public class ExpirationCacheType implements java.io.Serializable {


    //--------------------------/
    //- Class/Member Variables -/
    //--------------------------/

    /**
     * internal content storage
     */
    private int _content;

    /**
     * keeps track of state for field: _content
     */
    private boolean _has_content;


    //----------------/
    //- Constructors -/
    //----------------/

    public ExpirationCacheType() {
        super();
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.ExpirationCacheType()


    //-----------/
    //- Methods -/
    //-----------/

    /**
     * Method deleteContent
     */
    public void deleteContent() {
        this._has_content = false;
    } //-- void deleteContent()

    /**
     * Returns the value of field 'content'. The field 'content'
     * has the following description: internal content storage
     *
     * @return the value of field 'content'.
     */
    public int getContent() {
        return this._content;
    } //-- int getContent()

    /**
     * Method hasContent
     */
    public boolean hasContent() {
        return this._has_content;
    } //-- boolean hasContent()

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
    public void setContent(int content) {
        this._content = content;
        this._has_content = true;
    } //-- void setContent(int)

    /**
     * Method unmarshal
     *
     * @param reader
     */
    public static java.lang.Object unmarshal(java.io.Reader reader)
            throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.gridsphere.portletcontainer.impl.descriptor.ExpirationCacheType) Unmarshaller.unmarshal(org.gridsphere.portletcontainer.impl.descriptor.ExpirationCacheType.class, reader);
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
