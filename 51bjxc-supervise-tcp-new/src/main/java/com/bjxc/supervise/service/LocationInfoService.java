package com.bjxc.supervise.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bjxc.model.LocationInfo;
import com.bjxc.supervise.mapper.LocationInfoMapper;

@Service
public class LocationInfoService {
	
	@Resource
	private LocationInfoMapper locationInfoMapper;
	
	public LocationInfo add(LocationInfo locationInfo){
		locationInfoMapper.add(locationInfo);
		return locationInfo;
	}
	
	public LocationInfo getLocationInfoById(Integer id){
		return locationInfoMapper.getLocationInfoById(id);
	}

}
