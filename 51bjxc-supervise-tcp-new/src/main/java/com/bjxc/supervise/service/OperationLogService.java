package com.bjxc.supervise.service;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bjxc.supervise.mapper.OperationLogMapper;
import com.bjxc.supervise.model.OperationLog;


@Service
public class OperationLogService {
	
	@Resource
	private OperationLogMapper operationLogMapper;


	public void add(OperationLog operationLog) {
		operationLogMapper.add(operationLog);
	}

}
