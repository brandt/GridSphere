/*
 * This class was automatically generated with
 * <a href="http://www.castor.org">Castor 0.9.5.2</a>, using an XML
 * Schema.
 * $Id$
 */

package org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

import java.util.ArrayList;

/**
 * Supports indicates the portlet modes a
 *  portlet supports for a specific content type. All portlets must
 *
 *  support the view mode.
 *  Used in: portlet
 *
 *
 * @version $Revision$ $Date$
 */
public class SupportsType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _id
     */
    private java.lang.String _id;

    /**
     * Field _mimeType
     */
    private org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.MimeType _mimeType;

    /**
     * Field _portletModeList
     */
    private java.util.ArrayList _portletModeList;


      //----------------/
     //- Constructors -/
    //----------------/

    public SupportsType() {
        super();
        _portletModeList = new ArrayList();
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.SupportsType()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addPortletMode
     *
     * @param vPortletMode
     */
    public void addPortletMode(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletMode vPortletMode)
        throws java.lang.IndexOutOfBoundsException
    {
        _portletModeList.add(vPortletMode);
    } //-- void addPortletMode(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.PortletMode)

    /**
     * Method addPortletMode
     *
     * @param index
     * @param vPortletMode
     */
    public void addPortletMode(int index, org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletMode vPortletMode)
        throws java.lang.IndexOutOfBoundsException
    {
        _portletModeList.add(index, vPortletMode);
    } //-- void addPortletMode(int, org.gridlab.gridsphere.portletcontainer.jsr.descriptor.PortletMode)

    /**
     * Method clearPortletMode
     */
    public void clearPortletMode()
    {
        _portletModeList.clear();
    } //-- void clearPortletMode()

    /**
     * Method enumeratePortletMode
     */
    public java.util.Enumeration enumeratePortletMode()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_portletModeList.iterator());
    } //-- java.util.Enumeration enumeratePortletMode()

    /**
     * Returns the value of field 'id'.
     *
     * @return the value of field 'id'.
     */
    public java.lang.String getId()
    {
        return this._id;
    } //-- java.lang.String getId()

    /**
     * Returns the value of field 'mimeType'.
     *
     * @return the value of field 'mimeType'.
     */
    public org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.MimeType getMimeType()
    {
        return this._mimeType;
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.MimeType getMimeType()

    /**
     * Method getPortletMode
     *
     * @param index
     */
    public org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletMode getPortletMode(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _portletModeList.size())) {
            throw new IndexOutOfBoundsException();
        }

        return (org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletMode) _portletModeList.get(index);
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.PortletMode getPortletMode(int)

    /**
     * Method getPortletMode
     */
    public org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletMode[] getPortletMode()
    {
        int size = _portletModeList.size();
        org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletMode[] mArray = new org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletMode[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletMode) _portletModeList.get(index);
        }
        return mArray;
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.PortletMode[] getPortletMode()

    /**
     * Method getPortletModeCount
     */
    public int getPortletModeCount()
    {
        return _portletModeList.size();
    } //-- int getPortletModeCount()

    /**
     * Method isValid
     */
    public boolean isValid()
    {
        try {
            validate();
        }
        catch (org.exolab.castor.xml.ValidationException vex) {
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
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {

        Marshaller.marshal(this, out);
    } //-- void marshal(java.io.Writer)

    /**
     * Method marshal
     *
     * @param handler
     */
    public void marshal(org.xml.sax.ContentHandler handler)
        throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {

        Marshaller.marshal(this, handler);
    } //-- void marshal(org.xml.sax.ContentHandler)

    /**
     * Method removePortletMode
     *
     * @param vPortletMode
     */
    public boolean removePortletMode(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletMode vPortletMode)
    {
        boolean removed = _portletModeList.remove(vPortletMode);
        return removed;
    } //-- boolean removePortletMode(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.PortletMode)

    /**
     * Sets the value of field 'id'.
     *
     * @param id the value of field 'id'.
     */
    public void setId(java.lang.String id)
    {
        this._id = id;
    } //-- void setId(java.lang.String)

    /**
     * Sets the value of field 'mimeType'.
     *
     * @param mimeType the value of field 'mimeType'.
     */
    public void setMimeType(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.MimeType mimeType)
    {
        this._mimeType = mimeType;
    } //-- void setMimeType(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.MimeType)

    /**
     * Method setPortletMode
     *
     * @param index
     * @param vPortletMode
     */
    public void setPortletMode(int index, org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletMode vPortletMode)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _portletModeList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _portletModeList.set(index, vPortletMode);
    } //-- void setPortletMode(int, org.gridlab.gridsphere.portletcontainer.jsr.descriptor.PortletMode)

    /**
     * Method setPortletMode
     *
     * @param portletModeArray
     */
    public void setPortletMode(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletMode[] portletModeArray)
    {
        //-- copy array
        _portletModeList.clear();
        for (int i = 0; i < portletModeArray.length; i++) {
            _portletModeList.add(portletModeArray[i]);
        }
    } //-- void setPortletMode(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.PortletMode)

    /**
     * Method unmarshal
     *
     * @param reader
     */
    public static java.lang.Object unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.SupportsType) Unmarshaller.unmarshal(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.SupportsType.class, reader);
    } //-- java.lang.Object unmarshal(java.io.Reader)

    /**
     * Method validate
     */
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate()

}
