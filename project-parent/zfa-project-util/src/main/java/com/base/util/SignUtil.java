package com.base.util;

import java.util.ArrayList;
import java.util.Arrays;
/**
 * 微信签名校验
 * @author 15935
 *
 */
public class SignUtil {
	public static boolean checkSignature(String signature, String timestamp, String nonce,String token){
        ArrayList<String> array = new ArrayList<String>();
        array.add(signature);
        array.add(timestamp);
        array.add(nonce);    
        //排序
        String sortString = sort(token, timestamp, nonce);
        //加密
        String mytoken = Decript.SHA1(sortString);
        //校验签名
        if (mytoken != null && mytoken != "" && mytoken.equals(signature)) {
            System.out.println("签名校验通过。");
            return true;
        } else {
            System.out.println("签名校验失败。");
            return false;
        }  
	}
    /**
     * 排序方法
     * @param token
     * @param timestamp
     * @param nonce
     * @return
     */
    public static String sort(String token, String timestamp, String nonce) {
        String[] strArray = { token, timestamp, nonce };
        Arrays.sort(strArray);
     
        StringBuilder sbuilder = new StringBuilder();
        for (String str : strArray) {
            sbuilder.append(str);
        }
     
        return sbuilder.toString();
    }
}
