/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.impl;

import org.gridlab.gridsphere.portlet.PortletResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class StoredPortletResponseImpl extends SportletResponse
{
    private PrintWriter writer;

    public StoredPortletResponseImpl(HttpServletResponse response, PrintWriter writer)
    {
        super(response);
        this.writer = writer;
    }

    public PrintWriter getWriter()
        throws IOException
    {
        return writer;
    }

    public void flushBuffer()
        throws IOException
    {
        writer.flush();
    }

}
