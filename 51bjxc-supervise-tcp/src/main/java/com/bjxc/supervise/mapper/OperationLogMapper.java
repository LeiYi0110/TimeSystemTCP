package com.bjxc.supervise.mapper;


import org.apache.ibatis.annotations.Insert;

import com.bjxc.supervise.model.OperationLog;


public interface OperationLogMapper {


	@Insert("insert into toperationlog(logTime,logEvent,logUser,insId,remark) values(#{logTime},#{logEvent},#{logUser},#{insId},#{remark})")
	void add(OperationLog operationLog);

}
