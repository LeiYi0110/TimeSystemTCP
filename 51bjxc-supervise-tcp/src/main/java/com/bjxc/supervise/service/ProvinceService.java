package com.bjxc.supervise.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bjxc.model.Province;
import com.bjxc.supervise.mapper.ProvinceMapper;

@Service
public class ProvinceService {
	
	@Resource
	private ProvinceMapper provinceMapper;
	
	public void add(Province province){
		provinceMapper.add(province);
	}

}
