/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.portletcontainer.descriptor;

public class ConfigParam {

    private String ParamName = "";
    private String ParamValue = "";

    public ConfigParam() {}

    public ConfigParam(String ParamName, String ParamValue) {
        this.ParamName = ParamName;
        this.ParamValue = ParamValue;
    }

    /**
     * Returns the parameter name
     *
     * @returns ParamName
     */
    public String getParamName() {
        return ParamName;
    }

    /**
     * Sets the parameter name
     *
     * @param ParamName the parameter name
     */
    public void setParamName(String ParamName) {
        this.ParamName = ParamName;
    }

    /**
     * Returns the parameter value
     *
     * @returns ParamValue
     */
    public String getParamValue() {
        return ParamValue;
    }

    /**
     * Sets the parameter value
     *
     * @param ParamValue the parameter value
     */
    public void setParamValue(String ParamValue) {
        this.ParamValue = ParamValue;
    }


}

