package com.base.modal.message.req;
/** 
 * 音频消息 
 *  
 * @author zhongfa
 * @date 2017-04-16
 */  

public class VoiceMessage extends BaseMessage{
	 // 媒体ID  
   private String MediaId;  
   public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	public String getFormat() {
		return Format;
	}
	public void setFormat(String format) {
		Format = format;
	}
   // 语音格式  
   private String Format;  

}
