package com.ssm.controller;

/**
 * 代码说明： 



3）第53行代码：调用CoreService类的processRequest方法接收、处理消息，并得到处理结果；

4）第54行：调用response.getWriter().write()方法将消息的处理结果返回给用户

 */
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.core.CoreService;
import com.base.core.MeanService;
import com.base.modal.User;
import com.base.util.SignUtil;
import com.base.util.WeixinAPIHelper;



@Controller
@RequestMapping("/wechat")
public class WechatControlller{
	private static final long serialVersionUID = 4440739483644821986L;  

	private final String token = "huang";
    /** 
     * 确认请求来自微信服务器 
     */
	@RequestMapping("/wechat")
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException{
        request.setCharacterEncoding("UTF-8");  
        response.setCharacterEncoding("UTF-8");  
    	System.out.println("开始签名校验");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        if (SignUtil.checkSignature(signature, timestamp, nonce,token)) { 
            response.getWriter().println(echostr); //如果检验成功输出echostr，微信服务器接收到此输出，才会确认检验完成。
        }
        
       /*   返回消息给微信服务器      */  
        // 调用核心业务类接收消息、处理消息  
      String respMessage = CoreService.processRequest(request); 
      response.getWriter().write(respMessage);         
    }
	/**
	 * 创建微信菜单
	 * @param request
	 * @param response
	 */
	@RequestMapping("/createMean")
	public void createMean(HttpServletRequest request, HttpServletResponse response){
		MeanService.createMenu(MeanService.getMenu());
	}
	/**
	 * 网页授权
	 * @param request
	 * @param code
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
    @RequestMapping("/index")
    public User  index(ModelMap map,HttpServletResponse response,HttpServletRequest request) throws IOException{
		
    	String code=request.getParameter("code");
    	User user=new User();
		if (!"authdeny".equals(code)) {
			user = WeixinAPIHelper.getOauth2AccessToken(code);
			if(user!=null){
				user=CoreService.getUserInfo(user);
			}		
		}
       return user;  	
    }	

}
