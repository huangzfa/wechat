package com.base.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.base.modal.User;
import com.base.modal.message.resp.Music;



public abstract class WeixinAPIHelper {
    private static Logger log = Logger.getLogger(WeixinAPIHelper.class);
	private static PropertiesParser prop = new PropertiesParser();
	
	/*微信APPID*/
    public static final String AppId = prop.getProperty("wx.appid");
    
    /*微信APPSECRET*/
    public static final String AppSecret =prop.getProperty("wx.AppSecret");
    
    /*百度人脸识别应用APPId*/
    public static String FACE_APPID = prop.getProperty("face.appid");
    
    /*百度人脸识别应用KEY*/
    public static String FACE_API_KEY = prop.getProperty("face.api_key");
    
    /*百度人脸识别应用Secret*/
    public static String FACE_SECRET_KEY = prop.getProperty("face.secret_key");
    
    /*百度人脸识别token 有效期一个月*/
    public static String FACE_TOKEN = prop.getProperty("face.token");
    
       
    public static String access_token = null;
    
    protected static int expireTime= 7200;//过期时间7200秒， 因为微信token过期时间为2小时，即7200秒
    
    //获取ACCESS_TOKEN url
    public static final String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
            + AppId + "&secret="+AppSecret;
        
    //网页授权Url
    public static String GET_OAUTH2_TOKEN="https://api.weixin.qq.com/sns/oauth2/access_token?appid="
			+ AppId + "&secret="+AppSecret 
			+ "&grant_type=authorization_code";
        
    //创建菜单url
    public static String GET_MEAN_CREATE="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
    
    //删除菜单url
    public static String GET_MEAN_DELETE="https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=";
       
    //百度音乐列表url
    public static String BAIDU_SEARCH = "http://tingapi.ting.baidu.com/v1/restserver/ting?format=json&method=baidu.ting.search.catalogSug&query="; 
      
    //百度播放url
    public static String BAIDU_PLAY="http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.song.play&songid=";
    
    //百度人脸识别url
    public static String BAIDU_FACE_DETECT="https://aip.baidubce.com/rest/2.0/face/v1/detect?access_token=";
    
	/**
	* 获取accessToken
	* @param appID
	微信公众号凭证
	* @param appScret
	微信公众号凭证秘钥
	* @return
	*/
	public static void getAccessToken() {
		// 访问微信服务器
		JSONObject json=new JSONObject();
		try {
			URL getUrl = new URL(GET_ACCESS_TOKEN_URL);
			HttpURLConnection http = (HttpURLConnection) getUrl
					.openConnection();
			http.setRequestMethod("GET");
			http.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);

			http.connect();
			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] b = new byte[size];
			is.read(b);

			String message = new String(b, "UTF-8");

			 json= JSONObject.fromObject(message);
			access_token=json.getString("access_token");
			log.info("【access_token获取成功】"+access_token);
		} catch (Exception e) {
			log.info("【access_token获取失败：errcode:"+json.getString("errcode")+"】"+GET_ACCESS_TOKEN_URL);
		} 
	}
	
	
	/**
	 * 网页授权凭证，获取用户token
	 * @param code
	 * @return
	 */
	public static User getOauth2AccessToken(String code){
		String url=GET_OAUTH2_TOKEN+"&code="+code;
		User user=new User();
		JSONObject jsonObject=new JSONObject();
		try {
			jsonObject=httpsRequest(url,"GET",null);
            user.setOpenId(jsonObject.getString("openid"));     
            user.setAccess_token(jsonObject.getString("access_token"));
		}catch (Exception e) {
            int errorCode = jsonObject.getInt("errcode");
            String errorMsg = jsonObject.getString("errmsg");
        	log.error("【获取网页授权凭证失败 errcode:{} errmsg:{}】"+ errorCode+","+ errorMsg);
		}
		return user;
	}
	
	/**
     * 通过网页授权获取用户信息
     * 
     * @param accessToken 网页授权接口调用凭证,不是基础中的accessToken
     * @param openId 用户标识
     * @return SNSUserInfo
     */
    public static User getUserInfo(User user) {

		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="+user.getAccess_token()+"&openid="+user.getOpenId()+"";
        System.out.println(requestUrl);
		// 通过网页授权获取用户信息
		JSONObject jsonObject =httpsRequest(requestUrl, "GET", null);

		if (null != jsonObject) {
			try {
				// 用户的标识
				user.setOpenId(jsonObject.getString("openid"));
				// 昵称
				user.setNickname(jsonObject.getString("nickname"));
				// 性别（1是男性，2是女性，0是未知）
				user.setSex(jsonObject.getString("sex"));
				// 用户所在国家
				user.setCountry(jsonObject.getString("country"));
				// 用户所在省份
				user.setProvince(jsonObject.getString("province"));
				// 用户所在城市
				user.setCity(jsonObject.getString("city"));
				// 用户头像
				user.setHeadImgUrl(jsonObject.getString("headimgurl"));
				
				// 用户特权信息
/*				user.setPrivilegeList(JSONArray.toList(
						jsonObject.getJSONArray("privilege"), List.class));*/
			} catch (Exception e) {
				user = null;
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("【用户信息获取失败 errcode:{} errmsg:{}】"+ errorCode+","+ errorMsg);
			}
		}
		return user;	
    }
    
    
	/**
     * 发送https请求
     * 
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);

            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("【WeixinAPIHelper.httpsRequest发送https请求超时】");
        } catch (Exception e) {
        	log.error("【WeixinAPIHelper.httpsRequest发送https请求异常】");
        }
        return jsonObject;
    }
    
    
    /**
     * 音乐消息回复
     * @param music MUSCI对象
     * @return
     */
    public static String returnMusicXml(Music music){		
		String respXml="";
		respXml+="<Music>";
		respXml+="<Title><![CDATA["+music.getTitle()+"]]></Title>";
		respXml+="<Description><![CDATA["+music.getDescription()+"]]></Description>";
		respXml+="<MusicUrl><![CDATA["+music.getMusicUrl()+"]]></MusicUrl>";
		respXml+="<HQMusicUrl><![CDATA["+music.getMusicUrl()+"]]></HQMusicUrl>";
		respXml+="</Music>";
		return respXml;
    }
    /**
     * 图文消息回复
     * @param count   图文个数
     * @param weather  
     * @param url
     * @return
     */
    public static String returnTuWenXml(int count,List<Map<String, String>>  weather,String url){
    	String respXml ="<ArticleCount>"+count+"</ArticleCount>";
		respXml+="<Articles>";

		for (Map<String, String> list:weather) {
    		respXml +="<item>";
    		respXml +="<Title><![CDATA["+list.get("reply")+"]]></Title>";
    		respXml +="<Description><![CDATA["+list.get("reply")+"]]></Description>";
    		respXml +="<PicUrl><![CDATA["+list.get("imageurl")+"]]></PicUrl>";
    		respXml +="<Url><![CDATA["+url+"]]></Url>";
    		respXml +="</item>";
		}
		respXml +="</Articles>";
		return respXml;
    }
}
