/*
 * This class was automatically generated with
 * <a href="http://www.castor.org">Castor 0.9.5.2</a>, using an XML
 * Schema.
 * $Id: RoleNameDescriptor.java 4496 2006-02-08 20:27:04Z wehrens $
 */

package org.gridsphere.portletcontainer.impl.descriptor;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/


/**
 * Class RoleNameDescriptor.
 *
 * @version $Revision: 3298 $ $Date: 2004-06-29 07:19:44 -0700 (Tue, 29 Jun 2004) $
 */
public class RoleNameDescriptor extends RoleNameTypeDescriptor {


    //--------------------------/
    //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field nsPrefix
     */
    private String nsPrefix;

    /**
     * Field nsURI
     */
    private String nsURI;

    /**
     * Field xmlName
     */
    private String xmlName;

    /**
     * Field identity
     */
    private org.exolab.castor.xml.XMLFieldDescriptor identity;


    //----------------/
    //- Constructors -/
    //----------------/

    public RoleNameDescriptor() {
        super();
        setExtendsWithoutFlatten(new RoleNameTypeDescriptor());
        nsURI = "http://java.sun.com/xml/ns/portlet/portlet-app_1_0.xsd";
        xmlName = "role-name";
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.RoleNameDescriptor()


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
        return super.getExtends();
    } //-- org.exolab.castor.mapping.ClassDescriptor getExtends()

    /**
     * Method getIdentity
     */
    public org.exolab.castor.mapping.FieldDescriptor getIdentity() {
        if (identity == null)
            return super.getIdentity();
        return identity;
    } //-- org.exolab.castor.mapping.FieldDescriptor getIdentity()

    /**
     * Method getJavaClass
     */
    public Class getJavaClass() {
        return RoleName.class;
    } //-- java.lang.Class getJavaClass()

    /**
     * Method getNameSpacePrefix
     */
    public String getNameSpacePrefix() {
        return nsPrefix;
    } //-- java.lang.String getNameSpacePrefix()

    /**
     * Method getNameSpaceURI
     */
    public String getNameSpaceURI() {
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
    public String getXMLName() {
        return xmlName;
    } //-- java.lang.String getXMLName()

}
