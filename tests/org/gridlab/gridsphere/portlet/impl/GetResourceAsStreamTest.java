package org.gridlab.gridsphere.portlet.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.gridlab.gridsphere.portlet.Capability;
import org.gridlab.gridsphere.portlet.Client;
import org.gridlab.gridsphere.portlet.PortletContext;
import org.gridlab.gridsphere.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletMessage;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;

/**
 * Test case for SportletContext.  
 *
 */
public class GetResourceAsStreamTest extends TestCase {

        MockServletContext context;
        MockServletConfig config;
        PortletContext ctx;
        /**
         * Constructor for SPortletContextTest.
         * @param arg0
         */
        public GetResourceAsStreamTest(String arg0) {
                super(arg0);
        }

        /*
         * @see TestCase#setUp()
         */
        protected void setUp() throws Exception {
                super.setUp();
                context = new MockServletContext();
                config = new MockServletConfig(context);
                ctx = new MockSportletContext(config);
        }
        
        public void testGetResourceAsStreamStringClientLocale() {
                Locale loc = new Locale("en", "US", "");
                Client client = new MockClient();
                Assert.assertNull(ctx.getResourceAsStream(null, client, loc));

                context.setStreamPath("/alfa/beta/gamma/delta.jsp");
                Assert.assertNotNull(ctx.getResourceAsStream("/alfa/beta/gamma/delta.jsp", client, loc));
                context.setStreamPath("/alfa/beta/gamma/html/delta.jsp");
                Assert.assertNotNull(ctx.getResourceAsStream("/alfa/beta/gamma/delta.jsp", client, loc));
                context.setStreamPath("/alfa/beta/gamma/html/en/delta.jsp");
                Assert.assertNotNull(ctx.getResourceAsStream("/alfa/beta/gamma/delta.jsp", client, loc));
                context.setStreamPath("/alfa/beta/gamma/html/en_US/delta.jsp");
                Assert.assertNotNull(ctx.getResourceAsStream("/alfa/beta/gamma/delta.jsp", client, loc));
                
                loc = new Locale("en", "", "");        
                context.setStreamPath("/alfa/beta/gamma/delta.jsp");
                Assert.assertNotNull(ctx.getResourceAsStream("/alfa/beta/gamma/delta.jsp", client, loc));
                context.setStreamPath("/alfa/beta/gamma/html/delta.jsp");
                Assert.assertNotNull(ctx.getResourceAsStream("/alfa/beta/gamma/delta.jsp", client, loc));
                context.setStreamPath("/alfa/beta/gamma/html/en/delta.jsp");
                Assert.assertNotNull(ctx.getResourceAsStream("/alfa/beta/gamma/delta.jsp", client, loc));
                context.setStreamPath("/alfa/beta/gamma/html/en_US/delta.jsp");
                Assert.assertNull(ctx.getResourceAsStream("/alfa/beta/gamma/delta.jsp", client, loc));
                
                loc = null;
                context.setStreamPath("/alfa/beta/gamma/delta.jsp");
                Assert.assertNotNull(ctx.getResourceAsStream("/alfa/beta/gamma/delta.jsp", client, loc));
                context.setStreamPath("/alfa/beta/gamma/html/delta.jsp");
                Assert.assertNotNull(ctx.getResourceAsStream("/alfa/beta/gamma/delta.jsp", client, loc));
                context.setStreamPath("/alfa/beta/gamma/html/en/delta.jsp");
                Assert.assertNull(ctx.getResourceAsStream("/alfa/beta/gamma/delta.jsp", client, loc));
                context.setStreamPath("/alfa/beta/gamma/html/en_US/delta.jsp");
                Assert.assertNull(ctx.getResourceAsStream("/alfa/beta/gamma/delta.jsp", client, loc));
                
                client = null;
                context.setStreamPath("/alfa/beta/gamma/delta.jsp");
                Assert.assertNotNull(ctx.getResourceAsStream("/alfa/beta/gamma/delta.jsp", client, loc));
                context.setStreamPath("/alfa/beta/gamma/html/delta.jsp");
                Assert.assertNull(ctx.getResourceAsStream("/alfa/beta/gamma/delta.jsp", client, loc));
                context.setStreamPath("/alfa/beta/gamma/html/en/delta.jsp");
                Assert.assertNull(ctx.getResourceAsStream("/alfa/beta/gamma/delta.jsp", client, loc));
                context.setStreamPath("/alfa/beta/gamma/html/en_US/delta.jsp");
                Assert.assertNull(ctx.getResourceAsStream("/alfa/beta/gamma/delta.jsp", client, loc));
                
        }

}

class MockSportletContext implements PortletContext {
        ServletContext context;
        
        public MockSportletContext(ServletConfig config) {
                context = config.getServletContext();
        }


        /* (non-Javadoc)
         * @see org.gridlab.gridsphere.portlet.PortletContext#getAttribute(java.lang.String)
         */
        public Object getAttribute(String name) {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see org.gridlab.gridsphere.portlet.PortletContext#getAttributeNames()
         */
        public Enumeration getAttributeNames() {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see org.gridlab.gridsphere.portlet.PortletContext#removeAttribute(java.lang.String)
         */
        public void removeAttribute(String name) {
                // TODO Auto-generated method stub
                
        }

        /* (non-Javadoc)
         * @see org.gridlab.gridsphere.portlet.PortletContext#include(java.lang.String, org.gridlab.gridsphere.portlet.PortletRequest, org.gridlab.gridsphere.portlet.PortletResponse)
         */
        public void include(String path, PortletRequest request, PortletResponse response) throws PortletException, IOException {
                // TODO Auto-generated method stub
                
        }

        /* (non-Javadoc)
         * @see org.gridlab.gridsphere.portlet.PortletContext#getResourceAsStream(java.lang.String)
         */
        public InputStream getResourceAsStream(String path) {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see org.gridlab.gridsphere.portlet.PortletContext#getResourceAsStream(java.lang.String, org.gridlab.gridsphere.portlet.Client, java.util.Locale)
         */
        public InputStream getResourceAsStream(String path, Client client, Locale locale) {
                if (path == null) return null;

                int lastComponentIndex=path.lastIndexOf("/");
                String pathPrefix = path.substring(0, lastComponentIndex+1);
                String lastComponent = path.substring(lastComponentIndex);
                InputStream resourceStream = null;
                StringBuffer pathBuffer = new StringBuffer();
            
                pathBuffer.append(pathPrefix);

                if (client != null) {
                        String markupName = client.getMarkupName();
                        pathBuffer.append(markupName);
                        int clientIndex = pathBuffer.length();
                            
                        if (locale != null) {
                                String language = locale.getLanguage();
                                String country = locale.getCountry();
                                pathBuffer.append("/");
                                pathBuffer.append(language);
                            
                                int langIndex = pathBuffer.length();
                                if (!country.equals("")) {                                    
                                        pathBuffer.append("_");
                                        pathBuffer.append(country);
                                        pathBuffer.append(lastComponent);                                    
                                        resourceStream = context.getResourceAsStream(pathBuffer.toString());
                                        if (resourceStream != null) return resourceStream;
                                } 
                            
                                pathBuffer.replace(langIndex, pathBuffer.length(), lastComponent);
                                resourceStream = context.getResourceAsStream(pathBuffer.toString());
                                if (resourceStream != null) return resourceStream;
                        }
                    
                        pathBuffer.replace(clientIndex, pathBuffer.length(),lastComponent);
                        resourceStream = context.getResourceAsStream(pathBuffer.toString());
                        if (resourceStream != null) return resourceStream;                    
                }
            
            
            
                return context.getResourceAsStream(path);

        }

        /* (non-Javadoc)
         * @see org.gridlab.gridsphere.portlet.PortletContext#getText(java.lang.String, java.lang.String, java.util.Locale)
         */
        public String getText(String bundle, String key, Locale locale) {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see org.gridlab.gridsphere.portlet.PortletContext#send(java.lang.String, org.gridlab.gridsphere.portlet.PortletMessage)
         */
        public void send(String concretePortletID, PortletMessage message) {
                // TODO Auto-generated method stub
                
        }

        /* (non-Javadoc)
         * @see org.gridlab.gridsphere.portlet.PortletContext#getService(java.lang.Class)
         */
        public PortletService getService(Class service) throws PortletServiceUnavailableException, PortletServiceNotFoundException {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see org.gridlab.gridsphere.portlet.PortletContext#getService(java.lang.Class, org.gridlab.gridsphere.portlet.User)
         */
        public PortletService getService(Class service, User user) throws PortletServiceUnavailableException, PortletServiceNotFoundException {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see org.gridlab.gridsphere.portlet.PortletContext#getVersionInfo()
         */
        public String getVersionInfo() {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see org.gridlab.gridsphere.portlet.PortletContext#getReleaseInfo()
         */
        public String getReleaseInfo() {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see org.gridlab.gridsphere.portlet.PortletContext#getContainerInfo()
         */
        public String getContainerInfo() {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see org.gridlab.gridsphere.portlet.PortletContext#getLog()
         */
        public PortletLog getLog() {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getContext(java.lang.String)
         */
        public ServletContext getContext(String arg0) {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getMajorVersion()
         */
        public int getMajorVersion() {
                // TODO Auto-generated method stub
                return 0;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getMinorVersion()
         */
        public int getMinorVersion() {
                // TODO Auto-generated method stub
                return 0;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getMimeType(java.lang.String)
         */
        public String getMimeType(String arg0) {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getResourcePaths(java.lang.String)
         */
        public Set getResourcePaths(String arg0) {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getResource(java.lang.String)
         */
        public URL getResource(String arg0) throws MalformedURLException {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getRequestDispatcher(java.lang.String)
         */
        public RequestDispatcher getRequestDispatcher(String arg0) {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getNamedDispatcher(java.lang.String)
         */
        public RequestDispatcher getNamedDispatcher(String arg0) {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getServlet(java.lang.String)
         */
        public Servlet getServlet(String arg0) throws ServletException {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getServlets()
         */
        public Enumeration getServlets() {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getServletNames()
         */
        public Enumeration getServletNames() {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#log(java.lang.String)
         */
        public void log(String arg0) {
                // TODO Auto-generated method stub
                
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#log(java.lang.Exception, java.lang.String)
         */
        public void log(Exception arg0, String arg1) {
                // TODO Auto-generated method stub
                
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#log(java.lang.String, java.lang.Throwable)
         */
        public void log(String arg0, Throwable arg1) {
                // TODO Auto-generated method stub
                
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getRealPath(java.lang.String)
         */
        public String getRealPath(String arg0) {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getServerInfo()
         */
        public String getServerInfo() {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getInitParameter(java.lang.String)
         */
        public String getInitParameter(String arg0) {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getInitParameterNames()
         */
        public Enumeration getInitParameterNames() {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#setAttribute(java.lang.String, java.lang.Object)
         */
        public void setAttribute(String arg0, Object arg1) {
                // TODO Auto-generated method stub
                
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getServletContextName()
         */
        public String getServletContextName() {
                // TODO Auto-generated method stub
                return null;
        }
}

class MockClient implements Client {

        /* (non-Javadoc)
         * @see org.gridlab.gridsphere.portlet.Client#getManufacturer()
         */
        public String getManufacturer() {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see org.gridlab.gridsphere.portlet.Client#getModel()
         */
        public String getModel() {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see org.gridlab.gridsphere.portlet.Client#getVersion()
         */
        public String getVersion() {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see org.gridlab.gridsphere.portlet.Client#getUserAgent()
         */
        public String getUserAgent() {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see org.gridlab.gridsphere.portlet.Client#isCapableOf(org.gridlab.gridsphere.portlet.Capability)
         */
        public boolean isCapableOf(Capability capability) {
                // TODO Auto-generated method stub
                return false;
        }

        /* (non-Javadoc)
         * @see org.gridlab.gridsphere.portlet.Client#isCapableOf(org.gridlab.gridsphere.portlet.Capability[])
         */
        public boolean isCapableOf(Capability[] capabilities) {
                // TODO Auto-generated method stub
                return false;
        }

        /* (non-Javadoc)
         * @see org.gridlab.gridsphere.portlet.Client#getMimeType()
         */
        public String getMimeType() {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see org.gridlab.gridsphere.portlet.Client#getMarkupName()
         */
        public String getMarkupName() {
                return "html";
        }
}

class MockServletConfig implements ServletConfig {

        MockServletContext context = null;
        
        public MockServletConfig(MockServletContext ctx) {
                context = ctx;
        }
        
        /* (non-Javadoc)
         * @see javax.servlet.ServletConfig#getServletName()
         */
        public String getServletName() {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletConfig#getServletContext()
         */
        public ServletContext getServletContext() {                
                return context;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletConfig#getInitParameter(java.lang.String)
         */
        public String getInitParameter(String arg0) {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletConfig#getInitParameterNames()
         */
        public Enumeration getInitParameterNames() {
                // TODO Auto-generated method stub
                return null;
        }
}


class MockServletContext implements ServletContext {

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getContext(java.lang.String)
         */
        public ServletContext getContext(String arg0) {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getMajorVersion()
         */
        public int getMajorVersion() {
                // TODO Auto-generated method stub
                return 0;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getMinorVersion()
         */
        public int getMinorVersion() {
                // TODO Auto-generated method stub
                return 0;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getMimeType(java.lang.String)
         */
        public String getMimeType(String arg0) {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getResourcePaths(java.lang.String)
         */
        public Set getResourcePaths(String arg0) {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getResource(java.lang.String)
         */
        public URL getResource(String arg0) throws MalformedURLException {
                // TODO Auto-generated method stub
                return null;
        }

        
        private String streamPath = "";
        public void setStreamPath(String path) {
                streamPath = path; 
        }
        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getResourceAsStream(java.lang.String)
         */
        public InputStream getResourceAsStream(String arg0) {
                if (arg0.equals(streamPath)) return new InputStream() {
                        public int available() throws IOException {
                                // TODO Auto-generated method stub
                                return 0;
                        }

                        public void close() throws IOException {
                                // TODO Auto-generated method stub

                        }

                        public synchronized void mark(int readlimit) {
                                // TODO Auto-generated method stub

                        }

                        public boolean markSupported() {
                                // TODO Auto-generated method stub
                                return false;
                        }

                        public int read() throws IOException {
                                // TODO Auto-generated method stub
                                return 0;
                        }

                        public int read(byte[] b, int off, int len) throws IOException {
                                // TODO Auto-generated method stub
                                return 0;
                        }

                        public int read(byte[] b) throws IOException {
                                // TODO Auto-generated method stub
                                return 0;
                        }

                        public synchronized void reset() throws IOException {
                                // TODO Auto-generated method stub

                        }

                        public long skip(long n) throws IOException {
                                // TODO Auto-generated method stub
                                return 0;
                        }
                };
                return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getRequestDispatcher(java.lang.String)
         */
        public RequestDispatcher getRequestDispatcher(String arg0) {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getNamedDispatcher(java.lang.String)
         */
        public RequestDispatcher getNamedDispatcher(String arg0) {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getServlet(java.lang.String)
         */
        public Servlet getServlet(String arg0) throws ServletException {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getServlets()
         */
        public Enumeration getServlets() {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getServletNames()
         */
        public Enumeration getServletNames() {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#log(java.lang.String)
         */
        public void log(String arg0) {
                // TODO Auto-generated method stub
                
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#log(java.lang.Exception, java.lang.String)
         */
        public void log(Exception arg0, String arg1) {
                // TODO Auto-generated method stub
                
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#log(java.lang.String, java.lang.Throwable)
         */
        public void log(String arg0, Throwable arg1) {
                // TODO Auto-generated method stub
                
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getRealPath(java.lang.String)
         */
        public String getRealPath(String arg0) {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getServerInfo()
         */
        public String getServerInfo() {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getInitParameter(java.lang.String)
         */
        public String getInitParameter(String arg0) {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getInitParameterNames()
         */
        public Enumeration getInitParameterNames() {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getAttribute(java.lang.String)
         */
        public Object getAttribute(String arg0) {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getAttributeNames()
         */
        public Enumeration getAttributeNames() {
                // TODO Auto-generated method stub
                return null;
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#setAttribute(java.lang.String, java.lang.Object)
         */
        public void setAttribute(String arg0, Object arg1) {
                // TODO Auto-generated method stub
                
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#removeAttribute(java.lang.String)
         */
        public void removeAttribute(String arg0) {
                // TODO Auto-generated method stub
                
        }

        /* (non-Javadoc)
         * @see javax.servlet.ServletContext#getServletContextName()
         */
        public String getServletContextName() {
                // TODO Auto-generated method stub
                return null;
        }
}