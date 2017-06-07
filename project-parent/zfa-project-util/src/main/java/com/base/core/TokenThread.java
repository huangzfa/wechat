package com.base.core;

import com.base.util.WeixinAPIHelper;

/**
 * 时时获取AccessToken的servlet
 * @author Administrator
 *
 */
public class TokenThread  extends WeixinAPIHelper implements Runnable{

	public void run() {
		while (true) {
			try {
				// 调用工具类获取access_token(每日最多获取100000次，每次获取的有效期为7200秒)
				getAccessToken();
				if (null != access_token) {
					System.out.println("accessToken获取成功："
							+ 7200);
					// 7000秒之后重新进行获取
					Thread.sleep((expireTime- 200) * 1000);
				} else {
					// 获取失败时，60秒之后尝试重新获取
					Thread.sleep(60 * 1000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}		
		
	}


}
