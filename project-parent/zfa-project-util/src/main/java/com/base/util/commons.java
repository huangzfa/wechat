package com.base.util;

public class commons {
/*      public static String appid="wx89d2752d77d7f39a";
      public static String mch_id="194927104";//商户号
      //public static String openid="oEnN1wbFkg-Ms9b2cxXYc8dS2ubs";
      public static String client_ip="192.168.0.30";
      public static String act_name="红包活动名称";
      public static String remark="备注！";
      public static String wishing="红包祝福语";
      public static String nick_name="旅心购";//提供方名称
      public static String send_name="旅心购";//商户名称
      public static String re_openid="oEnN1wbFkg-Ms9b2cxXYc8dS2ubs";//接收红包用户openid
*/
	 private static PropertiesParser prop = new PropertiesParser();
	 public static String appid=prop.getProperty("wx.appid");
     public static String mch_id=prop.getProperty("wx.mch_id");//商户号
     //public static String openid="oEnN1wbFkg-Ms9b2cxXYc8dS2ubs";
     public static String client_ip=prop.getProperty("wx.client_ip");//调用接口的机器Ip地址
     public static String act_name="这是红包活动名称";//红包活动名称
     public static String remark="这是备注";
     public static String wishing="这是红包祝福语";//红包祝福语
     public static String nick_name="这是提供方名称";//提供方名称
     public static String send_name="这是商户名称";//商户名称
     public static String re_openid=prop.getProperty("wx.re_openid");//接收红包用户openid	 
    
	 
	}
