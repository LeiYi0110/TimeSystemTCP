package com.bjxc.supervise.netty.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplicationContext {
	
	private static SpringApplicationContext instance;
	private ApplicationContext context;
	private SpringApplicationContext()
	{
		context = new ClassPathXmlApplicationContext("classpath:spring/app*.xml");
	}
	
	public static synchronized SpringApplicationContext getInstance() {  
		if (instance == null) {  
	        instance = new SpringApplicationContext();  
	    }  
	    return instance;  
	}

	public ApplicationContext getContext() {
		return context;
	}
	
	
	

}
