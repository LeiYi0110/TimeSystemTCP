package com.bjxc.supervise.service;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Service;

import com.bjxc.model.DevAssignInfo;
import com.bjxc.supervise.mapper.DevAssignMapper;

@Service
public class DevAssignService {
	
	@Resource 
	private DevAssignMapper devAssignMapper;
	
	public void add(com.bjxc.model.DevAssign devAssign) {
		devAssignMapper.add(devAssign);
	}

	public void remove(Integer deviceId){
		devAssignMapper.remove(deviceId);
	}
	
	public void removeByMobile(String mobile){
		devAssignMapper.removeByMobile(mobile);
	}
	
	public DevAssignInfo getDevAssignByMobile(String mobile) {
		return devAssignMapper.getDevAssignByMobile(mobile);
	}
}
