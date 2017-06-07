package com.base.util;

import java.io.UnsupportedEncodingException;

public class UTF {
	/** 
	 * 计算采用utf-8编码方式时字符串所占字节数 
	 *  
	 * @param content 
	 * @return 
	 */  
	public static int getByteSize(String content) {  
	    int size = 0;  
	    if (null != content) {  
	        try {  
	            // 汉字采用utf-8编码时占3个字节  
	            size = content.getBytes("utf-8").length;  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    }  
	    return size;  
	}  
    /**
     * URL编码（utf-8）
     * 
     * @param source
     * @return
     */
    public static String urlEncodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    } 
	/**
	 * xiaoqrobot的主菜单
	 * 
	 * @return
	 */
	public static String getMainMenu() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("您好，我是小q，请回复数字选择服务：").append("\n\n");
		buffer.append("1  为什么要选择我们").append("\n");
		buffer.append("2  交易有保障吗").append("\n");
		buffer.append("3  资金安全吗").append("\n");
		buffer.append("4  怎么赚钱"+emoji(0x1F4B0)+"").append("\n");
		buffer.append("5  美女人工客服"+emoji(0x1F469)+"").append("\n");
		buffer.append("回复 \"音乐#歌曲名\"即可收听");
		buffer.append("回复 \"天气#地区名\"即可收看");
		buffer.append("终极功能,上传图片即可人脸识别");
		return buffer.toString();
	}
    /** 
     * emoji表情转换(hex -> utf-16) 
     *  
     * @param hexEmoji 
     * @return 
     */  
    public static String emoji(int hexEmoji) {  
        return String.valueOf(Character.toChars(hexEmoji));  
    }  	
}
