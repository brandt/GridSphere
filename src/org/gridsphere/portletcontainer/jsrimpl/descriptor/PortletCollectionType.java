/*
 * This class was automatically generated with
 * <a href="http://www.castor.org">Castor 0.9.5.2</a>, using an XML
 * Schema.
 * $Id: PortletCollectionType.java 4496 2006-02-08 20:27:04Z wehrens $
 */

package org.gridsphere.portletcontainer.jsrimpl.descriptor;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

import java.util.ArrayList;

/**
 * The portlet-collectionType is used to identify a subset
 * of portlets within a portlet application to which a
 * security constraint applies.
 * Used in: security-constraint
 *
 * @version $Revision: 3298 $ $Date: 2004-06-29 07:19:44 -0700 (Tue, 29 Jun 2004) $
 */
public class PortletCollectionType implements java.io.Serializable {


    //--------------------------/
    //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _portletNameList
     */
    private java.util.ArrayList _portletNameList;


    //----------------/
    //- Constructors -/
    //----------------/

    public PortletCollectionType() {
        super();
        _portletNameList = new ArrayList();
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.PortletCollectionType()


    //-----------/
    //- Methods -/
    //-----------/

    /**
     * Method addPortletName
     *
     * @param vPortletName
     */
    public void addPortletName(org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletName vPortletName)
            throws java.lang.IndexOutOfBoundsException {
        _portletNameList.add(vPortletName);
    } //-- void addPortletName(org.gridsphere.portletcontainer.jsr.descriptor.PortletName)

    /**
     * Method addPortletName
     *
     * @param index
     * @param vPortletName
     */
    public void addPortletName(int index, org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletName vPortletName)
            throws java.lang.IndexOutOfBoundsException {
        _portletNameList.add(index, vPortletName);
    } //-- void addPortletName(int, org.gridsphere.portletcontainer.jsr.descriptor.PortletName)

    /**
     * Method clearPortletName
     */
    public void clearPortletName() {
        _portletNameList.clear();
    } //-- void clearPortletName()

    /**
     * Method enumeratePortletName
     */
    public java.util.Enumeration enumeratePortletName() {
        return new org.exolab.castor.util.IteratorEnumeration(_portletNameList.iterator());
    } //-- java.util.Enumeration enumeratePortletName()

    /**
     * Method getPortletName
     *
     * @param index
     */
    public org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletName getPortletName(int index)
            throws java.lang.IndexOutOfBoundsException {
        //-- check bounds for index
        if ((index < 0) || (index > _portletNameList.size())) {
            throw new IndexOutOfBoundsException();
        }

        return (org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletName) _portletNameList.get(index);
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.PortletName getPortletName(int)

    /**
     * Method getPortletName
     */
    public org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletName[] getPortletName() {
        int size = _portletNameList.size();
        org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletName[] mArray = new org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletName[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletName) _portletNameList.get(index);
        }
        return mArray;
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.PortletName[] getPortletName()

    /**
     * Method getPortletNameCount
     */
    public int getPortletNameCount() {
        return _portletNameList.size();
    } //-- int getPortletNameCount()

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
     * Method removePortletName
     *
     * @param vPortletName
     */
    public boolean removePortletName(org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletName vPortletName) {
        boolean removed = _portletNameList.remove(vPortletName);
        return removed;
    } //-- boolean removePortletName(org.gridsphere.portletcontainer.jsr.descriptor.PortletName)

    /**
     * Method setPortletName
     *
     * @param index
     * @param vPortletName
     */
    public void setPortletName(int index, org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletName vPortletName)
            throws java.lang.IndexOutOfBoundsException {
        //-- check bounds for index
        if ((index < 0) || (index > _portletNameList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _portletNameList.set(index, vPortletName);
    } //-- void setPortletName(int, org.gridsphere.portletcontainer.jsr.descriptor.PortletName)

    /**
     * Method setPortletName
     *
     * @param portletNameArray
     */
    public void setPortletName(org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletName[] portletNameArray) {
        //-- copy array
        _portletNameList.clear();
        for (int i = 0; i < portletNameArray.length; i++) {
            _portletNameList.add(portletNameArray[i]);
        }
    } //-- void setPortletName(org.gridsphere.portletcontainer.jsr.descriptor.PortletName)

    /**
     * Method unmarshal
     *
     * @param reader
     */
    public static java.lang.Object unmarshal(java.io.Reader reader)
            throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletCollectionType) Unmarshaller.unmarshal(org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletCollectionType.class, reader);
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
