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
 * The security-role-ref element contains the declaration of a
 * security role reference in the code of the web application. The
 * <p/>
 * declaration consists of an optional description, the security
 * role name used in the code, and an optional link to a security
 * role. If the security role is not specified, the Deployer must
 * choose an appropriate security role.
 * The value of the role name element must be the String used
 * as the parameter to the
 * EJBContext.isCallerInRole(String roleName) method
 * or the HttpServletRequest.isUserInRole(String role) method.
 * Used in: portlet
 *
 * @version $Revision$ $Date$
 */
public class SecurityRoleRefType implements java.io.Serializable {


    //--------------------------/
    //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _id
     */
    protected java.lang.String _id;

    /**
     * Field _descriptionList
     */
    protected java.util.ArrayList _descriptionList;

    /**
     * Field _roleName
     */
    protected org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.RoleName _roleName;

    /**
     * Field _roleLink
     */
    protected org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.RoleLink _roleLink;


    //----------------/
    //- Constructors -/
    //----------------/

    public SecurityRoleRefType() {
        super();
        _descriptionList = new ArrayList();
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.SecurityRoleRefType()


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
     * Returns the value of field 'roleLink'.
     *
     * @return the value of field 'roleLink'.
     */
    public org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.RoleLink getRoleLink() {
        return this._roleLink;
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.RoleLink getRoleLink()

    /**
     * Returns the value of field 'roleName'.
     *
     * @return the value of field 'roleName'.
     */
    public org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.RoleName getRoleName() {
        return this._roleName;
    } //-- org.gridlab.gridsphere.portletcontainer.jsr.descriptor.RoleName getRoleName()

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
     * Sets the value of field 'roleLink'.
     *
     * @param roleLink the value of field 'roleLink'.
     */
    public void setRoleLink(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.RoleLink roleLink) {
        this._roleLink = roleLink;
    } //-- void setRoleLink(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.RoleLink)

    /**
     * Sets the value of field 'roleName'.
     *
     * @param roleName the value of field 'roleName'.
     */
    public void setRoleName(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.RoleName roleName) {
        this._roleName = roleName;
    } //-- void setRoleName(org.gridlab.gridsphere.portletcontainer.jsr.descriptor.RoleName)

    /**
     * Method unmarshal
     *
     * @param reader
     */
    public static java.lang.Object unmarshal(java.io.Reader reader)
            throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.SecurityRoleRefType) Unmarshaller.unmarshal(org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.SecurityRoleRefType.class, reader);
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
