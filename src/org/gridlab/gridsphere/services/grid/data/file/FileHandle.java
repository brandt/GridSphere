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
        if (this.nurl == null) {
            return "";
        }
        return this.protocol + "://" + this.nurl.getPath();
    }

    public void setFileUrl(String url)
            throws MalformedURLException {
        int index = url.indexOf(":");
        // If there's no protocol, insert our default protocol
        if (index == -1) {
            url = DEFAULT_PROTOCOL + "://" + url;
            this.nurl = new URL(url);
            this.protocol = DEFAULT_PROTOCOL;
        } else {
            // VERY TEMPORARY HACK...
            String protocol = url.substring(0, index);
            if (protocol.equals("https")  ||
                protocol.equals("gsiftp") ||
                protocol.equals("gridftp")) {
                url = DEFAULT_PROTOCOL + url.substring(index+3);
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

    public String toString() {
        return getFileUrl();
    }
}
