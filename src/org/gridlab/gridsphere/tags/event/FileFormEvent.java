/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @versiob $Id$
 */

package org.gridlab.gridsphere.tags.event;

import java.io.File;

public interface FileFormEvent extends FormEvent {

    public static final int MAX_UPLOAD_SIZE = 1024 * 1024;
    public static final String TEMP_DIR = "/tmp";

    public String saveFile(String filePath) throws FileFormException;

}
