package com.base.util;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import com.mysql.fabric.xmlrpc.Client;



public class PropertiesParser {
	 private static Properties prop = new Properties();

	  static {
	    try {
	    	prop.load(new InputStreamReader(Client.class.getClassLoader().getResourceAsStream("/wx.properties"), "UTF-8"));
	    	//prop.load(PropertiesParser.class.getResourceAsStream("/wx.properties"));
	    } catch (Exception e) {
	      e.printStackTrace();
	      prop = null;
	    }
	  }

	  public PropertiesParser()
	  {
	  }

	  public PropertiesParser(String url)
	  {
	    prop = new Properties();
	    try {
	      prop.load(getClass().getResourceAsStream(url));
	    } catch (Exception e) {
	      prop = null;
	    }
	  }

	  public PropertiesParser(FileInputStream is) {
	    prop = new Properties();
	    try {
	      prop.load(is);
	    } catch (Exception e) {
	      prop = null;
	    }
	  }

	  public String getProperty(String key) {
	    if ((key == null) || ("".equals(key.trim()))) {
	      return null;
	    }

	    String property = prop.getProperty(key);
	    if (property == null) {
	      if (key.equals("useInterface")) {
	        return "true";
	      }
	      return null;
	    }

	    try
	    {
	      return new String(property.getBytes("ISO-8859-1"), "UTF-8").trim();
	    } catch (Exception e) {
	      e.printStackTrace();
	    }return key;
	  }

	  public Properties getProp()
	  {
	    return prop;
	  }
}
