/*
 * This class was automatically generated with
 * <a href="http://www.castor.org">Castor 0.9.5.2</a>, using an XML
 * Schema.
 * $Id: SecurityRoleRefTypeDescriptor.java 4496 2006-02-08 20:27:04Z wehrens $
 */

package org.gridsphere.portletcontainer.impl.descriptor;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.validators.NameValidator;
import org.exolab.castor.xml.validators.StringValidator;

/**
 * Class SecurityRoleRefTypeDescriptor.
 *
 * @version $Revision: 3298 $ $Date: 2004-06-29 07:19:44 -0700 (Tue, 29 Jun 2004) $
 */
public class SecurityRoleRefTypeDescriptor extends org.exolab.castor.xml.util.XMLClassDescriptorImpl {


    //--------------------------/
    //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field nsPrefix
     */
    private java.lang.String nsPrefix;

    /**
     * Field nsURI
     */
    private java.lang.String nsURI;

    /**
     * Field xmlName
     */
    private java.lang.String xmlName;

    /**
     * Field identity
     */
    private org.exolab.castor.xml.XMLFieldDescriptor identity;


    //----------------/
    //- Constructors -/
    //----------------/

    public SecurityRoleRefTypeDescriptor() {
        super();
        nsURI = "http://java.sun.com/xml/ns/portlet/portlet-app_1_0.xsd";
        xmlName = "security-role-refType";

        //-- set grouping compositor
        setCompositorAsSequence();
        org.exolab.castor.xml.util.XMLFieldDescriptorImpl desc = null;
        org.exolab.castor.xml.XMLFieldHandler handler = null;
        org.exolab.castor.xml.FieldValidator fieldValidator = null;
        //-- initialize attribute descriptors

        //-- _id
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(java.lang.String.class, "_id", "id", org.exolab.castor.xml.NodeType.Attribute);
        desc.setImmutable(true);
        handler = (new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue(java.lang.Object object)
                    throws IllegalStateException {
                SecurityRoleRefType target = (SecurityRoleRefType) object;
                return target.getId();
            }

            public void setValue(java.lang.Object object, java.lang.Object value)
                    throws IllegalStateException, IllegalArgumentException {
                try {
                    SecurityRoleRefType target = (SecurityRoleRefType) object;
                    target.setId((java.lang.String) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }

            public java.lang.Object newInstance(java.lang.Object parent) {
                return null;
            }
        });
        desc.setHandler(handler);
        addFieldDescriptor(desc);

        //-- validation code for: _id
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        { //-- local scope
            StringValidator typeValidator = new StringValidator();
            typeValidator.setWhiteSpace("preserve");
            fieldValidator.setValidator(typeValidator);
        }
        desc.setValidator(fieldValidator);
        //-- initialize element descriptors

        //-- _descriptionList
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(org.gridsphere.portletcontainer.impl.descriptor.Description.class, "_descriptionList", "description", org.exolab.castor.xml.NodeType.Element);
        handler = (new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue(java.lang.Object object)
                    throws IllegalStateException {
                SecurityRoleRefType target = (SecurityRoleRefType) object;
                return target.getDescription();
            }

            public void setValue(java.lang.Object object, java.lang.Object value)
                    throws IllegalStateException, IllegalArgumentException {
                try {
                    SecurityRoleRefType target = (SecurityRoleRefType) object;
                    target.addDescription((org.gridsphere.portletcontainer.impl.descriptor.Description) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }

            public java.lang.Object newInstance(java.lang.Object parent) {
                return new org.gridsphere.portletcontainer.impl.descriptor.Description();
            }
        });
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://java.sun.com/xml/ns/portlet/portlet-app_1_0.xsd");
        desc.setMultivalued(true);
        addFieldDescriptor(desc);

        //-- validation code for: _descriptionList
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(0);
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
        //-- _roleName
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(java.lang.String.class, "_roleName", "role-name", org.exolab.castor.xml.NodeType.Element);
        handler = (new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue(java.lang.Object object)
                    throws IllegalStateException {
                SecurityRoleRefType target = (SecurityRoleRefType) object;
                return target.getRoleName();
            }

            public void setValue(java.lang.Object object, java.lang.Object value)
                    throws IllegalStateException, IllegalArgumentException {
                try {
                    SecurityRoleRefType target = (SecurityRoleRefType) object;
                    target.setRoleName(target.getRoleName());
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }

            public java.lang.Object newInstance(java.lang.Object parent) {
                return new java.lang.String();
            }
        });
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://java.sun.com/xml/ns/portlet/portlet-app_1_0.xsd");
        desc.setRequired(true);
        desc.setMultivalued(false);
        addFieldDescriptor(desc);

        //-- validation code for: _roleName
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        fieldValidator.setMinOccurs(1);
        { //-- local scope
            NameValidator typeValidator = new NameValidator(NameValidator.NMTOKEN);
            fieldValidator.setValidator(typeValidator);
        }
        desc.setValidator(fieldValidator);
        //-- _roleLink
        desc = new org.exolab.castor.xml.util.XMLFieldDescriptorImpl(org.gridsphere.portletcontainer.impl.descriptor.RoleLink.class, "_roleLink", "role-link", org.exolab.castor.xml.NodeType.Element);
        handler = (new org.exolab.castor.xml.XMLFieldHandler() {
            public java.lang.Object getValue(java.lang.Object object)
                    throws IllegalStateException {
                SecurityRoleRefType target = (SecurityRoleRefType) object;
                return target.getRoleLink();
            }

            public void setValue(java.lang.Object object, java.lang.Object value)
                    throws IllegalStateException, IllegalArgumentException {
                try {
                    SecurityRoleRefType target = (SecurityRoleRefType) object;
                    target.setRoleLink((org.gridsphere.portletcontainer.impl.descriptor.RoleLink) value);
                } catch (java.lang.Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }

            public java.lang.Object newInstance(java.lang.Object parent) {
                return new org.gridsphere.portletcontainer.impl.descriptor.RoleLink();
            }
        });
        desc.setHandler(handler);
        desc.setNameSpaceURI("http://java.sun.com/xml/ns/portlet/portlet-app_1_0.xsd");
        desc.setMultivalued(false);
        addFieldDescriptor(desc);

        //-- validation code for: _roleLink
        fieldValidator = new org.exolab.castor.xml.FieldValidator();
        { //-- local scope
        }
        desc.setValidator(fieldValidator);
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.SecurityRoleRefTypeDescriptor()


    //-----------/
    //- Methods -/
    //-----------/

    /**
     * Method getAccessMode
     */
    public org.exolab.castor.mapping.AccessMode getAccessMode() {
        return null;
    } //-- org.exolab.castor.mapping.AccessMode getAccessMode()

    /**
     * Method getExtends
     */
    public org.exolab.castor.mapping.ClassDescriptor getExtends() {
        return null;
    } //-- org.exolab.castor.mapping.ClassDescriptor getExtends()

    /**
     * Method getIdentity
     */
    public org.exolab.castor.mapping.FieldDescriptor getIdentity() {
        return identity;
    } //-- org.exolab.castor.mapping.FieldDescriptor getIdentity()

    /**
     * Method getJavaClass
     */
    public java.lang.Class getJavaClass() {
        return org.gridsphere.portletcontainer.impl.descriptor.SecurityRoleRefType.class;
    } //-- java.lang.Class getJavaClass()

    /**
     * Method getNameSpacePrefix
     */
    public java.lang.String getNameSpacePrefix() {
        return nsPrefix;
    } //-- java.lang.String getNameSpacePrefix()

    /**
     * Method getNameSpaceURI
     */
    public java.lang.String getNameSpaceURI() {
        return nsURI;
    } //-- java.lang.String getNameSpaceURI()

    /**
     * Method getValidator
     */
    public org.exolab.castor.xml.TypeValidator getValidator() {
        return this;
    } //-- org.exolab.castor.xml.TypeValidator getValidator()

    /**
     * Method getXMLName
     */
    public java.lang.String getXMLName() {
        return xmlName;
    } //-- java.lang.String getXMLName()

}
