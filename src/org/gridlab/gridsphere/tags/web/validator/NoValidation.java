/*
 * Created by IntelliJ IDEA.
 * User: novotny
 * Date: Jan 14, 2003
 * Time: 10:25:56 PM
 * To change template for new interface use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.tags.web.validator;

public class NoValidation implements Validator {

    public boolean isValid() {
        return true;
    }

}
