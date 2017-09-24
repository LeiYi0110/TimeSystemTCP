package com.bjxc.supervise.service;


import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bjxc.supervise.mapper.SystemConfigMapper;


/**
 * 
 * @author levin
 *
 */

@Service
public class SystemConfigService{

    private static final Logger logger = LoggerFactory.getLogger(SystemConfigService.class);

    //yyyyMMddHHmmss
    public final static String TrainningStartTime="TrainningStartTime";
    public final static String TrainningEndTime = "TrainningEndTime";
	
	@Resource
	private SystemConfigMapper systemConfigMapper;
	
	/**
	 * 获取培训开始时间
	 * @return yyyyMMddHHmmss
	 */
	public String getTrainningStartTime(){
		return systemConfigMapper.getSystemConfigValue(TrainningStartTime);
	}
	
	/**
	 * 获取培训结束时间
	 * @return yyyyMMddHHmmss
	 */
	public String getTrainningEndTime(){
		return systemConfigMapper.getSystemConfigValue(TrainningEndTime);
	}
	
}
