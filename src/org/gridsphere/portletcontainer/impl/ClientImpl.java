/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: ClientImpl.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.portletcontainer.impl;

import org.gridsphere.portletcontainer.Client;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * The <code>ClientImpl</code> class represents the client device that the user
 * connects to the portal with. It defines methods to obtain information about
 * clients, e.g. browsers running on PCs, WAP phones, PDAs etc.
 */
public class ClientImpl implements Client, Serializable {

    public static final String[] MIME_TYPES = {"text/html", "text/vnd.wap.wml"};
    public static final String[] MARKUP_TYPES = {"html", "wml", "chtml"};
    public static final String[] MANUFACTURER_NAMES = {"opera", "netscape", "mozilla", "IE","4thpass","Alcatel","BlackBerry","jBrowser","M3Gate","Motorola","Sony","Nokia","Panasonic","Philips","Sagem","Samsung","Siemens","SonyEricsson","WinWAP"};
    public static final String[] WAP_IDENTIFIER = {"4thpass","Alcatel-","BlackBerry/","jBrowser","M3GATE/","MOT-","Sony CMD","Nokia","Panasonic","PHILIPS","SAGEM-","SAMSUNG-","SIE-","SonyEricsson","WinWAP"};
    private String manufacturer = null;
    private String model = null;
    private String version = null;
    private String userAgent = null;
    private String mimeType = null;
    private String markupName = null;

    /**
     * Constructs an instance of ClientImpl from a servlet request
     *
     * @param req an <code>HttpServletRequest</code>
     */
    public ClientImpl(HttpServletRequest req) {
        // get the user-agent string containg client browser information
        userAgent = req.getHeader("user-agent");
        //System.err.println("User-agent: " + userAgent);
        // parse it!
        // below are Mac OS X
        // Netscape 6.2: Mozilla/5.0 (Macintosh; U; PPC Mac OS X; en-US; rv:0.9.4.1) Gecko/20020315 Netscape6/6.2.2
        // Netscape 7.0: Mozilla/5.0 (Macintosh; U; PPC Mac OS X; en-US; rv:1.0.1) Gecko/20020823 Netscape/7.0
        // IE 5.2:       Mozilla/4.0 (compatible; MSIE 5.22; Mac_PowerPC)
        // Mozilla 1.0:  Mozilla/5.0 (Macintosh; U; PPC Mac OS X; en-US; rv:1.0.1) Gecko/20020826
        // Opera 6.0b2:  Mozilla/4.0 (compatible; MSIE 5.0; Mac_PowerPC) Opera 6.0  [en]

        // Compaq IPAQ: Mozilla/4.0 (compatible; MSIE 4.01; Windows CE; PPC; 240x320)

        String browserInfo = "Unknown browser";
        //WAP 2.0 identifification begins here:
        for (int identString=0;identString<15;identString++) {
            if (userAgent == null) continue;
            if (userAgent.indexOf(WAP_IDENTIFIER[identString])!=-1) {
                browserInfo=MANUFACTURER_NAMES[identString+4];
                manufacturer=MANUFACTURER_NAMES[identString+4];
                mimeType=MIME_TYPES[0];
                markupName=MARKUP_TYPES[1];
            }
        }
        //WAP 2.0 identification ends here

        if (browserInfo.equals("Unknown browser")) {//WAP 2.0
            if (userAgent != null) {
                int i = userAgent.indexOf(" ");

                //System.err.println("mozilla version: " + mozillaVersion);
                int j = userAgent.lastIndexOf(")");

                if (j >= 0) {
                    browserInfo = userAgent.substring(j + 1).trim();
                }
            }
        }
        //System.err.println("browser info: " + browserInfo);
        mimeType = req.getHeader("accept");

        //System.err.println("MIME types: " + mimeTypes);
        // Netscape 6.2: text/xml, application/xml, application/xhtml+xml, text/html;q=0.9, image/png, image/jpeg, image/gif;q=0.2, text/plain;q=0.8, text/css, */*;q=0.1
        // IE 5.2:       */*
        // Mozilla 1.0: text/xml,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,video/x-mng,image/png,image/jpeg,image/gif;q=0.2,text/css,*/*;q=0.1
        // Opera 6.0b2: text/html, image/png, image/jpeg, image/gif, image/x-xbitmap, */*

        // get manufacturer
        if (browserInfo.startsWith("Opera")) {
            manufacturer = MANUFACTURER_NAMES[0];
        } else if (browserInfo.startsWith("Gecko")) {
            int i = browserInfo.indexOf(" ");
            if (i > 0) {
                if (browserInfo.substring(i).trim().startsWith("Netscape")) {
                    manufacturer = MANUFACTURER_NAMES[1];
                } else {
                    manufacturer = MANUFACTURER_NAMES[2];
                }
            } else {
                manufacturer = MANUFACTURER_NAMES[2];
            }
        } else {
            manufacturer = MANUFACTURER_NAMES[3];
        }

        if (markupName==null) {//WAP 2.0
            if (mimeType != null) {
                int i = mimeType.indexOf("html");
                if (i < 0) {
                    // IE 5.2 on  Mac OS X
                    if (mimeType.indexOf("*/*") >= 0) {
                        mimeType = MIME_TYPES[0];
                        markupName = MARKUP_TYPES[0];
                    }
                } else {
                    mimeType = MIME_TYPES[0];
                    markupName = MARKUP_TYPES[0];
                }
            }
        }
        // make up version for now
        version = "1.0";
        // make up model for now
        model = "gridsphere model";

        // if all else fails, it is html
        if (markupName == null) markupName = MARKUP_TYPES[0];
        if (mimeType == null) mimeType = MIME_TYPES[0];
        
        //logRequest(req);
    }


    /**
     * Returns the name of the manufacturer of this client, or
     * <code>null</code> if the name is not available.
     *
     * @return the manufacturer
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * Returns the name of the model of this client, or <code>null</code>
     * if the name is not available.
     *
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * Returns the version of the model of this client, or <code>null</code>
     * if the version is not available.
     *
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Returns the exact user agent that this client uses to identify
     * itself to the portal. If the client does not send a user agent,
     * this method returns <code>null</code>
     *
     * @return the user agent
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * Returns the preferred mime-type that this client device supports.
     *
     * @return the mime-type
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * Returns the preferred markup name that this client device supports.
     *
     * @return the name of the markup
     */
    public String getMarkupName() {
        return markupName;
    }

    /**
     * Return a <code>String</code> representation of the <code>Client</code>
     *
     * @return a <code>String</code> representation of the <code>Client</code>
     */
    public String toString() {
        StringBuffer sb = new StringBuffer("\n");
        sb.append("User-agent: " + userAgent + "\n");
        sb.append("manufacturer: " + manufacturer + "\n");
        sb.append("model: " + model + "\n");
        sb.append("version: " + version + "\n");
        sb.append("markup: " + markupName + "\n");
        sb.append("mimeType: " + mimeType);
        return sb.toString();
    }

}
