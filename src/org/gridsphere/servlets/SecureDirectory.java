package org.gridsphere.servlets;

import org.apache.oro.text.perl.Perl5Util;
import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.services.core.user.User;
import org.gridsphere.services.core.user.UserManagerService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import java.text.DateFormat;
import java.io.*;
import java.util.Enumeration;
import java.util.Date;
import java.util.Calendar;

public class SecureDirectory extends HttpServlet {

    public static final String SECURE_CONTEXT = "secure";
    public static final String SECURE_CONTEXT_PATH = "/WEB-INF/" + SecureDirectory.SECURE_CONTEXT;
    public static final String GUEST_SECUREDIR = "GUEST";

    private Perl5Util util = new Perl5Util();
    private final static int BUFFER_SIZE = 8 * 1024; //8 kB
    private final static boolean DEBUG = true; //leaving DEBUG=true helps to trace if somebody tries to break into ;-)
    private final static int EXPIRES = 15; //15 seconds, works only if strong protection is disabled
    private static String secureDirPath;

    private static boolean strongProtection = true;
    private static boolean inited = false;
    private DateFormat dateFormat = null;

    public void init() throws ServletException {
        if (!SecureDirectory.inited) {
            SecureDirectory.secureDirPath = getServletContext().getRealPath(SecureDirectory.SECURE_CONTEXT_PATH);
            SecureDirectory.strongProtection = Boolean.valueOf(getInitParameter("strongProtection")).booleanValue();
            File secureDir = new File(SecureDirectory.secureDirPath);
            if (SecureDirectory.secureDirPath != null && secureDir.isDirectory()) {
                SecureDirectory.inited = true;

                if (SecureDirectory.DEBUG)
                    log("Initialization OK (Strong protection " + (SecureDirectory.strongProtection ? "enabled" : "DISABLED (better enable it check web.xml) !!!") + "). Setting secureDirPath to " + SecureDirectory.secureDirPath);
            } else {
                if (SecureDirectory.DEBUG)

                    log("Initialization problem, please check if " + getServletContext().getRealPath(SecureDirectory.SECURE_CONTEXT_PATH) + " exists and if it is directory !!!");
            }

        }
        dateFormat = DateFormat.getDateInstance();
        dateFormat.setTimeZone(java.util.TimeZone.getTimeZone("GMT"));
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userID = getUserID(request);
/*
        if (userID == null || userID.equals("")) {
            if (DEBUG)
                log("Request blocked (userID=" + userID + ") !!! Request: " + request.getRequestURI() + "\nIP: " + request.getRemoteAddr() + "\n");
            response.setStatus(403);
        } else
*/
        if (!SecureDirectory.inited) {
            response.setStatus(503);
        } else {

            Enumeration params = request.getParameterNames();
            String saveAs = null;
            String contentType = null;
            boolean shared = false;
            while (params.hasMoreElements()) {
                String paramName = (String) params.nextElement();
                if (util.match("/(.+_)?saveAs/", paramName)) {
                    saveAs = request.getParameter(paramName);
                    if (contentType != null && shared)
                        break;
                } else if (util.match("/(.+_)?contentType/", paramName)) {
                    contentType = request.getParameter(paramName);
                    if (saveAs != null && shared)
                        break;
                } else if (util.match("/(.+_)?shared/", paramName)) {
                    String sharedString = request.getParameter(paramName);
                    if(sharedString.equals("true"))
                        shared = true;
                    if (saveAs != null && contentType != null)
                        break;
                }
            }

            if (userID == null || userID.equals("") || shared) {
                if (SecureDirectory.DEBUG)
                    log("No userID - request redirected to GUEST. Request: " + request.getRequestURI() + "\nIP: " + request.getRemoteAddr() + "\n");
                userID = SecureDirectory.GUEST_SECUREDIR;
            }
            String userDirPath = SecureDirectory.secureDirPath + "/" + userID;
            if (!(new File(userDirPath).isDirectory())) {
                if (SecureDirectory.DEBUG)
                    log("Request blocked (userDirPath=" + userDirPath + " is not directory) !!! Request: " + request.getRequestURI() + "\nIP: " + request.getRemoteAddr() + "\n");

                response.setStatus(403);
            } else {
                String resourcePath = util.substitute("s!" + request.getContextPath() + request.getServletPath() + "!!", request.getRequestURI());
                File resource = new File(userDirPath + resourcePath);
                if (!resource.canRead() || resource.isDirectory()) {
                    if (SecureDirectory.DEBUG)
                        log("Request blocked (Not found, resource=" + userDirPath + resourcePath + ") !!! Request: " + request.getRequestURI() + "\nIP: " + request.getRemoteAddr() + "\n");

                    response.setStatus(404);
                } else {
/*
                    Enumeration params = request.getParameterNames();
                    String saveAs = null;
                    String contentType = null;
                    while (params.hasMoreElements()) {
                        String paramName = (String) params.nextElement();
                        if (util.match("/(.+_)?saveAs/", paramName)) {
                            saveAs = request.getParameter(paramName);
                            if (contentType != null)
                                break;
                        } else if (util.match("/(.+_)?contentType/", paramName)) {
                            contentType = request.getParameter(paramName);
                            if (saveAs != null)
                                break;
                        }
                    }
*/

                    if (contentType == null)
                        contentType = getServletContext().getMimeType(resourcePath);
                    setHeaders(request, response, saveAs, contentType, resource.length());

                    ServletOutputStream output = response.getOutputStream();
                    FileInputStream input = new FileInputStream(resource);
                    rewrite(input, output);
                    input.close();
                }
            }
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (SecureDirectory.DEBUG)
            log("Request blocked (POST request) !!! Request: " + request.getRequestURI() + "\nIP: " + request.getRemoteAddr() + "\n");
        response.setStatus(403);  //to allow for HTTP POST requests comment this line (and 2 lines before) and uncomment next one
        //doGet(request,response);
    }

    private void setHeaders(HttpServletRequest request, HttpServletResponse response, String saveAs, String contentType, long size) {
        if (saveAs != null) {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=" + saveAs);
        } else {
            if (contentType == null)
                contentType = "application/octet-stream";
            response.setContentType(contentType);
        }
        response.setHeader("Content-Length", new Long(size).toString());
        if (SecureDirectory.strongProtection) {
            response.setHeader("Cache-Control", "no-store");
        } else {
            response.setHeader("Cache-Control", "private, must-revalidate");
            response.setHeader("Expires", dateFormat.format(new Date(new Date().getTime() + SecureDirectory.EXPIRES * 1000)));
        }
        response.setHeader("Pragma", "no-cache");
    }

    public long getLastModified(HttpServletRequest request) {
        if (Calendar.getInstance().getTimeInMillis() > 0) return Calendar.getInstance().getTimeInMillis(); //comment this line if you want allow browser to check when resource was last modified
        String userID = getUserID(request);
        if (userID == null || userID.equals("")) {
            if (SecureDirectory.DEBUG)
                log("LastModifiedRequest blocked (userID=" + userID + ") !!! Request: " + request.getRequestURI() + "\nIP: " + request.getRemoteAddr() + "\n");
            return Calendar.getInstance().getTimeInMillis();
        } else if (!SecureDirectory.inited) {
            return Calendar.getInstance().getTimeInMillis();
        } else {
            String userDirPath = SecureDirectory.secureDirPath + "/" + userID;
            if (!(new File(userDirPath).isDirectory())) {
                if (SecureDirectory.DEBUG)
                    log("LastModifiedRequest blocked (userDirPath=" + userDirPath + " is not directory) !!! Request: " + request.getRequestURI() + "\nIP: " + request.getRemoteAddr() + "\n");
                return Calendar.getInstance().getTimeInMillis();
            } else {
                String resourcePath = util.substitute("s!" + request.getContextPath() + request.getServletPath() + "!!", request.getRequestURI());
                File resource = new File(userDirPath + resourcePath);
                if (!resource.exists()) {
                    log("LastModifiedRequest blocked (Not found, resource=" + userDirPath + resourcePath + ") !!! Request: " + request.getRequestURI() + "\nIP: " + request.getRemoteAddr() + "\n");
                    return new Date().getTime();
                } else {
                    return resource.lastModified();
                }
            }
        }
    }

    private void rewrite(InputStream input, OutputStream output) throws IOException {
        int numRead;
        byte[] buf = new byte[SecureDirectory.BUFFER_SIZE];
        while (!((numRead = input.read(buf)) < 0)) {
            output.write(buf, 0, numRead);
        }
    }

    protected String getUserID(HttpServletRequest request) {
        String userID = (String) request.getSession().getAttribute(SportletProperties.PORTLET_USER);
        if (userID != null) {
            UserManagerService userManagerService = (UserManagerService) PortletServiceFactory.createPortletService(UserManagerService.class, true);
            User user = userManagerService.getUser(userID);
            if (user != null) {
                return user.getUserName();
            }

        }
        return userID;
    }
}
