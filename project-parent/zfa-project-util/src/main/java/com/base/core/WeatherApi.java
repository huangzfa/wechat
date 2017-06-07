package com.base.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public abstract class WeatherApi {
	/**
	 * 天气预报接口
	 * @param area   地区名称
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
  public static List<Map<String,String>> getWeather(String area) throws IOException, DocumentException{
	  String reply="";
	  //中文必须经过net,UTF-8编号吗
	  String contenturl=java.net.URLEncoder.encode(area,"UTF-8");  
	  //百度天气预报接口
      URL newsurl = new URL("http://api.map.baidu.com/telematics/v3/weather?location="+contenturl+"&ak=1a3cde429f38434f1811a75e1a90310c"); 
      //返回xml格式数据
      InputStream news=newsurl.openStream();
      SAXReader newsReader = new SAXReader();  
      //解析xml
      Document newsdocument = newsReader.read(news);  
      //返回四天的实时数据，包括今天
      List<Map<String,String>> list=new ArrayList();
      for (int i = 1; i <5; i++) {
    	  Map<String, String> map=new HashMap<String,String>();
    	  ///CityWeatherResponse/results/currentCity  获取xml数据的一种方式
          String city=newsdocument.selectSingleNode("/CityWeatherResponse/results/currentCity").getText();
          String temp=newsdocument.selectSingleNode("/CityWeatherResponse/results/weather_data/date["+i+"]").getText();  
          String imageurl=newsdocument.selectSingleNode("/CityWeatherResponse/results/weather_data/dayPictureUrl["+i+"]").getText();  
          String weather=newsdocument.selectSingleNode("/CityWeatherResponse/results/weather_data/weather["+i+"]").getText();  
          String wind=newsdocument.selectSingleNode("/CityWeatherResponse/results/weather_data/wind["+i+"]").getText();
          String temperature=newsdocument.selectSingleNode("/CityWeatherResponse/results/weather_data/temperature["+i+"]").getText();
          reply=city+temp+weather+wind+temperature;  
          map.put("reply", reply);
          map.put("imageurl", imageurl);
          list.add(map);
	}      
      return list;
  }
   
}
