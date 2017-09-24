package com.bjxc.supervise.netty.server;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import com.bjxc.model.PhotoInfo;
import com.bjxc.supervise.model.LoginInfo;

import io.netty.channel.ChannelHandlerContext;

public class TCPMap {
	
	private Map<String, ChannelHandlerContext> channelMap;
	
	private Map<String, ChannelHandlerContext> webClientMap;
	private Map<String, String> ipMap;
	private Map<String, LoginInfo> loginInfoMap;
	private static TCPMap instance;  
	private TCPMap (){
			
		channelMap = new HashMap<String, ChannelHandlerContext>();
		ipMap = new HashMap<String,String>();
		loginInfoMap = new HashMap<String,LoginInfo>();
		
		webClientMap = new HashMap<String, ChannelHandlerContext>();
	}
	    
	public Map<String, ChannelHandlerContext> getWebClientMap() {
		return webClientMap;
	}

	public static synchronized TCPMap getInstance() {  
		if (instance == null) {  
	        instance = new TCPMap();  
	    }  
	    return instance;  
	}
	
	public Map<String, ChannelHandlerContext> getChannelMap() {
		System.out.println(channelMap.toString());
		return channelMap;
	}  
	
	public Map<String, String> getIpMap() {
		return ipMap;
	}
	    
	public Map<String, LoginInfo> getLoginInfoMap() {
		return loginInfoMap;
	}
	
}
