/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.2</a>, using an XML
 * Schema.
 * $Id: PortletTypeChoiceSequence.java 4496 2006-02-08 20:27:04Z wehrens $
 */

package org.gridsphere.portletcontainer.jsrimpl.descriptor;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class PortletTypeChoiceSequence.
 * 
 * @version $Revision: 3298 $ $Date: 2004-06-29 07:19:44 -0700 (Tue, 29 Jun 2004) $
 */
public class PortletTypeChoiceSequence implements java.io.Serializable {


    //--------------------------/
    //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _resourceBundle
     */
    private org.gridsphere.portletcontainer.jsrimpl.descriptor.ResourceBundle _resourceBundle;

    /**
     * Field _portletInfo
     */
    private org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletInfo _portletInfo;


    //----------------/
    //- Constructors -/
    //----------------/

    public PortletTypeChoiceSequence() {
        super();
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.PortletTypeChoiceSequence()


    //-----------/
    //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'portletInfo'.
     * 
     * @return the value of field 'portletInfo'.
     */
    public org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletInfo getPortletInfo() {
        return this._portletInfo;
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.PortletInfo getPortletInfo()

    /**
     * Returns the value of field 'resourceBundle'.
     * 
     * @return the value of field 'resourceBundle'.
     */
    public org.gridsphere.portletcontainer.jsrimpl.descriptor.ResourceBundle getResourceBundle() {
        return this._resourceBundle;
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.ResourceBundle getResourceBundle()

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
     * Sets the value of field 'portletInfo'.
     * 
     * @param portletInfo the value of field 'portletInfo'.
     */
    public void setPortletInfo(org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletInfo portletInfo) {
        this._portletInfo = portletInfo;
    } //-- void setPortletInfo(org.gridsphere.portletcontainer.jsr.descriptor.PortletInfo)

    /**
     * Sets the value of field 'resourceBundle'.
     * 
     * @param resourceBundle the value of field 'resourceBundle'.
     */
    public void setResourceBundle(org.gridsphere.portletcontainer.jsrimpl.descriptor.ResourceBundle resourceBundle) {
        this._resourceBundle = resourceBundle;
    } //-- void setResourceBundle(org.gridsphere.portletcontainer.jsr.descriptor.ResourceBundle)

    /**
     * Method unmarshal
     *
     * @param reader
     */
    public static java.lang.Object unmarshal(java.io.Reader reader)
            throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletTypeChoiceSequence) Unmarshaller.unmarshal(org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletTypeChoiceSequence.class, reader);
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
