package org.gridlab.gridsphere.services.grid.data.file;

import java.net.MalformedURLException;
import java.net.InetAddress;
import java.net.URL;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.globus.util.GlobusURL;
import org.gridlab.gridsphere.services.grid.system.Local;

public class FileHandle {

    public static String DEFAULT_PROTOCOL = "file";

    private String id = "";
    //private User user = null;
    private URL nurl = null;
    private String protocol = "";

    public FileHandle() {
    }

    public FileHandle(String url)
            throws MalformedURLException {
        setFileUrl(url);
    }

    public FileHandle(FileHandle file)
            throws MalformedURLException {
        this.id = file.id;
        //this.user = file.user;
        this.nurl = file.nurl;
    }

    public String getFileId() {
        return this.id;
    }

    public void setFileId(String id) {
        if (id == null) {
            this.id = "";
        } else {
            this.id = id;
        }
    }

    /***
    public User getFileOwner() {
        return this.user;
    }

    public void setFileOwner(User user) {
        this.user = user;
    }
    ***/

    public String getFileUrl() {
        String url = null;
        // If no url return blank
        if (this.nurl == null) {
            url = "";
        } else {
            // If protocol is "file"...
            if (this.protocol.equals(DEFAULT_PROTOCOL)) {
                // If no host provided, then return the path only
                if (this.nurl.getHost().equals("")) {
                    url = this.nurl.getPath();
                // Otherwise just return the url contents
                } else {
                    url = this.nurl.toString();
                }
            // If protocol is not "file"...
            } else {
                // VERY TEMPORARY HACK... How does one get
                // Java to recognize other protocols?
                if (protocol.equals("https")  ||
                    protocol.equals("gsiftp") ||
                    protocol.equals("gridftp")) {
                    // We need to replace "file" with our protocol
                    int len = DEFAULT_PROTOCOL.length();
                    url = this.protocol + this.nurl.toString().substring(len);
                // Otherwise just return the url contents
                } else {
                    url = this.nurl.toString();
                }
            }
        }
        return url;
    }

    public void setFileUrl(String url)
            throws MalformedURLException {
        int index = url.indexOf(":");
        // If there's no protocol, insert our default protocol
        if (index == -1) {
            url = DEFAULT_PROTOCOL + ":" + url;
            this.nurl = new URL(url);
            this.protocol = DEFAULT_PROTOCOL;
        } else {
            // VERY TEMPORARY HACK... How in the hell does
            // one get Java to recognize other protocols?
            String protocol = url.substring(0, index);
            if (protocol.equals("https")  ||
                protocol.equals("gsiftp") ||
                protocol.equals("gridftp")) {
                url = DEFAULT_PROTOCOL + url.substring(index);
                this.nurl = new URL(url);
                this.protocol = protocol;
            // Otherwise, let URL bug out if it wants to
            } else {
                this.nurl = new URL(url);
                this.protocol = nurl.getProtocol();
            }
        }

        //System.out.println("FileHandle: file protocol = " + nurl.getProtocol());
        System.out.println("FileHandle: file protocol = " + this.protocol);
        System.out.println("FileHandle: file host = " + nurl.getHost());
        System.out.println("FileHandle: file path = " + nurl.getPath());
        System.out.println("FileHandle: file file = " + nurl.getFile());
        System.out.println("FileHandle: file query = " + nurl.getQuery());
        System.out.println("FileHandle: file url = " + nurl.toString());
    }

    public String getFileProtocol() {
        if (this.protocol == null) {
            return "";
        }
        return this.protocol;
    }

    public String getFileHost() {
        if (this.nurl == null) {
            return "";
        }
        return this.nurl.getHost();
    }

    public int getFilePort() {
        if (this.nurl == null) {
            return 0;
        }
        return this.nurl.getPort();
    }

    public String getFilePath() {
        if (this.nurl == null) {
            return "";
        }
        return this.nurl.getPath();
    }

    public String getFileName() {
        String filePath = getFilePath();
        int index = filePath.lastIndexOf("/");
        if (index > -1) {
            if (index == filePath.length()-1) {
                return "";
            } else {
                return filePath.substring(index+1);
            }
        } else {
            return "";
        }
    }

    public String getFileQuery() {
        if (this.nurl == null) {
            return "";
        }
        return this.nurl.getQuery();
    }

    public FileReader getFileReader()
            throws IOException {
        String hostname = getFileHost();
        if (hostname.equals(Local.getLocalHost())) {
            String path = getFilePath();
            return new FileReader(path);
        } else {
            throw new IOException("Remote file reader not supported yet!");
        }
    }

    public FileWriter getFileWriter()
            throws IOException {
        String hostname = getFileHost();
        if (hostname.equals(Local.getLocalHost())) {
            String path = getFilePath();
            return new FileWriter(path);
        } else {
            throw new IOException("Remote file writer not supported yet!");
        }
    }
}
