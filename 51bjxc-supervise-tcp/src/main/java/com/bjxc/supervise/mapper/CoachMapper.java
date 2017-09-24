package com.bjxc.supervise.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bjxc.model.Coach;

public interface CoachMapper {
	@Select("select * from tcoach where id=#{id}")
	Coach getCoachById(@Param("id")Integer id);
	
	@Select("select * from tcoach where coachnum=#{coachnum}")
	Coach getCoachByNum(@Param("coachnum")String coachnum);
	
	//修改教练登录状态为已登录并修改状态更改时间
	@Update("update tcoach set isTCPLogin=1,TCPStatusChangeTime=#{date} where coachnum=#{coachnum}")
	void updateTCPLogin(@Param("coachnum")String coachnum,@Param("date")Date date);
	
	//修改教练登录状态为未登录并修改状态更改时间
	@Update("update tcoach set isTCPLogin=0,TCPStatusChangeTime=#{date} where coachnum=#{coachnum}")
	void updateTCPLogout(@Param("coachnum")String coachnum,@Param("date")Date date);
}
