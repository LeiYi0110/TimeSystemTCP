package com.bjxc.supervise.mapper;


import org.apache.ibatis.annotations.Select;



/**
 * @author levin
 */
public interface SystemConfigMapper {
	
	@Select("select tsc.flagValue from jsystemconfig tsc where flagName=#{flagName}")
	String getSystemConfigValue(String flagName);
}
