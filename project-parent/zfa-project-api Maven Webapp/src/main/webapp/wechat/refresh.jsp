<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>欢迎来到OVU</title>
    
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet"  href="/jquery.mobile-1.4.5/jquery.mobile-1.4.5.css" />
 	<script src="/jquery.mobile-1.4.5/jquery.js"></script>
	<script src="/jquery.mobile-1.4.5/jquery.mobile-1.4.5.js"></script>

    <script type="javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
  </head> 
  <body>
    <div data-role="page"  id="page_one">
    <div data-role="header" data-position="fixed">
        <h4>欢迎来到OVU</h4>
    </div>
    <div data-role="content" class='content'>
    			<div>
			       <img src="${user.headImgUrl}" style="width:100%"/>
			    </div>
			    <div style="margin-left:35%">
			       <h4>欢迎你，${user.nickname}</h4>
			    </div>
			     <a href="#" class="ui-btn ui-corner-all">同意授权登录</a>
		</div>

</div>
	<input type="text" name=""openid"" id="openid" value="${user.nickname}">

  </body>
</html>
