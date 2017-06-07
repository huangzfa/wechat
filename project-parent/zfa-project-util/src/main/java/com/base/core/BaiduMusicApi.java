package com.base.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;  
import java.io.InputStreamReader;
import java.net.HttpURLConnection;  
import java.net.URL;  






import com.base.modal.message.resp.Music;
import com.base.util.UTF;
import com.base.util.WeixinAPIHelper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/** 
 * 百度音乐搜索API操作类 
 *  
 * @author liufeng 
 * @date 2013-12-09 
 */  
public abstract class BaiduMusicApi extends WeixinAPIHelper{
    /**
     * 百度音乐http请求函数
     * @param requestUrl  请求url
     * @return
     * @throws IOException
     */
    public static JSONObject searchMusic(String requestUrl) throws IOException {  
        // 处理名称、作者中间的空格  
        requestUrl = requestUrl.replaceAll("\\+", "%20");  
        // 查询并获取返回结果  
        String  str = httpRequest(requestUrl); 
		JSONObject json = JSONObject.fromObject(str);
        return json;  
    }  
    /** 
     * 发送http请求取得返回的输入流 
     *  
     * @param requestUrl 请求地址 
     * @return InputStream 
     */  
    private static String httpRequest(String requestUrl) {  
        String str = null;
        try {  
            URL url = new URL(requestUrl);  
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setRequestMethod("GET");  
            httpUrlConn.connect();  
            // 当outputStr不为null时向输出流写数据
            // 从输入流读取返回内容
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            str=buffer.toString();
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return str;  
    }   
  
    /**
     * 百度音乐数据抓取调用入口
     * @param title
     * @return
     * @throws IOException
     */
	public static Music getMusicLink(String title) throws IOException {
		Music music=new Music();
		JSONObject json = searchMusic(BAIDU_SEARCH
				+ UTF.urlEncodeUTF8(title));
		try {
		// 音乐列表
		JSONArray array = net.sf.json.JSONArray.fromObject(json.get("song"));
		if (array.size() > 0) {
			// 获取第一个歌曲id
			
				String songid = array.getJSONObject(0).get("songid").toString();
				json = searchMusic(BAIDU_PLAY + songid);
				JSONObject bitrate = JSONObject.fromObject(json.get("bitrate").toString());
				JSONObject songinfo=JSONObject.fromObject(json.get("songinfo").toString());
				
				String url = bitrate.get("show_link").toString();
				music.setMusicUrl(url);
				music.setDescription(songinfo.get("album_title").toString());
				title=songinfo.get("title").toString();
			 
		} else {
			title =null;
		}
		}catch (Exception e) {
			title =null;
		}
		music.setTitle(title);
		return music;
	}
}
