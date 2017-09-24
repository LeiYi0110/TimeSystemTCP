package com.bjxc.supervise.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bjxc.model.DrivingField;
import com.bjxc.supervise.mapper.DrivingFieldMapper;

@Service
public class DrivingFieldService {

	@Resource
	private DrivingFieldMapper drivingFieldMapper;
	
	public DrivingField getDrivingFieldById(Integer id){
		return drivingFieldMapper.get(id);
	}
}
