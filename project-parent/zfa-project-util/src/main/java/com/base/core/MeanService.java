package com.base.core;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;

import com.base.modal.menu.Menu;
import com.base.modal.menu.ViewButton;
import com.base.util.UTF;
import com.base.util.WeixinAPIHelper;

import net.sf.json.JSONObject;




public class MeanService  extends WeixinAPIHelper{
	 private static Logger logger = Logger.getLogger(MeanService.class);
	/**
	 * 封装微信菜单数据
	 * @return
	 */
	public static Menu getMenu(){
		ViewButton v1 = new ViewButton();
		v1.setName("我的攻略");
		v1.setType("view");
		// 需要使用网页授权获取微信用户的信息
		String redirect_uri = UTF
				.urlEncodeUTF8("http://16h7554b37.imwork.net/wechat/index");

		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
				+ AppId
				+ "&redirect_uri="
				+ redirect_uri
				+ "&"
				+ "response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
		v1.setUrl(url);
		// 创建第一个一级菜单
		ViewButton v2 = new ViewButton();
		v2.setName("Welcome come");
		v2.setType("view");
		v2.setUrl("http://h5.wps.cn/p/d978cd3e.html");
		Menu menu = new Menu();
		menu.setButton(new ViewButton[] {v1,v2 });
		return menu;	
	}
	/**
	* 创建自定义菜单(每天限制1000次)
	* */
	public static int createMenu(Menu menu) {
		String jsonMenu = JSONObject.fromObject(menu).toString();
		System.out.println(jsonMenu);
		int status = 0;
		try {
			URL url = new URL(GET_MEAN_CREATE+access_token);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setDoOutput(true);
			http.setDoInput(true);
			http.setRequestMethod("POST");
			http.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			http.connect();
			OutputStream os = http.getOutputStream();
			os.write(jsonMenu.getBytes("UTF-8"));
			os.close();

			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] bt = new byte[size];
			is.read(bt);
			String message = new String(bt, "UTF-8");
			JSONObject jsonMsg = JSONObject.fromObject(message);
			status = Integer.parseInt(jsonMsg.getString("errcode"));
			if(status!=0){
				logger.info("[菜单创建失败]errcode："+status);
			}else{
				logger.info("[菜单创建成功]");
			}			
		} catch (Exception e) {
			logger.info("[菜单创建失败]");
		}
		return status;
	}
	/**
	 * 删除菜单
	 */
	public static void deleteMenu() {
		int status = 0;
		JSONObject jsonObject = new JSONObject();
		try {
			URL url = new URL(GET_MEAN_DELETE);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();

			http.setRequestMethod("GET");
			http.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			http.connect();
			OutputStream os = http.getOutputStream();
			os.flush();
			os.close();

			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String message = new String(jsonBytes, "UTF-8");
			jsonObject = JSONObject.fromObject(message);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		status = Integer.parseInt(jsonObject.getString("errcode"));
		if (status == 0) {
			logger.error("[菜单删除成功]");
			System.out.println("菜单删除成功");
		} else {
			logger.error("[菜单删除失败]");
		}
	}
}
