package com.bjxc.supervise.mapper;


import org.apache.ibatis.annotations.Insert;

import com.bjxc.supervise.model.OperationLog;


public interface OperationLogMapper {

	@Insert("insert into t_log_tcp(log_time,log_event,log_user_name,ins_id,ins_code,log_desc) values(#{logTime},#{logEvent},#{logUser},#{insId},#{insCode},#{remark})")
	void add(OperationLog operationLog);

}
