/*
 * This class was automatically generated with
 * <a href="http://www.castor.org">Castor 0.9.5.2</a>, using an XML
 * Schema.
 * $Id: PortletPreferencesType.java 4496 2006-02-08 20:27:04Z wehrens $
 */

package org.gridsphere.portletcontainer.jsrimpl.descriptor;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

import java.util.ArrayList;

/**
 * Portlet persistent preference store.
 * Used in: portlet
 *
 * @version $Revision: 3298 $ $Date: 2004-06-29 07:19:44 -0700 (Tue, 29 Jun 2004) $
 */
public class PortletPreferencesType implements java.io.Serializable {


    //--------------------------/
    //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _id
     */
    private java.lang.String _id;

    /**
     * Field _preferenceList
     */
    private java.util.ArrayList _preferenceList;

    /**
     * Field _preferencesValidator
     */
    private org.gridsphere.portletcontainer.jsrimpl.descriptor.PreferencesValidator _preferencesValidator;

    //----------------/
    //- Constructors -/
    //----------------/

    public PortletPreferencesType() {
        super();
        _preferenceList = new ArrayList();
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.PortletPreferencesType()


    //-----------/
    //- Methods -/
    //-----------/

    /**
     * Method addPreference
     *
     * @param vPreference
     */
    public void addPreference(org.gridsphere.portletcontainer.jsrimpl.descriptor.Preference vPreference)
            throws java.lang.IndexOutOfBoundsException {
        _preferenceList.add(vPreference);
    } //-- void addPreference(org.gridsphere.portletcontainer.jsr.descriptor.Preference)

    /**
     * Method addPreference
     *
     * @param index
     * @param vPreference
     */
    public void addPreference(int index, org.gridsphere.portletcontainer.jsrimpl.descriptor.Preference vPreference)
            throws java.lang.IndexOutOfBoundsException {
        _preferenceList.add(index, vPreference);
    } //-- void addPreference(int, org.gridsphere.portletcontainer.jsr.descriptor.Preference)

    /**
     * Method clearPreference
     */
    public void clearPreference() {
        _preferenceList.clear();
    } //-- void clearPreference()

    /**
     * Method enumeratePreference
     */
    public java.util.Enumeration enumeratePreference() {
        return new org.exolab.castor.util.IteratorEnumeration(_preferenceList.iterator());
    } //-- java.util.Enumeration enumeratePreference()

    /**
     * Returns the value of field 'id'.
     *
     * @return the value of field 'id'.
     */
    public java.lang.String getId() {
        return this._id;
    } //-- java.lang.String getId()

    /**
     * Method getPreference
     *
     * @param index
     */
    public org.gridsphere.portletcontainer.jsrimpl.descriptor.Preference getPreference(int index)
            throws java.lang.IndexOutOfBoundsException {
        //-- check bounds for index
        if ((index < 0) || (index > _preferenceList.size())) {
            throw new IndexOutOfBoundsException();
        }

        return (org.gridsphere.portletcontainer.jsrimpl.descriptor.Preference) _preferenceList.get(index);
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.Preference getPreference(int)

    /**
     * Method getPreference
     */
    public org.gridsphere.portletcontainer.jsrimpl.descriptor.Preference[] getPreference() {
        int size = _preferenceList.size();
        org.gridsphere.portletcontainer.jsrimpl.descriptor.Preference[] mArray = new org.gridsphere.portletcontainer.jsrimpl.descriptor.Preference[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (org.gridsphere.portletcontainer.jsrimpl.descriptor.Preference) _preferenceList.get(index);
        }
        return mArray;
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.Preference[] getPreference()

    /**
     * Method getPreferenceCount
     */
    public int getPreferenceCount() {
        return _preferenceList.size();
    } //-- int getPreferenceCount()

    /**
     * Returns the value of field 'preferencesValidator'.
     *
     * @return the value of field 'preferencesValidator'.
     */
    public org.gridsphere.portletcontainer.jsrimpl.descriptor.PreferencesValidator getPreferencesValidator() {
        return this._preferencesValidator;
    } //-- java.lang.String getPreferencesValidator()

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
     * Method removePreference
     *
     * @param vPreference
     */
    public boolean removePreference(org.gridsphere.portletcontainer.jsrimpl.descriptor.Preference vPreference) {
        boolean removed = _preferenceList.remove(vPreference);
        return removed;
    } //-- boolean removePreference(org.gridsphere.portletcontainer.jsr.descriptor.Preference)

    /**
     * Sets the value of field 'id'.
     *
     * @param id the value of field 'id'.
     */
    public void setId(java.lang.String id) {
        this._id = id;
    } //-- void setId(java.lang.String)

    /**
     * Method setPreference
     *
     * @param index
     * @param vPreference
     */
    public void setPreference(int index, org.gridsphere.portletcontainer.jsrimpl.descriptor.Preference vPreference)
            throws java.lang.IndexOutOfBoundsException {
        //-- check bounds for index
        if ((index < 0) || (index > _preferenceList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _preferenceList.set(index, vPreference);
    } //-- void setPreference(int, org.gridsphere.portletcontainer.jsr.descriptor.Preference)

    /**
     * Method setPreference
     *
     * @param preferenceArray
     */
    public void setPreference(org.gridsphere.portletcontainer.jsrimpl.descriptor.Preference[] preferenceArray) {
        //-- copy array
        _preferenceList.clear();
        for (int i = 0; i < preferenceArray.length; i++) {
            _preferenceList.add(preferenceArray[i]);
        }
    } //-- void setPreference(org.gridsphere.portletcontainer.jsr.descriptor.Preference)

    /**
     * Sets the value of field 'preferencesValidator'.
     *
     * @param preferencesValidator the value of field
     *                             'preferencesValidator'.
     */
    public void setPreferencesValidator(org.gridsphere.portletcontainer.jsrimpl.descriptor.PreferencesValidator preferencesValidator) {
        this._preferencesValidator = preferencesValidator;
    } //-- void setPreferencesValidator(java.lang.String)

    /**
     * Method unmarshal
     *
     * @param reader
     */
    public static java.lang.Object unmarshal(java.io.Reader reader)
            throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletPreferencesType) Unmarshaller.unmarshal(org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletPreferencesType.class, reader);
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
