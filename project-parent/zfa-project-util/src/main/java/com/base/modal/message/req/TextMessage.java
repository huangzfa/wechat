package com.base.modal.message.req;
/** 
 * 文本消息 
 *  
 * @author zhongfa
 * @date 2017-4-16 
 */ 

public class TextMessage extends BaseMessage{
	 // 消息内容  
	 private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	} 

}
