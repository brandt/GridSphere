/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.2</a>, using an XML
 * Schema.
 * $Id: ReadOnlyType.java 4496 2006-02-08 20:27:04Z wehrens $
 */

package org.gridsphere.portletcontainer.jsrimpl.descriptor.types;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.Hashtable;

/**
 * Class ReadOnlyType.
 * 
 * @version $Revision: 3298 $ $Date: 2004-06-29 07:19:44 -0700 (Tue, 29 Jun 2004) $
 */
public class ReadOnlyType implements java.io.Serializable {


    //--------------------------/
    //- Class/Member Variables -/
    //--------------------------/

    /**
     * The true type
     */
    public static final int TRUE_TYPE = 0;

    /**
     * The instance of the true type
     */
    public static final ReadOnlyType TRUE = new ReadOnlyType(TRUE_TYPE, "true");

    /**
     * The false type
     */
    public static final int FALSE_TYPE = 1;

    /**
     * The instance of the false type
     */
    public static final ReadOnlyType FALSE = new ReadOnlyType(FALSE_TYPE, "false");

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
    public ReadOnlyType() {
        super();
        this.type = FALSE_TYPE;
        this.stringValue = "false";
    }

    private ReadOnlyType(int type, java.lang.String value) {
        super();
        this.type = type;
        this.stringValue = value;
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.types.ReadOnlyType(int, java.lang.String)


    //-----------/
    //- Methods -/
    //-----------/

    /**
     * Method enumerateReturns an enumeration of all possible
     * instances of ReadOnlyType
     */
    public static java.util.Enumeration enumerate() {
        return _memberTable.elements();
    } //-- java.util.Enumeration enumerate() 

    /**
     * Method getTypeReturns the type of this ReadOnlyType
     */
    public int getType() {
        return this.type;
    } //-- int getType()

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
     * Method init
     */
    private static java.util.Hashtable init() {
        Hashtable members = new Hashtable();
        members.put("true", TRUE);
        members.put("false", FALSE);
        return members;
    } //-- java.util.Hashtable init() 

    /**
     * Method toStringReturns the String representation of this
     * ReadOnlyType
     */
    public java.lang.String toString() {
        return this.stringValue;
    } //-- java.lang.String toString() 

    /**
     * Method valueOfReturns a new ReadOnlyType based on the given
     * String value.
     *
     * @param string
     */
    public static org.gridsphere.portletcontainer.jsrimpl.descriptor.types.ReadOnlyType valueOf(java.lang.String string) {
        java.lang.Object obj = null;
        if (string != null) obj = _memberTable.get(string);
        if (obj == null) {
            String err = "'" + string + "' is not a valid ReadOnlyType";
            throw new IllegalArgumentException(err);
        }
        return (ReadOnlyType) obj;
    } //-- org.gridsphere.portletcontainer.jsr.descriptor.types.ReadOnlyType valueOf(java.lang.String)

}
