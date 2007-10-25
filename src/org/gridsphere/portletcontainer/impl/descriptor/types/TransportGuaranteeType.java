/*
 * This class was automatically generated with
 * <a href="http://www.castor.org">Castor 0.9.5.2</a>, using an XML
 * Schema.
 * $Id$
 */

package org.gridsphere.portletcontainer.impl.descriptor.types;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.Hashtable;

/**
 * Class TransportGuaranteeType.
 *
 * @version $Revision: 3298 $ $Date: 2004-06-29 07:19:44 -0700 (Tue, 29 Jun 2004) $
 */
public class TransportGuaranteeType implements java.io.Serializable {


    //--------------------------/
    //- Class/Member Variables -/
    //--------------------------/

    /**
     * The NONE type
     */
    public static final int NONE_TYPE = 0;

    /**
     * The instance of the NONE type
     */
    public static final TransportGuaranteeType NONE = new TransportGuaranteeType(NONE_TYPE, "NONE");

    /**
     * The INTEGRAL type
     */
    public static final int INTEGRAL_TYPE = 1;

    /**
     * The instance of the INTEGRAL type
     */
    public static final TransportGuaranteeType INTEGRAL = new TransportGuaranteeType(INTEGRAL_TYPE, "INTEGRAL");

    /**
     * The CONFIDENTIAL type
     */
    public static final int CONFIDENTIAL_TYPE = 2;

    /**
     * The instance of the CONFIDENTIAL type
     */
    public static final TransportGuaranteeType CONFIDENTIAL = new TransportGuaranteeType(CONFIDENTIAL_TYPE, "CONFIDENTIAL");

    /**
     * Field _memberTable
     */
    private static java.util.Hashtable _memberTable = init();

    /**
     * Field type
     */
    private int type = -1;

    /**
     * Field stringValue
     */
    private java.lang.String stringValue = null;


    //----------------/
    //- Constructors -/
    //----------------/
    public TransportGuaranteeType() {
        this.type = NONE_TYPE;
        this.stringValue = "NONE";
    }

    private TransportGuaranteeType(int type, java.lang.String value) {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.types.TransportGuaranteeType(int, java.lang.String)


    //-----------/
    //- Methods -/
    //-----------/

    /**
     * Method enumerateReturns an enumeration of all possible
     * instances of TransportGuaranteeType
     */
    public static java.util.Enumeration enumerate() {
        return _memberTable.elements();
    } //-- java.util.Enumeration enumerate()

    /**
     * Method getTypeReturns the type of this TransportGuaranteeType
     */
    public int getType() {
        return this.type;
    } //-- int getType()

    /**
     * Method init
     */
    private static java.util.Hashtable init() {
        Hashtable members = new Hashtable();
        members.put("NONE", NONE);
        members.put("INTEGRAL", INTEGRAL);
        members.put("CONFIDENTIAL", CONFIDENTIAL);
        return members;
    } //-- java.util.Hashtable init()

    /**
     * Returns the value of field 'content'. The field 'content'
     * has the following description: internal content storage
     *
     * @return the value of field 'content'.
     */
    public java.lang.String getContent() {
        return this.stringValue;
    } //-- java.lang.String getContent()


    /**
     * Sets the value of field 'content'. The field 'content' has
     * the following description: internal content storage
     *
     * @param stringValue the value of field 'content'.
     */
    public void setContent(java.lang.String stringValue) {
        this.stringValue = stringValue;
    } //-- void setContent(java.lang.String)

    /**
     * Method toStringReturns the String representation of this
     * TransportGuaranteeType
     */
    public java.lang.String toString() {
        return this.stringValue;
    } //-- java.lang.String toString()

    /**
     * Method valueOfReturns a new TransportGuaranteeType based on
     * the given String value.
     *
     * @param string
     */
    public static org.gridsphere.portletcontainer.impl.descriptor.types.TransportGuaranteeType valueOf(java.lang.String string) {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid TransportGuaranteeType";
            throw new IllegalArgumentException(err);
        }
        return (TransportGuaranteeType) obj;
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.types.TransportGuaranteeType valueOf(java.lang.String)

}
