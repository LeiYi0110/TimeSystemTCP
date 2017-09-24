package com.bjxc.supervise.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bjxc.model.Ins;
import com.bjxc.supervise.mapper.InsMapper;

@Service
public class InsService {
	
	@Resource
	private InsMapper insMapper;
	
	public Ins getInsById(Integer insId){
		return insMapper.getInsById(insId);
	}

}
