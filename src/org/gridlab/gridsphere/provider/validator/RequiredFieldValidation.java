/*
 * Created by IntelliJ IDEA.
 * User: novotny
 * Date: Jan 14, 2003
 * Time: 10:25:18 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.provider.validator;

import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.JspException;

public class RequiredFieldValidation implements Validator  {

    private String validate;

    public boolean isValid() {
        return true;
    }

}
