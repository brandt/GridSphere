/*
 * This class was automatically generated with
 * <a href="http://www.castor.org">Castor 0.9.5.2</a>, using an XML
 * Schema.
 * $Id: SecurityConstraintType.java 4496 2006-02-08 20:27:04Z wehrens $
 */

package org.gridsphere.portletcontainer.jsrimpl.descriptor;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

import java.util.ArrayList;

/**
 * The security-constraintType is used to associate
 * intended security constraints with one or more portlets.
 * Used in: portlet-app
 *
 * @version $Revision: 3298 $ $Date: 2004-06-29 07:19:44 -0700 (Tue, 29 Jun 2004) $
 */
public class SecurityConstraintType implements java.io.Serializable {


    //--------------------------/
    //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _id
     */
    private java.lang.String _id;

    /**
     * Field _displayNameList
     */
    private java.util.ArrayList _displayNameList;

    /**
     * Field _portletCollection
     */
    private org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletCollection _portletCollection;

    /**
     * Field _userDataConstraint
     */
    private org.gridsphere.portletcontainer.jsrimpl.descriptor.UserDataConstraint _userDataConstraint;


    //----------------/
    //- Constructors -/
    //----------------/

    public SecurityConstraintType() {
        super();
        _displayNameList = new ArrayList();
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.SecurityConstraintType()


    //-----------/
    //- Methods -/
    //-----------/

    /**
     * Method addDisplayName
     *
     * @param vDisplayName
     */
    public void addDisplayName(org.gridsphere.portletcontainer.jsrimpl.descriptor.DisplayName vDisplayName)
            throws java.lang.IndexOutOfBoundsException {
        _displayNameList.add(vDisplayName);
    } //-- void addDisplayName(org.gridsphere.portletcontainer.jsr.descriptor.DisplayName)

    /**
     * Method addDisplayName
     *
     * @param index
     * @param vDisplayName
     */
    public void addDisplayName(int index, org.gridsphere.portletcontainer.jsrimpl.descriptor.DisplayName vDisplayName)
            throws java.lang.IndexOutOfBoundsException {
        _displayNameList.add(index, vDisplayName);
    } //-- void addDisplayName(int, org.gridsphere.portletcontainer.jsr.descriptor.DisplayName)

    /**
     * Method clearDisplayName
     */
    public void clearDisplayName() {
        _displayNameList.clear();
    } //-- void clearDisplayName()

    /**
     * Method enumerateDisplayName
     */
    public java.util.Enumeration enumerateDisplayName() {
        return new org.exolab.castor.util.IteratorEnumeration(_displayNameList.iterator());
    } //-- java.util.Enumeration enumerateDisplayName()

    /**
     * Method getDisplayName
     *
     * @param index
     */
    public org.gridsphere.portletcontainer.jsrimpl.descriptor.DisplayName getDisplayName(int index)
            throws java.lang.IndexOutOfBoundsException {
        //-- check bounds for index
        if ((index < 0) || (index > _displayNameList.size())) {
            throw new IndexOutOfBoundsException();
        }

        return (org.gridsphere.portletcontainer.jsrimpl.descriptor.DisplayName) _displayNameList.get(index);
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.DisplayName getDisplayName(int)

    /**
     * Method getDisplayName
     */
    public org.gridsphere.portletcontainer.jsrimpl.descriptor.DisplayName[] getDisplayName() {
        int size = _displayNameList.size();
        org.gridsphere.portletcontainer.jsrimpl.descriptor.DisplayName[] mArray = new org.gridsphere.portletcontainer.jsrimpl.descriptor.DisplayName[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (org.gridsphere.portletcontainer.jsrimpl.descriptor.DisplayName) _displayNameList.get(index);
        }
        return mArray;
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.DisplayName[] getDisplayName()

    /**
     * Method getDisplayNameCount
     */
    public int getDisplayNameCount() {
        return _displayNameList.size();
    } //-- int getDisplayNameCount()

    /**
     * Returns the value of field 'id'.
     *
     * @return the value of field 'id'.
     */
    public java.lang.String getId() {
        return this._id;
    } //-- java.lang.String getId()

    /**
     * Returns the value of field 'portletCollection'.
     *
     * @return the value of field 'portletCollection'.
     */
    public org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletCollection getPortletCollection() {
        return this._portletCollection;
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.PortletCollection getPortletCollection()

    /**
     * Returns the value of field 'userDataConstraint'.
     *
     * @return the value of field 'userDataConstraint'.
     */
    public org.gridsphere.portletcontainer.jsrimpl.descriptor.UserDataConstraint getUserDataConstraint() {
        return this._userDataConstraint;
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.UserDataConstraint getUserDataConstraint()

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
     * Method removeDisplayName
     *
     * @param vDisplayName
     */
    public boolean removeDisplayName(org.gridsphere.portletcontainer.jsrimpl.descriptor.DisplayName vDisplayName) {
        boolean removed = _displayNameList.remove(vDisplayName);
        return removed;
    } //-- boolean removeDisplayName(org.gridsphere.portletcontainer.jsr.descriptor.DisplayName)

    /**
     * Method setDisplayName
     *
     * @param index
     * @param vDisplayName
     */
    public void setDisplayName(int index, org.gridsphere.portletcontainer.jsrimpl.descriptor.DisplayName vDisplayName)
            throws java.lang.IndexOutOfBoundsException {
        //-- check bounds for index
        if ((index < 0) || (index > _displayNameList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _displayNameList.set(index, vDisplayName);
    } //-- void setDisplayName(int, org.gridsphere.portletcontainer.jsr.descriptor.DisplayName)

    /**
     * Method setDisplayName
     *
     * @param displayNameArray
     */
    public void setDisplayName(org.gridsphere.portletcontainer.jsrimpl.descriptor.DisplayName[] displayNameArray) {
        //-- copy array
        _displayNameList.clear();
        for (int i = 0; i < displayNameArray.length; i++) {
            _displayNameList.add(displayNameArray[i]);
        }
    } //-- void setDisplayName(org.gridsphere.portletcontainer.jsr.descriptor.DisplayName)

    /**
     * Sets the value of field 'id'.
     *
     * @param id the value of field 'id'.
     */
    public void setId(java.lang.String id) {
        this._id = id;
    } //-- void setId(java.lang.String)

    /**
     * Sets the value of field 'portletCollection'.
     *
     * @param portletCollection the value of field
     *                          'portletCollection'.
     */
    public void setPortletCollection(org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletCollection portletCollection) {
        this._portletCollection = portletCollection;
    } //-- void setPortletCollection(org.gridsphere.portletcontainer.jsr.descriptor.PortletCollection)

    /**
     * Sets the value of field 'userDataConstraint'.
     *
     * @param userDataConstraint the value of field
     *                           'userDataConstraint'.
     */
    public void setUserDataConstraint(org.gridsphere.portletcontainer.jsrimpl.descriptor.UserDataConstraint userDataConstraint) {
        this._userDataConstraint = userDataConstraint;
    } //-- void setUserDataConstraint(org.gridsphere.portletcontainer.jsr.descriptor.UserDataConstraint)

    /**
     * Method unmarshal
     *
     * @param reader
     */
    public static java.lang.Object unmarshal(java.io.Reader reader)
            throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.gridsphere.portletcontainer.jsrimpl.descriptor.SecurityConstraintType) Unmarshaller.unmarshal(org.gridsphere.portletcontainer.jsrimpl.descriptor.SecurityConstraintType.class, reader);
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
