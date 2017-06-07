package com.ssm.controller;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.base.core.TokenThread;
/**
 * 定时获取access_token类
 * @author 15935
 *
 */
@Component("beanDefineConfigue")
public class AccessTokenController implements
ApplicationListener<ContextRefreshedEvent>{


	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		new Thread(new TokenThread()).start();//间隔7200秒执行 一次
		
	}

}