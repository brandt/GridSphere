/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.grid.job;

import org.gridlab.gridsphere.services.grid.data.file.FileHandle;

import java.net.URL;
import java.net.MalformedURLException;

public class FileArgument extends Argument {

    public FileArgument() {
        super();
        setFile(new FileHandle());
    }

    public FileArgument(String url)
            throws MalformedURLException {
        super();
        setFileUrl(url);
    }

    public FileArgument(FileHandle file)
            throws MalformedURLException {
        super();
        setFile(file);
    }
}
