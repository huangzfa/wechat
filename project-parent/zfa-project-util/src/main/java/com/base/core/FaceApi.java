package com.base.core;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import sun.misc.BASE64Decoder;  
import sun.misc.BASE64Encoder;  



import com.base.modal.Face;
import com.base.util.HttpUtils;
import com.base.util.WeixinAPIHelper;

/**
 * 人脸检测服务
 * @author 15935
 *
 */
public class FaceApi extends WeixinAPIHelper{

	public static void main(String[] args) throws Exception {
/*	String access_token_url = "https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials" +
			"&client_id="+API_KEY
		   +"&client_secret="+ SERCET_KEY;*/
		//初始化一个FaceClient	
	/****************/	

	
   }
  public static String 	baidubceFace(String imageUrl) throws Exception{
	    String resutl="erro";
	    String str = encodeImgageToBase64(imageUrl);  //读取输入流,转换为Base64字符 
	    String url = BAIDU_FACE_DETECT+FACE_TOKEN;
		Map<String, String> headers = new HashMap<String, String>();
		Map<String, String> bodys = new HashMap<String, String>();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		bodys.put("image", str);
		bodys.put("max_face_num","10");
		bodys.put("face_fields", "age,beauty,expression,gender,glasses,race,qualities");
		try {
			HttpResponse response = HttpUtils.doPostBD(url,headers,bodys);
			
			resutl=EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resutl;
  }
  public static String detect(String imageUrl) throws Exception{
	  
	  String result=baidubceFace(imageUrl);
	  if(result.equals("erro")){
		  return "系统内部出错";
	  }
	  JSONObject json = JSONObject.fromObject(result);
	  if(json.get("result_num")!=null&&json.get("result_num").equals(0)){
		  return "没有检测到人脸";
	  }
	  System.out.println(result);
	  JSONArray jsonArray = json.getJSONArray("result");
	  List<Face> faceList = new ArrayList<Face>();
      // 遍历检测到的人脸  
      for (int i = 0; i < jsonArray.size(); i++) { 
    	  Face face = new Face();
    	  JSONObject faceObject = (JSONObject) jsonArray.get(i);
    	  face.setType(faceObject.getJSONObject("qualities").get("type").toString());
    	  face.setBeauty(faceObject.getDouble("beauty"));
    	  face.setFace_probability(faceObject.getDouble("face_probability"));
    	  face.setGlasses(faceObject.getInt("glasses"));
    	  face.setAge(faceObject.getDouble("age"));
    	  face.setRace(faceObject.get("race").toString());
    	  face.setExpression(faceObject.getInt("expression"));
    	  face.setGender(faceObject.get("gender").toString());
    	  faceList.add(face);
      }
	  return makeMessage(faceList);
  } 
  /** 
   * 根据人脸识别结果组装消息 
   *  
   * @param faceList 人脸列表 
   * @return 
   */  
  private static String makeMessage(List<Face> faceList) {
	  StringBuffer buffer = new StringBuffer();
	 // 检测到1张脸 
      // 检测到1张脸  
      if (1 == faceList.size()) {  
          buffer.append("共检测到 ").append(faceList.size()).append(" 张人脸").append("\n");  
          for (Face face : faceList) {  
              buffer.append(getRace(face.getRace())).append("人种,");
              buffer.append(getSex(face.getGender())+",");
              buffer.append(getType(face.getType())+",");
              buffer.append(expression(face.getExpression())+"\n");
              buffer.append((int)Math.floor(face.getAge())).append("岁左右,"+glasses(face.getGlasses())).append("\n");
              buffer.append("颜值"+(int)Math.floor(face.getBeauty())+"分\n");
              buffer.append(isPersonFace(face.getType()));  
          }  
      }
      // 检测到2-10张脸  
      else if (faceList.size() > 1 && faceList.size() <= 10) {  
          buffer.append("共检测到 ").append(faceList.size()).append(" 张人脸，按脸部中心位置从左至右依次为：").append("\n");  
          for (Face face : faceList) {  
              buffer.append(getRace(face.getRace())).append("人种,");
              buffer.append(getSex(face.getGender())+",");
              buffer.append(getType(face.getType())+",");
              buffer.append(expression(face.getExpression())+"\n");
              buffer.append((int)Math.floor(face.getAge())).append("岁左右,"+glasses(face.getGlasses())).append("\n");
              buffer.append("颜值"+(int)Math.floor(face.getBeauty())+"分\n");
              buffer.append(isPersonFace(face.getType())+"\n");  
          }  
      } 
      return buffer.toString();
  }
  public static String getSex(String gender){

	  if(gender.equals("male")){
		  return "男";
	  }else{
		  return "女";
	  }
  }
  /**
   * 脸型square/triangle/oval/heart/round检测
   * @param type
   * @return
   */
  public static String getType(String type){
	  if(type.equals("square")){
		  return "方脸";
	  }else if(type.equals("triangle")){
		  return "瓜子脸";
	  }else if(type.equals("oval")){
		  return "椭圆脸";
	  }else if(type.equals("heart")){
		  return "心形脸";
	  }else{
		  return "圆脸";
	  }
  }
  /**
   * 是否戴眼镜检测
   * @param glasses
   * @return
   */
  public static String glasses(Integer glasses){
	  
	  if(glasses==1){
		  return "普通眼镜";
	  }else if(glasses==2){
		  return "墨镜";
	  }else{
		  return "";
	  }
  }
  /**
   * 微表情检测
   * @param expression
   * @return
   */
  public static String  expression(Integer expression){
	  if(expression==0){
		  return "不笑";
	  }else if(expression==1){
		  return "微笑";
	  }else{
		  return "大笑";
	  }
  }
  /**
   * 肤色鉴别
   * @param race
   * @return
   */
  public static String getRace(String race){
	  if(race.equals("yellow")){
		  return "黄色";
	  }else if(race.equals("white")){
		  return "白色";
		  
	  }else if(race.equals("black")){
		  return "黑色";
		  
	  }else if(race.equals("arab")){
		  return "阿拉伯";
		  
	  }else{
		  return "未知";
	  }
	 
  }
  public static String isPersonFace(String type){
	  JSONObject json = JSONObject.fromObject(type);
	  //真实人脸值
	  double human=Double.parseDouble(json.get("human").toString());
	  //卡通人脸值
	  double cartoon=Double.parseDouble(json.get("cartoon").toString());
	  if(human>=cartoon){
		  return "真实人脸,置信度较高";
	  }else{
		  return "卡通人脸,置信度较高";
	  }
  }

	/**
	 * 将网络图片进行Base64位编码
	 * 
	 * @param imgUrl
	 *			图片的url路径，如http://.....xx.jpg
	 * @return
	 * @throws MalformedURLException 
	 */
	public static String encodeImgageToBase64(String imageUrl) throws MalformedURLException {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		 URL url = new URL(imageUrl);  
		ByteArrayOutputStream outputStream = null;
		try {
			BufferedImage bufferedImage = ImageIO.read(url);
			outputStream = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "jpg", outputStream);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(outputStream.toByteArray());// 返回Base64编码过的字节数组字符串
	}
	/**
	 * 将本地图片进行Base64位编码
	 * 
	 * @param imgUrl
	 *			图片的url路径，如http://.....xx.jpg
	 * @return
	 */
	public static String encodeLocationImgageToBase64(String  imageUrl) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		File file=new File(imageUrl);
		ByteArrayOutputStream outputStream = null;
		try {
			BufferedImage bufferedImage = ImageIO.read(file);
			outputStream = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "jpg", outputStream);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(outputStream.toByteArray());// 返回Base64编码过的字节数组字符串
	}    
}
