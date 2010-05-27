package org.gridsphere.portlets.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.gridsphere.portlets.core.login.ShibbolethLoginPortlet;

public class ShibbolethProperties {
	
	private Properties prop = null;
	private static ShibbolethProperties instance = null;
	
	protected ShibbolethProperties() {
		try {
			InputStream is = new FileInputStream(ShibbolethLoginPortlet.SHIB_PROPERTIES);
			prop = new Properties();
			prop.load(is);
			is.close();
		} catch (Exception ex) {
			instance = null;
			ex.printStackTrace();
		}
	}
	
	public static Properties getInstance() {
		if (instance == null)
			instance = new ShibbolethProperties();
		return instance.getProperties();
	}
	
	private Properties getProperties() {
		return prop;
	}
}
