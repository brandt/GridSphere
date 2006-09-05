/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: StoredPortletResponseImpl.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.portlet.impl;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class StoredPortletResponseImpl extends SportletResponse {

    private PrintWriter writer;

    private class StoredServletOutputStreamImpl extends ServletOutputStream {

        protected Writer writer;

        public StoredServletOutputStreamImpl(Writer writer) {
            this.writer = writer;
        }

        public void write(int b) throws IOException {
            writer.write(b);
        }

        public String toString() {
            return writer.toString();
        }

    }

    public StoredPortletResponseImpl(HttpServletResponse response, Writer writer) {
        super(response);
        this.writer = new PrintWriter(writer);
    }

    /**
     * Writes an array of bytes
     *
     * @param buf the array to be written
     * @throws IOException if an I/O error occurred
     */
    public void write(byte[] buf) throws IOException {
        char[] tmp = new char[buf.length];
        for (int i = 0; i < tmp.length; i++)
            tmp[i] = (char) (buf[i] & 0xff);
        writer.write(tmp, 0, buf.length);
    }

    /**
     * Writes a single byte to the output stream
     */
    public void write(int val) throws IOException {
        writer.write(val);
    }

    /**
     * Writes a subarray of bytes
     *
     * @param buf     the array to be written
     * @param pOffset the offset into the array
     * @param length  the number of bytes to write
     * @throws IOException if an I/O error occurred
     */
    public void write(byte[] buf, int pOffset, int length) throws IOException {
        char[] tmp = new char[length];
        for (int i = 0; i < length; i++)
            tmp[i] = (char) (buf[i + pOffset] & 0xff);
        writer.write(tmp, 0, length);
    }

    public PrintWriter getWriter() throws IOException {
        return writer;
    }

    public ServletOutputStream getOutputStream() throws IOException {
        return new StoredServletOutputStreamImpl(writer);
    }

    public void flushBuffer() throws IOException {
        writer.flush();
    }

}
