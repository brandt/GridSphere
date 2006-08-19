/*
 * This class was automatically generated with
 * <a href="http://www.castor.org">Castor 0.9.5.2</a>, using an XML
 * Schema.
 * $Id: PortletDefinitionType.java 4496 2006-02-08 20:27:04Z wehrens $
 */

package org.gridsphere.portletcontainer.jsrimpl.descriptor;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

import java.util.ArrayList;

/**
 * The portlet element contains the declarative data of a portlet.
 * Used in: portlet-app
 *
 * @version $Revision: 3558 $ $Date: 2004-09-21 07:47:21 -0700 (Tue, 21 Sep 2004) $
 */
public class PortletDefinitionType implements java.io.Serializable {


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
     * Field _portletName
     */
    private org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletName _portletName;

    /**
     * Field _displayNameList
     */
    private java.util.ArrayList _displayNameList;

    /**
     * Field _portletClass
     */
    private org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletClass _portletClass;

    /**
     * Field _initParamList
     */
    private java.util.ArrayList _initParamList;

    /**
     * Field _expirationCache
     */
    private org.gridsphere.portletcontainer.jsrimpl.descriptor.ExpirationCache _expirationCache;

    /**
     * Field _supportsList
     */
    private java.util.ArrayList _supportsList;

    /**
     * Field _supportedLocaleList
     */
    private java.util.ArrayList _supportedLocaleList;

    /**
     * Field _portletInfo
     */
    private org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletInfo _portletInfo;

    /**
     * Field _portletPreferences
     */
    private org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletPreferences _portletPreferences;

    /**
     * Field _securityRoleRefList
     */
    private java.util.ArrayList _securityRoleRefList;

    /**
     * Field _resourceBundle
     */
    private org.gridsphere.portletcontainer.jsrimpl.descriptor.ResourceBundle _resourceBundle;

    //----------------/
    //- Constructors -/
    //----------------/

    public PortletDefinitionType() {
        super();
        _descriptionList = new ArrayList();
        _displayNameList = new ArrayList();
        _initParamList = new ArrayList();
        _supportsList = new ArrayList();
        _supportedLocaleList = new ArrayList();
        _securityRoleRefList = new ArrayList();
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.PortletType()


    //-----------/
    //- Methods -/
    //-----------/

    /**
     * Method addDescription
     *
     * @param vDescription
     */
    public void addDescription(org.gridsphere.portletcontainer.jsrimpl.descriptor.Description vDescription)
            throws java.lang.IndexOutOfBoundsException {
        _descriptionList.add(vDescription);
    } //-- void addDescription(org.gridsphere.portletcontainer.jsr.descriptor.Description)

    /**
     * Method addDescription
     *
     * @param index
     * @param vDescription
     */
    public void addDescription(int index, org.gridsphere.portletcontainer.jsrimpl.descriptor.Description vDescription)
            throws java.lang.IndexOutOfBoundsException {
        _descriptionList.add(index, vDescription);
    } //-- void addDescription(int, org.gridsphere.portletcontainer.jsr.descriptor.Description)

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
     * Method addInitParam
     *
     * @param vInitParam
     */
    public void addInitParam(org.gridsphere.portletcontainer.jsrimpl.descriptor.InitParam vInitParam)
            throws java.lang.IndexOutOfBoundsException {
        _initParamList.add(vInitParam);
    } //-- void addInitParam(org.gridsphere.portletcontainer.jsr.descriptor.InitParam)

    /**
     * Method addInitParam
     *
     * @param index
     * @param vInitParam
     */
    public void addInitParam(int index, org.gridsphere.portletcontainer.jsrimpl.descriptor.InitParam vInitParam)
            throws java.lang.IndexOutOfBoundsException {
        _initParamList.add(index, vInitParam);
    } //-- void addInitParam(int, org.gridsphere.portletcontainer.jsr.descriptor.InitParam)

    /**
     * Method addSecurityRoleRef
     *
     * @param vSecurityRoleRef
     */
    public void addSecurityRoleRef(org.gridsphere.portletcontainer.jsrimpl.descriptor.SecurityRoleRef vSecurityRoleRef)
            throws java.lang.IndexOutOfBoundsException {
        _securityRoleRefList.add(vSecurityRoleRef);
    } //-- void addSecurityRoleRef(org.gridsphere.portletcontainer.jsr.descriptor.SecurityRoleRef)

    /**
     * Method addSecurityRoleRef
     *
     * @param index
     * @param vSecurityRoleRef
     */
    public void addSecurityRoleRef(int index, org.gridsphere.portletcontainer.jsrimpl.descriptor.SecurityRoleRef vSecurityRoleRef)
            throws java.lang.IndexOutOfBoundsException {
        _securityRoleRefList.add(index, vSecurityRoleRef);
    } //-- void addSecurityRoleRef(int, org.gridsphere.portletcontainer.jsr.descriptor.SecurityRoleRef)

    /**
     * Method addSupportedLocale
     *
     * @param vSupportedLocale
     */
    public void addSupportedLocale(org.gridsphere.portletcontainer.jsrimpl.descriptor.SupportedLocale vSupportedLocale)
            throws java.lang.IndexOutOfBoundsException {
        _supportedLocaleList.add(vSupportedLocale);
    } //-- void addSupportedLocale(org.gridsphere.portletcontainer.jsr.descriptor.SupportedLocale)

    /**
     * Method addSupportedLocale
     *
     * @param index
     * @param vSupportedLocale
     */
    public void addSupportedLocale(int index, org.gridsphere.portletcontainer.jsrimpl.descriptor.SupportedLocale vSupportedLocale)
            throws java.lang.IndexOutOfBoundsException {
        _supportedLocaleList.add(index, vSupportedLocale);
    } //-- void addSupportedLocale(int, org.gridsphere.portletcontainer.jsr.descriptor.SupportedLocale)

    /**
     * Method addSupports
     *
     * @param vSupports
     */
    public void addSupports(org.gridsphere.portletcontainer.jsrimpl.descriptor.Supports vSupports)
            throws java.lang.IndexOutOfBoundsException {
        _supportsList.add(vSupports);
    } //-- void addSupports(org.gridsphere.portletcontainer.jsr.descriptor.Supports)

    /**
     * Method addSupports
     *
     * @param index
     * @param vSupports
     */
    public void addSupports(int index, org.gridsphere.portletcontainer.jsrimpl.descriptor.Supports vSupports)
            throws java.lang.IndexOutOfBoundsException {
        _supportsList.add(index, vSupports);
    } //-- void addSupports(int, org.gridsphere.portletcontainer.jsr.descriptor.Supports)

    /**
     * Method clearDescription
     */
    public void clearDescription() {
        _descriptionList.clear();
    } //-- void clearDescription()

    /**
     * Method clearDisplayName
     */
    public void clearDisplayName() {
        _displayNameList.clear();
    } //-- void clearDisplayName()

    /**
     * Method clearInitParam
     */
    public void clearInitParam() {
        _initParamList.clear();
    } //-- void clearInitParam()

    /**
     * Method clearSecurityRoleRef
     */
    public void clearSecurityRoleRef() {
        _securityRoleRefList.clear();
    } //-- void clearSecurityRoleRef()

    /**
     * Method clearSupportedLocale
     */
    public void clearSupportedLocale() {
        _supportedLocaleList.clear();
    } //-- void clearSupportedLocale()

    /**
     * Method clearSupports
     */
    public void clearSupports() {
        _supportsList.clear();
    } //-- void clearSupports()

    /**
     * Method enumerateDescription
     */
    public java.util.Enumeration enumerateDescription() {
        return new org.exolab.castor.util.IteratorEnumeration(_descriptionList.iterator());
    } //-- java.util.Enumeration enumerateDescription()

    /**
     * Method enumerateDisplayName
     */
    public java.util.Enumeration enumerateDisplayName() {
        return new org.exolab.castor.util.IteratorEnumeration(_displayNameList.iterator());
    } //-- java.util.Enumeration enumerateDisplayName()

    /**
     * Method enumerateInitParam
     */
    public java.util.Enumeration enumerateInitParam() {
        return new org.exolab.castor.util.IteratorEnumeration(_initParamList.iterator());
    } //-- java.util.Enumeration enumerateInitParam()

    /**
     * Method enumerateSecurityRoleRef
     */
    public java.util.Enumeration enumerateSecurityRoleRef() {
        return new org.exolab.castor.util.IteratorEnumeration(_securityRoleRefList.iterator());
    } //-- java.util.Enumeration enumerateSecurityRoleRef()

    /**
     * Method enumerateSupportedLocale
     */
    public java.util.Enumeration enumerateSupportedLocale() {
        return new org.exolab.castor.util.IteratorEnumeration(_supportedLocaleList.iterator());
    } //-- java.util.Enumeration enumerateSupportedLocale()

    /**
     * Method enumerateSupports
     */
    public java.util.Enumeration enumerateSupports() {
        return new org.exolab.castor.util.IteratorEnumeration(_supportsList.iterator());
    } //-- java.util.Enumeration enumerateSupports()

    /**
     * Method getDescription
     *
     * @param index
     */
    public org.gridsphere.portletcontainer.jsrimpl.descriptor.Description getDescription(int index)
            throws java.lang.IndexOutOfBoundsException {
        //-- check bounds for index
        if ((index < 0) || (index > _descriptionList.size())) {
            throw new IndexOutOfBoundsException();
        }

        return (org.gridsphere.portletcontainer.jsrimpl.descriptor.Description) _descriptionList.get(index);
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.Description getDescription(int)

    /**
     * Method getDescription
     */
    public org.gridsphere.portletcontainer.jsrimpl.descriptor.Description[] getDescription() {
        int size = _descriptionList.size();
        org.gridsphere.portletcontainer.jsrimpl.descriptor.Description[] mArray = new org.gridsphere.portletcontainer.jsrimpl.descriptor.Description[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (org.gridsphere.portletcontainer.jsrimpl.descriptor.Description) _descriptionList.get(index);
        }
        return mArray;
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.Description[] getDescription()

    /**
     * Method getDescriptionCount
     */
    public int getDescriptionCount() {
        return _descriptionList.size();
    } //-- int getDescriptionCount()

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
     * Returns the value of field 'expirationCache'.
     *
     * @return the value of field 'expirationCache'.
     */
    public org.gridsphere.portletcontainer.jsrimpl.descriptor.ExpirationCache getExpirationCache() {
        return this._expirationCache;
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.ExpirationCache getExpirationCache()

    /**
     * Returns the value of field 'id'.
     *
     * @return the value of field 'id'.
     */
    public java.lang.String getId() {
        return this._id;
    } //-- java.lang.String getId()

    /**
     * Method getInitParam
     *
     * @param index
     */
    public org.gridsphere.portletcontainer.jsrimpl.descriptor.InitParam getInitParam(int index)
            throws java.lang.IndexOutOfBoundsException {
        //-- check bounds for index
        if ((index < 0) || (index > _initParamList.size())) {
            throw new IndexOutOfBoundsException();
        }

        return (org.gridsphere.portletcontainer.jsrimpl.descriptor.InitParam) _initParamList.get(index);
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.InitParam getInitParam(int)

    /**
     * Method getInitParam
     */
    public org.gridsphere.portletcontainer.jsrimpl.descriptor.InitParam[] getInitParam() {
        int size = _initParamList.size();
        org.gridsphere.portletcontainer.jsrimpl.descriptor.InitParam[] mArray = new org.gridsphere.portletcontainer.jsrimpl.descriptor.InitParam[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (org.gridsphere.portletcontainer.jsrimpl.descriptor.InitParam) _initParamList.get(index);
        }
        return mArray;
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.InitParam[] getInitParam()

    /**
     * Method getInitParamCount
     */
    public int getInitParamCount() {
        return _initParamList.size();
    } //-- int getInitParamCount()

    /**
     * Returns the value of field 'portletClass'.
     *
     * @return the value of field 'portletClass'.
     */
    public org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletClass getPortletClass() {
        return this._portletClass;
    } //-- java.lang.String getPortletID()

    /**
     * Returns the value of field 'portletName'.
     *
     * @return the value of field 'portletName'.
     */
    public org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletName getPortletName() {
        return this._portletName;
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.PortletName getPortletName()

    /**
     * Returns the value of field 'portletPreferences'.
     *
     * @return the value of field 'portletPreferences'.
     */
    public org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletPreferences getPortletPreferences() {
        return this._portletPreferences;
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.PortletPreferences getPortletPreferences()

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
     * Method getSecurityRoleRef
     *
     * @param index
     */
    public org.gridsphere.portletcontainer.jsrimpl.descriptor.SecurityRoleRef getSecurityRoleRef(int index)
            throws java.lang.IndexOutOfBoundsException {
        //-- check bounds for index
        if ((index < 0) || (index > _securityRoleRefList.size())) {
            throw new IndexOutOfBoundsException();
        }

        return (org.gridsphere.portletcontainer.jsrimpl.descriptor.SecurityRoleRef) _securityRoleRefList.get(index);
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.SecurityRoleRef getSecurityRoleRef(int)

    /**
     * Method getSecurityRoleRef
     */
    public org.gridsphere.portletcontainer.jsrimpl.descriptor.SecurityRoleRef[] getSecurityRoleRef() {
        int size = _securityRoleRefList.size();
        org.gridsphere.portletcontainer.jsrimpl.descriptor.SecurityRoleRef[] mArray = new org.gridsphere.portletcontainer.jsrimpl.descriptor.SecurityRoleRef[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (org.gridsphere.portletcontainer.jsrimpl.descriptor.SecurityRoleRef) _securityRoleRefList.get(index);
        }
        return mArray;
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.SecurityRoleRef[] getSecurityRoleRef()

    /**
     * Method getSecurityRoleRefCount
     */
    public int getSecurityRoleRefCount() {
        return _securityRoleRefList.size();
    } //-- int getSecurityRoleRefCount()

    /**
     * Method getSupportedLocale
     *
     * @param index
     */
    public org.gridsphere.portletcontainer.jsrimpl.descriptor.SupportedLocale getSupportedLocale(int index)
            throws java.lang.IndexOutOfBoundsException {
        //-- check bounds for index
        if ((index < 0) || (index > _supportedLocaleList.size())) {
            throw new IndexOutOfBoundsException();
        }

        return (org.gridsphere.portletcontainer.jsrimpl.descriptor.SupportedLocale) _supportedLocaleList.get(index);
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.SupportedLocale getSupportedLocale(int)

    /**
     * Method getSupportedLocale
     */
    public org.gridsphere.portletcontainer.jsrimpl.descriptor.SupportedLocale[] getSupportedLocale() {
        int size = _supportedLocaleList.size();
        org.gridsphere.portletcontainer.jsrimpl.descriptor.SupportedLocale[] mArray = new org.gridsphere.portletcontainer.jsrimpl.descriptor.SupportedLocale[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (org.gridsphere.portletcontainer.jsrimpl.descriptor.SupportedLocale) _supportedLocaleList.get(index);
        }
        return mArray;
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.SupportedLocale[] getSupportedLocale()

    /**
     * Method getSupportedLocaleCount
     */
    public int getSupportedLocaleCount() {
        return _supportedLocaleList.size();
    } //-- int getSupportedLocaleCount()

    /**
     * Method getSupports
     *
     * @param index
     */
    public org.gridsphere.portletcontainer.jsrimpl.descriptor.Supports getSupports(int index)
            throws java.lang.IndexOutOfBoundsException {
        //-- check bounds for index
        if ((index < 0) || (index > _supportsList.size())) {
            throw new IndexOutOfBoundsException();
        }

        return (org.gridsphere.portletcontainer.jsrimpl.descriptor.Supports) _supportsList.get(index);
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.Supports getSupports(int)

    /**
     * Method getSupports
     */
    public org.gridsphere.portletcontainer.jsrimpl.descriptor.Supports[] getSupports() {
        int size = _supportsList.size();
        org.gridsphere.portletcontainer.jsrimpl.descriptor.Supports[] mArray = new org.gridsphere.portletcontainer.jsrimpl.descriptor.Supports[size];
        for (int index = 0; index < size; index++) {
            mArray[index] = (org.gridsphere.portletcontainer.jsrimpl.descriptor.Supports) _supportsList.get(index);
        }
        return mArray;
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.Supports[] getSupports()

    /**
     * Method getSupportsCount
     */
    public int getSupportsCount() {
        return _supportsList.size();
    } //-- int getSupportsCount()

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
    public boolean removeDescription(org.gridsphere.portletcontainer.jsrimpl.descriptor.Description vDescription) {
        boolean removed = _descriptionList.remove(vDescription);
        return removed;
    } //-- boolean removeDescription(org.gridsphere.portletcontainer.jsr.descriptor.Description)

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
     * Method removeInitParam
     *
     * @param vInitParam
     */
    public boolean removeInitParam(org.gridsphere.portletcontainer.jsrimpl.descriptor.InitParam vInitParam) {
        boolean removed = _initParamList.remove(vInitParam);
        return removed;
    } //-- boolean removeInitParam(org.gridsphere.portletcontainer.jsr.descriptor.InitParam)

    /**
     * Method removeSecurityRoleRef
     *
     * @param vSecurityRoleRef
     */
    public boolean removeSecurityRoleRef(org.gridsphere.portletcontainer.jsrimpl.descriptor.SecurityRoleRef vSecurityRoleRef) {
        boolean removed = _securityRoleRefList.remove(vSecurityRoleRef);
        return removed;
    } //-- boolean removeSecurityRoleRef(org.gridsphere.portletcontainer.jsr.descriptor.SecurityRoleRef)

    /**
     * Method removeSupportedLocale
     *
     * @param vSupportedLocale
     */
    public boolean removeSupportedLocale(org.gridsphere.portletcontainer.jsrimpl.descriptor.SupportedLocale vSupportedLocale) {
        boolean removed = _supportedLocaleList.remove(vSupportedLocale);
        return removed;
    } //-- boolean removeSupportedLocale(org.gridsphere.portletcontainer.jsr.descriptor.SupportedLocale)

    /**
     * Method removeSupports
     *
     * @param vSupports
     */
    public boolean removeSupports(org.gridsphere.portletcontainer.jsrimpl.descriptor.Supports vSupports) {
        boolean removed = _supportsList.remove(vSupports);
        return removed;
    } //-- boolean removeSupports(org.gridsphere.portletcontainer.jsr.descriptor.Supports)

    /**
     * Method setDescription
     *
     * @param index
     * @param vDescription
     */
    public void setDescription(int index, org.gridsphere.portletcontainer.jsrimpl.descriptor.Description vDescription)
            throws java.lang.IndexOutOfBoundsException {
        //-- check bounds for index
        if ((index < 0) || (index > _descriptionList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _descriptionList.set(index, vDescription);
    } //-- void setDescription(int, org.gridsphere.portletcontainer.jsr.descriptor.Description)

    /**
     * Method setDescription
     *
     * @param descriptionArray
     */
    public void setDescription(org.gridsphere.portletcontainer.jsrimpl.descriptor.Description[] descriptionArray) {
        //-- copy array
        _descriptionList.clear();
        for (int i = 0; i < descriptionArray.length; i++) {
            _descriptionList.add(descriptionArray[i]);
        }
    } //-- void setDescription(org.gridsphere.portletcontainer.jsr.descriptor.Description)

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
     * Sets the value of field 'expirationCache'.
     *
     * @param expirationCache the value of field 'expirationCache'.
     */
    public void setExpirationCache(org.gridsphere.portletcontainer.jsrimpl.descriptor.ExpirationCache expirationCache) {
        this._expirationCache = expirationCache;
    } //-- void setExpirationCache(org.gridsphere.portletcontainer.jsr.descriptor.ExpirationCache)

    /**
     * Sets the value of field 'id'.
     *
     * @param id the value of field 'id'.
     */
    public void setId(java.lang.String id) {
        this._id = id;
    } //-- void setId(java.lang.String)

    /**
     * Method setInitParam
     *
     * @param index
     * @param vInitParam
     */
    public void setInitParam(int index, org.gridsphere.portletcontainer.jsrimpl.descriptor.InitParam vInitParam)
            throws java.lang.IndexOutOfBoundsException {
        //-- check bounds for index
        if ((index < 0) || (index > _initParamList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _initParamList.set(index, vInitParam);
    } //-- void setInitParam(int, org.gridsphere.portletcontainer.jsr.descriptor.InitParam)

    /**
     * Method setInitParam
     *
     * @param initParamArray
     */
    public void setInitParam(org.gridsphere.portletcontainer.jsrimpl.descriptor.InitParam[] initParamArray) {
        //-- copy array
        _initParamList.clear();
        for (int i = 0; i < initParamArray.length; i++) {
            _initParamList.add(initParamArray[i]);
        }
    } //-- void setInitParam(org.gridsphere.portletcontainer.jsr.descriptor.InitParam)

    /**
     * Sets the value of field 'portletClass'.
     *
     * @param portletClass the value of field 'portletClass'.
     */
    public void setPortletClass(org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletClass portletClass) {
        this._portletClass = portletClass;
    } //-- void setPortletID(java.lang.String)

    /**
     * Sets the value of field 'portletName'.
     *
     * @param portletName the value of field 'portletName'.
     */
    public void setPortletName(org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletName portletName) {
        this._portletName = portletName;
    } //-- void setPortletName(org.gridsphere.portletcontainer.jsr.descriptor.PortletName)

    /**
     * Sets the value of field 'portletPreferences'.
     *
     * @param portletPreferences the value of field
     *                           'portletPreferences'.
     */
    public void setPortletPreferences(org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletPreferences portletPreferences) {
        this._portletPreferences = portletPreferences;
    } //-- void setPortletPreferences(org.gridsphere.portletcontainer.jsr.descriptor.PortletPreferences)

    /**
     * Sets the value of field 'portletInfo'.
     *
     * @param portletInfo the value of field
     *                    'portletInfo'.
     */
    public void setPortletInfo(org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletInfo portletInfo) {
        this._portletInfo = portletInfo;
    } //-- void setPortletTypeChoice(org.gridsphere.portletcontainer.jsr.descriptor.PortletInfo)

    /**
     * Sets the value of field 'resourceBundle'.
     *
     * @param resourceBundle the value of field 'resourceBundle'.
     */
    public void setResourceBundle(org.gridsphere.portletcontainer.jsrimpl.descriptor.ResourceBundle resourceBundle) {
        this._resourceBundle = resourceBundle;
    } //-- void setResourceBundle(org.gridsphere.portletcontainer.jsr.descriptor.ResourceBundle)

    /**
     * Method setSecurityRoleRef
     *
     * @param index
     * @param vSecurityRoleRef
     */
    public void setSecurityRoleRef(int index, org.gridsphere.portletcontainer.jsrimpl.descriptor.SecurityRoleRef vSecurityRoleRef)
            throws java.lang.IndexOutOfBoundsException {
        //-- check bounds for index
        if ((index < 0) || (index > _securityRoleRefList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _securityRoleRefList.set(index, vSecurityRoleRef);
    } //-- void setSecurityRoleRef(int, org.gridsphere.portletcontainer.jsr.descriptor.SecurityRoleRef)

    /**
     * Method setSecurityRoleRef
     *
     * @param securityRoleRefArray
     */
    public void setSecurityRoleRef(org.gridsphere.portletcontainer.jsrimpl.descriptor.SecurityRoleRef[] securityRoleRefArray) {
        //-- copy array
        _securityRoleRefList.clear();
        for (int i = 0; i < securityRoleRefArray.length; i++) {
            _securityRoleRefList.add(securityRoleRefArray[i]);
        }
    } //-- void setSecurityRoleRef(org.gridsphere.portletcontainer.jsr.descriptor.SecurityRoleRef)

    /**
     * Method setSupportedLocale
     *
     * @param index
     * @param vSupportedLocale
     */
    public void setSupportedLocale(int index, org.gridsphere.portletcontainer.jsrimpl.descriptor.SupportedLocale vSupportedLocale)
            throws java.lang.IndexOutOfBoundsException {
        //-- check bounds for index
        if ((index < 0) || (index > _supportedLocaleList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _supportedLocaleList.set(index, vSupportedLocale);
    } //-- void setSupportedLocale(int, org.gridsphere.portletcontainer.jsr.descriptor.SupportedLocale)

    /**
     * Method setSupportedLocale
     *
     * @param supportedLocaleArray
     */
    public void setSupportedLocale(org.gridsphere.portletcontainer.jsrimpl.descriptor.SupportedLocale[] supportedLocaleArray) {
        //-- copy array
        _supportedLocaleList.clear();
        for (int i = 0; i < supportedLocaleArray.length; i++) {
            _supportedLocaleList.add(supportedLocaleArray[i]);
        }
    } //-- void setSupportedLocale(org.gridsphere.portletcontainer.jsr.descriptor.SupportedLocale)

    /**
     * Method setSupports
     *
     * @param index
     * @param vSupports
     */
    public void setSupports(int index, org.gridsphere.portletcontainer.jsrimpl.descriptor.Supports vSupports)
            throws java.lang.IndexOutOfBoundsException {
        //-- check bounds for index
        if ((index < 0) || (index > _supportsList.size())) {
            throw new IndexOutOfBoundsException();
        }
        _supportsList.set(index, vSupports);
    } //-- void setSupports(int, org.gridsphere.portletcontainer.jsr.descriptor.Supports)

    /**
     * Method setSupports
     *
     * @param supportsArray
     */
    public void setSupports(org.gridsphere.portletcontainer.jsrimpl.descriptor.Supports[] supportsArray) {
        //-- copy array
        _supportsList.clear();
        for (int i = 0; i < supportsArray.length; i++) {
            _supportsList.add(supportsArray[i]);
        }
    } //-- void setSupports(org.gridsphere.portletcontainer.jsr.descriptor.Supports)

    /**
     * Method unmarshal
     *
     * @param reader
     */
    public static java.lang.Object unmarshal(java.io.Reader reader)
            throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletDefinitionType) Unmarshaller.unmarshal(org.gridsphere.portletcontainer.jsrimpl.descriptor.PortletDefinitionType.class, reader);
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
