/**
 * @author <a href="mailto:tkucz@icis.pcz.pl">Tomasz Kuczynski</a>
 * @version 0.1 2004/03/11
 */
package org.gridlab.gridsphere.services.core.secdir.impl;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.services.core.secdir.SecureDirectoryService;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portletcontainer.GridSphereConfig;

import java.net.URLEncoder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

import org.apache.oro.text.perl.Perl5Util;

public class SecureDirectoryServiceImpl implements  SecureDirectoryService, PortletServiceProvider{
  private Perl5Util util = new Perl5Util();
  private static boolean inited=false; 
	private final static int BUFFER_SIZE=8*1024; //8 kB
  private static String secureDirPath;
  private static String secureServletMapping="secure";
	protected PortletLog log = SportletLog.getInstance(SecureDirectoryServiceImpl.class);

  public void init(PortletServiceConfig config) {
    if (!inited) {
      secureDirPath=config.getServletContext().getRealPath("/WEB-INF/secure");
			File f = new File(secureDirPath);
      if (!f.exists()) {
        log.debug("Creating secure directory for users: " + secureDirPath);
        if (!f.mkdirs()) 
				  log.error("Unable to create directory" + secureDirPath);
      }
			inited = true;
    }
  }  

  public void destroy(){
	
	}
	
	public String getFileUrl(User user,String appName,String resource){
	  return getFileUrl(user,appName,resource,null,null);	
	}
  
	public String getFileUrl(User user,String appName,String resource, String saveAs){
	  return getFileUrl(user,appName,resource,saveAs,null);
	}

	public String getFileUrl(User user,String appName,String resource, String saveAs, String contentType){
		if(user==null || appName==null || resource==null || !inited)
	    return null;
		String userDirectoryPath;	
		resource=util.substitute("s!\\\\!/!g",resource);
		resource=util.substitute("s!^/!!",resource);	  
		if((userDirectoryPath=getUserDirectoryPath(user))!=null){
		  String filePath=userDirectoryPath+"/"+appName+"/"+resource;
			File file=new File(filePath);
			if(!file.exists() || file.isDirectory())
			  return null;
			String queryString="";
			if(saveAs!=null && !saveAs.equals("")){
			  try{
				  queryString+="saveAs="+URLEncoder.encode(saveAs,"UTF-8");
			  }
				catch(Exception e){}
			}
			if(contentType!=null && !contentType.equals("")){
			  if(!queryString.equals(""))
				  queryString+="&";
				try{
				  queryString+="contentType="+URLEncoder.encode(contentType,"UTF-8");	
			  }
				catch(Exception e){}
			}			  
			resource=util.substitute("s!\\\\!/!g",resource);
			String url=secureServletMapping+"/"+appName+"/"+resource+(queryString!=null && !queryString.equals("")?"?"+queryString:"");	
			return url;
		}else{
		  return null;
		}	  	
	}
	
  public File getFile(User user,String appName,String resource){
		if(user==null || appName==null || resource==null || !inited)
		  return null;
		resource=util.substitute("s!\\\\!/!g",resource);
		resource=util.substitute("s!^/!!",resource);
		String userDirectoryPath;	
	  if((userDirectoryPath=getUserDirectoryPath(user))!=null){
		  String filePath=userDirectoryPath+"/"+appName;
			File file=new File(filePath);
			if(!file.exists()){
        if (!file.mkdir()){
  		    log.error("Unable to create directory for application " + file);
          return null;
  			}			
			}else if(!file.isDirectory()){
        return null;
			}
			boolean canCreate=true;
			if(util.match("m!/!",resource)){
			  String tmpPath=util.substitute("s!/[^/]*$!!",resource);
				File dirTree=new File(filePath+"/"+tmpPath);
				if(!dirTree.exists()){
				  canCreate=dirTree.mkdirs();
			  }else if(!dirTree.isDirectory())
				  canCreate=false;
			}
			if(canCreate){
			  filePath+="/"+resource;
			  file=new File(filePath);
			  return file;
			}else{
			  return null;
			}	
		}else
		  return null;	  
	}

  public boolean deleteFile(User user,String appName,String resource){
	  File file=getFile(user,appName,resource);
	  if(file==null)
		  return false;
		if(!file.delete())
		  return false;

  	File secureDir = new File(secureDirPath);
		String parent=file.getParent();	
		while(parent!=null){	
		  File dir=new File(parent);
			if(dir.isDirectory()){ //just to be sure ;-)
			  if(dir.compareTo(secureDir)==0)
				  break;
				if(!dir.delete())
				  break;
		  }else break;
			parent=dir.getParent();	
		}
		return true;
	}	

  public boolean fileExists(User user,String appName,String resource){
	  File file=getFile(user,appName,resource);
	  if(file==null)
		  return false;
		return true;	
	}
	
  public boolean writeFromStream(User user,String appName,String resource,InputStream input){
	  File file=getFile(user,appName,resource);
	  if(file==null)
		  return false;
	  try{
		  file.delete();
			if(!file.createNewFile())
			  return false;
			FileOutputStream output=new FileOutputStream(file);
			rewrite(input,output);
			output.close();
		  return true;
		}
		catch(Exception e){
		  return false;
		}    
	}

  public boolean writeFromFile(User user,String appName,String resource,File inputFile){
    try{
		  FileInputStream input=new FileInputStream(inputFile);
		  return writeFromStream(user,appName,resource,input);    
    }
		catch(Exception e){
		  return false;
		}
	}
	
	private void rewrite(InputStream input,OutputStream output)throws IOException{
    int numRead;
    byte[] buf=new byte[BUFFER_SIZE];
		while(!((numRead=input.read(buf))<0)){
      output.write(buf,0,numRead);
    }
  }
	
	private String getUserDirectoryPath(User user){
		String userDirectoryPath=secureDirPath+"/"+user.getID();
	  File userDirectory=new File(userDirectoryPath);
    if (!userDirectory.exists()) {
      log.debug("Creating directory for user: " + userDirectoryPath);
      if (!userDirectory.mkdir()){
		    log.error("Unable to create directory" + userDirectoryPath);
        return null;
			}
		}
    return userDirectoryPath;		
	}
}