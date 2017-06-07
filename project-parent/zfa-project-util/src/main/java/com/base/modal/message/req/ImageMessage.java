package com.base.modal.message.req;
/** 
 * 图片消息 
 *  
 * @author zhongfa
 * @date 2017-4-16 
 */  

public class ImageMessage extends BaseMessage{
    // 图片链接  
    private String PicUrl;

	public String getPicUrl() {
		return PicUrl;
	}
	
	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	} 

}
