/*
 * This class was automatically generated with
 * <a href="http://www.castor.org">Castor 0.9.5.2</a>, using an XML
 * Schema.
 * $Id: SupportsType.java 4892 2006-06-28 22:56:26Z novotny $
 */

package org.gridsphere.portletcontainer.impl.descriptor;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

import java.util.ArrayList;

/**
 * Supports indicates the portlet modes a
 * portlet supports for a specific content type. All portlets must
 * <p/>
 * support the view mode.
 * Used in: portlet
 *
 * @version $Revision: 3298 $ $Date: 2004-06-29 07:19:44 -0700 (Tue, 29 Jun 2004) $
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
    private org.gridsphere.portletcontainer.impl.descriptor.MimeType _mimeType;

    /**
     * Field _portletModeList
     */
    private java.util.ArrayList _portletModeList;

    /**
     * Field _windowStateList
     */
    private java.util.ArrayList _windowStateList;

    //----------------/
    //- Constructors -/
    //----------------/

    public SupportsType() {
        super();
        _portletModeList = new ArrayList();
        _windowStateList = new ArrayList();
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.SupportsType()


    //-----------/
    //- Methods -/
    //-----------/

    /**
     * Method addPortletMode
     *
     * @param vPortletMode
     */
    public void addPortletMode(org.gridsphere.portletcontainer.impl.descriptor.PortletMode vPortletMode)
            throws java.lang.IndexOutOfBoundsException {
        _portletModeList.add(vPortletMode);
    } //-- void addPortletMode(org.gridsphere.portletcontainer.jsr.descriptor.PortletMode)

    /**
     * Method addPortletMode
     *
     * @param index
     * @param vPortletMode
     */
    public void addPortletMode(int index, org.gridsphere.portletcontainer.impl.descriptor.PortletMode vPortletMode)
            throws java.lang.IndexOutOfBoundsException {
        _portletModeList.add(index, vPortletMode);
    } //-- void addPortletMode(int, org.gridsphere.portletcontainer.jsr.descriptor.PortletMode)

    /**
     * Method clearPortletMode
     */
    public void clearPortletMode() {
        _portletModeList.clear();
    } //-- void clearPortletMode()

    /**
     * Method enumeratePortletMode
     */
    public java.util.Enumeration enumeratePortletMode() {
        return new org.exolab.castor.util.IteratorEnumeration(_portletModeList.iterator());
    } //-- java.util.Enumeration enumeratePortletMode()

    /**
     * Method addWindowState
     *
     * @param vWindowState
     */
    public void addWindowState(org.gridsphere.portletcontainer.impl.descriptor.WindowState vWindowState)
            throws java.lang.IndexOutOfBoundsException {
        _windowStateList.add(vWindowState);
    } //-- void addPortletMode(org.gridsphere.portletcontainer.jsr.descriptor.WindowState)

    /**
     * Method addWindowState
     *
     * @param index
     * @param vWindowState
     */
    public void addWindowState(int index, org.gridsphere.portletcontainer.impl.descriptor.WindowState vWindowState)
            throws java.lang.IndexOutOfBoundsException {
        _windowStateList.add(index, vWindowState);
    } //-- void addWindowState(int, org.gridsphere.portletcontainer.jsr.descriptor.WindowState)

    /**
     * Method clearWindowState
     */
    public void clearWindowState() {
        _windowStateList.clear();
    } //-- void clearWindowState()

    /**
     * Method enumerateWindowState
     */
    public java.util.Enumeration enumerateWindowState() {
        return new org.exolab.castor.util.IteratorEnumeration(_windowStateList.iterator());
    } //-- java.util.Enumeration enumerateWindowState()

    /**
     * Returns the value of field 'id'.
     *
     * @return the value of field 'id'.
     */
    public java.lang.String getId() {
        return this._id;
    } //-- java.lang.String getId()

    /**
     * Returns the value of field 'mimeType'.
     *
     * @return the value of field 'mimeType'.
     */
    public org.gridsphere.portletcontainer.impl.descriptor.MimeType getMimeType() {
        return this._mimeType;
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.MimeType getMimeType()

    /**
     * Method getWindowState
     *
     * @param index
     */
    public org.gridsphere.portletcontainer.impl.descriptor.WindowState getWindowState(int index)
            throws java.lang.IndexOutOfBoundsException {
        //-- check bounds for index
        if ((index < 0) || (index > _windowStateList.size())) {
            throw new IndexOutOfBoundsException();
        }

        return (org.gridsphere.portletcontainer.impl.descriptor.WindowState) _windowStateList.get(index);
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.WindowState getWindowState(int)

    /**
     * Method getWindowState
     */
    public org.gridsphere.portletcontainer.impl.descriptor.WindowState[] getWindowState() {
        int size = _windowStateList.size();
        org.gridsphere.portletcontainer.impl.descriptor.WindowState[] mArray = new org.gridsphere.portletcontainer.impl.descriptor.WindowState[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (org.gridsphere.portletcontainer.impl.descriptor.WindowState) _windowStateList.get(index);
        }
        return mArray;
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.WindowState[] getWindowState()

    /**
     * Method getWindowStateCount
     */
    public int getWindowStateCount() {
        return _windowStateList.size();
    } //-- int getWindowStateCount()

    /**
     * Method getPortletMode
     *
     * @param index
     */
    public org.gridsphere.portletcontainer.impl.descriptor.PortletMode getPortletMode(int index)
            throws java.lang.IndexOutOfBoundsException {
        //-- check bounds for index
        if ((index < 0) || (index > _portletModeList.size())) {
            throw new IndexOutOfBoundsException();
        }

        return (org.gridsphere.portletcontainer.impl.descriptor.PortletMode) _portletModeList.get(index);
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.PortletMode getPortletMode(int)

    /**
     * Method getPortletMode
     */
    public org.gridsphere.portletcontainer.impl.descriptor.PortletMode[] getPortletMode() {
        int size = _portletModeList.size();
        org.gridsphere.portletcontainer.impl.descriptor.PortletMode[] mArray = new org.gridsphere.portletcontainer.impl.descriptor.PortletMode[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (org.gridsphere.portletcontainer.impl.descriptor.PortletMode) _portletModeList.get(index);
        }
        return mArray;
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.PortletMode[] getPortletMode()

    /**
     * Method getPortletModeCount
     */
    public int getPortletModeCount() {
        return _portletModeList.size();
    } //-- int getPortletModeCount()

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
     * Method removePortletMode
     *
     * @param vPortletMode
     */
    public boolean removePortletMode(org.gridsphere.portletcontainer.impl.descriptor.PortletMode vPortletMode) {
        return _portletModeList.remove(vPortletMode);
    } //-- boolean removePortletMode(org.gridsphere.portletcontainer.jsr.descriptor.PortletMode)

    /**
     * Method removeWindowState
     *
     * @param vWindowState
     */
    public boolean removeWindowState(org.gridsphere.portletcontainer.impl.descriptor.WindowState vWindowState) {
        return _windowStateList.remove(vWindowState);
    } //-- boolean removeWindowState(org.gridsphere.portletcontainer.jsr.descriptor.WindowState)

    /**
     * Sets the value of field 'id'.
     *
     * @param id the value of field 'id'.
     */
    public void setId(java.lang.String id) {
        this._id = id;
    } //-- void setId(java.lang.String)

    /**
     * Sets the value of field 'mimeType'.
     *
     * @param mimeType the value of field 'mimeType'.
     */
    public void setMimeType(org.gridsphere.portletcontainer.impl.descriptor.MimeType mimeType) {
        this._mimeType = mimeType;
    } //-- void setMimeType(org.gridsphere.portletcontainer.jsr.descriptor.MimeType)

    /**
     * Method setPortletMode
     *
     * @param index
     * @param vPortletMode
     */
    public void setPortletMode(int index, org.gridsphere.portletcontainer.impl.descriptor.PortletMode vPortletMode)
            throws java.lang.IndexOutOfBoundsException {
        //-- check bounds for index
        if ((index < 0) || (index > _portletModeList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _portletModeList.set(index, vPortletMode);
    } //-- void setPortletMode(int, org.gridsphere.portletcontainer.jsr.descriptor.PortletMode)

    /**
     * Method setPortletMode
     *
     * @param portletModeArray
     */
    public void setPortletMode(org.gridsphere.portletcontainer.impl.descriptor.PortletMode[] portletModeArray) {
        //-- copy array
        _portletModeList.clear();
        for (int i = 0; i < portletModeArray.length; i++) {
            _portletModeList.add(portletModeArray[i]);
        }
    } //-- void setPortletMode(org.gridsphere.portletcontainer.jsr.descriptor.PortletMode)

    /**
     * Method setWindowState
     *
     * @param index
     * @param vWindowState
     */
    public void setWindowState(int index, org.gridsphere.portletcontainer.impl.descriptor.WindowState vWindowState)
            throws java.lang.IndexOutOfBoundsException {
        //-- check bounds for index
        if ((index < 0) || (index > _windowStateList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _windowStateList.set(index, vWindowState);
    } //-- void setWindowState(int, org.gridsphere.portletcontainer.jsr.descriptor.WindowState)

    /**
     * Method setWindowState
     *
     * @param windowStateArray
     */
    public void setWindowState(org.gridsphere.portletcontainer.impl.descriptor.WindowState[] windowStateArray) {
        //-- copy array
        _windowStateList.clear();
        for (int i = 0; i < windowStateArray.length; i++) {
            _windowStateList.add(windowStateArray[i]);
        }
    } //-- void setWindowState(org.gridsphere.portletcontainer.jsr.descriptor.WindowState)

    /**
     * Method unmarshal
     *
     * @param reader
     */
    public static java.lang.Object unmarshal(java.io.Reader reader)
            throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.gridsphere.portletcontainer.impl.descriptor.SupportsType) Unmarshaller.unmarshal(org.gridsphere.portletcontainer.impl.descriptor.SupportsType.class, reader);
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
