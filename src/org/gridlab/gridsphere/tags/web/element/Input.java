/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.web.element;

public interface Input extends ReadOnly {

    /**
     * Sets the size of the input field
     * @param size the size
     */
    public void setSize(int size);

    /**
     * Gets the size of the field
     * @return the size
     */
    public int getSize();

    /**
     * Sets the maxlength of the field
     * @param length maxlength of the field
     */
    public void setMaxlength(int length);

    /**
     * Gets the maxlength of the field
     * @return maxlnegth of the field
     */
    public int getMaxlength();

    /**
     * Sets the input type of the field
     * @param type type of the field
     */
    public void setInputtype(String type);

    /**
     * Gets the type of the input field
     * @return type of the field
     */
    public String getInputtype();
}
