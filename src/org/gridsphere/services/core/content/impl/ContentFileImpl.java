package org.gridsphere.services.core.content.impl;

import org.gridsphere.services.core.content.ContentFile;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

/**
 * @author <a href="mailto:jnovotny@ucsd.edu">Jason Novotny</a>
 * @version $Id$
 */
public class ContentFileImpl implements ContentFile {

    private File file = null;

    public ContentFileImpl(File file) {
        this.file = file;
    }

    public StringBuffer getContentBuffer() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        StringBuffer content = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            content.append(line);
        }
        return content;
    }

    public File getFile() {
        return file;
    }
}
