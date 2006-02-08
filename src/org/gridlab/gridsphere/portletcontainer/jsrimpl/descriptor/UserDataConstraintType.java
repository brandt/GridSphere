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
 * The user-data-constraintType is used to indicate how
 * data communicated between the client and portlet should be
 * protected.
 * Used in: security-constraint
 *
 * @version $Revision: 3298 $ $Date: 2004-06-29 07:19:44 -0700 (Tue, 29 Jun 2004) $
 */
public class UserDataConstraintType implements java.io.Serializable {


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
     * Field _transportGuarantee
     */
    private org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.types.TransportGuaranteeType _transportGuarantee;


    //----------------/
    //- Constructors -/
    //----------------/

    public UserDataConstraintType() {
        super();
        _descriptionList = new ArrayList();
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.UserDataConstraintType()


    //-----------/
    //- Methods -/
    //-----------/

    /**
     * Method addDescription
     *
     * @param vDescription
     */
    public void addDescription(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Description vDescription)
            throws java.lang.IndexOutOfBoundsException {
        _descriptionList.add(vDescription);
    } //-- void addDescription(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.Description)

    /**
     * Method addDescription
     *
     * @param index
     * @param vDescription
     */
    public void addDescription(int index, org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Description vDescription)
            throws java.lang.IndexOutOfBoundsException {
        _descriptionList.add(index, vDescription);
    } //-- void addDescription(int, org.gridlab.gridsphere.portletcontainer.jsr.descriptor.Description)

    /**
     * Method clearDescription
     */
    public void clearDescription() {
        _descriptionList.clear();
    } //-- void clearDescription()

    /**
     * Method enumerateDescription
     */
    public java.util.Enumeration enumerateDescription() {
        return new org.exolab.castor.util.IteratorEnumeration(_descriptionList.iterator());
    } //-- java.util.Enumeration enumerateDescription()

    /**
     * Method getDescription
     *
     * @param index
     */
    public org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Description getDescription(int index)
            throws java.lang.IndexOutOfBoundsException {
        //-- check bounds for index
        if ((index < 0) || (index > _descriptionList.size())) {
            throw new IndexOutOfBoundsException();
        }

        return (org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Description) _descriptionList.get(index);
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.Description getDescription(int)

    /**
     * Method getDescription
     */
    public org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Description[] getDescription() {
        int size = _descriptionList.size();
        org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Description[] mArray = new org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Description[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Description) _descriptionList.get(index);
        }
        return mArray;
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.Description[] getDescription()

    /**
     * Method getDescriptionCount
     */
    public int getDescriptionCount() {
        return _descriptionList.size();
    } //-- int getDescriptionCount()

    /**
     * Returns the value of field 'id'.
     *
     * @return the value of field 'id'.
     */
    public java.lang.String getId() {
        return this._id;
    } //-- java.lang.String getId()

    /**
     * Returns the value of field 'transportGuarantee'.
     *
     * @return the value of field 'transportGuarantee'.
     */
    public org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.types.TransportGuaranteeType getTransportGuarantee() {
        return this._transportGuarantee;
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.types.TransportGuaranteeType getTransportGuarantee()

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
     * Method removeDescription
     *
     * @param vDescription
     */
    public boolean removeDescription(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Description vDescription) {
        boolean removed = _descriptionList.remove(vDescription);
        return removed;
    } //-- boolean removeDescription(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.Description)

    /**
     * Method setDescription
     *
     * @param index
     * @param vDescription
     */
    public void setDescription(int index, org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Description vDescription)
            throws java.lang.IndexOutOfBoundsException {
        //-- check bounds for index
        if ((index < 0) || (index > _descriptionList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _descriptionList.set(index, vDescription);
    } //-- void setDescription(int, org.gridlab.gridsphere.portletcontainer.jsr.descriptor.Description)

    /**
     * Method setDescription
     *
     * @param descriptionArray
     */
    public void setDescription(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.Description[] descriptionArray) {
        //-- copy array
        _descriptionList.clear();
        for (int i = 0; i < descriptionArray.length; i++) {
            _descriptionList.add(descriptionArray[i]);
        }
    } //-- void setDescription(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.Description)

    /**
     * Sets the value of field 'id'.
     *
     * @param id the value of field 'id'.
     */
    public void setId(java.lang.String id) {
        this._id = id;
    } //-- void setId(java.lang.String)

    /**
     * Sets the value of field 'transportGuarantee'.
     *
     * @param transportGuarantee the value of field
     *                           'transportGuarantee'.
     */
    public void setTransportGuarantee(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.types.TransportGuaranteeType transportGuarantee) {
        this._transportGuarantee = transportGuarantee;
    } //-- void setTransportGuarantee(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.types.TransportGuaranteeType)

    /**
     * Method unmarshal
     *
     * @param reader
     */
    public static java.lang.Object unmarshal(java.io.Reader reader)
            throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.UserDataConstraintType) Unmarshaller.unmarshal(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.UserDataConstraintType.class, reader);
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
