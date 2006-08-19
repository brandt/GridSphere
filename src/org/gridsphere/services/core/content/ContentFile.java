package org.gridsphere.services.core.content;

import java.io.File;
import java.io.IOException;

/**
 * @author <a href="mailto:jnovotny@ucsd.edu">Jason Novotny</a>
 * @version $Id$
 */
public interface ContentFile {

    public StringBuffer getContentBuffer() throws IOException;

    public File getFile();
}
