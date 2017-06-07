package com.base.core;  

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.base.modal.message.resp.Music;
import com.base.modal.message.resp.TextMessage;
import com.base.util.MessageUtil;
import com.base.util.UTF;
import com.base.util.WeixinAPIHelper;



/** 
 *
1）第44行：调用消息工具类MessageUtil解析微信发来的xml格式的消息，解析的结果放在HashMap里；

2）49~56行：从HashMap中取出消息中的字段；

3）58~63行：组装要返回的文本消息对象；

4) 74~86行：组装要返回的音乐消息xml

5) 91~105行：组装了要返回图文消息xml

6）59~94行：演示了如何接收微信发送的各类型的消息，根据MsgType判断属于哪种类型的消息；

5）97行：调用消息工具类MessageUtil将要返回的文本消息对象TextMessage转化成xml格式的字符串；
 *  
 * @author zhongfa 
 * @date 2017-04-19  
 */  

public class CoreService extends WeixinAPIHelper{
    /** 
     * 处理微信发来的请求 
     *  
     * @param request 
     * @return 
     */  
    public static String processRequest(HttpServletRequest request) {  
        String respXml  = "<xml>";  
        try {  
            // 默认返回的文本消息内容  
           String respContent = "请求处理异常，请稍候尝试！";  
  
            // xml请求解析  
            Map<String, String> requestMap = MessageUtil.parseXml(request);  
            if(requestMap==null){
            	return null;
            } 
            // 发送方帐号（open_id）  
            String fromUserName = requestMap.get("FromUserName");  
            // 公众帐号  
            String toUserName = requestMap.get("ToUserName");  
            // 消息类型  
            String msgType = requestMap.get("MsgType");  
            //发送消息内容
            String content=requestMap.get("Content");
            // 回复文本消息  
            TextMessage textMessage = new TextMessage();  
            textMessage.setToUserName(fromUserName);  
            respXml +="<ToUserName><![CDATA["+fromUserName+"]]></ToUserName>";
            textMessage.setFromUserName(toUserName);  
            respXml +="<FromUserName><![CDATA["+toUserName+"]]></FromUserName>";
            textMessage.setCreateTime(new Date().getTime());  
            respXml +="<CreateTime>"+textMessage.getCreateTime()+"</CreateTime>";
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
            
            textMessage.setFuncFlag(0);  
  
            // 文本消息  
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {  
            	/*此处非常坑爹，网上的都是错误的*/
            	//String href="<a href=\"http://blog.csdn.net/lyq8479\">点击这里会有你想要的内容呦</a>[愉快]";
            	
            	/*音乐关键字回复*/
            	if(content.contains("音乐")&&content.contains("#")){
            		content=content.substring(content.lastIndexOf("#")+1,content.length());
            		Music music= BaiduMusicApi.getMusicLink(content);
            		if(music.getTitle()==null){            			          			
            		    respXml+="<Content><![CDATA[未搜索到歌曲]]></Content>";
            		}else{
            			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_MUSIC);
            			respXml+=returnMusicXml(music);
            		}

            	}
            	/*天气预报关键字回复*/
            	else if(content.contains("天气")&&content.contains("#")){
            		content=content.substring(content.lastIndexOf("#")+1,content.length());
            		List<Map<String,String>> weather=WeatherApi.getWeather(content);
            		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
            		
            		/*本项目首页url*/
            		String redirect_uri = UTF
            				.urlEncodeUTF8("http://16h7554b37.imwork.net/wechat/index");
            		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
            				+ AppId
            				+ "&redirect_uri="
            				+ redirect_uri
            				+ "&"
            				+ "response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
            		respXml+=returnTuWenXml(weather.size(),weather,url);
            	}else{                	
                	if(content.equals("1")){
                		respContent="我们是全球唯一一家境外代购服务商，需求量大[愉快]";
                	}else if(content.equals("2")){
                		respContent="我们已经和支付宝,微信等第三方签订协议,交易安全的呢[愉快]";
                	}else if(content.equals("3")){
                		respContent="如果您的芝麻信誉达到600以上,我们是全额赔偿呦[愉快]";
                	}else if(content.equals("4")){
                		respContent="请点击菜单我的攻略开始吧[愉快]";
                	}else if(content.equals("5")){
                		respContent="不好意，你撩到一个汉子[偷笑]";
                	}else{
                		respContent=UTF.getMainMenu();
                	}
        		    respXml+="<Content><![CDATA["+respContent+"]]></Content>";
            	}
            	
            }  
            // 图片消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {  
                // 取得图片地址  
                String picUrl = requestMap.get("PicUrl");  
                // 人脸检测  
                respContent = FaceApi.detect(picUrl); 
                respXml+="<Content><![CDATA["+respContent+"]]></Content>";
            }  
            // 地理位置消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {  
                respContent = "您发送的是地理位置消息！";  
            }  
            // 链接消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {  
                respContent = "您发送的是链接消息！";  
            }  
            // 音频消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {  
                respContent = "您发送的是音频消息！";  
            }  
            // 事件推送  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {  
                // 事件类型  
                String eventType = requestMap.get("Event");  
                // 订阅  
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {  
        		    respXml+="<Content><![CDATA["+UTF.getMainMenu()+"]]></Content>";
                }  
                // 取消订阅  
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {  
                    // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息  
                }  
                // 自定义菜单点击事件  
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {  
                    // TODO 自定义菜单权没有开放，暂不处理该类消息  
                }  
            }
            respXml +="<MsgType><![CDATA["+textMessage.getMsgType()+"]]></MsgType>";
            respXml +="</xml>";
        } catch (Exception e) {  
           e.printStackTrace();
        }   
        return respXml ;  
    }  

}
