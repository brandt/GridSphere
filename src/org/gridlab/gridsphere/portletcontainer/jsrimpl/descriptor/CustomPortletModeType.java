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
 * A custom portlet mode that one or more portlets in
 *  this portlet application supports.
 *  Used in: portlet-app
 *
 *
 * @version $Revision$ $Date$
 */
public class CustomPortletModeType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _id
     */
    private java.lang.String _id;

    /**
     * Field _descriptionList
     */
    private java.util.ArrayList _descriptionList;

    /**
     * Field _portletMode
     */
    private org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletMode _portletMode;


      //----------------/
     //- Constructors -/
    //----------------/

    public CustomPortletModeType() {
        super();
        _descriptionList = new ArrayList();
    } //-- org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.CustomPortletModeType()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addDescription
     *
     * @param vDescription
     */
    public void addDescription(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Description vDescription)
        throws java.lang.IndexOutOfBoundsException
    {
        _descriptionList.add(vDescription);
    } //-- void addDescription(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Description)

    /**
     * Method addDescription
     *
     * @param index
     * @param vDescription
     */
    public void addDescription(int index, org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Description vDescription)
        throws java.lang.IndexOutOfBoundsException
    {
        _descriptionList.add(index, vDescription);
    } //-- void addDescription(int, org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Description)

    /**
     * Method clearDescription
     */
    public void clearDescription()
    {
        _descriptionList.clear();
    } //-- void clearDescription()

    /**
     * Method enumerateDescription
     */
    public java.util.Enumeration enumerateDescription()
    {
        return new org.exolab.castor.util.IteratorEnumeration(_descriptionList.iterator());
    } //-- java.util.Enumeration enumerateDescription()

    /**
     * Method getDescription
     *
     * @param index
     */
    public org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Description getDescription(int index)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _descriptionList.size())) {
            throw new IndexOutOfBoundsException();
        }

        return (org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Description) _descriptionList.get(index);
    } //-- org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Description getDescription(int)

    /**
     * Method getDescription
     */
    public org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Description[] getDescription()
    {
        int size = _descriptionList.size();
        org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Description[] mArray = new org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Description[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Description) _descriptionList.get(index);
        }
        return mArray;
    } //-- org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Description[] getDescription()

    /**
     * Method getDescriptionCount
     */
    public int getDescriptionCount()
    {
        return _descriptionList.size();
    } //-- int getDescriptionCount()

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
     * Returns the value of field 'portletMode'.
     *
     * @return the value of field 'portletMode'.
     */
    public org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletMode getPortletMode()
    {
        return this._portletMode;
    } //-- org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletMode getPortletMode()

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
     * Method removeDescription
     *
     * @param vDescription
     */
    public boolean removeDescription(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Description vDescription)
    {
        boolean removed = _descriptionList.remove(vDescription);
        return removed;
    } //-- boolean removeDescription(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Description)

    /**
     * Method setDescription
     *
     * @param index
     * @param vDescription
     */
    public void setDescription(int index, org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Description vDescription)
        throws java.lang.IndexOutOfBoundsException
    {
        //-- check bounds for index
        if ((index < 0) || (index > _descriptionList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _descriptionList.set(index, vDescription);
    } //-- void setDescription(int, org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Description)

    /**
     * Method setDescription
     *
     * @param descriptionArray
     */
    public void setDescription(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Description[] descriptionArray)
    {
        //-- copy array
        _descriptionList.clear();
        for (int i = 0; i < descriptionArray.length; i++) {
            _descriptionList.add(descriptionArray[i]);
        }
    } //-- void setDescription(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Description)

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
     * Sets the value of field 'portletMode'.
     *
     * @param portletMode the value of field 'portletMode'.
     */
    public void setPortletMode(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletMode portletMode)
    {
        this._portletMode = portletMode;
    } //-- void setPortletMode(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletMode)

    /**
     * Method unmarshal
     *
     * @param reader
     */
    public static java.lang.Object unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.CustomPortletModeType) Unmarshaller.unmarshal(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.CustomPortletModeType.class, reader);
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
