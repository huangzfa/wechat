package com.base.modal.message.resp; 
/** 
 * 文本消息 
 *  
 * @author zhongfa 
 * @date 2017-04-19   
 */  

public class TextMessage  extends BaseMessage {
	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	// 回复的消息内容  
	private String Content;  

}
